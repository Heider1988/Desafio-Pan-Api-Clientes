package com.api.desafiopanapiclentes.application.service;

import com.api.desafiopanapiclentes.application.port.out.IbgeClient;
import com.api.desafiopanapiclentes.domain.model.Municipio;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MunicipioServiceImplTest {

    @Mock
    private IbgeClient ibgeClient;

    @InjectMocks
    private MunicipioServiceImpl municipioService;

    private List<Municipio> municipios;
    private final Long ESTADO_ID = 35L;

    @BeforeEach
    void setUp() {
        municipios = new ArrayList<>();
        municipios.add(Municipio.builder().id(3550308L).nome("São Paulo").build());
        municipios.add(Municipio.builder().id(3548500L).nome("Santos").build());
        municipios.add(Municipio.builder().id(3509502L).nome("Campinas").build());
        municipios.add(Municipio.builder().id(3543402L).nome("Ribeirão Preto").build());
        municipios.add(Municipio.builder().id(3529401L).nome("Marília").build());
    }

    @Test
    void listarMunicipiosPorEstado_QuandoExistemMunicipios_DeveRetornarListaOrdenada() {
        when(ibgeClient.buscarMunicipiosPorEstado(ESTADO_ID)).thenReturn(municipios);

        ApiResponseWrapper<List<Municipio>> response = municipioService.listarMunicipiosPorEstado(ESTADO_ID);

        assertNotNull(response);
        assertNotNull(response.getDetail());
        assertNotNull(response.getDetail().getData());
        assertTrue(response.getErros().isEmpty());

        List<Municipio> resultado = response.getDetail().getData();
        assertEquals(5, resultado.size());

        for (int i = 0; i < resultado.size() - 1; i++) {
            assertTrue(resultado.get(i).getNome().compareTo(resultado.get(i + 1).getNome()) <= 0);
        }

        verify(ibgeClient, times(1)).buscarMunicipiosPorEstado(ESTADO_ID);
    }

    @Test
    void listarMunicipiosPorEstado_QuandoListaVazia_DeveRetornarErro() {
        when(ibgeClient.buscarMunicipiosPorEstado(ESTADO_ID)).thenReturn(new ArrayList<>());

        ApiResponseWrapper<List<Municipio>> response = municipioService.listarMunicipiosPorEstado(ESTADO_ID);

        assertNotNull(response);
        assertFalse(response.getErros().isEmpty());
        assertTrue(response.getErros().get(0).contains("Municípios"));
        assertTrue(response.getErros().get(0).contains(ESTADO_ID.toString()));
        assertNull(response.getDetail().getData());

        verify(ibgeClient, times(1)).buscarMunicipiosPorEstado(ESTADO_ID);
    }

    @Test
    void listarMunicipiosPorEstado_QuandoEstadoIdInvalido_DeveRetornarErro() {
        Long estadoIdInvalido = 999L;
        when(ibgeClient.buscarMunicipiosPorEstado(estadoIdInvalido)).thenReturn(new ArrayList<>());

        ApiResponseWrapper<List<Municipio>> response = municipioService.listarMunicipiosPorEstado(estadoIdInvalido);

        assertNotNull(response);
        assertFalse(response.getErros().isEmpty());
        assertTrue(response.getErros().get(0).contains("Municípios"));
        assertTrue(response.getErros().get(0).contains(estadoIdInvalido.toString()));
        assertNull(response.getDetail().getData());

        verify(ibgeClient, times(1)).buscarMunicipiosPorEstado(estadoIdInvalido);
    }
}

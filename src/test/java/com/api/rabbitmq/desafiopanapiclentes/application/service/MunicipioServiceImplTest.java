package com.api.rabbitmq.desafiopanapiclentes.application.service;

import com.api.rabbitmq.desafiopanapiclentes.application.port.out.IbgeClient;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Municipio;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
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

        List<Municipio> resultado = municipioService.listarMunicipiosPorEstado(ESTADO_ID);

        assertNotNull(resultado);
        assertEquals(5, resultado.size());

        for (int i = 0; i < resultado.size() - 1; i++) {
            assertTrue(resultado.get(i).getNome().compareTo(resultado.get(i + 1).getNome()) <= 0);
        }

        verify(ibgeClient, times(1)).buscarMunicipiosPorEstado(ESTADO_ID);
    }

    @Test
    void listarMunicipiosPorEstado_QuandoListaVazia_DeveLancarExcecao() {

        when(ibgeClient.buscarMunicipiosPorEstado(ESTADO_ID)).thenReturn(new ArrayList<>());

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> municipioService.listarMunicipiosPorEstado(ESTADO_ID)
        );

        assertTrue(exception.getMessage().contains("Municípios"));
        assertTrue(exception.getMessage().contains(ESTADO_ID.toString()));

        verify(ibgeClient, times(1)).buscarMunicipiosPorEstado(ESTADO_ID);
    }

    @Test
    void listarMunicipiosPorEstado_QuandoEstadoIdInvalido_DeveLancarExcecao() {
        Long estadoIdInvalido = 999L;
        when(ibgeClient.buscarMunicipiosPorEstado(estadoIdInvalido)).thenReturn(new ArrayList<>());

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> municipioService.listarMunicipiosPorEstado(estadoIdInvalido)
        );

        assertTrue(exception.getMessage().contains("Municípios"));
        assertTrue(exception.getMessage().contains(estadoIdInvalido.toString()));

        verify(ibgeClient, times(1)).buscarMunicipiosPorEstado(estadoIdInvalido);
    }
}
package com.api.desafiopanapiclentes.application.service;

import com.api.desafiopanapiclentes.application.port.out.IbgeClient;
import com.api.desafiopanapiclentes.domain.model.Estado;
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
class EstadoServiceImplTest {

    @Mock
    private IbgeClient ibgeClient;

    @InjectMocks
    private EstadoServiceImpl estadoService;

    private List<Estado> estados;

    @BeforeEach
    void setUp() {
        estados = new ArrayList<>();
        estados.add(Estado.builder().id(35L).sigla("SP").nome("São Paulo").build());
        estados.add(Estado.builder().id(33L).sigla("RJ").nome("Rio de Janeiro").build());
        estados.add(Estado.builder().id(31L).sigla("MG").nome("Minas Gerais").build());
        estados.add(Estado.builder().id(43L).sigla("RS").nome("Rio Grande do Sul").build());
        estados.add(Estado.builder().id(50L).sigla("MS").nome("Mato Grosso do Sul").build());
    }

    @Test
    void listarEstados_QuandoExistemEstados_DeveRetornarListaOrdenada() {
        when(ibgeClient.buscarEstados()).thenReturn(estados);

        ApiResponseWrapper<List<Estado>> response = estadoService.listarEstados();
        List<Estado> resultado = response.getDetail().getData();

        assertNotNull(response);
        assertNotNull(resultado);
        assertEquals(5, resultado.size());

        assertEquals("SP", resultado.get(0).getSigla());
        assertEquals("RJ", resultado.get(1).getSigla());

        assertTrue(resultado.get(2).getNome().compareTo(resultado.get(3).getNome()) <= 0);
        assertTrue(resultado.get(3).getNome().compareTo(resultado.get(4).getNome()) <= 0);

        verify(ibgeClient, times(1)).buscarEstados();
    }

    @Test
    void listarEstados_QuandoSPNaoExiste_DeveManterOrdenacaoCorreta() {

        estados.removeIf(estado -> "SP".equals(estado.getSigla()));
        when(ibgeClient.buscarEstados()).thenReturn(estados);

        ApiResponseWrapper<List<Estado>> response = estadoService.listarEstados();
        List<Estado> resultado = response.getDetail().getData();

        assertNotNull(response);
        assertNotNull(resultado);
        assertEquals(4, resultado.size());

        assertEquals("RJ", resultado.get(0).getSigla());

        assertTrue(resultado.get(1).getNome().compareTo(resultado.get(2).getNome()) <= 0);
        assertTrue(resultado.get(2).getNome().compareTo(resultado.get(3).getNome()) <= 0);

        verify(ibgeClient, times(1)).buscarEstados();
    }

    @Test
    void listarEstados_QuandoRJNaoExiste_DeveManterOrdenacaoCorreta() {
        estados.removeIf(estado -> "RJ".equals(estado.getSigla()));
        when(ibgeClient.buscarEstados()).thenReturn(estados);

        ApiResponseWrapper<List<Estado>> response = estadoService.listarEstados();
        List<Estado> resultado = response.getDetail().getData();

        assertNotNull(response);
        assertNotNull(resultado);
        assertEquals(4, resultado.size());

        assertEquals("SP", resultado.get(0).getSigla());

        assertTrue(resultado.get(1).getNome().compareTo(resultado.get(2).getNome()) <= 0);
        assertTrue(resultado.get(2).getNome().compareTo(resultado.get(3).getNome()) <= 0);

        verify(ibgeClient, times(1)).buscarEstados();
    }

    @Test
    void listarEstados_QuandoListaVazia_DeveRetornarListaVazia() {

        when(ibgeClient.buscarEstados()).thenReturn(new ArrayList<>());

        ApiResponseWrapper<List<Estado>> response = estadoService.listarEstados();
        List<Estado> resultado = response.getDetail().getData();

        assertNotNull(response);
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(ibgeClient, times(1)).buscarEstados();
    }
}

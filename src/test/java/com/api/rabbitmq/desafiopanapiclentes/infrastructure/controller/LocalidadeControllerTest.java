package com.api.rabbitmq.desafiopanapiclentes.infrastructure.controller;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.EstadoService;
import com.api.rabbitmq.desafiopanapiclentes.application.port.in.MunicipioService;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Estado;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Municipio;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.GlobalExceptionHandler;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LocalidadeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EstadoService estadoService;

    @Mock
    private MunicipioService municipioService;

    @InjectMocks
    private LocalidadeController localidadeController;

    private List<Estado> estados;
    private List<Municipio> municipios;
    private final Long ESTADO_ID = 35L; // ID do estado de São Paulo
    private final Long ESTADO_ID_INVALIDO = 999L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(localidadeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        estados = Arrays.asList(
                Estado.builder().id(35L).sigla("SP").nome("São Paulo").build(),
                Estado.builder().id(33L).sigla("RJ").nome("Rio de Janeiro").build(),
                Estado.builder().id(31L).sigla("MG").nome("Minas Gerais").build()
        );

        municipios = Arrays.asList(
                Municipio.builder().id(3550308L).nome("São Paulo").build(),
                Municipio.builder().id(3548500L).nome("Santos").build(),
                Municipio.builder().id(3509502L).nome("Campinas").build()
        );
    }

    @Test
    void listarEstados_QuandoExistemEstados_DeveRetornarListaDeEstados() throws Exception {
        when(estadoService.listarEstados()).thenReturn(estados);

        mockMvc.perform(get("/api/localidades/estados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].sigla").value("SP"))
                .andExpect(jsonPath("$[1].sigla").value("RJ"))
                .andExpect(jsonPath("$[2].sigla").value("MG"));
    }

    @Test
    void listarEstados_QuandoNaoExistemEstados_DeveRetornarListaVazia() throws Exception {
        when(estadoService.listarEstados()).thenReturn(Arrays.asList());

        // Act & Assert - Realizar a requisição e verificar o resultado
        mockMvc.perform(get("/api/localidades/estados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void listarMunicipiosPorEstado_QuandoExistemMunicipios_DeveRetornarListaDeMunicipios() throws Exception {
        when(municipioService.listarMunicipiosPorEstado(ESTADO_ID)).thenReturn(municipios);

        mockMvc.perform(get("/api/localidades/estados/{estadoId}/municipios", ESTADO_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].nome").value("São Paulo"))
                .andExpect(jsonPath("$[1].nome").value("Santos"))
                .andExpect(jsonPath("$[2].nome").value("Campinas"));
    }

    @Test
    void listarMunicipiosPorEstado_QuandoEstadoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(municipioService.listarMunicipiosPorEstado(ESTADO_ID_INVALIDO))
                .thenThrow(new ResourceNotFoundException("Municípios", "Estado ID", ESTADO_ID_INVALIDO));

        mockMvc.perform(get("/api/localidades/estados/{estadoId}/municipios", ESTADO_ID_INVALIDO))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Municípios não encontrado com Estado ID: '" + ESTADO_ID_INVALIDO + "'"));
    }
}
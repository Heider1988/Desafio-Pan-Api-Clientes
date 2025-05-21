package com.api.rabbitmq.desafiopanapiclentes.infrastructure.controller;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.EnderecoService;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.GlobalExceptionHandler;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EnderecoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    private EnderecoDTO enderecoDTO;
    private final String CEP_VALIDO = "01001000";
    private final String CEP_INVALIDO = "123";
    private final String CEP_NAO_ENCONTRADO = "99999999";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(enderecoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        enderecoDTO = EnderecoDTO.builder()
                .cep(CEP_VALIDO)
                .logradouro("Praça da Sé")
                .numero("100")
                .complemento("Lado ímpar")
                .bairro("Sé")
                .cidade("São Paulo")
                .estado("SP")
                .build();
    }

    @Test
    void consultarEnderecoPorCep_QuandoCepValido_DeveRetornarEndereco() throws Exception {
        when(enderecoService.buscarEnderecoPorCep(CEP_VALIDO)).thenReturn(enderecoDTO);

        mockMvc.perform(get("/api/enderecos/cep/{cep}", CEP_VALIDO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value(CEP_VALIDO))
                .andExpect(jsonPath("$.logradouro").value("Praça da Sé"))
                .andExpect(jsonPath("$.bairro").value("Sé"))
                .andExpect(jsonPath("$.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.estado").value("SP"));
    }

    @Test
    void consultarEnderecoPorCep_QuandoCepInvalido_DeveRetornarBadRequest() throws Exception {
        when(enderecoService.buscarEnderecoPorCep(CEP_INVALIDO))
                .thenThrow(new ValidationException("CEP inválido: deve conter 8 dígitos numéricos"));

        mockMvc.perform(get("/api/enderecos/cep/{cep}", CEP_INVALIDO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("CEP inválido: deve conter 8 dígitos numéricos"));
    }

    @Test
    void consultarEnderecoPorCep_QuandoCepNaoEncontrado_DeveRetornarNotFound() throws Exception {
        when(enderecoService.buscarEnderecoPorCep(CEP_NAO_ENCONTRADO))
                .thenThrow(new ResourceNotFoundException("Endereço", "CEP", CEP_NAO_ENCONTRADO));

        mockMvc.perform(get("/api/enderecos/cep/{cep}", CEP_NAO_ENCONTRADO))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Endereço não encontrado com CEP: '" + CEP_NAO_ENCONTRADO + "'"));
    }
}
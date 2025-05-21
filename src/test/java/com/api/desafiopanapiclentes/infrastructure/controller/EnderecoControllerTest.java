package com.api.desafiopanapiclentes.infrastructure.controller;

import com.api.desafiopanapiclentes.application.port.in.EnderecoService;
import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.infrastructure.exception.GlobalExceptionHandler;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
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
        when(enderecoService.buscarEnderecoPorCep(CEP_VALIDO)).thenReturn(ApiResponseWrapper.success(enderecoDTO));

        mockMvc.perform(get("/api/enderecos/cep/{cep}", CEP_VALIDO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.detail.data.cep").value(CEP_VALIDO))
                .andExpect(jsonPath("$.detail.data.logradouro").value("Praça da Sé"))
                .andExpect(jsonPath("$.detail.data.bairro").value("Sé"))
                .andExpect(jsonPath("$.detail.data.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.detail.data.estado").value("SP"));
    }

    @Test
    void consultarEnderecoPorCep_QuandoCepInvalido_DeveRetornarBadRequest() throws Exception {
        String errorMessage = "CEP inválido: deve conter 8 dígitos numéricos";
        when(enderecoService.buscarEnderecoPorCep(CEP_INVALIDO))
                .thenReturn(ApiResponseWrapper.error(errorMessage));

        mockMvc.perform(get("/api/enderecos/cep/{cep}", CEP_INVALIDO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.erros[0]").value(errorMessage));
    }

    @Test
    void consultarEnderecoPorCep_QuandoCepNaoEncontrado_DeveRetornarNotFound() throws Exception {
        when(enderecoService.buscarEnderecoPorCep(CEP_NAO_ENCONTRADO))
                .thenReturn(ApiResponseWrapper.error("Endereço", "CEP", CEP_NAO_ENCONTRADO));

        mockMvc.perform(get("/api/enderecos/cep/{cep}", CEP_NAO_ENCONTRADO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.erros[0]").value("Endereço não encontrado com CEP: '" + CEP_NAO_ENCONTRADO + "'"));
    }
}

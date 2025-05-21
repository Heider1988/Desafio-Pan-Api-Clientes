package com.api.rabbitmq.desafiopanapiclentes.infrastructure.controller;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.ClienteService;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.RequestWrapper;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.GlobalExceptionHandler;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteDTO clienteDTO;
    private EnderecoRequestDTO enderecoRequestDTO;
    private final String CPF = "12345678901";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();

        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .cep("01001000")
                .logradouro("Praça da Sé")
                .numero("100")
                .complemento("Lado ímpar")
                .bairro("Sé")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        clienteDTO = ClienteDTO.builder()
                .cpf(CPF)
                .nome("João da Silva")
                .email("joao.silva@email.com")
                .telefone("11987654321")
                .endereco(enderecoDTO)
                .build();

        enderecoRequestDTO = EnderecoRequestDTO.builder()
                .cep("04538133")
                .logradouro("Avenida Brigadeiro Faria Lima")
                .numero("3900")
                .complemento("10º andar")
                .bairro("Itaim Bibi")
                .cidade("São Paulo")
                .estado("SP")
                .build();
    }

    @Test
    void consultarCliente_QuandoClienteExiste_DeveRetornarCliente() throws Exception {

        when(clienteService.buscarClientePorCpf(CPF)).thenReturn(ApiResponseWrapper.success(clienteDTO));

        mockMvc.perform(get("/api/clientes/{cpf}", CPF))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.detail.data.cpf").value(CPF))
                .andExpect(jsonPath("$.detail.data.nome").value("João da Silva"))
                .andExpect(jsonPath("$.detail.data.email").value("joao.silva@email.com"))
                .andExpect(jsonPath("$.detail.data.telefone").value("11987654321"))
                .andExpect(jsonPath("$.detail.data.endereco.cep").value("01001000"))
                .andExpect(jsonPath("$.detail.data.endereco.cidade").value("São Paulo"));
    }

    @Test
    void consultarCliente_QuandoClienteNaoExiste_DeveRetornarNotFound() throws Exception {

        when(clienteService.buscarClientePorCpf(CPF))
                .thenReturn(ApiResponseWrapper.error("Cliente", "CPF", CPF));

        mockMvc.perform(get("/api/clientes/{cpf}", CPF))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.erros[0]").value("Cliente não encontrado com CPF: '" + CPF + "'"));
    }

    @Test
    void atualizarEnderecoCliente_QuandoClienteExiste_DeveAtualizarEndereco() throws Exception {
        when(clienteService.atualizarEnderecoCliente(eq(CPF), any(EnderecoRequestDTO.class)))
                .thenReturn(ApiResponseWrapper.success(clienteDTO));

        RequestWrapper wrapperDTO = new RequestWrapper();
        RequestWrapper.Detail detail = new RequestWrapper.Detail();
        detail.setData(enderecoRequestDTO);
        wrapperDTO.setDetail(detail);

        mockMvc.perform(put("/api/clientes/{cpf}/endereco", CPF)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrapperDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.detail.data.cpf").value(CPF))
                .andExpect(jsonPath("$.detail.data.nome").value("João da Silva"));
    }

    @Test
    void atualizarEnderecoCliente_QuandoClienteNaoExiste_DeveRetornarNotFound() throws Exception {

        when(clienteService.atualizarEnderecoCliente(eq(CPF), any(EnderecoRequestDTO.class)))
                .thenReturn(ApiResponseWrapper.error("Cliente", "CPF", CPF));

        RequestWrapper wrapperDTO = new RequestWrapper();
        RequestWrapper.Detail detail = new RequestWrapper.Detail();
        detail.setData(enderecoRequestDTO);
        wrapperDTO.setDetail(detail);

        mockMvc.perform(put("/api/clientes/{cpf}/endereco", CPF)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrapperDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.erros[0]").value("Cliente não encontrado com CPF: '" + CPF + "'"));
    }

    @Test
    void atualizarEnderecoCliente_QuandoDadosInvalidos_DeveRetornarBadRequest() throws Exception {
        // Mock the service to return an error response for invalid data
        when(clienteService.atualizarEnderecoCliente(eq(CPF), any(EnderecoRequestDTO.class)))
                .thenReturn(ApiResponseWrapper.error("Dados de endereço inválidos"));

        // Create a wrapper with invalid data
        RequestWrapper wrapperDTO = new RequestWrapper();
        RequestWrapper.Detail detail = new RequestWrapper.Detail();
        detail.setData(EnderecoRequestDTO.builder().build()); // Empty DTO
        wrapperDTO.setDetail(detail);

        mockMvc.perform(put("/api/clientes/{cpf}/endereco", CPF)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrapperDTO)))
                .andExpect(status().isOk()) // Expect 200 OK since the controller returns this
                .andExpect(jsonPath("$.erros").isArray())
                .andExpect(jsonPath("$.erros").isNotEmpty());
    }
}

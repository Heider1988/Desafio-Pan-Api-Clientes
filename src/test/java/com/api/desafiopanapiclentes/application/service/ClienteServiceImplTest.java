package com.api.desafiopanapiclentes.application.service;

import com.api.desafiopanapiclentes.application.port.out.ClienteRepository;
import com.api.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.desafiopanapiclentes.domain.factory.EnderecoFactory;
import com.api.desafiopanapiclentes.domain.mapper.ClienteMapper;
import com.api.desafiopanapiclentes.domain.model.entity.Cliente;
import com.api.desafiopanapiclentes.domain.model.entity.Endereco;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private EnderecoFactory enderecoFactory;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;
    private Endereco endereco;
    private final String CPF = "12345678901";

    @BeforeEach
    void setUp() {
        endereco = Endereco.builder()
                .cep("01001000")
                .logradouro("Praça da Sé")
                .numero("100")
                .complemento("Lado ímpar")
                .bairro("Sé")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        cliente = Cliente.builder()
                .cpf(CPF)
                .nome("João da Silva")
                .email("joao.silva@email.com")
                .telefone("11987654321")
                .endereco(endereco)
                .build();

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
    }

    @Test
    void buscarClientePorCpf_QuandoClienteExiste_DeveRetornarClienteDTO() {
        when(clienteRepository.findByCpf(CPF)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toClienteDTO(cliente)).thenReturn(clienteDTO);

        ApiResponseWrapper<ClienteDTO> response = clienteService.buscarClientePorCpf(CPF);

        assertNotNull(response);
        assertNotNull(response.getDetail());
        assertNotNull(response.getDetail().getData());
        assertTrue(response.getErros().isEmpty());

        ClienteDTO resultado = response.getDetail().getData();
        assertEquals(CPF, resultado.getCpf());
        assertEquals("João da Silva", resultado.getNome());
        assertEquals("joao.silva@email.com", resultado.getEmail());
        assertEquals("11987654321", resultado.getTelefone());

        assertNotNull(resultado.getEndereco());
        assertEquals("01001000", resultado.getEndereco().getCep());
        assertEquals("Praça da Sé", resultado.getEndereco().getLogradouro());

        verify(clienteRepository, times(1)).findByCpf(CPF);
        verify(clienteMapper, times(1)).toClienteDTO(cliente);
    }

    @Test
    void buscarClientePorCpf_QuandoClienteNaoExiste_DeveRetornarErro() {
        when(clienteRepository.findByCpf(CPF)).thenReturn(Optional.empty());

        ApiResponseWrapper<ClienteDTO> response = clienteService.buscarClientePorCpf(CPF);

        assertNotNull(response);
        assertFalse(response.getErros().isEmpty());
        assertTrue(response.getErros().get(0).contains(CPF));
        assertNull(response.getDetail().getData());

        verify(clienteRepository, times(1)).findByCpf(CPF);
    }

    @Test
    void atualizarEnderecoCliente_QuandoClienteExiste_DeveAtualizarEndereco() {
        when(clienteRepository.findByCpf(CPF)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toClienteDTO(any(Cliente.class))).thenReturn(clienteDTO);
        when(enderecoFactory.createFromRequest(any(EnderecoRequestDTO.class))).thenReturn(endereco);

        EnderecoRequestDTO enderecoRequest = EnderecoRequestDTO.builder()
                .cep("04538133")
                .logradouro("Avenida Brigadeiro Faria Lima")
                .numero("3900")
                .complemento("10º andar")
                .bairro("Itaim Bibi")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        ApiResponseWrapper<ClienteDTO> response = clienteService.atualizarEnderecoCliente(CPF, enderecoRequest);

        assertNotNull(response);
        assertNotNull(response.getDetail());
        assertNotNull(response.getDetail().getData());
        assertTrue(response.getErros().isEmpty());

        ClienteDTO resultado = response.getDetail().getData();
        assertEquals(CPF, resultado.getCpf());

        verify(clienteRepository, times(1)).findByCpf(CPF);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(clienteMapper, times(1)).toClienteDTO(any(Cliente.class));
        verify(enderecoFactory, times(1)).createFromRequest(any(EnderecoRequestDTO.class));
    }

    @Test
    void atualizarEnderecoCliente_QuandoClienteNaoExiste_DeveRetornarErro() {
        when(clienteRepository.findByCpf(CPF)).thenReturn(Optional.empty());

        EnderecoRequestDTO enderecoRequest = EnderecoRequestDTO.builder()
                .cep("04538133")
                .logradouro("Avenida Brigadeiro Faria Lima")
                .numero("3900")
                .bairro("Itaim Bibi")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        ApiResponseWrapper<ClienteDTO> response = clienteService.atualizarEnderecoCliente(CPF, enderecoRequest);

        assertNotNull(response);
        assertFalse(response.getErros().isEmpty());
        assertTrue(response.getErros().get(0).contains(CPF));
        assertNull(response.getDetail().getData());

        verify(clienteRepository, times(1)).findByCpf(CPF);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}

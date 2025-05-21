package com.api.rabbitmq.desafiopanapiclentes.application.service;

import com.api.rabbitmq.desafiopanapiclentes.application.port.out.ViaCepClient;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ValidationException;
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
class EnderecoServiceImplTest {

    @Mock
    private ViaCepClient viaCepClient;

    @InjectMocks
    private EnderecoServiceImpl enderecoService;

    private EnderecoDTO enderecoDTO;
    private final String CEP_VALIDO = "01001000";
    private final String CEP_INVALIDO = "123";
    private final String CEP_NAO_ENCONTRADO = "99999999";

    @BeforeEach
    void setUp() {

        enderecoDTO = EnderecoDTO.builder()
                .cep(CEP_VALIDO)
                .logradouro("Praça da Sé")
                .complemento("lado ímpar")
                .bairro("Sé")
                .cidade("São Paulo")
                .estado("SP")
                .build();
    }

    @Test
    void buscarEnderecoPorCep_QuandoCepValido_DeveRetornarEndereco() {

        when(viaCepClient.buscarEnderecoPorCep(CEP_VALIDO)).thenReturn(Optional.of(enderecoDTO));


        EnderecoDTO resultado = enderecoService.buscarEnderecoPorCep(CEP_VALIDO);


        assertNotNull(resultado);
        assertEquals(CEP_VALIDO, resultado.getCep());
        assertEquals("Praça da Sé", resultado.getLogradouro());
        assertEquals("Sé", resultado.getBairro());
        assertEquals("São Paulo", resultado.getCidade());
        assertEquals("SP", resultado.getEstado());

        verify(viaCepClient, times(1)).buscarEnderecoPorCep(CEP_VALIDO);
    }

    @Test
    void buscarEnderecoPorCep_QuandoCepComFormatacao_DeveRemoverFormatacao() {

        String cepFormatado = "01001-000";
        when(viaCepClient.buscarEnderecoPorCep(CEP_VALIDO)).thenReturn(Optional.of(enderecoDTO));

        EnderecoDTO resultado = enderecoService.buscarEnderecoPorCep(cepFormatado);

        assertNotNull(resultado);
        assertEquals(CEP_VALIDO, resultado.getCep());

        verify(viaCepClient, times(1)).buscarEnderecoPorCep(CEP_VALIDO);
    }

    @Test
    void buscarEnderecoPorCep_QuandoCepInvalido_DeveLancarExcecao() {

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> enderecoService.buscarEnderecoPorCep(CEP_INVALIDO)
        );

        assertTrue(exception.getMessage().contains("CEP inválido"));

        verify(viaCepClient, never()).buscarEnderecoPorCep(anyString());
    }

    @Test
    void buscarEnderecoPorCep_QuandoCepNaoEncontrado_DeveLancarExcecao() {

        when(viaCepClient.buscarEnderecoPorCep(CEP_NAO_ENCONTRADO)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> enderecoService.buscarEnderecoPorCep(CEP_NAO_ENCONTRADO)
        );

        assertTrue(exception.getMessage().contains(CEP_NAO_ENCONTRADO));

        verify(viaCepClient, times(1)).buscarEnderecoPorCep(CEP_NAO_ENCONTRADO);
    }
}
package com.api.rabbitmq.desafiopanapiclentes.application.port.out;

import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;

import java.util.Optional;

public interface ViaCepClient {

    Optional<EnderecoDTO> buscarEnderecoPorCep(String cep);
}
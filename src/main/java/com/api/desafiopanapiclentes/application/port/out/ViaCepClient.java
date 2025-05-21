package com.api.desafiopanapiclentes.application.port.out;

import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;

import java.util.Optional;

public interface ViaCepClient {

    Optional<EnderecoDTO> buscarEnderecoPorCep(String cep);
}
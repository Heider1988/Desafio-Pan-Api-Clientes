package com.api.rabbitmq.desafiopanapiclentes.application.port.in;

import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;

public interface EnderecoService {

    EnderecoDTO buscarEnderecoPorCep(String cep);
}

package com.api.rabbitmq.desafiopanapiclentes.application.port.in;

import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;

public interface EnderecoService {

    ApiResponseWrapper<EnderecoDTO> buscarEnderecoPorCep(String cep);
}

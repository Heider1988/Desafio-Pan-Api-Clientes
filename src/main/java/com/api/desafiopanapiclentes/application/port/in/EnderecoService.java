package com.api.desafiopanapiclentes.application.port.in;

import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;

public interface EnderecoService {

    ApiResponseWrapper<EnderecoDTO> buscarEnderecoPorCep(String cep);
}

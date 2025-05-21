package com.api.desafiopanapiclentes.application.port.in;

import com.api.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;

public interface ClienteService {

    ApiResponseWrapper<ClienteDTO> buscarClientePorCpf(String cpf);

    ApiResponseWrapper<ClienteDTO> atualizarEnderecoCliente(String cpf, EnderecoRequestDTO enderecoRequest);
}

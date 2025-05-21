package com.api.rabbitmq.desafiopanapiclentes.application.port.in;

import com.api.rabbitmq.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;

public interface ClienteService {

    ApiResponseWrapper<ClienteDTO> buscarClientePorCpf(String cpf);

    ApiResponseWrapper<ClienteDTO> atualizarEnderecoCliente(String cpf, EnderecoRequestDTO enderecoRequest);
}

package com.api.rabbitmq.desafiopanapiclentes.application.port.in;

import com.api.rabbitmq.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;

public interface ClienteService {

    ClienteDTO buscarClientePorCpf(String cpf);

    ClienteDTO atualizarEnderecoCliente(String cpf, EnderecoRequestDTO enderecoRequest);
}

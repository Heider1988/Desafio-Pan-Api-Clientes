package com.api.rabbitmq.desafiopanapiclentes.application.port.out;

import com.api.rabbitmq.desafiopanapiclentes.domain.model.entity.Cliente;

import java.util.Optional;

public interface ClienteRepository {

    Optional<Cliente> findByCpf(String cpf);

    Cliente save(Cliente cliente);
}
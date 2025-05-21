package com.api.desafiopanapiclentes.application.port.out;

import com.api.desafiopanapiclentes.domain.model.entity.Cliente;

import java.util.Optional;

public interface ClienteRepository {

    Optional<Cliente> findByCpf(String cpf);

    Cliente save(Cliente cliente);
}
package com.api.desafiopanapiclentes.infrastructure.adapter;

import com.api.desafiopanapiclentes.application.port.out.ClienteRepository;
import com.api.desafiopanapiclentes.domain.model.entity.Cliente;
import com.api.desafiopanapiclentes.infrastructure.repository.ClienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;

    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        return clienteJpaRepository.findByCpf(cpf);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteJpaRepository.save(cliente);
    }
}
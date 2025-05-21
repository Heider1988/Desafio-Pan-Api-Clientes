package com.api.desafiopanapiclentes.infrastructure.repository;

import com.api.desafiopanapiclentes.domain.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteJpaRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findByCpf(String cpf);
}
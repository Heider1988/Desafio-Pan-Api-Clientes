package com.api.desafiopanapiclentes.domain.mapper;

import com.api.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.domain.model.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toClienteDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        EnderecoDTO enderecoDTO = null;

        if (cliente.getEndereco() != null) {
            enderecoDTO = EnderecoDTO.builder()
                    .cep(cliente.getEndereco().getCep())
                    .logradouro(cliente.getEndereco().getLogradouro())
                    .numero(cliente.getEndereco().getNumero())
                    .complemento(cliente.getEndereco().getComplemento())
                    .bairro(cliente.getEndereco().getBairro())
                    .cidade(cliente.getEndereco().getCidade())
                    .estado(cliente.getEndereco().getEstado())
                    .build();
        }

        return ClienteDTO.builder()
                .cpf(cliente.getCpf())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .telefone(cliente.getTelefone())
                .endereco(enderecoDTO)
                .build();
    }
}
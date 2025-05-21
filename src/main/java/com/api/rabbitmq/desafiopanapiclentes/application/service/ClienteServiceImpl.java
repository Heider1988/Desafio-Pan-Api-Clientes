package com.api.rabbitmq.desafiopanapiclentes.application.service;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.ClienteService;
import com.api.rabbitmq.desafiopanapiclentes.application.port.out.ClienteRepository;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Cliente;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Endereco;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO buscarClientePorCpf(String cpf) {
        log.debug("Buscando cliente pelo CPF: {}", cpf);

        return clienteRepository.findByCpf(cpf)
                .map(this::mapToClienteDTO)
                .orElseThrow(() -> {
                    log.error("Cliente não encontrado com CPF: {}", cpf);
                    return new ResourceNotFoundException("Cliente", "CPF", cpf);
                });
    }

    @Override
    @Transactional
    public ClienteDTO atualizarEnderecoCliente(String cpf, EnderecoRequestDTO enderecoRequest) {
        log.debug("Atualizando endereço do cliente com CPF: {}", cpf);

        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> {
                    log.error("Cliente não encontrado com CPF: {}", cpf);
                    return new ResourceNotFoundException("Cliente", "CPF", cpf);
                });

        Endereco endereco = Endereco.builder()
                .cep(enderecoRequest.getCep())
                .logradouro(enderecoRequest.getLogradouro())
                .numero(enderecoRequest.getNumero())
                .complemento(enderecoRequest.getComplemento())
                .bairro(enderecoRequest.getBairro())
                .cidade(enderecoRequest.getCidade())
                .estado(enderecoRequest.getEstado())
                .build();

        cliente.setEndereco(endereco);

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        log.info("Endereço do cliente atualizado com sucesso. CPF: {}", cpf);

        return mapToClienteDTO(clienteAtualizado);
    }

    private ClienteDTO mapToClienteDTO(Cliente cliente) {
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

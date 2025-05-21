package com.api.desafiopanapiclentes.application.service;

import com.api.desafiopanapiclentes.application.port.in.ClienteService;
import com.api.desafiopanapiclentes.application.port.out.ClienteRepository;
import com.api.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.desafiopanapiclentes.domain.factory.EnderecoFactory;
import com.api.desafiopanapiclentes.domain.mapper.ClienteMapper;
import com.api.desafiopanapiclentes.domain.model.entity.Cliente;
import com.api.desafiopanapiclentes.domain.model.entity.Endereco;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final EnderecoFactory enderecoFactory;

    @Override
    @Transactional(readOnly = true)
    public ApiResponseWrapper<ClienteDTO> buscarClientePorCpf(String cpf) {
        log.debug("Buscando cliente pelo CPF: {}", cpf);

        return clienteRepository.findByCpf(cpf)
                .map(cliente -> ApiResponseWrapper.success(clienteMapper.toClienteDTO(cliente)))
                .orElseGet(() -> {
                    log.error("Cliente não encontrado com CPF: {}", cpf);
                    return ApiResponseWrapper.error("Cliente", "CPF", cpf);
                });
    }

    @Override
    @Transactional
    public ApiResponseWrapper<ClienteDTO> atualizarEnderecoCliente(String cpf, EnderecoRequestDTO enderecoRequest) {
        log.debug("Atualizando endereço do cliente com CPF: {}", cpf);

        return clienteRepository.findByCpf(cpf)
                .map(cliente -> {
                    Endereco endereco = enderecoFactory.createFromRequest(enderecoRequest);

                    cliente.setEndereco(endereco);

                    Cliente clienteAtualizado = clienteRepository.save(cliente);
                    log.info("Endereço do cliente atualizado com sucesso. CPF: {}", cpf);

                    return ApiResponseWrapper.success(clienteMapper.toClienteDTO(clienteAtualizado));
                })
                .orElseGet(() -> {
                    log.error("Cliente não encontrado com CPF: {}", cpf);
                    return ApiResponseWrapper.error("Cliente", "CPF", cpf);
                });
    }

}

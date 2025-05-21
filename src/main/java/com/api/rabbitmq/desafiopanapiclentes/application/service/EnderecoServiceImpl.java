package com.api.rabbitmq.desafiopanapiclentes.application.service;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.EnderecoService;
import com.api.rabbitmq.desafiopanapiclentes.application.port.out.ViaCepClient;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final ViaCepClient viaCepClient;

    @Override
    public EnderecoDTO buscarEnderecoPorCep(String cep) {
        log.debug("Buscando endereço pelo CEP: {}", cep);

        // Remove caracteres não numéricos do CEP
        String cepLimpo = cep.replaceAll("\\D", "");

        if (cepLimpo.length() != 8) {
            log.warn("CEP inválido: {}", cep);
            throw new ValidationException("CEP inválido: deve conter 8 dígitos numéricos");
        }

        return viaCepClient.buscarEnderecoPorCep(cepLimpo)
                .orElseThrow(() -> {
                    log.error("Endereço não encontrado para o CEP: {}", cep);
                    return new ResourceNotFoundException("Endereço", "CEP", cep);
                });
    }
}

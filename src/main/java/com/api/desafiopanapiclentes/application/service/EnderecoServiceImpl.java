package com.api.desafiopanapiclentes.application.service;

import com.api.desafiopanapiclentes.application.port.in.EnderecoService;
import com.api.desafiopanapiclentes.application.port.out.ViaCepClient;
import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final ViaCepClient viaCepClient;

    @Override
    public ApiResponseWrapper<EnderecoDTO> buscarEnderecoPorCep(String cep) {
        log.debug("Buscando endereço pelo CEP: {}", cep);

        // Remove caracteres não numéricos do CEP
        String cepLimpo = cep.replaceAll("\\D", "");

        if (cepLimpo.length() != 8) {
            log.warn("CEP inválido: {}", cep);
            return ApiResponseWrapper.error("CEP inválido: deve conter 8 dígitos numéricos");
        }

        return viaCepClient.buscarEnderecoPorCep(cepLimpo)
                .map(ApiResponseWrapper::success)
                .orElseGet(() -> {
                    log.error("Endereço não encontrado para o CEP: {}", cep);
                    return ApiResponseWrapper.error("Endereço", "CEP", cep);
                });
    }
}

package com.api.desafiopanapiclentes.infrastructure.adapter;

import com.api.desafiopanapiclentes.application.port.out.ViaCepClient;
import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.domain.dto.ViaCepResponseDTO;
import com.api.desafiopanapiclentes.domain.mapper.ViaCepMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViaCepClientImpl implements ViaCepClient {

    private final WebClient webClient;
    private final ViaCepMapper viaCepMapper;

    @Value("${api.viacep.url}")
    private String viaCepUrl;

    @Override
    public Optional<EnderecoDTO> buscarEnderecoPorCep(String cep) {
        log.debug("Buscando endereço pelo CEP: {}", cep);

        try {
            return webClient.get()
                    .uri(viaCepUrl + "/{cep}/json", cep)
                    .retrieve()
                    .bodyToMono(ViaCepResponseDTO.class)
                    .map(viaCepMapper::toEnderecoDTO)
                    .onErrorResume(e -> {
                        log.error("Erro ao buscar endereço pelo CEP: {}", cep, e);
                        return Mono.empty();
                    })
                    .blockOptional();
        } catch (Exception e) {
            log.error("Erro ao buscar endereço pelo CEP: {}", cep, e);
            return Optional.empty();
        }
    }
}

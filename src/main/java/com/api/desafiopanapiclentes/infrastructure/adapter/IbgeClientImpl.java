package com.api.desafiopanapiclentes.infrastructure.adapter;

import com.api.desafiopanapiclentes.application.port.out.IbgeClient;
import com.api.desafiopanapiclentes.domain.dto.IbgeEstadoResponseDTO;
import com.api.desafiopanapiclentes.domain.dto.IbgeMunicipioResponseDTO;
import com.api.desafiopanapiclentes.domain.mapper.IbgeMapper;
import com.api.desafiopanapiclentes.domain.model.Estado;
import com.api.desafiopanapiclentes.domain.model.Municipio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class IbgeClientImpl implements IbgeClient {

    private final WebClient webClient;
    private final IbgeMapper ibgeMapper;

    @Value("${api.ibge.estados.url}")
    private String ibgeEstadosUrl;

    @Value("${api.ibge.municipios.url}")
    private String ibgeMunicipiosUrl;

    @Override
    public List<Estado> buscarEstados() {
        log.debug("Buscando estados do Brasil");

        try {
            return webClient.get()
                    .uri(ibgeEstadosUrl)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<IbgeEstadoResponseDTO>>() {})
                    .map(ibgeMapper::toEstados)
                    .onErrorResume(e -> {
                        log.error("Erro ao buscar estados do Brasil", e);
                        return Mono.just(Collections.emptyList());
                    })
                    .block();
        } catch (Exception e) {
            log.error("Erro ao buscar estados do Brasil", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Municipio> buscarMunicipiosPorEstado(Long estadoId) {
        log.debug("Buscando municípios do estado com ID: {}", estadoId);

        try {
            return webClient.get()
                    .uri(ibgeMunicipiosUrl, estadoId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<IbgeMunicipioResponseDTO>>() {})
                    .map(ibgeMapper::toMunicipios)
                    .onErrorResume(e -> {
                        log.error("Erro ao buscar municípios do estado com ID: {}", estadoId, e);
                        return Mono.just(Collections.emptyList());
                    })
                    .block();
        } catch (Exception e) {
            log.error("Erro ao buscar municípios do estado com ID: {}", estadoId, e);
            return Collections.emptyList();
        }
    }

}

package com.api.desafiopanapiclentes.infrastructure.adapter;

import com.api.desafiopanapiclentes.application.port.out.IbgeClient;
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
                    .bodyToMono(new ParameterizedTypeReference<List<IbgeEstadoResponse>>() {})
                    .map(this::mapToEstados)
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
                    .bodyToMono(new ParameterizedTypeReference<List<IbgeMunicipioResponse>>() {})
                    .map(this::mapToMunicipios)
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

    private List<Estado> mapToEstados(List<IbgeEstadoResponse> responses) {
        return responses.stream()
                .map(response -> Estado.builder()
                        .id(response.getId())
                        .sigla(response.getSigla())
                        .nome(response.getNome())
                        .build())
                .toList();
    }

    private List<Municipio> mapToMunicipios(List<IbgeMunicipioResponse> responses) {
        return responses.stream()
                .map(response -> Municipio.builder()
                        .id(response.getId())
                        .nome(response.getNome())
                        .build())
                .toList();
    }

    private static class IbgeEstadoResponse {
        private Long id;
        private String sigla;
        private String nome;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getSigla() {
            return sigla;
        }

        public void setSigla(String sigla) {
            this.sigla = sigla;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }

    private static class IbgeMunicipioResponse {
        private Long id;
        private String nome;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }
}
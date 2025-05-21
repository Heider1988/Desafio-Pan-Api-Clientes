package com.api.rabbitmq.desafiopanapiclentes.infrastructure.adapter;

import com.api.rabbitmq.desafiopanapiclentes.application.port.out.ViaCepClient;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoDTO;
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

    @Value("${api.viacep.url}")
    private String viaCepUrl;

    @Override
    public Optional<EnderecoDTO> buscarEnderecoPorCep(String cep) {
        log.debug("Buscando endereço pelo CEP: {}", cep);
        
        try {
            return webClient.get()
                    .uri(viaCepUrl + "/{cep}/json", cep)
                    .retrieve()
                    .bodyToMono(ViaCepResponse.class)
                    .map(this::mapToEnderecoDTO)
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

    private EnderecoDTO mapToEnderecoDTO(ViaCepResponse response) {
        if (response.getErro() != null && response.getErro()) {
            return null;
        }
        
        return EnderecoDTO.builder()
                .cep(response.getCep().replaceAll("\\D", ""))
                .logradouro(response.getLogradouro())
                .complemento(response.getComplemento())
                .bairro(response.getBairro())
                .cidade(response.getLocalidade())
                .estado(response.getUf())
                .build();
    }

    private static class ViaCepResponse {
        private String cep;
        private String logradouro;
        private String complemento;
        private String bairro;
        private String localidade;
        private String uf;
        private Boolean erro;

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getComplemento() {
            return complemento;
        }

        public void setComplemento(String complemento) {
            this.complemento = complemento;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getLocalidade() {
            return localidade;
        }

        public void setLocalidade(String localidade) {
            this.localidade = localidade;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public Boolean getErro() {
            return erro;
        }

        public void setErro(Boolean erro) {
            this.erro = erro;
        }
    }
}
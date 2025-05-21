package com.api.desafiopanapiclentes.infrastructure.controller;

import com.api.desafiopanapiclentes.application.port.in.EnderecoService;
import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.infrastructure.docs.EnderecoControllerDocs;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/enderecos")
@RequiredArgsConstructor
public class EnderecoController implements EnderecoControllerDocs {

    private final EnderecoService enderecoService;

    @GetMapping("/cep/{cep}")
    public ResponseEntity<ApiResponseWrapper<EnderecoDTO>> consultarEnderecoPorCep(
            @PathVariable String cep) {
        log.info("Recebida requisição para consultar endereço com CEP: {}", cep);

        ApiResponseWrapper<EnderecoDTO> response = enderecoService.buscarEnderecoPorCep(cep);
        return ResponseEntity.ok(response);
    }
}

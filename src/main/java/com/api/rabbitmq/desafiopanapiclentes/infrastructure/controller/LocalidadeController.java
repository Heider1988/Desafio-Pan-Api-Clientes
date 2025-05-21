package com.api.rabbitmq.desafiopanapiclentes.infrastructure.controller;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.EstadoService;
import com.api.rabbitmq.desafiopanapiclentes.application.port.in.MunicipioService;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Estado;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Municipio;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.docs.LocalidadeControllerDocs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/localidades")
@RequiredArgsConstructor
public class LocalidadeController implements LocalidadeControllerDocs {

    private final EstadoService estadoService;
    private final MunicipioService municipioService;

    @GetMapping("/estados")
    public ResponseEntity<List<Estado>> listarEstados() {
        log.info("Recebida requisição para listar estados");

        List<Estado> estados = estadoService.listarEstados();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/estados/{estadoId}/municipios")
    public ResponseEntity<List<Municipio>> listarMunicipiosPorEstado(
            @PathVariable Long estadoId) {
        log.info("Recebida requisição para listar municípios do estado com ID: {}", estadoId);

        List<Municipio> municipios = municipioService.listarMunicipiosPorEstado(estadoId);
        return ResponseEntity.ok(municipios);
    }
}

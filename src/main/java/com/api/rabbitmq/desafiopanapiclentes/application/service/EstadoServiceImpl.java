package com.api.rabbitmq.desafiopanapiclentes.application.service;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.EstadoService;
import com.api.rabbitmq.desafiopanapiclentes.application.port.out.IbgeClient;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadoServiceImpl implements EstadoService {

    private final IbgeClient ibgeClient;

    @Override
    public List<Estado> listarEstados() {
        log.debug("Listando estados do Brasil com ordenação especial");
        
        List<Estado> estados = ibgeClient.buscarEstados();
        
        if (estados.isEmpty()) {
            log.warn("Nenhum estado encontrado");
            return estados;
        }

        List<Estado> estadosOrdenados = new ArrayList<>();

        estados.stream()
                .filter(estado -> "SP".equals(estado.getSigla()))
                .findFirst()
                .ifPresent(estadosOrdenados::add);

        estados.stream()
                .filter(estado -> "RJ".equals(estado.getSigla()))
                .findFirst()
                .ifPresent(estadosOrdenados::add);

        List<Estado> demaisEstados = estados.stream()
                .filter(estado -> !("SP".equals(estado.getSigla()) || "RJ".equals(estado.getSigla())))
                .sorted(Comparator.comparing(Estado::getNome))
                .toList();
        
        estadosOrdenados.addAll(demaisEstados);
        
        return estadosOrdenados;
    }
}
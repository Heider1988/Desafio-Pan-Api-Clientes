package com.api.rabbitmq.desafiopanapiclentes.application.service;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.MunicipioService;
import com.api.rabbitmq.desafiopanapiclentes.application.port.out.IbgeClient;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Municipio;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MunicipioServiceImpl implements MunicipioService {

    private final IbgeClient ibgeClient;

    @Override
    public List<Municipio> listarMunicipiosPorEstado(Long estadoId) {
        log.debug("Listando municípios do estado com ID: {}", estadoId);

        List<Municipio> municipios = ibgeClient.buscarMunicipiosPorEstado(estadoId);

        if (municipios.isEmpty()) {
            log.warn("Nenhum município encontrado para o estado com ID: {}", estadoId);
            throw new ResourceNotFoundException("Municípios", "Estado ID", estadoId);
        }

        return municipios.stream()
                .sorted(Comparator.comparing(Municipio::getNome))
                .toList();
    }
}

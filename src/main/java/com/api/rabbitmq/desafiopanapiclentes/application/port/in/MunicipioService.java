package com.api.rabbitmq.desafiopanapiclentes.application.port.in;

import com.api.rabbitmq.desafiopanapiclentes.domain.model.Municipio;

import java.util.List;

public interface MunicipioService {

    List<Municipio> listarMunicipiosPorEstado(Long estadoId);
}
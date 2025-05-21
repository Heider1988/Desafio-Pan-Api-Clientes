package com.api.rabbitmq.desafiopanapiclentes.application.port.in;

import com.api.rabbitmq.desafiopanapiclentes.domain.model.Municipio;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;

import java.util.List;

public interface MunicipioService {

    ApiResponseWrapper<List<Municipio>> listarMunicipiosPorEstado(Long estadoId);
}

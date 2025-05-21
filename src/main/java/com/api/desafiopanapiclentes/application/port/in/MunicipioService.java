package com.api.desafiopanapiclentes.application.port.in;

import com.api.desafiopanapiclentes.domain.model.Municipio;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;

import java.util.List;

public interface MunicipioService {

    ApiResponseWrapper<List<Municipio>> listarMunicipiosPorEstado(Long estadoId);
}

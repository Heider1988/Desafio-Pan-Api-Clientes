package com.api.desafiopanapiclentes.application.port.in;

import com.api.desafiopanapiclentes.domain.model.Estado;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;

import java.util.List;

public interface EstadoService {

    ApiResponseWrapper<List<Estado>> listarEstados();
}

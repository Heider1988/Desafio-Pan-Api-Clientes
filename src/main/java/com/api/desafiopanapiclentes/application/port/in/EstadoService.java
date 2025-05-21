package com.api.desafiopanapiclentes.application.port.in;

import com.api.desafiopanapiclentes.domain.model.Estado;

import java.util.List;

public interface EstadoService {

    List<Estado> listarEstados();
}
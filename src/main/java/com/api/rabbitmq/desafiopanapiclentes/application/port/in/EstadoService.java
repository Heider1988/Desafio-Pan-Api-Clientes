package com.api.rabbitmq.desafiopanapiclentes.application.port.in;

import com.api.rabbitmq.desafiopanapiclentes.domain.model.Estado;

import java.util.List;

public interface EstadoService {

    List<Estado> listarEstados();
}
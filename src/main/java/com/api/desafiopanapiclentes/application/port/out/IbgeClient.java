package com.api.desafiopanapiclentes.application.port.out;

import com.api.desafiopanapiclentes.domain.model.Estado;
import com.api.desafiopanapiclentes.domain.model.Municipio;

import java.util.List;

public interface IbgeClient {

    List<Estado> buscarEstados();

    List<Municipio> buscarMunicipiosPorEstado(Long estadoId);
}
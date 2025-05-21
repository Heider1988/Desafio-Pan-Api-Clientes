package com.api.desafiopanapiclentes.domain.mapper;

import com.api.desafiopanapiclentes.domain.dto.IbgeEstadoResponseDTO;
import com.api.desafiopanapiclentes.domain.dto.IbgeMunicipioResponseDTO;
import com.api.desafiopanapiclentes.domain.model.Estado;
import com.api.desafiopanapiclentes.domain.model.Municipio;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IbgeMapper {

    public List<Estado> toEstados(List<IbgeEstadoResponseDTO> responses) {
        return responses.stream()
                .map(this::toEstado)
                .collect(Collectors.toList());
    }

    public Estado toEstado(IbgeEstadoResponseDTO response) {
        return Estado.builder()
                .id(response.getId())
                .sigla(response.getSigla())
                .nome(response.getNome())
                .build();
    }

    public List<Municipio> toMunicipios(List<IbgeMunicipioResponseDTO> responses) {
        return responses.stream()
                .map(this::toMunicipio)
                .collect(Collectors.toList());
    }

    public Municipio toMunicipio(IbgeMunicipioResponseDTO response) {
        return Municipio.builder()
                .id(response.getId())
                .nome(response.getNome())
                .build();
    }
}
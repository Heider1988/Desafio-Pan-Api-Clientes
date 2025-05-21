package com.api.desafiopanapiclentes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for IBGE Municipio API response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IbgeMunicipioResponseDTO {
    private Long id;
    private String nome;
}
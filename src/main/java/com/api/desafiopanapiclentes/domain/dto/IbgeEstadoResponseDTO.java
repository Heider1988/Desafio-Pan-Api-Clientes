package com.api.desafiopanapiclentes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for IBGE Estado API response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IbgeEstadoResponseDTO {
    private Long id;
    private String sigla;
    private String nome;
}
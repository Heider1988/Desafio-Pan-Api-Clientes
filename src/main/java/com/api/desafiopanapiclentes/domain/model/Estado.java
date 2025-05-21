package com.api.desafiopanapiclentes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estado {
    private Long id;
    private String sigla;
    private String nome;
}
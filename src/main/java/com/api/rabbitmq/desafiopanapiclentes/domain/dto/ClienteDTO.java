package com.api.rabbitmq.desafiopanapiclentes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private EnderecoDTO endereco;
}
package com.api.desafiopanapiclentes.domain.factory;

import com.api.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.desafiopanapiclentes.domain.model.entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoFactory {

    public Endereco createFromRequest(EnderecoRequestDTO enderecoRequest) {
        if (enderecoRequest == null) {
            return null;
        }
        
        return Endereco.builder()
                .cep(enderecoRequest.getCep())
                .logradouro(enderecoRequest.getLogradouro())
                .numero(enderecoRequest.getNumero())
                .complemento(enderecoRequest.getComplemento())
                .bairro(enderecoRequest.getBairro())
                .cidade(enderecoRequest.getCidade())
                .estado(enderecoRequest.getEstado())
                .build();
    }
}
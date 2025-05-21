package com.api.desafiopanapiclentes.domain.mapper;

import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.domain.dto.ViaCepResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ViaCepMapper {

    public EnderecoDTO toEnderecoDTO(ViaCepResponseDTO response) {
        if (response == null || (response.getErro() != null && response.getErro())) {
            return null;
        }
        
        return EnderecoDTO.builder()
                .cep(response.getCep().replaceAll("\\D", ""))
                .logradouro(response.getLogradouro())
                .complemento(response.getComplemento())
                .bairro(response.getBairro())
                .cidade(response.getLocalidade())
                .estado(response.getUf())
                .build();
    }
}
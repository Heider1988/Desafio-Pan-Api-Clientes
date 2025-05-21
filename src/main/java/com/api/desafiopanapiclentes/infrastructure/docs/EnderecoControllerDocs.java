package com.api.desafiopanapiclentes.infrastructure.docs;

import com.api.desafiopanapiclentes.domain.dto.EnderecoDTO;
import com.api.desafiopanapiclentes.infrastructure.exception.ErrorResponse;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Endereços", description = "API para consulta de endereços")
public interface EnderecoControllerDocs {

    @Operation(summary = "Consultar endereço por CEP", description = "Retorna o endereço correspondente ao CEP informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = ApiResponseWrapper.class, 
                                    subTypes = {EnderecoDTO.class}))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "CEP inválido", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<EnderecoDTO>> consultarEnderecoPorCep(
            @Parameter(description = "CEP do endereço", required = true)
            @PathVariable String cep);
}

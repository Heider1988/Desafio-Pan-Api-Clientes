package com.api.desafiopanapiclentes.infrastructure.docs;

import com.api.desafiopanapiclentes.domain.model.Estado;
import com.api.desafiopanapiclentes.domain.model.Municipio;
import com.api.desafiopanapiclentes.infrastructure.exception.ErrorResponse;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Localidades", description = "API para consulta de estados e municípios")
public interface LocalidadeControllerDocs {

    @Operation(summary = "Listar estados", description = "Retorna a lista de estados do Brasil com ordenação especial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = ApiResponseWrapper.class,
                                    subTypes = {List.class}),
                            array = @ArraySchema(schema = @Schema(implementation = Estado.class))))
    })
    ResponseEntity<ApiResponseWrapper<List<Estado>>> listarEstados();

    @Operation(summary = "Listar municípios por estado", description = "Retorna a lista de municípios do estado informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de municípios",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = ApiResponseWrapper.class,
                                    subTypes = {List.class}),
                            array = @ArraySchema(schema = @Schema(implementation = Municipio.class)))),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<List<Municipio>>> listarMunicipiosPorEstado(
            @Parameter(description = "ID do estado", required = true)
            @PathVariable Long estadoId);
}

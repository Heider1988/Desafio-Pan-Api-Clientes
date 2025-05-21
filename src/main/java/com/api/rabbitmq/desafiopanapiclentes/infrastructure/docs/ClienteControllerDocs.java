package com.api.rabbitmq.desafiopanapiclentes.infrastructure.docs;

import com.api.rabbitmq.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.RequestWrapper;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ErrorResponse;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
public interface ClienteControllerDocs {

    @Operation(summary = "Consultar cliente por CPF", description = "Retorna os dados cadastrais do cliente com o CPF informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = ApiResponseWrapper.class, 
                                    subTypes = {ClienteDTO.class}))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<ClienteDTO>> consultarCliente(
            @Parameter(description = "CPF do cliente", required = true)
            @PathVariable String cpf);

    @Operation(summary = "Atualizar endereço do cliente", description = "Atualiza o endereço do cliente com o CPF informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = ApiResponseWrapper.class, 
                                    subTypes = {ClienteDTO.class}))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<ClienteDTO>> atualizarEnderecoCliente(
            @Parameter(description = "CPF do cliente", required = true)
            @PathVariable String cpf,
            @Parameter(description = "Dados do novo endereço em formato aninhado", required = true)
            @Valid @RequestBody RequestWrapper<EnderecoRequestDTO> enderecoRequestWrapper);
}

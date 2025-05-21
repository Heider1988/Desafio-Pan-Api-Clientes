package com.api.desafiopanapiclentes.infrastructure.controller;

import com.api.desafiopanapiclentes.application.port.in.ClienteService;
import com.api.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.desafiopanapiclentes.domain.dto.RequestWrapper;
import com.api.desafiopanapiclentes.infrastructure.docs.ClienteControllerDocs;
import com.api.desafiopanapiclentes.infrastructure.response.ApiResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteControllerDocs {

    private final ClienteService clienteService;

    @GetMapping("/{cpf}")
    public ResponseEntity<ApiResponseWrapper<ClienteDTO>> consultarCliente(
            @PathVariable String cpf) {
        log.info("Recebida requisição para consultar cliente com CPF: {}", cpf);

        ApiResponseWrapper<ClienteDTO> response = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cpf}/endereco")
    public ResponseEntity<ApiResponseWrapper<ClienteDTO>> atualizarEnderecoCliente(
            @PathVariable String cpf,
            @Valid @RequestBody RequestWrapper<EnderecoRequestDTO> enderecoRequestWrapper) {
        log.info("Recebida requisição para atualizar endereço do cliente com CPF: {}", cpf);

        ApiResponseWrapper<ClienteDTO> response = clienteService.atualizarEnderecoCliente(cpf, enderecoRequestWrapper.getDetail().getData());
        return ResponseEntity.ok(response);
    }
}

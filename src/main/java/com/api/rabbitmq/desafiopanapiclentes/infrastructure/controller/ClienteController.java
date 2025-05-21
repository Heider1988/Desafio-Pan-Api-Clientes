package com.api.rabbitmq.desafiopanapiclentes.infrastructure.controller;

import com.api.rabbitmq.desafiopanapiclentes.application.port.in.ClienteService;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.ClienteDTO;
import com.api.rabbitmq.desafiopanapiclentes.domain.dto.EnderecoRequestDTO;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.docs.ClienteControllerDocs;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteControllerDocs {

    private final ClienteService clienteService;

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> consultarCliente(
            @PathVariable String cpf) {
        log.info("Recebida requisição para consultar cliente com CPF: {}", cpf);

        ClienteDTO cliente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{cpf}/endereco")
    public ResponseEntity<ClienteDTO> atualizarEnderecoCliente(
            @PathVariable String cpf,
            @Valid @RequestBody EnderecoRequestDTO enderecoRequest) {
        log.info("Recebida requisição para atualizar endereço do cliente com CPF: {}", cpf);

        ClienteDTO clienteAtualizado = clienteService.atualizarEnderecoCliente(cpf, enderecoRequest);
        return ResponseEntity.ok(clienteAtualizado);
    }
}

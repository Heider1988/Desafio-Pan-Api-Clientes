package com.api.rabbitmq.desafiopanapiclentes.infrastructure.config;

import com.api.rabbitmq.desafiopanapiclentes.domain.model.Cliente;
import com.api.rabbitmq.desafiopanapiclentes.domain.model.Endereco;
import com.api.rabbitmq.desafiopanapiclentes.infrastructure.repository.ClienteJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final ClienteJpaRepository clienteRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            log.info("Carregando dados iniciais...");
            
            // Cliente 1
            Cliente cliente1 = Cliente.builder()
                    .cpf("11939949777")
                    .nome("Heider Oliveira")
                    .email("heider.o@icloud.com")
                    .telefone("27997045408")
                    .endereco(Endereco.builder()
                            .cep("29070170")
                            .logradouro("Praça da Sé")
                            .numero("100")
                            .complemento("Lado ímpar")
                            .bairro("Sé")
                            .cidade("Espírito Santo")
                            .estado("ES")
                            .build())
                    .build();
            
            // Cliente 2
            Cliente cliente2 = Cliente.builder()
                    .cpf("98765432109")
                    .nome("Maria Oliveira")
                    .email("maria.oliveira@email.com")
                    .telefone("21987654321")
                    .endereco(Endereco.builder()
                            .cep("20040002")
                            .logradouro("Avenida Rio Branco")
                            .numero("156")
                            .complemento("Andar 3")
                            .bairro("Centro")
                            .cidade("Rio de Janeiro")
                            .estado("RJ")
                            .build())
                    .build();
            
            // Cliente 3
            Cliente cliente3 = Cliente.builder()
                    .cpf("45678912301")
                    .nome("Carlos Pereira")
                    .email("carlos.pereira@email.com")
                    .telefone("31987654321")
                    .endereco(Endereco.builder()
                            .cep("30170110")
                            .logradouro("Avenida Afonso Pena")
                            .numero("1500")
                            .complemento("Sala 202")
                            .bairro("Centro")
                            .cidade("Belo Horizonte")
                            .estado("MG")
                            .build())
                    .build();
            
            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
            clienteRepository.save(cliente3);
            
            log.info("Dados iniciais carregados com sucesso!");
        };
    }
}
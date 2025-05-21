package com.api.desafiopanapiclentes.infrastructure.config;

import com.api.desafiopanapiclentes.domain.model.entity.Cliente;
import com.api.desafiopanapiclentes.domain.model.entity.Endereco;
import com.api.desafiopanapiclentes.infrastructure.repository.ClienteJpaRepository;
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


            Cliente cliente1 = Cliente.builder()
                    .cpf("12339949123")
                    .nome("Heider Oliveira")
                    .email("heider.o@icloud.com")
                    .telefone("27900040000")
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
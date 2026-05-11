package com.acmespiele.config;

import com.acmespiele.entity.*;
import com.acmespiele.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private UsoRepository usoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Criar categorias
        Categoria categoryAção = Categoria.builder()
            .num(1)
            .nome("Ação")
            .valorMinimo(50.0)
            .build();
        
        Categoria categoryAventura = Categoria.builder()
            .num(2)
            .nome("Aventura")
            .valorMinimo(40.0)
            .build();
        
        Categoria categoryEsportes = Categoria.builder()
            .num(3)
            .nome("Esportes")
            .valorMinimo(30.0)
            .build();
        
        categoriaRepository.save(categoryAção);
        categoriaRepository.save(categoryAventura);
        categoriaRepository.save(categoryEsportes);

        // Criar clientes
        Cliente cliente1 = Cliente.builder()
            .cpf("12345678901")
            .nome("João Silva")
            .email("joao@email.com")
            .nascimento(LocalDate.of(1990, 5, 15))
            .username("joao123")
            .password("senha123")
            .build();
        
        Cliente cliente2 = Cliente.builder()
            .cpf("98765432101")
            .nome("Maria Santos")
            .email("maria@email.com")
            .nascimento(LocalDate.of(1992, 3, 20))
            .username("maria456")
            .password("senha456")
            .build();
        
        Cliente cliente3 = Cliente.builder()
            .cpf("55555555555")
            .nome("Pedro Oliveira")
            .email("pedro@email.com")
            .nascimento(LocalDate.of(1995, 7, 10))
            .username("pedro789")
            .password("senha789")
            .build();
        
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        clienteRepository.save(cliente3);

        // Criar jogos
        Jogo jogo1 = Jogo.builder()
            .codigo(1)
            .nome("Cyberpunk 2077")
            .ano(2020)
            .valorMinuto(2.5)
            .situacao("disponivel")
            .categoria(categoryAção)
            .dataCriacao(LocalDate.of(2020, 12, 10))
            .build();
        
        Jogo jogo2 = Jogo.builder()
            .codigo(2)
            .nome("The Witcher 3")
            .ano(2015)
            .valorMinuto(2.0)
            .situacao("disponivel")
            .categoria(categoryAventura)
            .dataCriacao(LocalDate.of(2015, 5, 19))
            .build();
        
        Jogo jogo3 = Jogo.builder()
            .codigo(3)
            .nome("Elden Ring")
            .ano(2022)
            .valorMinuto(3.0)
            .situacao("disponivel")
            .categoria(categoryAção)
            .dataCriacao(LocalDate.of(2022, 2, 25))
            .build();
        
        Jogo jogo4 = Jogo.builder()
            .codigo(4)
            .nome("FIFA 24")
            .ano(2023)
            .valorMinuto(1.5)
            .situacao("disponivel")
            .categoria(categoryEsportes)
            .dataCriacao(LocalDate.of(2023, 9, 29))
            .build();
        
        Jogo jogo5 = Jogo.builder()
            .codigo(5)
            .nome("Starfield")
            .ano(2023)
            .valorMinuto(2.8)
            .situacao("disponivel")
            .categoria(categoryAventura)
            .dataCriacao(LocalDate.of(2023, 9, 6))
            .build();
        
        jogoRepository.save(jogo1);
        jogoRepository.save(jogo2);
        jogoRepository.save(jogo3);
        jogoRepository.save(jogo4);
        jogoRepository.save(jogo5);

        // Criar contratos
        Contrato contrato1 = Contrato.builder()
            .id(1)
            .data(LocalDate.now())
            .periodo(30)
            .cliente(cliente1)
            .jogo(jogo1)
            .ativo(true)
            .cancelado(false)
            .build();
        
        Contrato contrato2 = Contrato.builder()
            .id(2)
            .data(LocalDate.now())
            .periodo(30)
            .cliente(cliente1)
            .jogo(jogo2)
            .ativo(true)
            .cancelado(false)
            .build();
        
        Contrato contrato3 = Contrato.builder()
            .id(3)
            .data(LocalDate.now())
            .periodo(15)
            .cliente(cliente2)
            .jogo(jogo3)
            .ativo(true)
            .cancelado(false)
            .build();
        
        Contrato contrato4 = Contrato.builder()
            .id(4)
            .data(LocalDate.now())
            .periodo(15)
            .cliente(cliente2)
            .jogo(jogo4)
            .ativo(true)
            .cancelado(false)
            .build();
        
        contratoRepository.save(contrato1);
        contratoRepository.save(contrato2);
        contratoRepository.save(contrato3);
        contratoRepository.save(contrato4);

        // Atualizar status dos jogos para contratado
        jogo1.setSituacao("contratado");
        jogo1.setDataUltimoContrato(LocalDate.now());
        jogo2.setSituacao("contratado");
        jogo2.setDataUltimoContrato(LocalDate.now());
        jogo3.setSituacao("contratado");
        jogo3.setDataUltimoContrato(LocalDate.now());
        jogo4.setSituacao("contratado");
        jogo4.setDataUltimoContrato(LocalDate.now());
        
        jogoRepository.save(jogo1);
        jogoRepository.save(jogo2);
        jogoRepository.save(jogo3);
        jogoRepository.save(jogo4);

        // Criar usos para os contratos
        Uso uso1 = Uso.builder()
            .contrato(contrato1)
            .dataInicio(LocalDate.now().minusDays(5))
            .dataFim(LocalDate.now().minusDays(3))
            .horarioInicio(10)
            .horarioFim(22)
            .build();
        
        Uso uso2 = Uso.builder()
            .contrato(contrato1)
            .dataInicio(LocalDate.now().minusDays(2))
            .dataFim(LocalDate.now())
            .horarioInicio(14)
            .horarioFim(20)
            .build();
        
        Uso uso3 = Uso.builder()
            .contrato(contrato2)
            .dataInicio(LocalDate.now().minusDays(10))
            .dataFim(LocalDate.now().minusDays(5))
            .horarioInicio(18)
            .horarioFim(23)
            .build();
        
        Uso uso4 = Uso.builder()
            .contrato(contrato3)
            .dataInicio(LocalDate.now().minusDays(3))
            .dataFim(LocalDate.now())
            .horarioInicio(15)
            .horarioFim(19)
            .build();
        
        usoRepository.save(uso1);
        usoRepository.save(uso2);
        usoRepository.save(uso3);
        usoRepository.save(uso4);

        System.out.println("✓ Dados iniciais carregados com sucesso!");
    }

}

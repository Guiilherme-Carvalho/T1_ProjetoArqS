package com.acmespiele.repository;

import com.acmespiele.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContratoRepositoryTest {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Cliente cliente;
    private Jogo jogo;
    private Contrato contratoAtivo;
    private Contrato contratoCancelado;

    @BeforeEach
    void setUp() {
        // Criar categoria
        Categoria categoria = Categoria.builder()
                .num(1)
                .nome("Ação")
                .valorMinimo(10.0)
                .build();
        categoriaRepository.save(categoria);

        // Criar cliente
        cliente = Cliente.builder()
                .cpf("12345678901")
                .nome("João Silva")
                .email("joao@email.com")
                .nascimento(LocalDate.of(1990, 5, 15))
                .username("joao_silva")
                .password("senha123")
                .build();
        clienteRepository.save(cliente);

        // Criar jogo
        jogo = Jogo.builder()
                .codigo(1)
                .nome("Game 1")
                .ano(2023)
                .valorMinuto(0.50)
                .situacao("contratado")
                .categoria(categoria)
                .dataCriacao(LocalDate.now())
                .dataUltimoContrato(LocalDate.now())
                .build();
        jogoRepository.save(jogo);

        // Criar contratos
        contratoAtivo = Contrato.builder()
                .id(1)
                .data(LocalDate.now())
                .periodo(30)
                .ativo(true)
                .cancelado(false)
                .cliente(cliente)
                .jogo(jogo)
                .build();

        contratoCancelado = Contrato.builder()
                .id(2)
                .data(LocalDate.now().minusDays(10))
                .periodo(15)
                .ativo(false)
                .cancelado(true)
                .cliente(cliente)
                .jogo(jogo)
                .build();

        contratoRepository.save(contratoAtivo);
        contratoRepository.save(contratoCancelado);
    }

    @Test
    void testFindByClienteCpf() {
        // Act
        List<Contrato> resultado = contratoRepository.findByCliente_Cpf("12345678901");

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(c -> c.getCliente().getCpf().equals("12345678901")));
    }

    @Test
    void testFindByClienteCpfNaoEncontrado() {
        // Act
        List<Contrato> resultado = contratoRepository.findByCliente_Cpf("99999999999");

        // Assert
        assertEquals(0, resultado.size());
    }

    @Test
    void testFindByAtivo() {
        // Act
        List<Contrato> resultado = contratoRepository.findByAtivo(true);

        // Assert
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getAtivo());
        assertFalse(resultado.get(0).getCancelado());
    }

    @Test
    void testFindByAtivoFalso() {
        // Act
        List<Contrato> resultado = contratoRepository.findByAtivo(false);

        // Assert
        assertEquals(1, resultado.size());
        assertFalse(resultado.get(0).getAtivo());
        assertTrue(resultado.get(0).getCancelado());
    }

    @Test
    void testSaveContrato() {
        // Arrange
        Contrato novoContrato = Contrato.builder()
                .id(3)
                .data(LocalDate.now())
                .periodo(45)
                .ativo(true)
                .cancelado(false)
                .cliente(cliente)
                .jogo(jogo)
                .build();

        // Act
        Contrato salvo = contratoRepository.save(novoContrato);

        // Assert
        assertNotNull(salvo);
        assertEquals(3, salvo.getId());
        assertTrue(salvo.getAtivo());
    }
}

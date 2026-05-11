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
class JogoRepositoryTest {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;
    private Jogo jogoDisponivel;
    private Jogo jogoContratado;

    @BeforeEach
    void setUp() {
        // Criar categoria
        categoria = Categoria.builder()
                .num(1)
                .nome("Ação")
                .valorMinimo(10.0)
                .build();
        categoriaRepository.save(categoria);

        // Criar jogos
        jogoDisponivel = Jogo.builder()
                .codigo(1)
                .nome("Game Disponível")
                .ano(2023)
                .valorMinuto(0.50)
                .situacao("disponivel")
                .categoria(categoria)
                .dataCriacao(LocalDate.now())
                .dataUltimoContrato(null)
                .build();

        jogoContratado = Jogo.builder()
                .codigo(2)
                .nome("Game Contratado")
                .ano(2022)
                .valorMinuto(0.75)
                .situacao("contratado")
                .categoria(categoria)
                .dataCriacao(LocalDate.now().minusDays(10))
                .dataUltimoContrato(LocalDate.now())
                .build();

        jogoRepository.save(jogoDisponivel);
        jogoRepository.save(jogoContratado);
    }

    @Test
    void testFindBySituacaoDisponivel() {
        // Act
        List<Jogo> resultado = jogoRepository.findBySituacao("disponivel");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Game Disponível", resultado.get(0).getNome());
        assertEquals("disponivel", resultado.get(0).getSituacao());
    }

    @Test
    void testFindBySituacaoContratado() {
        // Act
        List<Jogo> resultado = jogoRepository.findBySituacao("contratado");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Game Contratado", resultado.get(0).getNome());
        assertEquals("contratado", resultado.get(0).getSituacao());
    }

    @Test
    void testFindBySituacaoVazia() {
        // Act
        List<Jogo> resultado = jogoRepository.findBySituacao("removido");

        // Assert
        assertEquals(0, resultado.size());
    }

    @Test
    void testFindById() {
        // Act
        var resultado = jogoRepository.findById(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Game Disponível", resultado.get().getNome());
    }

    @Test
    void testSaveJogo() {
        // Arrange
        Jogo novoJogo = Jogo.builder()
                .codigo(3)
                .nome("Novo Game")
                .ano(2024)
                .valorMinuto(1.00)
                .situacao("disponivel")
                .categoria(categoria)
                .dataCriacao(LocalDate.now())
                .dataUltimoContrato(null)
                .build();

        // Act
        Jogo salvo = jogoRepository.save(novoJogo);

        // Assert
        assertNotNull(salvo);
        assertEquals(3, salvo.getCodigo());
        assertEquals("Novo Game", salvo.getNome());
    }
}

package com.acmespiele.service;

import com.acmespiele.entity.Jogo;
import com.acmespiele.entity.Categoria;
import com.acmespiele.repository.JogoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JogoServiceTest {

    @Mock
    private JogoRepository jogoRepository;

    @InjectMocks
    private JogoService jogoService;

    private Categoria categoria;
    private Jogo jogo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = Categoria.builder()
                .num(1)
                .nome("Ação")
                .valorMinimo(10.0)
                .build();

        jogo = Jogo.builder()
                .codigo(1)
                .nome("Game 1")
                .ano(2023)
                .valorMinuto(0.50)
                .situacao("disponivel")
                .categoria(categoria)
                .dataCriacao(LocalDate.now())
                .dataUltimoContrato(null)
                .build();
    }

    @Test
    void testListarTodos() {
        // Arrange
        List<Jogo> jogos = Arrays.asList(jogo);
        when(jogoRepository.findAll()).thenReturn(jogos);

        // Act
        List<Jogo> resultado = jogoService.listarTodos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Game 1", resultado.get(0).getNome());
        verify(jogoRepository, times(1)).findAll();
    }

    @Test
    void testListarPorSituacao() {
        // Arrange
        Jogo jogoContratado = Jogo.builder()
                .codigo(1)
                .nome("Game 1")
                .ano(2023)
                .valorMinuto(0.50)
                .situacao("contratado")
                .categoria(categoria)
                .dataCriacao(LocalDate.now())
                .dataUltimoContrato(LocalDate.now())
                .build();

        List<Jogo> jogos = Arrays.asList(jogoContratado);
        when(jogoRepository.findBySituacao("contratado")).thenReturn(jogos);

        // Act
        List<Jogo> resultado = jogoService.listarPorSituacao("contratado");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("contratado", resultado.get(0).getSituacao());
    }

    @Test
    void testValidarTransicaoEstadoValida() {
        // Disponível para Contratado é válido
        assertTrue(jogoService.validarTransicaoEstado("disponivel", "contratado"));

        // Disponível para Obsoleto é válido
        assertTrue(jogoService.validarTransicaoEstado("disponivel", "obsoleto"));

        // Contratado para Disponível é válido
        assertTrue(jogoService.validarTransicaoEstado("contratado", "disponivel"));

        // Obsoleto para Removido é válido
        assertTrue(jogoService.validarTransicaoEstado("obsoleto", "removido"));

        // Removido para Disponível é válido
        assertTrue(jogoService.validarTransicaoEstado("removido", "disponivel"));
    }

    @Test
    void testValidarTransicaoEstadoInvalida() {
        // Disponível para Disponível é inválido
        assertFalse(jogoService.validarTransicaoEstado("disponivel", "disponivel"));

        // Removido para Contratado é inválido
        assertFalse(jogoService.validarTransicaoEstado("removido", "contratado"));

        // Contratado para Contratado é inválido
        assertFalse(jogoService.validarTransicaoEstado("contratado", "contratado"));
    }

    @Test
    void testAtualizarSituacao() {
        // Arrange
        when(jogoRepository.findById(1)).thenReturn(java.util.Optional.of(jogo));
        when(jogoRepository.save(jogo)).thenReturn(jogo);

        // Act
        jogo.setSituacao("contratado");
        Jogo resultado = jogoService.atualizarSituacao(1, "contratado");

        // Assert
        assertNotNull(resultado);
        assertEquals("contratado", resultado.getSituacao());
    }
}

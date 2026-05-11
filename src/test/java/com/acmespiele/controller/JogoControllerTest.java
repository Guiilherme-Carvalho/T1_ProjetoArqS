package com.acmespiele.controller;

import com.acmespiele.entity.Jogo;
import com.acmespiele.entity.Categoria;
import com.acmespiele.service.JogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JogoControllerTest {

    @Mock
    private JogoService jogoService;

    @InjectMocks
    private JogoController jogoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarJogos() {
        // Arrange
        Categoria categoria = Categoria.builder()
                .num(1)
                .nome("Ação")
                .valorMinimo(10.0)
                .build();

        Jogo jogo1 = Jogo.builder()
                .codigo(1)
                .nome("Game 1")
                .ano(2023)
                .valorMinuto(0.50)
                .situacao("disponivel")
                .categoria(categoria)
                .dataCriacao(LocalDate.now())
                .dataUltimoContrato(null)
                .build();

        List<Jogo> jogos = Arrays.asList(jogo1);
        when(jogoService.listarTodos()).thenReturn(jogos);

        // Act
        ResponseEntity<List<Jogo>> response = jogoController.listaJogos();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("disponivel", response.getBody().get(0).getSituacao());
        verify(jogoService, times(1)).listarTodos();
    }

    @Test
    void testConsultarJogosPorSituacao() {
        // Arrange
        Categoria categoria = Categoria.builder()
                .num(1)
                .nome("Ação")
                .valorMinimo(10.0)
                .build();

        Jogo jogo = Jogo.builder()
                .codigo(1)
                .nome("Game Contratado")
                .ano(2023)
                .valorMinuto(0.50)
                .situacao("contratado")
                .categoria(categoria)
                .dataCriacao(LocalDate.now())
                .dataUltimoContrato(LocalDate.now())
                .build();

        List<Jogo> jogos = Arrays.asList(jogo);
        when(jogoService.listarPorSituacao("contratado")).thenReturn(jogos);

        // Act
        ResponseEntity<List<Jogo>> response = jogoController.consultarJogosSituacao("contratado");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("contratado", response.getBody().get(0).getSituacao());
    }

    @Test
    void testAtualizarSituacao() {
        // Arrange
        Categoria categoria = Categoria.builder()
                .num(1)
                .nome("Ação")
                .valorMinimo(10.0)
                .build();

        Jogo jogoAtualizado = Jogo.builder()
                .codigo(1)
                .nome("Game 1")
                .ano(2023)
                .valorMinuto(0.50)
                .situacao("obsoleto")
                .categoria(categoria)
                .dataCriacao(LocalDate.now())
                .dataUltimoContrato(null)
                .build();

        when(jogoService.atualizarSituacao(1, "obsoleto")).thenReturn(jogoAtualizado);

        // Act
        ResponseEntity<Jogo> response = jogoController.atualizaJogo(1, "obsoleto");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("obsoleto", response.getBody().getSituacao());
    }

    @Test
    void testAtualizarSituacaoNaoEncontrado() {
        // Arrange
        when(jogoService.atualizarSituacao(999, "obsoleto")).thenReturn(null);

        // Act
        ResponseEntity<Jogo> response = jogoController.atualizaJogo(999, "obsoleto");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }
}

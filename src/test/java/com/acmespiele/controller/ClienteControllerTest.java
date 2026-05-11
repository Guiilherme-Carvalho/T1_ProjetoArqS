package com.acmespiele.controller;

import com.acmespiele.entity.Cliente;
import com.acmespiele.service.ClienteService;
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

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarClientes() {
        // Arrange
        Cliente cliente1 = Cliente.builder()
                .cpf("12345678901")
                .nome("João Silva")
                .email("joao@email.com")
                .nascimento(LocalDate.of(1990, 5, 15))
                .username("joao_silva")
                .password("senha123")
                .build();

        Cliente cliente2 = Cliente.builder()
                .cpf("98765432101")
                .nome("Maria Santos")
                .email("maria@email.com")
                .nascimento(LocalDate.of(1985, 3, 20))
                .username("maria_santos")
                .password("senha456")
                .build();

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteService.listarTodos()).thenReturn(clientes);

        // Act
        ResponseEntity<List<Cliente>> response = clienteController.listaClientes();

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("João Silva", response.getBody().get(0).getNome());
        verify(clienteService, times(1)).listarTodos();
    }

    @Test
    void testListarClientesVazio() {
        // Arrange
        when(clienteService.listarTodos()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<Cliente>> response = clienteController.listaClientes();

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}

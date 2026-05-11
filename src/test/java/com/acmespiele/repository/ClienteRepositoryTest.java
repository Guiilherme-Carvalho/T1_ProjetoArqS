package com.acmespiele.repository;

import com.acmespiele.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() {
        cliente1 = Cliente.builder()
                .cpf("12345678901")
                .nome("João Silva")
                .email("joao@email.com")
                .nascimento(LocalDate.of(1990, 5, 15))
                .username("joao_silva")
                .password("senha123")
                .build();

        cliente2 = Cliente.builder()
                .cpf("98765432101")
                .nome("Maria Santos")
                .email("maria@email.com")
                .nascimento(LocalDate.of(1985, 3, 20))
                .username("maria_santos")
                .password("senha456")
                .build();

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
    }

    @Test
    void testFindById() {
        // Act
        var resultado = clienteRepository.findById("12345678901");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
        assertEquals("joao@email.com", resultado.get().getEmail());
    }

    @Test
    void testFindByIdNaoEncontrado() {
        // Act
        var resultado = clienteRepository.findById("99999999999");

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    void testFindAll() {
        // Act
        var resultado = clienteRepository.findAll();

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("João Silva")));
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Maria Santos")));
    }

    @Test
    void testSaveCliente() {
        // Arrange
        Cliente novoCliente = Cliente.builder()
                .cpf("55555555555")
                .nome("Pedro Oliveira")
                .email("pedro@email.com")
                .nascimento(LocalDate.of(1992, 7, 10))
                .username("pedro_oliveira")
                .password("senha789")
                .build();

        // Act
        Cliente salvo = clienteRepository.save(novoCliente);

        // Assert
        assertNotNull(salvo);
        assertEquals("55555555555", salvo.getCpf());
        assertEquals("Pedro Oliveira", salvo.getNome());
    }

    @Test
    void testDeleteCliente() {
        // Act
        clienteRepository.delete(cliente1);
        var resultado = clienteRepository.findById("12345678901");

        // Assert
        assertFalse(resultado.isPresent());
        assertEquals(1, clienteRepository.count());
    }

    @Test
    void testUpdateCliente() {
        // Arrange
        cliente1.setEmail("novo_email@email.com");

        // Act
        Cliente atualizado = clienteRepository.save(cliente1);

        // Assert
        assertEquals("novo_email@email.com", atualizado.getEmail());
    }
}

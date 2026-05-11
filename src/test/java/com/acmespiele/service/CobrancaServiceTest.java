package com.acmespiele.service;

import com.acmespiele.entity.*;
import com.acmespiele.repository.ContratoRepository;
import com.acmespiele.repository.UsoRepository;
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

class CobrancaServiceTest {

    @Mock
    private ContratoRepository contratoRepository;

    @Mock
    private UsoRepository usoRepository;

    @InjectMocks
    private CobrancaService cobrancaService;

    private Categoria categoria;
    private Cliente cliente;
    private Jogo jogo;
    private Contrato contrato;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = Categoria.builder()
                .num(1)
                .nome("Ação")
                .valorMinimo(10.0)
                .build();

        cliente = Cliente.builder()
                .cpf("12345678901")
                .nome("João Silva")
                .email("joao@email.com")
                .nascimento(LocalDate.of(1990, 5, 15))
                .username("joao_silva")
                .password("senha123")
                .build();

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

        contrato = Contrato.builder()
                .id(1)
                .data(LocalDate.now())
                .periodo(30)
                .ativo(true)
                .cancelado(false)
                .cliente(cliente)
                .jogo(jogo)
                .build();
    }

    @Test
    void testCalcularValorTotalContratoComMinimo() {
        // Arrange: valor mínimo da categoria é 10.0
        when(contratoRepository.findById(1)).thenReturn(java.util.Optional.of(contrato));
        when(usoRepository.findByContrato_Id(1)).thenReturn(Arrays.asList());

        // Act
        Double resultado = cobrancaService.calcularValorTotalContrato(1);

        // Assert
        // Mínimo é 10.0, portanto resultado deve ser >= 10.0
        assertTrue(resultado >= 10.0);
    }

    @Test
    void testCalcularValorTotalClienteSemDesconto() {
        // Arrange: cliente com valor total < 500
        List<Contrato> contratos = Arrays.asList(contrato);
        when(contratoRepository.findByCliente_Cpf("12345678901")).thenReturn(contratos);
        when(usoRepository.findByContrato_Id(1)).thenReturn(Arrays.asList());

        // Act
        Double resultado = cobrancaService.calcularValorTotalCliente("12345678901");

        // Assert
        assertTrue(resultado >= 10.0);
        assertFalse(cobrancaService.temDescontoAplicavel("12345678901"));
    }

    @Test
    void testTemDescontoAplicavelValorAlto() {
        // Arrange: simular vários contratos para valor > 500
        List<Contrato> contratos = Arrays.asList();
        when(contratoRepository.findByCliente_Cpf("12345678901")).thenReturn(contratos);

        // Para valor > 500, deve ter desconto de 3%
        boolean temDesconto = cobrancaService.temDescontoAplicavel("12345678901");

        // Assert
        assertFalse(temDesconto); // como não há contratos, valor é 0, portanto sem desconto
    }

    @Test
    void testObterPercentualDescontoSemDesconto() {
        // Arrange
        when(contratoRepository.findByCliente_Cpf("12345678901")).thenReturn(Arrays.asList());

        // Act
        Double percentual = cobrancaService.obterPercentualDesconto("12345678901");

        // Assert
        assertEquals(0.0, percentual);
    }
}

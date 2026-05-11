package com.acmespiele.service;

import com.acmespiele.entity.Contrato;
import com.acmespiele.entity.Uso;
import com.acmespiele.repository.ContratoRepository;
import com.acmespiele.repository.UsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CobrancaService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private UsoRepository usoRepository;

    /**
     * Calcula o valor total de um contrato
     * Fórmula: valorMinimo + (minutosJogados × valorMinuto)
     * @param contratoId ID do contrato
     * @return Valor total do contrato
     */
    public Double calcularValorTotalContrato(Integer contratoId) {
        var contrato = contratoRepository.findById(contratoId);
        if (contrato.isEmpty()) {
            return 0.0;
        }

        return calcularValorContrato(contrato.get());
    }

    /**
     * Calcula o valor total de cobrança de um cliente
     * Soma todos os contratos e aplica desconto de 3% se total > $500
     * @param cpf CPF do cliente
     * @return Valor total de cobrança com desconto aplicado
     */
    public Double calcularValorTotalCliente(String cpf) {
        List<Contrato> contratos = contratoRepository.findByCliente_Cpf(cpf);
        
        if (contratos.isEmpty()) {
            return 0.0;
        }

        // Calcular valor total de todos os contratos
        Double valorTotal = 0.0;
        for (Contrato contrato : contratos) {
            valorTotal += calcularValorContrato(contrato);
        }

        // Aplicar desconto de 3% se total > 500
        if (valorTotal > 500) {
            valorTotal = valorTotal * 0.97;
        }

        return valorTotal;
    }

    /**
     * Calcula o valor de um contrato específico
     * @param contrato Contrato a calcular
     * @return Valor total do contrato
     */
    private Double calcularValorContrato(Contrato contrato) {
        List<Uso> usos = usoRepository.findByContrato_Id(contrato.getId());
        
        Double valorMinimo = contrato.getJogo().getCategoria().getValorMinimo();
        Double valorMinuto = contrato.getJogo().getValorMinuto();
        
        Long minutosJogados = 0L;
        for (Uso uso : usos) {
            minutosJogados += calcularMinutosJogados(uso);
        }

        Double valorTotal = valorMinimo + (minutosJogados * valorMinuto);
        return valorTotal;
    }

    /**
     * Calcula minutos jogados em um uso específico
     * @param uso Uso a calcular
     * @return Número de minutos jogados
     */
    private Long calcularMinutosJogados(Uso uso) {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(
            uso.getDataInicio(), 
            uso.getDataFim()
        );
        
        long minutos = dias * 24 * 60;
        
        if (dias == 0) {
            // Mesmo dia
            minutos = (uso.getHorarioFim() - uso.getHorarioInicio()) * 60;
        } else {
            // Múltiplos dias
            minutos = minutos - ((24 - uso.getHorarioInicio()) * 60) + (uso.getHorarioFim() * 60);
        }
        
        return Math.max(minutos, 0);
    }

    /**
     * Verifica se cliente tem desconto aplicável
     * @param cpf CPF do cliente
     * @return true se total > 500 (desconto aplicável)
     */
    public boolean temDescontoAplicavel(String cpf) {
        List<Contrato> contratos = contratoRepository.findByCliente_Cpf(cpf);
        
        Double valorTotal = 0.0;
        for (Contrato contrato : contratos) {
            valorTotal += calcularValorContrato(contrato);
        }

        return valorTotal > 500;
    }

    /**
     * Retorna o percentual de desconto aplicado
     * @param cpf CPF do cliente
     * @return Percentual de desconto (0 ou 3)
     */
    public Double obterPercentualDesconto(String cpf) {
        return temDescontoAplicavel(cpf) ? 3.0 : 0.0;
    }

}

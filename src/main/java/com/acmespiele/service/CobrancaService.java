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

    public Double calcularValorTotalContrato(Integer contratoId) {
        var contrato = contratoRepository.findById(contratoId);
        if (contrato.isEmpty()) {
            return 0.0;
        }

        return calcularValorContrato(contrato.get());
    }

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

    public boolean temDescontoAplicavel(String cpf) {
        List<Contrato> contratos = contratoRepository.findByCliente_Cpf(cpf);
        
        Double valorTotal = 0.0;
        for (Contrato contrato : contratos) {
            valorTotal += calcularValorContrato(contrato);
        }

        return valorTotal > 500;
    }

    public Double obterPercentualDesconto(String cpf) {
        return temDescontoAplicavel(cpf) ? 3.0 : 0.0;
    }

}

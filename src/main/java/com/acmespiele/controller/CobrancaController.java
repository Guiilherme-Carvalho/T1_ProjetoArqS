package com.acmespiele.controller;

import com.acmespiele.service.CobrancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acmespiele")
public class CobrancaController {

    @Autowired
    private CobrancaService cobrancaService;

    @GetMapping("/consultatotalcontrato")
    public ResponseEntity<Double> consultaTotalContrato(@RequestParam Integer id) {
        Double valorTotal = cobrancaService.calcularValorTotalContrato(id);
        if (valorTotal == 0.0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/consultatotalcliente")
    public ResponseEntity<Double> consultaTotalCliente(@RequestParam String cpf) {
        Double valorTotal = cobrancaService.calcularValorTotalCliente(cpf);
        return ResponseEntity.ok(valorTotal);
    }

}

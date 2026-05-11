package com.acmespiele.controller;

import com.acmespiele.entity.Contrato;
import com.acmespiele.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acmespiele")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping("/listacontratos")
    public ResponseEntity<List<Contrato>> listaContratos() {
        List<Contrato> contratos = contratoService.listarTodos();
        return ResponseEntity.ok(contratos);
    }

    @PostMapping("/cadastro/cadcontrato")
    public ResponseEntity<Boolean> cadastraContrato(@RequestBody Contrato contrato) {
        boolean sucesso = contratoService.cadastrar(contrato);
        return ResponseEntity.ok(sucesso);
    }

    @DeleteMapping("/cadastro/cancelacontrato")
    public ResponseEntity<Boolean> cancelaContrato(@RequestParam Integer id) {
        boolean sucesso = contratoService.cancelar(id);
        return ResponseEntity.ok(sucesso);
    }

}

package com.acmespiele.controller;

import com.acmespiele.entity.Uso;
import com.acmespiele.service.UsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acmespiele")
public class UsoController {

    @Autowired
    private UsoService usoService;

    @PostMapping("/cadastro/caduso")
    public ResponseEntity<Boolean> cadastraUso(@RequestBody Uso uso) {
        boolean sucesso = usoService.cadastrar(uso);
        return ResponseEntity.ok(sucesso);
    }

}

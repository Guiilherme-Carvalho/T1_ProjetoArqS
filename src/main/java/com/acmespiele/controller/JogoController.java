package com.acmespiele.controller;

import com.acmespiele.entity.Jogo;
import com.acmespiele.service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acmespiele")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @GetMapping("/listajogos")
    public ResponseEntity<List<Jogo>> listaJogos() {
        List<Jogo> jogos = jogoService.listarTodos();
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/consultarjogossituacao/{situacao}")
    public ResponseEntity<List<Jogo>> consultarJogosSituacao(@PathVariable String situacao) {
        List<Jogo> jogos = jogoService.listarPorSituacao(situacao);
        return ResponseEntity.ok(jogos);
    }

    @PutMapping("/cadastro/atualizajogo/{codigo}/situacao/{status}")
    public ResponseEntity<Jogo> atualizaJogo(@PathVariable Integer codigo, @PathVariable String status) {
        Jogo jogoAtualizado = jogoService.atualizarSituacao(codigo, status);
        
        if (jogoAtualizado != null) {
            return ResponseEntity.ok(jogoAtualizado);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}

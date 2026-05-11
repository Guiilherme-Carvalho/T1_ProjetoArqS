package com.acmespiele.controller;

import com.acmespiele.entity.Cliente;
import com.acmespiele.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acmespiele")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listaclientes")
    public ResponseEntity<List<Cliente>> listaClientes() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

}

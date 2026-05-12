package com.acmespiele.service;

import com.acmespiele.entity.Cliente;
import com.acmespiele.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findById(cpf);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(String cpf, Cliente clienteAtualizado) {
        Optional<Cliente> cliente = clienteRepository.findById(cpf);
        if (cliente.isPresent()) {
            Cliente c = cliente.get();
            c.setNome(clienteAtualizado.getNome());
            c.setEmail(clienteAtualizado.getEmail());
            c.setNascimento(clienteAtualizado.getNascimento());
            c.setUsername(clienteAtualizado.getUsername());
            c.setPassword(clienteAtualizado.getPassword());
            return clienteRepository.save(c);
        }
        return null;
    }

    public void deletar(String cpf) {
        clienteRepository.deleteById(cpf);
    }

}

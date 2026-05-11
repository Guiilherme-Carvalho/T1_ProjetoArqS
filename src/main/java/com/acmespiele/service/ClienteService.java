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

    /**
     * Busca todos os clientes cadastrados
     * @return Lista de todos os clientes
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Busca um cliente específico pelo CPF
     * @param cpf CPF do cliente
     * @return Optional contendo o cliente se encontrado
     */
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findById(cpf);
    }

    /**
     * Cadastra um novo cliente
     * @param cliente Cliente a ser cadastrado
     * @return Cliente cadastrado
     */
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Atualiza um cliente existente
     * @param cpf CPF do cliente
     * @param clienteAtualizado Dados do cliente atualizado
     * @return Cliente atualizado
     */
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

    /**
     * Deleta um cliente
     * @param cpf CPF do cliente
     */
    public void deletar(String cpf) {
        clienteRepository.deleteById(cpf);
    }

}

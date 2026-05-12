package com.acmespiele.service;

import com.acmespiele.entity.Contrato;
import com.acmespiele.entity.Cliente;
import com.acmespiele.entity.Jogo;
import com.acmespiele.repository.ContratoRepository;
import com.acmespiele.repository.ClienteRepository;
import com.acmespiele.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JogoRepository jogoRepository;

    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    public Optional<Contrato> buscarPorId(Integer id) {
        return contratoRepository.findById(id);
    }

    public List<Contrato> listarPorCliente(String cpf) {
        return contratoRepository.findByCliente_Cpf(cpf);
    }

    public boolean cadastrar(Contrato contrato) {
        try {
            // Validar se cliente existe
            Optional<Cliente> cliente = clienteRepository.findById(contrato.getCliente().getCpf());
            if (!cliente.isPresent()) {
                return false;
            }

            // Validar se jogo existe
            Optional<Jogo> jogo = jogoRepository.findById(contrato.getJogo().getCodigo());
            if (!jogo.isPresent()) {
                return false;
            }

            // Atualizar situação do jogo e data do último contrato
            Jogo jogoAtualizado = jogo.get();
            if (!jogoAtualizado.getSituacao().equals("contratado")) {
                jogoAtualizado.setSituacao("contratado");
                jogoAtualizado.setDataUltimoContrato(LocalDate.now());
                jogoRepository.save(jogoAtualizado);
            }

            contrato.setData(LocalDate.now());
            contratoRepository.save(contrato);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cancelar(Integer id) {
        try {
            Optional<Contrato> contrato = contratoRepository.findById(id);
            if (contrato.isPresent()) {
                Contrato c = contrato.get();
                c.setAtivo(false);
                contratoRepository.save(c);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Contrato atualizar(Integer id, Contrato contratoAtualizado) {
        Optional<Contrato> contrato = contratoRepository.findById(id);
        if (contrato.isPresent()) {
            Contrato c = contrato.get();
            c.setPeriodo(contratoAtualizado.getPeriodo());
            c.setAtivo(contratoAtualizado.getAtivo());
            return contratoRepository.save(c);
        }
        return null;
    }

    public void deletar(Integer id) {
        contratoRepository.deleteById(id);
    }

}

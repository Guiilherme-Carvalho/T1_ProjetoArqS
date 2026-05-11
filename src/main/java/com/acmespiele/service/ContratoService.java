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

    /**
     * Busca todos os contratos cadastrados
     * @return Lista de todos os contratos
     */
    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    /**
     * Busca um contrato específico
     * @param id ID do contrato
     * @return Optional contendo o contrato se encontrado
     */
    public Optional<Contrato> buscarPorId(Integer id) {
        return contratoRepository.findById(id);
    }

    /**
     * Busca todos os contratos de um cliente
     * @param cpf CPF do cliente
     * @return Lista de contratos do cliente
     */
    public List<Contrato> listarPorCliente(String cpf) {
        return contratoRepository.findByCliente_Cpf(cpf);
    }

    /**
     * Cadastra um novo contrato
     * @param contrato Contrato a ser cadastrado
     * @return true se cadastrado com sucesso, false caso contrário
     */
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

    /**
     * Cancela um contrato (soft delete)
     * @param id ID do contrato
     * @return true se cancelado com sucesso, false caso contrário
     */
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

    /**
     * Atualiza um contrato existente
     * @param id ID do contrato
     * @param contratoAtualizado Dados do contrato atualizado
     * @return Contrato atualizado ou null
     */
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

    /**
     * Deleta um contrato permanentemente
     * @param id ID do contrato
     */
    public void deletar(Integer id) {
        contratoRepository.deleteById(id);
    }

}

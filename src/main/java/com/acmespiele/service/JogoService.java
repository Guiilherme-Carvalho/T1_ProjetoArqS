package com.acmespiele.service;

import com.acmespiele.entity.Jogo;
import com.acmespiele.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    public List<Jogo> listarTodos() {
        return jogoRepository.findAll();
    }


    public List<Jogo> listarPorSituacao(String situacao) {
        return jogoRepository.findBySituacao(situacao);
    }


    public Optional<Jogo> buscarPorCodigo(Integer codigo) {
        return jogoRepository.findById(codigo);
    }

    public Jogo atualizarSituacao(Integer codigo, String novoStatus) {
        Optional<Jogo> jogo = jogoRepository.findById(codigo);
        
        if (jogo.isPresent()) {
            Jogo j = jogo.get();
            j.setSituacao(novoStatus);
            return jogoRepository.save(j);
        }
        
        return null;
    }

    public Jogo salvar(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    public void deletar(Integer codigo) {
        jogoRepository.deleteById(codigo);
    }


    public boolean validarTransicaoEstado(String estadoAtual, String estadoNovo) {
        // Estados válidos: disponivel -> contratado -> obsoleto -> removido
        if (estadoAtual.equals("disponivel") && estadoNovo.equals("contratado")) return true;
        if (estadoAtual.equals("contratado") && estadoNovo.equals("obsoleto")) return true;
        if (estadoAtual.equals("obsoleto") && estadoNovo.equals("removido")) return true;
        if (estadoAtual.equals("contratado") && estadoNovo.equals("disponivel")) return true;
        
        return false;
    }

}

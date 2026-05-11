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

    /**
     * Busca todos os jogos cadastrados
     * @return Lista de todos os jogos
     */
    public List<Jogo> listarTodos() {
        return jogoRepository.findAll();
    }

    /**
     * Busca jogos por situação
     * @param situacao Situação do jogo (disponivel, contratado, obsoleto, removido)
     * @return Lista de jogos com a situação especificada
     */
    public List<Jogo> listarPorSituacao(String situacao) {
        return jogoRepository.findBySituacao(situacao);
    }

    /**
     * Busca um jogo específico pelo código
     * @param codigo Código do jogo
     * @return Optional contendo o jogo se encontrado
     */
    public Optional<Jogo> buscarPorCodigo(Integer codigo) {
        return jogoRepository.findById(codigo);
    }

    /**
     * Atualiza a situação de um jogo
     * @param codigo Código do jogo
     * @param novoStatus Novo status do jogo
     * @return Jogo atualizado
     */
    public Jogo atualizarSituacao(Integer codigo, String novoStatus) {
        Optional<Jogo> jogo = jogoRepository.findById(codigo);
        
        if (jogo.isPresent()) {
            Jogo j = jogo.get();
            j.setSituacao(novoStatus);
            return jogoRepository.save(j);
        }
        
        return null;
    }

    /**
     * Salva um novo jogo
     * @param jogo Jogo a ser salvo
     * @return Jogo salvo
     */
    public Jogo salvar(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    /**
     * Deleta um jogo
     * @param codigo Código do jogo
     */
    public void deletar(Integer codigo) {
        jogoRepository.deleteById(codigo);
    }

    /**
     * Valida a transição de estados do jogo
     * @param estadoAtual Estado atual do jogo
     * @param estadoNovo Novo estado do jogo
     * @return true se a transição é válida, false caso contrário
     */
    public boolean validarTransicaoEstado(String estadoAtual, String estadoNovo) {
        // Estados válidos: disponivel -> contratado -> obsoleto -> removido
        if (estadoAtual.equals("disponivel") && estadoNovo.equals("contratado")) return true;
        if (estadoAtual.equals("contratado") && estadoNovo.equals("obsoleto")) return true;
        if (estadoAtual.equals("obsoleto") && estadoNovo.equals("removido")) return true;
        if (estadoAtual.equals("contratado") && estadoNovo.equals("disponivel")) return true;
        
        return false;
    }

}

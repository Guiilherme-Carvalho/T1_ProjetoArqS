package com.acmespiele.service;

import com.acmespiele.entity.Uso;
import com.acmespiele.entity.Contrato;
import com.acmespiele.repository.UsoRepository;
import com.acmespiele.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsoService {

    @Autowired
    private UsoRepository usoRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    /**
     * Busca todos os usos cadastrados
     * @return Lista de todos os usos
     */
    public List<Uso> listarTodos() {
        return usoRepository.findAll();
    }

    /**
     * Busca um uso específico
     * @param numero Número do uso
     * @return Optional contendo o uso se encontrado
     */
    public Optional<Uso> buscarPorNumero(Integer numero) {
        return usoRepository.findById(numero);
    }

    /**
     * Busca todos os usos de um contrato
     * @param contratoId ID do contrato
     * @return Lista de usos do contrato
     */
    public List<Uso> listarPorContrato(Integer contratoId) {
        return usoRepository.findByContrato_Id(contratoId);
    }

    /**
     * Cadastra um novo uso
     * @param uso Uso a ser cadastrado
     * @return true se cadastrado com sucesso, false caso contrário
     */
    public boolean cadastrar(Uso uso) {
        try {
            // Validar se contrato existe
            Optional<Contrato> contrato = contratoRepository.findById(uso.getContrato().getId());
            if (!contrato.isPresent()) {
                return false;
            }

            // Validar se contrato está ativo
            Contrato c = contrato.get();
            if (!c.getAtivo()) {
                return false;
            }

            // Validar horários
            if (uso.getHorarioInicio() < 0 || uso.getHorarioInicio() > 23 ||
                uso.getHorarioFim() < 0 || uso.getHorarioFim() > 23) {
                return false;
            }

            usoRepository.save(uso);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Atualiza um uso existente
     * @param numero Número do uso
     * @param usoAtualizado Dados do uso atualizado
     * @return Uso atualizado ou null
     */
    public Uso atualizar(Integer numero, Uso usoAtualizado) {
        Optional<Uso> uso = usoRepository.findById(numero);
        if (uso.isPresent()) {
            Uso u = uso.get();
            u.setDataInicio(usoAtualizado.getDataInicio());
            u.setDataFim(usoAtualizado.getDataFim());
            u.setHorarioInicio(usoAtualizado.getHorarioInicio());
            u.setHorarioFim(usoAtualizado.getHorarioFim());
            return usoRepository.save(u);
        }
        return null;
    }

    /**
     * Deleta um uso
     * @param numero Número do uso
     */
    public void deletar(Integer numero) {
        usoRepository.deleteById(numero);
    }

    /**
     * Calcula minutos jogados entre duas datas e horários
     * @param uso Uso com datas e horários
     * @return Número de minutos jogados
     */
    public long calcularMinutosJogados(Uso uso) {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(
            uso.getDataInicio(), 
            uso.getDataFim()
        );
        
        long minutos = dias * 24 * 60;
        
        if (dias == 0) {
            // Mesmo dia
            minutos = (uso.getHorarioFim() - uso.getHorarioInicio()) * 60;
        } else {
            // Múltiplos dias
            minutos = minutos - ((24 - uso.getHorarioInicio()) * 60) + (uso.getHorarioFim() * 60);
        }
        
        return Math.max(minutos, 0);
    }

}

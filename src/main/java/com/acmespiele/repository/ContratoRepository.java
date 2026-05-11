package com.acmespiele.repository;

import com.acmespiele.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    List<Contrato> findByCliente_Cpf(String cpf);
    List<Contrato> findByAtivo(Boolean ativo);
}

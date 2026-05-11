package com.acmespiele.repository;

import com.acmespiele.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {
    List<Jogo> findBySituacao(String situacao);
}

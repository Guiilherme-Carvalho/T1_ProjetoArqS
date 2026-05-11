package com.acmespiele.repository;

import com.acmespiele.entity.Uso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsoRepository extends JpaRepository<Uso, Integer> {
    List<Uso> findByContrato_Id(Integer contratoId);
}

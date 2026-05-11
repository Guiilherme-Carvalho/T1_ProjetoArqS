package com.acmespiele.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "uso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Uso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "id_contrato", nullable = false)
    private Contrato contrato;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "horario_inicio", nullable = false)
    private Integer horarioInicio; // 0-23

    @Column(name = "horario_fim", nullable = false)
    private Integer horarioFim; // 0-23

}

package com.acmespiele.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "contrato")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contrato {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "periodo", nullable = false) // em dias
    private Integer periodo;

    @ManyToOne
    @JoinColumn(name = "cpf_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "codigo_jogo", nullable = false)
    private Jogo jogo;

    @Column(name = "ativo", nullable = false)
    @lombok.Builder.Default
    private Boolean ativo = true;

    @Column(name = "cancelado", nullable = false)
    @lombok.Builder.Default
    private Boolean cancelado = false;

}

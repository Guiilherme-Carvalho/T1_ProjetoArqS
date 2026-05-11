package com.acmespiele.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "jogo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jogo {

    @Id
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "valor_minuto", nullable = false)
    private Double valorMinuto;

    @Column(name = "situacao", nullable = false)
    @lombok.Builder.Default
    private String situacao = "disponivel"; // disponivel, contratado, obsoleto, removido

    @ManyToOne
    @JoinColumn(name = "categoria_num", nullable = false)
    private Categoria categoria;

    @Column(name = "data_criacao")
    @lombok.Builder.Default
    private LocalDate dataCriacao = LocalDate.now();

    @Column(name = "data_ultimo_contrato")
    private LocalDate dataUltimoContrato;

}

package com.partida.partidas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "partidas")
public class Partida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPartida;

    @Builder.Default
    @Column(nullable = false)
    private Integer puntajeTotal=0;
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean finalizada=false;

    
    private Integer idJugador;

    private Integer idNivel;
}

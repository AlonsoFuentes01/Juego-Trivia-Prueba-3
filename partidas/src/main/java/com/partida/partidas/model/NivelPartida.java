package com.partida.partidas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nivel_partida")
public class NivelPartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNivelPartida;

    @ManyToOne
    @JoinColumn(name = "id_nivel", nullable = false)
    @NotBlank(message = "Debes ingresar el nivel")
    private Nivel nivel;

    @ManyToOne
    @JoinColumn(name = "id_partida", nullable = false)
    private Partida partida;
}
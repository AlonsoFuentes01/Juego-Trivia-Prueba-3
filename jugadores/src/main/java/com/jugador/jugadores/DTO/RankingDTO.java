package com.jugador.jugadores.DTO;

import lombok.Data;

@Data
public class RankingDTO {
    private Integer posicion;
    private String nombreJugador;
    private String origenJugador;
    private Integer puntos;
    private Integer nivelActual;
}

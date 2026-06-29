package com.partida.partidas.DTO;


import lombok.Data;

@Data
public class PartidaDTO {
    private Integer puntajeTotal;
    private String nombreJugador;
    private Integer nivel;
    private JugadorDTOext jugadorDTOext;
    private Integer idPartida;
}

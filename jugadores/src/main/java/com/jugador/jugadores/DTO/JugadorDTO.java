package com.jugador.jugadores.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JugadorDTO {
    private Integer idJugador;
    private String nombreJugador;
    private String origenJugador;
    private Integer puntosJugador;
    private Integer nivelJugador;
    private List<ArmaDTO> armas;
}

package com.jugador.jugadores.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmaDTO {
    private Integer id;
    private Integer jugadorId;
    private String nombreArma;
}

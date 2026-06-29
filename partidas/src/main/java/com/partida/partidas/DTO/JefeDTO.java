package com.partida.partidas.DTO;


import lombok.Data;

@Data


public class JefeDTO {
    private Integer idJefe;
    private String nombreJefe;
    private Integer numeroNivel; 
    private String dificultadNivel;
    private Integer recompensa;
}


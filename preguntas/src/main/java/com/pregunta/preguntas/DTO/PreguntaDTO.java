package com.pregunta.preguntas.DTO;

import lombok.Data;

@Data
public class PreguntaDTO {
    private Integer idPregunta;
    private Integer idNivel;   
    private String enunciado;
    private Integer puntaje;
    private String dificultad;
    private String categoria;
    private String tipo;

}

package com.pregunta.preguntas.DTO;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoRespuestaDTO {

    private Boolean esCorrecta;
    private String mensaje;
    private Integer puntajeObtenido;
    private Integer puntajeTotal;
    private Integer nivelActual;
    private Boolean partidaFinalizada;
    private Boolean subioDeNivel;
    private String armaDesbloqueada;   
    private String jefeEncontrado;     
    private Boolean jefeDerrotado;     
    private Integer idSiguientePregunta;
    private String enunciadoSiguientePregunta;
    private List<Map<String, Object>> opciones;
    private Map<String, Object> pregunta;

}

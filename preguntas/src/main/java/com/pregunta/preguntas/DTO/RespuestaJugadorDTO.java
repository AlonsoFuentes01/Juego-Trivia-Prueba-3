package com.pregunta.preguntas.DTO;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespuestaJugadorDTO {

    private Integer idPartida;
    private Integer idRespuesta;
    private Map<String, Object> pregunta;

}

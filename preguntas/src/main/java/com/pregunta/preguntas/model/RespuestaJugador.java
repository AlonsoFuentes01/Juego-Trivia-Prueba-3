package com.pregunta.preguntas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "respuestas_jugador")
public class RespuestaJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRespuestaJugador;

    @ManyToOne
    @JoinColumn(name = "respuesta_id", nullable = false)
    private Respuesta respuestaElegida;

    @Column(nullable = false)
    private Boolean esCorrecta;

    @Column(name = "partida_id", nullable = false)
    private Integer idPartida; 
}
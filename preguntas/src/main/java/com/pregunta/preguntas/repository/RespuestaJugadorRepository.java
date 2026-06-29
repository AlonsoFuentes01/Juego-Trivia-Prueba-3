package com.pregunta.preguntas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pregunta.preguntas.model.RespuestaJugador;

public interface RespuestaJugadorRepository extends JpaRepository<RespuestaJugador, Integer> {

    List<RespuestaJugador> findByIdPartida(Integer idPartida);

}

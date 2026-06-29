package com.pregunta.preguntas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pregunta.preguntas.model.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta, Integer>{

    List<Respuesta> findByPregunta_IdPregunta(Integer idPregunta); 
    Respuesta findByPregunta_IdPreguntaAndCorrectaTrue(Integer idPregunta);

}

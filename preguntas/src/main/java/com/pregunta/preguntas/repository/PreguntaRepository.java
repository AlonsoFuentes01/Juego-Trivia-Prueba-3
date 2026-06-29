package com.pregunta.preguntas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import com.pregunta.preguntas.model.Pregunta;

public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {


    List<Pregunta> findByIdNivel(Integer idNivel);

    @Query("SELECT p FROM Pregunta p WHERE p.idNivel = :idNivel AND p.idPregunta > :idPregunta ORDER BY p.idPregunta ASC")
    List<Pregunta> findSiguientePregunta(
        @Param("idNivel") Integer idNivel, 
        @Param("idPregunta") Integer idPregunta, 
        Pageable pageable
    );
}

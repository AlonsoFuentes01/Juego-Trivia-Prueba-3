package com.pregunta.preguntas.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pregunta.preguntas.DTO.PreguntaDTO;
import com.pregunta.preguntas.model.Pregunta;
import com.pregunta.preguntas.repository.PreguntaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PreguntaService {

    private static final Logger log = LoggerFactory.getLogger(PreguntaService.class);

    @Autowired
    private PreguntaRepository preguntaRepository;

    public List<Pregunta> findAll() {
        return preguntaRepository.findAll();
    }

    public List<PreguntaDTO> obtenerTodos() {
        log.info("Listando todas las preguntas");
        return preguntaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public PreguntaDTO buscarPreguntaDTOPorId(Integer id) {
        log.info("Buscando pregunta con ID: {}", id);
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + id));
        return convertirADTO(pregunta);
    }

    public Pregunta buscarPreguntaPorId(Integer id) {
        return preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + id));
    }

    public List<PreguntaDTO> buscarPorNivel(Integer idNivel) {
        log.info("Buscando preguntas del nivel ID: {}", idNivel);
        return preguntaRepository.findByIdNivel(idNivel).stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Pregunta actualizarPregunta(Integer id, Pregunta pregunta) {
        Pregunta preguntaExistente = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + id));
        preguntaExistente.setEnunciado(pregunta.getEnunciado());
        preguntaExistente.setCategoria(pregunta.getCategoria());
        preguntaExistente.setDificultad(pregunta.getDificultad());
        preguntaExistente.setPuntaje(pregunta.getPuntaje());
        log.info("Pregunta ID {} actualizada", id);
        return preguntaRepository.save(preguntaExistente);
    }

    private PreguntaDTO convertirADTO(Pregunta pregunta) {
        PreguntaDTO preguntaDTO = new PreguntaDTO();
        preguntaDTO.setEnunciado(pregunta.getEnunciado());
        preguntaDTO.setDificultad(pregunta.getDificultad());
        preguntaDTO.setCategoria(pregunta.getCategoria());
        preguntaDTO.setPuntaje(pregunta.getPuntaje());
        preguntaDTO.setTipo(pregunta.getTipo());
        return preguntaDTO;
    }

}

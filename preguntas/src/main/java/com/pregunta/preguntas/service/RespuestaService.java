package com.pregunta.preguntas.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pregunta.preguntas.DTO.RespuestaDTO;
import com.pregunta.preguntas.model.Respuesta;
import com.pregunta.preguntas.repository.RespuestaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RespuestaService {
    private static final Logger log = LoggerFactory.getLogger(RespuestaService.class);

    @Autowired
    private RespuestaRepository respuestaRepository;

    public List<Respuesta> findAll() {
        return respuestaRepository.findAll();
    }

    public List<RespuestaDTO> obtenerTodos() {
        log.info("Listando todas las respuestas");
        return respuestaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }
    public List<RespuestaDTO> buscarPorPregunta(Integer idPregunta) {
    return respuestaRepository.findByPregunta_IdPregunta(idPregunta)
            .stream()
            .map(this::convertirADTO)
            .toList();
    }

    public RespuestaDTO buscarRespuestaDTOPorId(Integer id) {
        log.info("Buscando respuesta con ID: {}", id);
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + id));
        return convertirADTO(respuesta);
    }

    public Respuesta buscarRespuestaPorId(Integer id) {
        return respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + id));
    }

    public boolean esCorrecta(Integer idRespuesta) {
        Respuesta respuesta = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + idRespuesta));
        log.info("Verificando si respuesta ID {} es correcta: {}", idRespuesta, respuesta.getCorrecta());
        return respuesta.getCorrecta();
    }

    public Respuesta actualizarRespuesta(Integer id, Respuesta respuesta) {
        Respuesta respuestaExistente = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + id));
        respuestaExistente.setContenido(respuesta.getContenido());
        respuestaExistente.setCorrecta(respuesta.getCorrecta());
        log.info("Respuesta ID {} actualizada", id);
        return respuestaRepository.save(respuestaExistente);
    }

    private RespuestaDTO convertirADTO(Respuesta respuesta) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setIdRespuesta(respuesta.getIdRespuesta());
        respuestaDTO.setContenido(respuesta.getContenido());
        return respuestaDTO;
    }
}

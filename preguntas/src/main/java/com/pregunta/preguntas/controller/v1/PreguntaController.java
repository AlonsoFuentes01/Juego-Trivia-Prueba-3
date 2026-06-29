package com.pregunta.preguntas.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pregunta.preguntas.DTO.PreguntaDTO;
import com.pregunta.preguntas.DTO.RespuestaDTO;
import com.pregunta.preguntas.model.Pregunta;
import com.pregunta.preguntas.service.PreguntaService;
import com.pregunta.preguntas.service.RespuestaService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/preguntas")
public class PreguntaController {
    @Autowired
    private PreguntaService preguntaService;
    @Autowired
    private RespuestaService respuestaService;

    @GetMapping
    public ResponseEntity<List<PreguntaDTO>> todasLasPreguntas() {
        List<PreguntaDTO> preguntas = preguntaService.obtenerTodos();
        if (preguntas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(preguntas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            PreguntaDTO pregunta = preguntaService.buscarPreguntaDTOPorId(id);
            return ResponseEntity.ok(pregunta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/nivel/{idNivel}")
    public ResponseEntity<?> buscarPorNivel(@PathVariable Integer idNivel) {
        try {
            List<PreguntaDTO> preguntas = preguntaService.buscarPorNivel(idNivel);
            if (preguntas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(preguntas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPregunta(@PathVariable Integer id,
            @Valid @RequestBody Pregunta pregunta) {
        try {
            Pregunta actualizada = preguntaService.actualizarPregunta(id, pregunta);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/opciones")
    public ResponseEntity<?> buscarPorIdConOpciones(@PathVariable Integer id) {
    try {
        PreguntaDTO pregunta = preguntaService.buscarPreguntaDTOPorId(id);
        List<RespuestaDTO> opciones = respuestaService.buscarPorPregunta(id);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("pregunta", pregunta);
        respuesta.put("opciones", opciones);
        
        return ResponseEntity.ok(respuesta);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    
    }

}

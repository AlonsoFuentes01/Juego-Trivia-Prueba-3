package com.pregunta.preguntas.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pregunta.preguntas.DTO.RespuestaDTO;
import com.pregunta.preguntas.model.Respuesta;
import com.pregunta.preguntas.service.RespuestaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @GetMapping
    public ResponseEntity<List<RespuestaDTO>> todasLasRespuestas() {
        List<RespuestaDTO> respuestas = respuestaService.obtenerTodos();
        if (respuestas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            RespuestaDTO respuesta = respuestaService.buscarRespuestaDTOPorId(id);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRespuesta(@PathVariable Integer id,
            @Valid @RequestBody Respuesta respuesta) {
        try {
            Respuesta actualizada = respuestaService.actualizarRespuesta(id, respuesta);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}

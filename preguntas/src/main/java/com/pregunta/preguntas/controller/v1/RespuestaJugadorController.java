package com.pregunta.preguntas.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pregunta.preguntas.DTO.ResultadoRespuestaDTO;
import com.pregunta.preguntas.model.RespuestaJugador;
import com.pregunta.preguntas.service.RespuestaJugadorService;

@RestController
@RequestMapping("/api/v1/respuestas-jugador")
public class RespuestaJugadorController {

    @Autowired
    private RespuestaJugadorService respuestaJugadorService;

    @GetMapping
    public ResponseEntity<List<RespuestaJugador>> listar() {
        return ResponseEntity.ok(respuestaJugadorService.listar());
    }

    @GetMapping("/partida/{idPartida}")
    public ResponseEntity<List<RespuestaJugador>> listarPorPartida(@PathVariable Integer idPartida) {
        return ResponseEntity.ok(respuestaJugadorService.listarPorPartida(idPartida));
    }

    @PostMapping("/responder")
    public ResponseEntity<?> responder(
            @RequestParam Integer idPartida,
            @RequestParam Integer idRespuesta) {
        try {
            ResultadoRespuestaDTO resultado = respuestaJugadorService.responder(idPartida, idRespuesta);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

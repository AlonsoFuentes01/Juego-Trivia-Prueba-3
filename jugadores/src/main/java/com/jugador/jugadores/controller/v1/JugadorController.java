package com.jugador.jugadores.controller.v1;

import com.jugador.jugadores.DTO.JugadorDTO;
import com.jugador.jugadores.DTO.RankingDTO;
import com.jugador.jugadores.model.Jugador;
import com.jugador.jugadores.service.JugadorService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("jugadorControllerV1")
@RequestMapping("/api/v1/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> todos() {
        return new ResponseEntity<>(jugadorService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(jugadorService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Jugador jugador) {
        try {
            return new ResponseEntity<>(jugadorService.guardar(jugador), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar jugador: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Jugador datosNuevos) {
        try {
            return new ResponseEntity<>(jugadorService.actualizarJugador(id, datosNuevos), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/sumar-puntos")
    public ResponseEntity<?> sumarPuntos(@PathVariable Integer id, @RequestParam Integer puntos) {
        try {
            return new ResponseEntity<>(jugadorService.actualizarPuntos(id, puntos), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
    @GetMapping("/ranking")
    public ResponseEntity<List<RankingDTO>> ranking() {
        return new ResponseEntity<>(jugadorService.obtenerRanking(), HttpStatus.OK);
    }

}

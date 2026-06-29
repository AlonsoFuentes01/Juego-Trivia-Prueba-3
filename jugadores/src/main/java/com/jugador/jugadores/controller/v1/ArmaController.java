package com.jugador.jugadores.controller.v1;

import com.jugador.jugadores.DTO.ArmaDTO;
import com.jugador.jugadores.model.Arma;
import com.jugador.jugadores.service.ArmaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("armaControllerV1")
@RequestMapping("/api/v1/armas")
public class ArmaController {

    @Autowired
    private ArmaService armaService;

    @GetMapping
    public ResponseEntity<List<ArmaDTO>> todas() {
        return new ResponseEntity<>(armaService.obtenerTodas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(armaService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscar-por-jugador/{jugadorId}")
    public ResponseEntity<?> porJugador(@PathVariable Integer jugadorId) {
        try {
            return new ResponseEntity<>(armaService.buscarPorJugador(jugadorId), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Arma arma) {
        try {
            return new ResponseEntity<>(armaService.guardar(arma), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error al registrar arma: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            armaService.eliminar(id);
            return new ResponseEntity<>("Arma eliminada del arsenal", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

package com.partida.partidas.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.partida.partidas.DTO.PartidaDTO;
import com.partida.partidas.service.PartidaService;

@RestController("partidaControllerV1")
@RequestMapping("/api/v1/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<PartidaDTO>> listarPartidas() {
        List<PartidaDTO> partidas = partidaService.listarPartidas();
        return ResponseEntity.ok(partidas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            PartidaDTO partida = partidaService.buscarPartidaPorId(id);
            return ResponseEntity.ok(partida);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarPartida(@RequestParam Integer idJugador) {
        try {
            PartidaDTO nueva = partidaService.crearPartida(idJugador);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/sumar-puntaje")
    public ResponseEntity<?> sumarPuntaje(@PathVariable Integer id, @RequestParam Integer puntaje) {
        try {
            PartidaDTO actualizada = partidaService.sumarPuntaje(id, puntaje);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarPartida(@PathVariable Integer id) {
        try {
            PartidaDTO finalizada = partidaService.finalizarPartida(id);
            return ResponseEntity.ok(finalizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPartida(@PathVariable Integer id) {
        try {
            partidaService.eliminarPartida(id);
            return ResponseEntity.ok("Partida ID " + id + " eliminada exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

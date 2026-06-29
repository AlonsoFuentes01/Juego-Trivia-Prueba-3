package com.partida.partidas.controller.v1;


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

import com.partida.partidas.DTO.JefeDTO;
import com.partida.partidas.model.Jefe;
import com.partida.partidas.service.JefeService;

import jakarta.validation.Valid;

@RestController("jefeControllerV1")
@RequestMapping("/api/v1/jefes")
public class JefeController {

    @Autowired private JefeService jefeService;

    @GetMapping
    public ResponseEntity<List<JefeDTO>> todosLosJefes() {
        List<JefeDTO> jefes = jefeService.obtenerTodos();
        if (jefes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jefes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            JefeDTO jefe = jefeService.buscarJefePorId(id);
            return ResponseEntity.ok(jefe);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarJefe(@PathVariable Integer id,
            @Valid @RequestBody Jefe jefe) {
        try {
            Jefe actualizado = jefeService.actualizarJefe(id, jefe);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

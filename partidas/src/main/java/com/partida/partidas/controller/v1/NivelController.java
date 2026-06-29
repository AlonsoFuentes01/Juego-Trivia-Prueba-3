package com.partida.partidas.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.partida.partidas.DTO.NivelDTO;
import com.partida.partidas.service.NivelService;


@RestController("nivelControllerV1")
@RequestMapping("/api/v1/niveles")
public class NivelController {

    @Autowired
    private NivelService nivelService;

    @GetMapping
    public ResponseEntity<List<NivelDTO>> todosLosNiveles() {
        List<NivelDTO> niveles = nivelService.obtenerTodos();
        if (niveles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(niveles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            NivelDTO nivel = nivelService.buscarNivelPorId(id);
            return ResponseEntity.ok(nivel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

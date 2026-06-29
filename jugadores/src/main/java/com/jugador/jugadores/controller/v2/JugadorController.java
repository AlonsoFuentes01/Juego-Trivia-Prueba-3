package com.jugador.jugadores.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jugador.jugadores.DTO.JugadorDTO;
import com.jugador.jugadores.DTO.RankingDTO;
import com.jugador.jugadores.assemblers.JugadorModelAssembler;
import com.jugador.jugadores.model.Jugador;
import com.jugador.jugadores.service.JugadorService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.validation.Valid;

@RestController("jugadorControllerV2")
@RequestMapping("/api/v2/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private JugadorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<JugadorDTO>>> todos() {
        List<EntityModel<JugadorDTO>> jugadores = jugadorService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (jugadores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                jugadores,
                linkTo(methodOn(JugadorController.class).todos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JugadorDTO>> porId(@PathVariable Integer id) {
        try {
            JugadorDTO dto = jugadorService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JugadorDTO>> registrar(@Valid @RequestBody Jugador jugador) {
        try {
            JugadorDTO nuevo = jugadorService.guardar(jugador);
            return ResponseEntity
                    .created(linkTo(methodOn(JugadorController.class).porId(nuevo.getIdJugador())).toUri())
                    .body(assembler.toModel(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JugadorDTO>> actualizar(@PathVariable Integer id, @RequestBody Jugador datosNuevos) {
        try {
            return ResponseEntity.ok(assembler.toModel(jugadorService.actualizarJugador(id, datosNuevos)));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{id}/puntos", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JugadorDTO>> sumarPuntos(@PathVariable Integer id, @RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(assembler.toModel(jugadorService.actualizarPuntos(id, cantidad)));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingDTO>> ranking() {
        return ResponseEntity.ok(jugadorService.obtenerRanking());
    }
}

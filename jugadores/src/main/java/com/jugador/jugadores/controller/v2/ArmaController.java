package com.jugador.jugadores.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jugador.jugadores.DTO.ArmaDTO;
import com.jugador.jugadores.assemblers.ArmaModelAssembler;
import com.jugador.jugadores.model.Arma;
import com.jugador.jugadores.service.ArmaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.validation.Valid;

@RestController("armaControllerV2")
@RequestMapping("/api/v2/armas")
public class ArmaController {

    @Autowired
    private ArmaService armaService;

    @Autowired
    private ArmaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ArmaDTO>>> todas() {
        List<EntityModel<ArmaDTO>> armas = armaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (armas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                armas,
                linkTo(methodOn(ArmaController.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ArmaDTO>> porId(@PathVariable Integer id) {
        try {
            ArmaDTO dto = armaService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/buscar-por-jugador/{jugadorId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ArmaDTO>>> porJugador(@PathVariable Integer jugadorId) {
        try {
            List<EntityModel<ArmaDTO>> armas = armaService.buscarPorJugador(jugadorId).stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(CollectionModel.of(
                    armas,
                    linkTo(methodOn(ArmaController.class).porJugador(jugadorId)).withSelfRel()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ArmaDTO>> registrar(@Valid @RequestBody Arma arma) {
        try {
            ArmaDTO nueva = armaService.guardar(arma);
            return ResponseEntity
                    .created(linkTo(methodOn(ArmaController.class).porId(nueva.getId())).toUri())
                    .body(assembler.toModel(nueva));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            armaService.eliminar(id);
            return ResponseEntity.ok("Arma eliminada del arsenal");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

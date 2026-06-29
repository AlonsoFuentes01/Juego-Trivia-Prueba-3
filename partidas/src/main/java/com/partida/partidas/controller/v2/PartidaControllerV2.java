package com.partida.partidas.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partida.partidas.DTO.PartidaDTO;
import com.partida.partidas.assemblers.PartidaModelAssembler;
import com.partida.partidas.service.PartidaService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("partidaControllerV2")
@RequestMapping("/api/v2/partidas")
public class PartidaControllerV2 {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private PartidaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PartidaDTO>>> todas() {
        List<EntityModel<PartidaDTO>> partidas = partidaService.listarPartidas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (partidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                partidas,
                linkTo(methodOn(PartidaControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PartidaDTO>> porId(@PathVariable Integer id) {
        try {
            PartidaDTO dto = partidaService.buscarPartidaPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PartidaDTO>> registrar(@Valid @RequestBody Integer idJugador) {
        try {
            PartidaDTO newPartida = partidaService.crearPartida(idJugador);
            
            return ResponseEntity
                    .created(linkTo(methodOn(PartidaControllerV2.class).porId(newPartida.getIdPartida())).toUri())
                    .body(assembler.toModel(newPartida));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
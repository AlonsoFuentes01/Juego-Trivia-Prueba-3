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

import com.partida.partidas.DTO.JefeDTO;
import com.partida.partidas.assemblers.JefeModelAssembler;
import com.partida.partidas.model.Jefe;
import com.partida.partidas.service.JefeService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("jefeControllerV2")
@RequestMapping("/api/v2/jefes")
public class JefeControllerV2 {

    @Autowired
    private JefeService jefeService;

    @Autowired
    private JefeModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<JefeDTO>>> todas() {
        List<EntityModel<JefeDTO>> jefes = jefeService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (jefes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                jefes,
                linkTo(methodOn(JefeControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JefeDTO>> porId(@PathVariable Integer id) {
        try {
            JefeDTO dto = jefeService.buscarJefePorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JefeDTO>> registrar(@Valid @RequestBody Jefe jefe) {
    try {
        Jefe nuevoJefe = jefeService.actualizarJefe(jefe.getIdJefe(), jefe); 
        JefeDTO dto = jefeService.buscarJefePorId(nuevoJefe.getIdJefe());
        return ResponseEntity
                .created(linkTo(methodOn(JefeControllerV2.class).porId(dto.getIdJefe())).toUri())
                .body(assembler.toModel(dto));
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
}
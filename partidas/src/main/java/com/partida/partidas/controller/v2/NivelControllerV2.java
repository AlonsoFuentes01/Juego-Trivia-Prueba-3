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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partida.partidas.DTO.NivelDTO;
import com.partida.partidas.assemblers.NivelModelAssembler;
import com.partida.partidas.service.NivelService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("nivelControllerV2")
@RequestMapping("/api/v2/niveles")
public class NivelControllerV2 {

    @Autowired
    private NivelService nivelService;

    @Autowired
    private NivelModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<NivelDTO>>> todas() {
        List<EntityModel<NivelDTO>> niveles = nivelService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (niveles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                niveles,
                linkTo(methodOn(NivelControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<NivelDTO>> porId(@PathVariable Integer id) {
        try {
            NivelDTO dto = nivelService.buscarNivelPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
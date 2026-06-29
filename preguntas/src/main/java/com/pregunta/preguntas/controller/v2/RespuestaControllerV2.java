package com.pregunta.preguntas.controller.v2;


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

import com.pregunta.preguntas.DTO.RespuestaDTO;
import com.pregunta.preguntas.assemblers.RespuestaModelAssembler;
import com.pregunta.preguntas.service.RespuestaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/respuestas")
public class RespuestaControllerV2 {

    @Autowired private RespuestaService respuestaService;
    @Autowired private RespuestaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<RespuestaDTO>>> todas() {
        List<EntityModel<RespuestaDTO>> items = respuestaService.obtenerTodos().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(items, linkTo(methodOn(RespuestaControllerV2.class).todas()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RespuestaDTO>> porId(@PathVariable Integer id) {
        RespuestaDTO dto = respuestaService.buscarRespuestaDTOPorId(id);
        return (dto != null) ? ResponseEntity.ok(assembler.toModel(dto)) : ResponseEntity.notFound().build();
    }
}
package com.pregunta.preguntas.controller.v2;

import com.pregunta.preguntas.DTO.PreguntaDTO;
import com.pregunta.preguntas.assemblers.PreguntaModelAssembler;
import com.pregunta.preguntas.model.Pregunta;
import com.pregunta.preguntas.service.PreguntaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/preguntas")
public class PreguntaControllerV2 {

    @Autowired private PreguntaService preguntaService;
    @Autowired private PreguntaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PreguntaDTO>>> todas() {
        List<EntityModel<PreguntaDTO>> items = preguntaService.obtenerTodos().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(items, linkTo(methodOn(PreguntaControllerV2.class).todas()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PreguntaDTO>> porId(@PathVariable Integer id) {
        PreguntaDTO dto = preguntaService.buscarPreguntaDTOPorId(id);
        return (dto != null) ? ResponseEntity.ok(assembler.toModel(dto)) : ResponseEntity.notFound().build();
    }
}
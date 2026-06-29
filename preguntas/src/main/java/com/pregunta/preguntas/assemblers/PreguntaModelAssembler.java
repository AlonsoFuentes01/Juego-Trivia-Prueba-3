package com.pregunta.preguntas.assemblers;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
 
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import com.pregunta.preguntas.DTO.PreguntaDTO;
import com.pregunta.preguntas.controller.v1.PreguntaController;

@Component
public class PreguntaModelAssembler implements RepresentationModelAssembler<PreguntaDTO, EntityModel<PreguntaDTO>>{

    @Override
    public EntityModel<PreguntaDTO> toModel(PreguntaDTO pregunta) {
        return EntityModel.of(pregunta,
                linkTo(methodOn(PreguntaController.class).buscarPorId(pregunta.getIdPregunta())).withSelfRel(),
                linkTo(methodOn(PreguntaController.class).todasLasPreguntas()).withRel("preguntas"),
                linkTo(methodOn(PreguntaController.class).buscarPorIdConOpciones(pregunta.getIdPregunta())).withRel("opciones"),
                linkTo(methodOn(PreguntaController.class).buscarPorNivel(pregunta.getIdNivel())).withRel("preguntas-del-nivel")
        );
    }

}

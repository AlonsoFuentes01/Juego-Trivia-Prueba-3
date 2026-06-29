package com.pregunta.preguntas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;
 
import com.pregunta.preguntas.DTO.RespuestaDTO;
import com.pregunta.preguntas.controller.v1.RespuestaController;
 
@Component
public class RespuestaModelAssembler implements RepresentationModelAssembler<RespuestaDTO, EntityModel<RespuestaDTO>> {
 
    @Override
    public EntityModel<RespuestaDTO> toModel(RespuestaDTO respuesta) {
        return EntityModel.of(respuesta,
                linkTo(methodOn(RespuestaController.class).buscarPorId(respuesta.getIdRespuesta())).withSelfRel(),
                linkTo(methodOn(RespuestaController.class).todasLasRespuestas()).withRel("respuestas")
        );
    }
}
 
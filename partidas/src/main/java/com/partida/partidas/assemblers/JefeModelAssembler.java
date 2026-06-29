package com.partida.partidas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.partida.partidas.controller.v2.JefeControllerV2; 
import com.partida.partidas.DTO.JefeDTO; 

@Component
public class JefeModelAssembler implements RepresentationModelAssembler<JefeDTO, EntityModel<JefeDTO>> {

    @Override
    public EntityModel<JefeDTO> toModel(JefeDTO jefe) {
        return EntityModel.of(jefe,
                linkTo(methodOn(JefeControllerV2.class).porId(jefe.getIdJefe())).withSelfRel(),
                linkTo(methodOn(JefeControllerV2.class).todas()).withRel("jefes")
        );
    }
}
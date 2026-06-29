package com.partida.partidas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.partida.partidas.controller.v2.NivelControllerV2;
import com.partida.partidas.DTO.NivelDTO; 

@Component
public class NivelModelAssembler implements RepresentationModelAssembler<NivelDTO, EntityModel<NivelDTO>> {

    @Override
    public EntityModel<NivelDTO> toModel(NivelDTO nivel) {
        return EntityModel.of(nivel,
                linkTo(methodOn(NivelControllerV2.class).porId(nivel.getIdNivel())).withSelfRel(),
                linkTo(methodOn(NivelControllerV2.class).todas()).withRel("niveles")
        );
    }
}
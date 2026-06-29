package com.partida.partidas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.partida.partidas.controller.v2.PartidaControllerV2;
import com.partida.partidas.DTO.PartidaDTO; 

@Component
public class PartidaModelAssembler implements RepresentationModelAssembler<PartidaDTO, EntityModel<PartidaDTO>> {

    @Override
    public EntityModel<PartidaDTO> toModel(PartidaDTO partida) {
        return EntityModel.of(partida,
                linkTo(methodOn(PartidaControllerV2.class).porId(partida.getIdPartida())).withSelfRel(),
                linkTo(methodOn(PartidaControllerV2.class).todas()).withRel("partidas")
        );
    }
}
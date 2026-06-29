package com.jugador.jugadores.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jugador.jugadores.DTO.ArmaDTO;
import com.jugador.jugadores.controller.v2.ArmaController;

@Component
public class ArmaModelAssembler implements RepresentationModelAssembler<ArmaDTO, EntityModel<ArmaDTO>> {

    @Override
    public EntityModel<ArmaDTO> toModel(ArmaDTO arma) {
        return EntityModel.of(arma,
                linkTo(methodOn(ArmaController.class).porId(arma.getId())).withSelfRel(),
                linkTo(methodOn(ArmaController.class).todas()).withRel("armas"),
                linkTo(methodOn(ArmaController.class).porJugador(arma.getJugadorId())).withRel("armas-del-jugador")
        );
    }
}

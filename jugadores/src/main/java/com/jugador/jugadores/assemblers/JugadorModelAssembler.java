package com.jugador.jugadores.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jugador.jugadores.DTO.JugadorDTO;
import com.jugador.jugadores.controller.v2.JugadorController;

@Component
public class JugadorModelAssembler implements RepresentationModelAssembler<JugadorDTO, EntityModel<JugadorDTO>> {

    @Override
    public EntityModel<JugadorDTO> toModel(JugadorDTO jugador) {
        return EntityModel.of(jugador,
                linkTo(methodOn(JugadorController.class).porId(jugador.getIdJugador())).withSelfRel(),
                linkTo(methodOn(JugadorController.class).todos()).withRel("jugadores"),
                linkTo(methodOn(JugadorController.class).ranking()).withRel("ranking")
        );
    }
}

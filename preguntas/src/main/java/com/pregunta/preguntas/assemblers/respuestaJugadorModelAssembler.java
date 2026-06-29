package com.pregunta.preguntas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;
 
import com.pregunta.preguntas.DTO.RespuestaJugadorDTO;
import com.pregunta.preguntas.controller.v1.RespuestaJugadorController;
 
@Component
public class respuestaJugadorModelAssembler implements RepresentationModelAssembler<RespuestaJugadorDTO, EntityModel<RespuestaJugadorDTO>> {
 
    @Override
    public EntityModel<RespuestaJugadorDTO> toModel(RespuestaJugadorDTO respuestaJugador) {
        return EntityModel.of(respuestaJugador,
                linkTo(methodOn(RespuestaJugadorController.class).listar()).withRel("respuestas-jugador"),
                linkTo(methodOn(RespuestaJugadorController.class).listarPorPartida(respuestaJugador.getIdPartida())).withRel("respuestas-de-la-partida")
        );
    }
}
 
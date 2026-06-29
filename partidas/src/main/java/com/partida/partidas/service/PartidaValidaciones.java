package com.partida.partidas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.partida.partidas.DTO.JugadorDTOext;
import com.partida.partidas.DTO.PartidaDTO;
import com.partida.partidas.model.Partida;

import reactor.core.publisher.Mono;

@Service
public class PartidaValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public JugadorDTOext obtenerJugador(Integer id) {
        JugadorDTOext jugadorRe = new JugadorDTOext();
        try {
            JugadorDTOext resultado = webClientBuilder.build()
                .get()
                .uri("http://jugadores/api/v1/jugadores/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> Mono.empty())
                .bodyToMono(JugadorDTOext.class)
                .block();

            if (resultado != null) return resultado;

            jugadorRe.setIdJugador(id);
            jugadorRe.setNombreJugador("jugador no encontrado");
            return jugadorRe;

        } catch (Exception e) {
            jugadorRe.setIdJugador(id);
            jugadorRe.setNombreJugador("no se pudo conectar con jugadores");
            return jugadorRe;
        }
    }

    public PartidaDTO convertirADTO(Partida partida) {
        PartidaDTO dto = new PartidaDTO();
        JugadorDTOext jugador = obtenerJugador(partida.getIdJugador());
        dto.setIdPartida(partida.getIdPartida());
        dto.setPuntajeTotal(partida.getPuntajeTotal());
        dto.setNombreJugador(jugador.getNombreJugador());
        dto.setNivel(jugador.getNivelJugador());
        dto.setJugadorDTOext(jugador);
        return dto;
    }
}

package com.jugador.jugadores.service;

import com.jugador.jugadores.DTO.ArmaDTO;
import com.jugador.jugadores.DTO.JugadorDTO;
import com.jugador.jugadores.DTO.RankingDTO;
import com.jugador.jugadores.model.Arma;
import com.jugador.jugadores.model.Jugador;
import com.jugador.jugadores.repository.ArmaRepository;
import com.jugador.jugadores.repository.JugadorRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class JugadorService {


private static final Logger log = Logger.getLogger(JugadorService.class.getName());
    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private ArmaRepository armaRepository;


    public List<JugadorDTO> obtenerTodos() {
        List<JugadorDTO> lista = new ArrayList<>();
        for (Jugador j : jugadorRepository.findAll()) {
            lista.add(convertirDTO(j));
        }
        return lista;
    }

    public JugadorDTO buscarPorId(Integer id) {
        Jugador j = jugadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Este guerrero aún no se adentra por las mazmorras"));
        return convertirDTO(j);
    }

    public JugadorDTO guardar(Jugador jugador) {
    if (jugadorRepository.existsByNombre(jugador.getNombre())) {
        throw new RuntimeException("Ya existe un jugador con el nombre: " + jugador.getNombre());
    }

    if (jugador.getPuntos() == null) jugador.setPuntos(0);
    if (jugador.getNivelActual() == null) jugador.setNivelActual(1);
    Jugador guardado = jugadorRepository.save(jugador);
    return convertirDTO(guardado);
}

public JugadorDTO actualizarJugador(Integer id, Jugador nuevo) {
    Jugador j = jugadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

    if (nuevo.getNombre() != null) {
        boolean nombreTomado = jugadorRepository.existsByNombre(nuevo.getNombre());
        if (nombreTomado && !nuevo.getNombre().equals(j.getNombre())) {
            throw new RuntimeException("Ya existe un jugador con el nombre: " + nuevo.getNombre());
        }
        j.setNombre(nuevo.getNombre());
    }
    if (nuevo.getOrigen() != null) {
        j.setOrigen(nuevo.getOrigen());
    }
    if (nuevo.getNivelActual() != null) {
        j.setNivelActual(nuevo.getNivelActual());
    }
    return convertirDTO(jugadorRepository.save(j));
}

    public JugadorDTO actualizarPuntos(Integer id, Integer puntos) {
        Jugador j = jugadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        j.setPuntos(j.getPuntos() + puntos);
        return convertirDTO(jugadorRepository.save(j));
    }


    public List<RankingDTO> obtenerRanking() {
        List<Jugador> jugadores = jugadorRepository.findAllOrderByPuntosDesc();
        List<RankingDTO> ranking = new ArrayList<>();
        int posicion = 1;
        for (Jugador j : jugadores) {
            RankingDTO r = new RankingDTO();
            r.setPosicion(posicion++);
            r.setNombreJugador(j.getNombre());
            r.setOrigenJugador(j.getOrigen());
            r.setPuntos(j.getPuntos());
            r.setNivelActual(j.getNivelActual());
            ranking.add(r);
        }
        return ranking;
    }
    public JugadorDTO sumarPuntos(Integer id, Integer puntos) {
    Jugador jugador = jugadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado con ID: " + id));
    jugador.setPuntos(jugador.getPuntos() + puntos);
    log.info("Sumando " + puntos + " puntos al jugador ID: " + id);
    return convertirDTO(jugadorRepository.save(jugador));
}

    private JugadorDTO convertirDTO(Jugador jugador) {
        JugadorDTO dto = new JugadorDTO();
        dto.setIdJugador(jugador.getIdJugador());
        dto.setNombreJugador(jugador.getNombre());
        dto.setOrigenJugador(jugador.getOrigen());
        dto.setPuntosJugador(jugador.getPuntos());
        dto.setNivelJugador(jugador.getNivelActual());
        dto.setArmas(obtenerArmasDeJugador(jugador.getIdJugador()));
        return dto;
    }

    private List<ArmaDTO> obtenerArmasDeJugador(Integer jugadorId) {
        List<ArmaDTO> lista = new ArrayList<>();
        for (Arma a : armaRepository.findByJugadorId(jugadorId)) {
            ArmaDTO dto = new ArmaDTO();
            dto.setId(a.getId());
            dto.setJugadorId(a.getJugadorId());
            dto.setNombreArma(a.getNombreArma());
            lista.add(dto);
        }
        return lista;
    }
    
}

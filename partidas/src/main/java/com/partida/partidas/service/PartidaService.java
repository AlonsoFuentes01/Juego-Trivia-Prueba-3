package com.partida.partidas.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partida.partidas.DTO.PartidaDTO;
import com.partida.partidas.model.Partida;
import com.partida.partidas.repository.PartidaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PartidaService {

    private static final Logger log = Logger.getLogger(PartidaService.class.getName());

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private PartidaValidaciones partidaValidaciones;

    public List<PartidaDTO> listarPartidas() {
        log.info("Listando todas las partidas");
        return partidaRepository.findAll().stream()
                .map(partidaValidaciones::convertirADTO)
                .toList();
    }

    public PartidaDTO buscarPartidaPorId(Integer id) {
        log.info("Buscando partida con ID: " + id);
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada con ID: " + id));
        return partidaValidaciones.convertirADTO(partida);
    }

    public Partida buscarEntidadPorId(Integer id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada con ID: " + id));
    }

    public PartidaDTO crearPartida(Integer idJugador) {
        if(idJugador == null || idJugador <= 0){

        }
        Partida partida = Partida.builder()
                .idJugador(idJugador)
                .puntajeTotal(0)
                .finalizada(false)
                .build();
        Partida nueva = partidaRepository.save(partida);
        log.info("Partida creada con ID " + nueva.getIdPartida() + " para jugador ID " + idJugador);
        return partidaValidaciones.convertirADTO(nueva);
    }

    public PartidaDTO sumarPuntaje(Integer idPartida, Integer puntaje) {
        Partida partida = partidaRepository.findById(idPartida)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada con ID: " + idPartida));
        partida.setPuntajeTotal(partida.getPuntajeTotal() + puntaje);
        Partida actualizada = partidaRepository.save(partida);
        log.info("Puntaje sumado a partida ID " + idPartida + ": +" + puntaje + " -> total: " + actualizada.getPuntajeTotal());
        return partidaValidaciones.convertirADTO(actualizada);
    }

    public PartidaDTO finalizarPartida(Integer idPartida) {
        Partida partida = partidaRepository.findById(idPartida)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada con ID: " + idPartida));
        partida.setFinalizada(true);
        Partida actualizada = partidaRepository.save(partida);
        log.info("Partida ID " + idPartida + " finalizada con puntaje total: " + actualizada.getPuntajeTotal());
        return partidaValidaciones.convertirADTO(actualizada);
    }

    public void eliminarPartida(Integer id) {
        if (!partidaRepository.existsById(id)) {
            throw new RuntimeException("Partida no encontrada con ID: " + id);
        }
        partidaRepository.deleteById(id);
        log.info("Partida ID " + id + " eliminada");
    }

    public void verificarPartidaActiva(Integer idPartida) {
    Partida partida = partidaRepository.findById(idPartida)
            .orElseThrow(() -> new RuntimeException("Partida no encontrada con ID: " + idPartida));
    if (partida.getFinalizada()) {
        throw new RuntimeException("La partida ID " + idPartida + " ya está finalizada");
    }
}



}

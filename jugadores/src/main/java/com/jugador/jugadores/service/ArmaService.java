package com.jugador.jugadores.service;

import com.jugador.jugadores.DTO.ArmaDTO;
import com.jugador.jugadores.model.Arma;
import com.jugador.jugadores.repository.ArmaRepository;
import com.jugador.jugadores.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArmaService {

    @Autowired
    private ArmaRepository armaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<ArmaDTO> obtenerTodas() {
        List<ArmaDTO> lista = new ArrayList<>();
        for (Arma a : armaRepository.findAll()) {
            lista.add(convertirDTO(a));
        }
        return lista;
    }

    public ArmaDTO buscarPorId(Integer id) {
        Arma a = armaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Arma no encontrada en el arsenal"));
        return convertirDTO(a);
    }

    public List<ArmaDTO> buscarPorJugador(Integer jugadorId) {
        jugadorRepository.findById(jugadorId)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        List<ArmaDTO> lista = new ArrayList<>();
        for (Arma a : armaRepository.findByJugadorId(jugadorId)) {
            lista.add(convertirDTO(a));
        }
        return lista;
    }

    public ArmaDTO guardar(Arma arma) {
        jugadorRepository.findById(arma.getJugadorId())
            .orElseThrow(() -> new RuntimeException("No se puede asignar arma: jugador no encontrado"));
        Arma guardada = armaRepository.save(arma);
        return convertirDTO(guardada);
    }

    public void eliminar(Integer id) {
        armaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Arma no encontrada"));
        armaRepository.deleteById(id);
    }

    private ArmaDTO convertirDTO(Arma arma) {
        ArmaDTO dto = new ArmaDTO();
        dto.setId(arma.getId());
        dto.setJugadorId(arma.getJugadorId());
        dto.setNombreArma(arma.getNombreArma());
        return dto;
    }
}

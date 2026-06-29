package com.partida.partidas.service;

import java.util.List;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.partida.partidas.DTO.NivelDTO;

import com.partida.partidas.model.Nivel;

import com.partida.partidas.repository.NivelRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NivelService {

    private static final Logger log = Logger.getLogger(NivelService.class.getName());

    @Autowired private NivelRepository nivelRepository;

    public List<NivelDTO> obtenerTodos() {
        log.info("Listando todos los niveles");
        return nivelRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public NivelDTO buscarNivelPorId(Integer id) {
        log.info("Buscando nivel con ID: " + id);
        Nivel nivel = nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));
        return convertirADTO(nivel);
    }

    public Nivel buscarEntidadPorId(Integer id) {
        return nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));
    }

    private NivelDTO convertirADTO(Nivel nivel) {
        NivelDTO nivelDTO = new NivelDTO();
        nivelDTO.setNumero(nivel.getNumero());
        nivelDTO.setDificultad(nivel.getDificultad());
        return nivelDTO;
    }
}

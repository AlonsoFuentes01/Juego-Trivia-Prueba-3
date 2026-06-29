package com.partida.partidas.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partida.partidas.DTO.JefeDTO;
import com.partida.partidas.model.Jefe;
import com.partida.partidas.model.Nivel;
import com.partida.partidas.repository.JefeRepository;
import com.partida.partidas.repository.NivelRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class JefeService {
    private static final Logger log = Logger.getLogger(JefeService.class.getName());

    @Autowired
    private JefeRepository jefeRepository;

    @Autowired
    private NivelRepository nivelRepository;

    public List<JefeDTO> obtenerTodos() {
        log.info("Listando todos los jefes");
        return jefeRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public JefeDTO buscarJefePorId(Integer id) {
        log.info("Buscando jefe con ID: " + id);
        Jefe jefe = jefeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jefe no encontrado con ID: " + id));
        return convertirADTO(jefe);
    }

    public Optional<Jefe> buscarJefePorNivel(Integer idNivel) {
        log.info("Buscando jefe del nivel ID: {}" + idNivel);
        return jefeRepository.findByNivel_IdNivel(idNivel);
    }

    public Jefe actualizarJefe(Integer id, Jefe jefe) {
        Jefe jefeExistente = jefeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jefe no encontrado con ID: " + id));
        jefeExistente.setNombreJefe(jefe.getNombreJefe());
        jefeExistente.setRecompensa(jefe.getRecompensa());
        if (jefe.getNivel() != null && jefe.getNivel().getIdNivel() != null) {
            Nivel nivel = nivelRepository.findById(jefe.getNivel().getIdNivel())
                    .orElseThrow(() -> new RuntimeException("Nivel no encontrado"));
            jefeExistente.setNivel(nivel);
        }
        log.info("Jefe ID {} actualizado: {}" + id + jefe.getNombreJefe());
        return jefeRepository.save(jefeExistente);
    }

    private JefeDTO convertirADTO(Jefe jefe) {
        JefeDTO jefeDTO = new JefeDTO();
        jefeDTO.setNombreJefe(jefe.getNombreJefe());
        jefeDTO.setNumeroNivel(jefe.getNivel().getNumero());
        jefeDTO.setDificultadNivel(jefe.getNivel().getDificultad());
        jefeDTO.setRecompensa(jefe.getRecompensa());
        return jefeDTO;
    }
}

package com.partida.partidas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.partida.partidas.DTO.NivelDTO;
import com.partida.partidas.model.Nivel;
import com.partida.partidas.repository.NivelRepository;

@SpringBootTest
public class NivelServiceTest {

    @Autowired
    private NivelService nivelService;

    @MockitoBean
    private NivelRepository nivelRepository;

    private Nivel createNivel() {
        Nivel nivel = new Nivel();
        nivel.setIdNivel(1);
        nivel.setNumero(10);
        nivel.setDificultad("Media");
        return nivel;
    }

    @Test
    public void testObtenerTodos() {
        when(nivelRepository.findAll()).thenReturn(List.of(createNivel()));

        List<NivelDTO> result = nivelService.obtenerTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getNumero());
    }

    @Test
    public void testBuscarNivelPorId() {
        when(nivelRepository.findById(1)).thenReturn(Optional.of(createNivel()));

        NivelDTO result = nivelService.buscarNivelPorId(1);

        assertNotNull(result);
        assertEquals(10, result.getNumero());
    }

    @Test
    public void testBuscarEntidadPorId() {
        when(nivelRepository.findById(1)).thenReturn(Optional.of(createNivel()));

        Nivel result = nivelService.buscarEntidadPorId(1);

        assertNotNull(result);
        assertEquals(1, result.getIdNivel());
    }
}
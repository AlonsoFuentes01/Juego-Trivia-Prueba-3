package com.partida.partidas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.partida.partidas.DTO.PartidaDTO;
import com.partida.partidas.model.Partida;
import com.partida.partidas.repository.PartidaRepository;

@SpringBootTest
public class PartidaServiceTest {

    @Autowired
    private PartidaService partidaService;

    @MockitoBean
    private PartidaRepository partidaRepository;

    private Partida createPartida() {
        Partida partida = new Partida();
        partida.setIdPartida(1);
        partida.setIdJugador(99);
        partida.setPuntajeTotal(5000);
        partida.setFinalizada(false);
        return partida;
    }

    @Test
    public void testListarPartidas() {
        when(partidaRepository.findAll()).thenReturn(List.of(createPartida()));

        List<PartidaDTO> result = partidaService.listarPartidas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("fabricioLOLXD", result.get(0).getNombreJugador());
        assertEquals(5000, result.get(0).getPuntajeTotal());
    }

    @Test
    public void testBuscarPartidaPorId() {
        when(partidaRepository.findById(1)).thenReturn(Optional.of(createPartida()));

        PartidaDTO result = partidaService.buscarPartidaPorId(1);

        assertNotNull(result);
        assertEquals(5000, result.getPuntajeTotal());
    }

    @Test
    public void testBuscarEntidadPorId() {
        when(partidaRepository.findById(1)).thenReturn(Optional.of(createPartida()));

        Partida result = partidaService.buscarEntidadPorId(1);

        assertNotNull(result);
        assertEquals(1, result.getIdPartida());
    }

    @Test
    public void testCrearPartida() {
        Partida partidaGuardada = createPartida();
        when(partidaRepository.save(any(Partida.class))).thenReturn(partidaGuardada);

        PartidaDTO result = partidaService.crearPartida(99);

        assertNotNull(result);
        assertEquals("fabricioLOLXD", result.getNombreJugador());
        verify(partidaRepository, times(1)).save(any(Partida.class));
    }

    @Test
    public void testEliminarPartida() {
        when(partidaRepository.existsById(1)).thenReturn(true);
        doNothing().when(partidaRepository).deleteById(1);

        partidaService.eliminarPartida(1);

        verify(partidaRepository, times(1)).deleteById(1);
    }

    @Test
    public void testEliminarPartida_ThrowsException() {
        when(partidaRepository.existsById(1)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> partidaService.eliminarPartida(1));
    }
}
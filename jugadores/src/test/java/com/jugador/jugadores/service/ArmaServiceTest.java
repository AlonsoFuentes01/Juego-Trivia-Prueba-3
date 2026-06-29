package com.jugador.jugadores.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jugador.jugadores.DTO.ArmaDTO;
import com.jugador.jugadores.model.Arma;
import com.jugador.jugadores.model.Jugador;
import com.jugador.jugadores.repository.ArmaRepository;
import com.jugador.jugadores.repository.JugadorRepository;

@ExtendWith(MockitoExtension.class)
public class ArmaServiceTest {

    @InjectMocks
    private ArmaService armaService;

    @Mock
    private ArmaRepository armaRepository;

    @Mock
    private JugadorRepository jugadorRepository;

    private Arma createArma() {
        return new Arma(1, 1, "Espada del Rey arruinado");
    }

    private Jugador createJugador() {
        return new Jugador(1, "Cesar Galvez", "Peru", 0, 1,1);
    }

    @Test
    public void testObtenerTodas() {
        when(armaRepository.findAll()).thenReturn(List.of(createArma()));

        List<ArmaDTO> armas = armaService.obtenerTodas();
        assertNotNull(armas);
        assertEquals(1, armas.size());
    }

    @Test
    public void testBuscarPorId() {
        when(armaRepository.findById(1)).thenReturn(Optional.of(createArma()));

        ArmaDTO arma = armaService.buscarPorId(1);
        assertNotNull(arma);
        assertEquals("Espada del Rey arruinado", arma.getNombreArma());
    }

    @Test
    public void testBuscarPorJugador() {
        when(jugadorRepository.findById(1)).thenReturn(Optional.of(createJugador()));
        when(armaRepository.findByJugadorId(1)).thenReturn(List.of(createArma()));

        List<ArmaDTO> armas = armaService.buscarPorJugador(1);
        assertNotNull(armas);
        assertEquals(1, armas.size());
        assertEquals(1, armas.get(0).getJugadorId());
    }

    @Test
    public void testGuardar() {
        Arma arma = createArma();
        when(jugadorRepository.findById(1)).thenReturn(Optional.of(createJugador()));
        when(armaRepository.save(any(Arma.class))).thenReturn(arma);

        ArmaDTO guardada = armaService.guardar(arma);
        assertNotNull(guardada);
        assertEquals("Espada del Rey arruinado", guardada.getNombreArma());
    }

    @Test
    public void testEliminar() {
        when(armaRepository.findById(1)).thenReturn(Optional.of(createArma()));

        armaService.eliminar(1);
        verify(armaRepository, times(1)).deleteById(1);
    }
}


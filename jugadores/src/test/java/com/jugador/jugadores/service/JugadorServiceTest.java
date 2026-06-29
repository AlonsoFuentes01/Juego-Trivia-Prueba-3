package com.jugador.jugadores.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jugador.jugadores.DTO.JugadorDTO;
import com.jugador.jugadores.DTO.RankingDTO;
import com.jugador.jugadores.model.Jugador;
import com.jugador.jugadores.repository.ArmaRepository;
import com.jugador.jugadores.repository.JugadorRepository;

@ExtendWith(MockitoExtension.class)
public class JugadorServiceTest {

    @InjectMocks
    private JugadorService jugadorService;

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private ArmaRepository armaRepository;

    private Jugador createJugador(){
        return new Jugador(1, "Cesar Galvez", "Peru", 0, 1,1);
    }

    @Test
    public void testFindAll(){
        when(jugadorRepository.findAll()).thenReturn(List.of(createJugador()));
        when(armaRepository.findByJugadorId(any())).thenReturn(List.of());

        List<JugadorDTO> jugadores = jugadorService.obtenerTodos();
        assertNotNull(jugadores);
        assertEquals(1, jugadores.size());

        System.out.println("Jugadores encontrados: "+jugadores.size());
        System.out.println("Nombre: "+ jugadores.get(0).getNombreJugador());
    }

    @Test
    public void testFindById() {
        when(jugadorRepository.findById(1)).thenReturn(Optional.of(createJugador()));
        when(armaRepository.findByJugadorId(any())).thenReturn(List.of());

        JugadorDTO jugador = jugadorService.buscarPorId(1);
        assertNotNull(jugador);
        assertEquals("Cesar Galvez", jugador.getNombreJugador());
        System.out.println("Jugador encontrado: "+jugador.getNombreJugador());
    }

    @Test
    public void testGuardar() {
        Jugador jugador = createJugador();
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugador);
        when(armaRepository.findByJugadorId(any())).thenReturn(List.of());

        JugadorDTO guardado = jugadorService.guardar(jugador);
        assertNotNull(guardado);
        assertEquals("Cesar Galvez", guardado.getNombreJugador());
    }

    @Test
    public void testPatchJugador() {
        Jugador existingJugador = createJugador();
        Jugador patchData = new Jugador();
        patchData.setNombre("Cesar p Actualizado");

        when(jugadorRepository.findById(1)).thenReturn(Optional.of(existingJugador));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(existingJugador);
        when(armaRepository.findByJugadorId(any())).thenReturn(List.of());

        JugadorDTO patchedJugador = jugadorService.actualizarJugador(1, patchData);
        assertNotNull(patchedJugador);
        assertEquals("Cesar p Actualizado", patchedJugador.getNombreJugador());
    }
    

    @Test
    public void testActualizarPuntos() {
        Jugador jugador = createJugador();
        when(jugadorRepository.findById(1)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugador);
        when(armaRepository.findByJugadorId(any())).thenReturn(List.of());

        JugadorDTO resultado = jugadorService.actualizarPuntos(1, 100);
        assertNotNull(resultado);
        assertEquals(100, resultado.getPuntosJugador());
    }

    @Test
    public void testObtenerRanking() {
        when(jugadorRepository.findAllOrderByPuntosDesc()).thenReturn(List.of(createJugador()));

        List<RankingDTO> ranking = jugadorService.obtenerRanking();
        assertNotNull(ranking);
        assertEquals(1, ranking.size());
        assertEquals(1, ranking.get(0).getPosicion());
        assertEquals("Cesar Galvez", ranking.get(0).getNombreJugador());
    }
}
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

import com.partida.partidas.DTO.JefeDTO;
import com.partida.partidas.model.Jefe;
import com.partida.partidas.model.Nivel;
import com.partida.partidas.repository.JefeRepository;
import com.partida.partidas.repository.NivelRepository;

@SpringBootTest
public class JefeServiceTest {

    @Autowired
    private JefeService jefeService;

    @MockitoBean 
    private JefeRepository jefeRepository;

    @MockitoBean
    private NivelRepository nivelRepository;

    private Nivel createNivel() {
        Nivel nivel = new Nivel();
        nivel.setIdNivel(1);
        nivel.setNumero(5);
        nivel.setDificultad("Alta");
        return nivel;
    }

    private Jefe createJefe() {
        Jefe jefe = new Jefe();
        jefe.setIdJefe(1);
        jefe.setNombreJefe("Bowser");
        jefe.setRecompensa(2);
        jefe.setNivel(createNivel());
        return jefe;
    }

    @Test
    public void testObtenerTodos() {
        when(jefeRepository.findAll()).thenReturn(List.of(createJefe()));
        
        List<JefeDTO> result = jefeService.obtenerTodos();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bowser", result.get(0).getNombreJefe());
    }

    @Test
    public void testBuscarJefePorId() {
        when(jefeRepository.findById(1)).thenReturn(Optional.of(createJefe()));
        
        JefeDTO result = jefeService.buscarJefePorId(1);
        
        assertNotNull(result);
        assertEquals("Bowser", result.getNombreJefe());
    }

    @Test
    public void testBuscarJefePorId_ThrowsException() {
        when(jefeRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> jefeService.buscarJefePorId(1));
    }

    @Test
    public void testBuscarJefePorNivel() {
        Jefe jefe = createJefe();
        when(jefeRepository.findByNivel_IdNivel(1)).thenReturn(Optional.of(jefe));
        
        Optional<Jefe> result = jefeService.buscarJefePorNivel(1);
        
        assertTrue(result.isPresent());
        assertEquals("Bowser", result.get().getNombreJefe());
    }

    @Test
    public void testActualizarJefe() {
        Jefe existente = createJefe();
        Jefe nuevosDatos = new Jefe();
        nuevosDatos.setNombreJefe("Ganondorf");
        nuevosDatos.setRecompensa(2);
        nuevosDatos.setNivel(createNivel());

        when(jefeRepository.findById(1)).thenReturn(Optional.of(existente));
        when(nivelRepository.findById(1)).thenReturn(Optional.of(createNivel()));
        when(jefeRepository.save(any(Jefe.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Jefe actualizado = jefeService.actualizarJefe(1, nuevosDatos);

        assertNotNull(actualizado);
        assertEquals("Ganondorf", actualizado.getNombreJefe());
        assertEquals(2, actualizado.getRecompensa());
    }
}
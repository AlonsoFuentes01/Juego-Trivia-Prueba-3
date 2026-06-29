package com.pregunta.preguntas.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pregunta.preguntas.model.Pregunta;
import com.pregunta.preguntas.repository.PreguntaRepository;
import com.pregunta.preguntas.service.PreguntaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class PreguntasServiceTest {

    @Mock
    private PreguntaRepository preguntaRepository;

    @InjectMocks
	private PreguntaService preguntaService;

    private Faker faker = new Faker();

    private Pregunta createPregunta() {
        return new Pregunta  (9, "¿Cuántos años tiene un siglo?", 10, "FACIL" ,  "matematicas","opcion_multiple",1 );
        
    }

    @Test
    public void testFindAll() {
        when(preguntaRepository.findAll()).thenReturn(List.of(createPregunta()));
        List<Pregunta> preguntas = preguntaService.findAll();
        assertNotNull(preguntas);
        assertEquals(1, preguntas.size());
    }

    @Test
    public void testFindById() {
        when(preguntaRepository.findById(11)).thenReturn(java.util.Optional.of(createPregunta()));
        Pregunta pregunta = preguntaService.buscarPreguntaPorId(11);
        assertNotNull(pregunta);
        assertEquals("¿Cuántos años tiene un siglo?", pregunta.getEnunciado());
    }

    @Test
    public void testPatchPregunta() {
        Pregunta existingPregunta = createPregunta();
        Pregunta patchData = new Pregunta();
        patchData.setEnunciado("¿Cuántos años tiene un siglo? ACTUALIZADO");;

        when(preguntaRepository.findById(9)).thenReturn(java.util.Optional.of(existingPregunta));
        when(preguntaRepository.save(any(Pregunta.class))).thenReturn(existingPregunta);

        Pregunta patchedPregunta = preguntaService.actualizarPregunta(9, patchData);
        assertNotNull(patchedPregunta);
        assertEquals("¿Cuántos años tiene un siglo? ACTUALIZADO", patchedPregunta.getEnunciado());
        System.out.println(createPregunta());
    }

}

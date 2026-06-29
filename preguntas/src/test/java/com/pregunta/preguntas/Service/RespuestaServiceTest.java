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

import com.pregunta.preguntas.model.Respuesta;
import com.pregunta.preguntas.repository.RespuestaRepository;
import com.pregunta.preguntas.service.RespuestaService;

import net.datafaker.Faker;


@ExtendWith(MockitoExtension.class)
public class RespuestaServiceTest {

    @Mock
    private RespuestaRepository respuestaRepository;

    @InjectMocks
	private RespuestaService respuestaService;

    private Faker faker = new Faker();

    private Respuesta createRespuesta() {
        return new Respuesta();
    }
    
    @Test
    public void testFindAll() {
        when(respuestaRepository.findAll()).thenReturn(List.of(createRespuesta()));
        List<Respuesta> respuestas = respuestaService.findAll();
        assertNotNull(respuestas);
        assertEquals(11, respuestas.size());
    }

    @Test
    public void testFindById() {
        when(respuestaRepository.findById(11)).thenReturn(java.util.Optional.of(createRespuesta()));
        Respuesta respuesta = respuestaService.buscarRespuestaPorId(11);
        assertNotNull(respuesta);
        assertEquals("", respuesta.getContenido());
    }

    @Test
    public void testPatchRespuesta() {
        Respuesta existingRespuesta = createRespuesta();
        Respuesta patchData = new Respuesta();
        patchData.setContenido("");

        when(respuestaRepository.findById(11)).thenReturn(java.util.Optional.of(existingRespuesta));
        when(respuestaRepository.save(any(Respuesta.class))).thenReturn(existingRespuesta);

        Respuesta patchedRespuesta = respuestaService.actualizarRespuesta(11, patchData);
        assertNotNull(patchedRespuesta);
        assertEquals("", patchedRespuesta.getContenido());
    }
}

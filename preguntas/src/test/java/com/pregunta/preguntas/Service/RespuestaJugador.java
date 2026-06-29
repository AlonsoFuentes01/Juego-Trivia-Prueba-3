package com.pregunta.preguntas.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pregunta.preguntas.model.Pregunta;
import com.pregunta.preguntas.model.Respuesta;
import com.pregunta.preguntas.repository.RespuestaJugadorRepository;
import com.pregunta.preguntas.repository.RespuestaRepository;
import com.pregunta.preguntas.service.PreguntaService;
import com.pregunta.preguntas.service.RespuestaJugadorService;

import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
public class RespuestaJugador {

    @InjectMocks
    private RespuestaJugadorService respuestaJugadorService;

    @Mock
    private RespuestaJugadorRepository respuestaJugadorRepository;

    @Mock
    private RespuestaRepository respuestaRepository;

    @Mock
    private PreguntaService preguntaService;

    @Mock
    private WebClient.Builder webClientBuilder;

    private Pregunta createPregunta() {
        Pregunta p = new Pregunta();
        p.setIdPregunta(1);
        p.setEnunciado("¿Cuánto es 2+2?");
        p.setPuntaje(10);
        return p;
    }

    private Respuesta createRespuesta(boolean correcta) {
        Respuesta r = new Respuesta();
        r.setIdRespuesta(1);
        r.setContenido("4");
        r.setCorrecta(correcta);
        r.setPregunta(createPregunta());
        return r;
    }

    private com.pregunta.preguntas.model.RespuestaJugador createRespuestaJugador() {
        return com.pregunta.preguntas.model.RespuestaJugador.builder()
                .idRespuestaJugador(1)
                .respuestaElegida(createRespuesta(true))
                .esCorrecta(true)
                .idPartida(1)
                .build();
    }

    @Test
    public void testListar() {
        when(respuestaJugadorRepository.findAll()).thenReturn(List.of(createRespuestaJugador()));

        List<com.pregunta.preguntas.model.RespuestaJugador> lista = respuestaJugadorService.listar();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testListarPorPartida() {
        when(respuestaJugadorRepository.findByIdPartida(1)).thenReturn(List.of(createRespuestaJugador()));

        List<com.pregunta.preguntas.model.RespuestaJugador> lista = respuestaJugadorService.listarPorPartida(1);
        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(1, lista.get(0).getIdPartida());
    }

    @Test
    public void testResponderCorrecta() {
        Respuesta respuesta = createRespuesta(true);
        when(respuestaRepository.findById(1)).thenReturn(Optional.of(respuesta));
        when(respuestaJugadorRepository.save(any())).thenReturn(createRespuestaJugador());

        var resultado = respuestaJugadorService.responder(1, 1);
        assertNotNull(resultado);
        assertEquals(true, resultado.getEsCorrecta());
        assertEquals(10, resultado.getPuntajeObtenido());
    }

    @Test
    public void testResponderIncorrecta() {
        Respuesta respuesta = createRespuesta(false);
        when(respuestaRepository.findById(2)).thenReturn(Optional.of(respuesta));
        when(respuestaJugadorRepository.save(any())).thenReturn(createRespuestaJugador());

        var resultado = respuestaJugadorService.responder(1, 2);
        assertNotNull(resultado);
        assertEquals(false, resultado.getEsCorrecta());
        assertEquals(0, resultado.getPuntajeObtenido());
    }
}

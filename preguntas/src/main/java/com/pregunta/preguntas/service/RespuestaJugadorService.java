package com.pregunta.preguntas.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pregunta.preguntas.DTO.ResultadoRespuestaDTO;
import com.pregunta.preguntas.model.Pregunta;
import com.pregunta.preguntas.model.Respuesta;
import com.pregunta.preguntas.model.RespuestaJugador;
import com.pregunta.preguntas.repository.PreguntaRepository;
import com.pregunta.preguntas.repository.RespuestaJugadorRepository;
import com.pregunta.preguntas.repository.RespuestaRepository;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class RespuestaJugadorService {

    private static final Logger log = LoggerFactory.getLogger(RespuestaJugadorService.class);

    // Puntos necesarios para subir de nivel
    private static final int PUNTOS_NIVEL_2 = 30;
    private static final int PUNTOS_NIVEL_3 = 90;

    @Autowired
    private RespuestaJugadorRepository respuestaJugadorRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<RespuestaJugador> listar() {
        try {
            log.info("Listando todas las respuestas de jugadores");
            return respuestaJugadorRepository.findAll();
        } catch (Exception e) {
            log.error("Error al listar respuestas: {}", e.getMessage());
            throw new RuntimeException("Error al listar respuestas");
        }
    }

    public List<RespuestaJugador> listarPorPartida(Integer idPartida) {
        try {
            log.info("Listando respuestas de la partida ID: {}", idPartida);
            return respuestaJugadorRepository.findByIdPartida(idPartida);
        } catch (Exception e) {
            log.error("Error al listar respuestas de partida {}: {}", idPartida, e.getMessage());
            throw new RuntimeException("Error al listar respuestas de la partida");
        }
    }

    public ResultadoRespuestaDTO responder(Integer idPartida, Integer idRespuesta) {
        Respuesta respuestaElegida = respuestaRepository.findById(idRespuesta)
            .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + idRespuesta));
        boolean esCorrecta = respuestaElegida.getCorrecta();
        Pregunta pregunta = respuestaElegida.getPregunta();
        int puntajeObtenido = 0;

        RespuestaJugador registro = RespuestaJugador.builder()
                .respuestaElegida(respuestaElegida)
                .esCorrecta(esCorrecta)
                .idPartida(idPartida)
                .build();
        respuestaJugadorRepository.save(registro);

        Integer puntajeTotal = null;
        boolean subioDeNivel = false;
        Integer nivelActual = null;

        if (esCorrecta) {
            puntajeObtenido = pregunta.getPuntaje();
        try {
                puntajeTotal = sumarPuntajeEnPartida(idPartida, puntajeObtenido);

                Integer idJugador = obtenerJugadorDePartida(idPartida);
                if (idJugador != null) {
                    actualizarPuntosJugador(idJugador, puntajeObtenido);
                    nivelActual = obtenerNivelJugador(idJugador);
                    Integer nuevoNivel = calcularNivel(puntajeTotal);
                    if (nuevoNivel != null && nivelActual != null && nuevoNivel > nivelActual) {
                        subirNivelJugador(idJugador, nuevoNivel);
                        nivelActual = nuevoNivel;
                        subioDeNivel = true;
                        log.info("Jugador {} subió al nivel {}", idJugador, nuevoNivel);
                    }
                }
            } catch (Exception e) {
                log.warn("No se pudo conectar con otros microservicios: {}", e.getMessage());
            }
        }

        Integer idSiguientePregunta = null;
        String enunciadoSiguiente = null;

        List<Pregunta> siguientes = preguntaRepository.findSiguientePregunta(
                pregunta.getIdNivel(), pregunta.getIdPregunta(), PageRequest.of(0, 1));

        if (!siguientes.isEmpty()) {
            idSiguientePregunta = siguientes.get(0).getIdPregunta();
            enunciadoSiguiente = siguientes.get(0).getEnunciado();
        }

        String mensaje = esCorrecta
                ? "¡Correcto! Sumaste " + puntajeObtenido + " puntos." + (subioDeNivel ? " ¡Subiste al nivel " + nivelActual + "!" : "")
                : "Incorrecto. La respuesta correcta era otra.";

        return ResultadoRespuestaDTO.builder()
                .esCorrecta(esCorrecta)
                .mensaje(mensaje)
                .puntajeObtenido(puntajeObtenido)
                .puntajeTotal(puntajeTotal)
                .nivelActual(nivelActual)
                .partidaFinalizada(false)
                .subioDeNivel(subioDeNivel)
                .idSiguientePregunta(idSiguientePregunta)
                .enunciadoSiguientePregunta(enunciadoSiguiente)
                .build();
    }

    private Integer calcularNivel(Integer puntajeTotal) {
        if (puntajeTotal == null) return null;
        if (puntajeTotal >= PUNTOS_NIVEL_3) return 3;
        if (puntajeTotal >= PUNTOS_NIVEL_2) return 2;
        return 1;
    }

    private Integer sumarPuntajeEnPartida(Integer idPartida, Integer puntaje) {
        try {
            var result = webClientBuilder.build()
                .put()
                .uri("http://partidas/api/v1/partidas/" + idPartida + "/sumar-puntaje?puntaje=" + puntaje)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> Mono.empty())
                .bodyToMono(java.util.Map.class)
                .block();

            if (result != null && result.get("puntajeTotal") != null) {
                return (Integer) result.get("puntajeTotal");
            }
        } catch (Exception e) {
            log.error("Error al sumar puntaje en partidas: {}", e.getMessage());
        }
        return null;
    }

    private Integer obtenerJugadorDePartida(Integer idPartida) {
        try {
            var result = webClientBuilder.build()
                .get()
                .uri("http://partidas/api/v1/partidas/" + idPartida)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> Mono.empty())
                .bodyToMono(java.util.Map.class)
                .block();

            if (result != null && result.get("jugadorDTOext") != null) {
                java.util.Map<String, Object> jugador = (java.util.Map<String, Object>) result.get("jugadorDTOext");
                return (Integer) jugador.get("idJugador");
            }
        } catch (Exception e) {
            log.error("Error al obtener jugador de partida: {}", e.getMessage());
        }
        return null;
    }

    private Integer obtenerNivelJugador(Integer idJugador) {
        try {
            var result = webClientBuilder.build()
                .get()
                .uri("http://jugadores/api/v1/jugadores/" + idJugador)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> Mono.empty())
                .bodyToMono(java.util.Map.class)
                .block();

            if (result != null && result.get("nivelJugador") != null) {
                return (Integer) result.get("nivelJugador");
            }
        } catch (Exception e) {
            log.error("Error al obtener nivel del jugador: {}", e.getMessage());
        }
        return null;
    }

    private void subirNivelJugador(Integer idJugador, Integer nuevoNivel) {
        try {
            webClientBuilder.build()
                .patch()
                .uri("http://jugadores/api/v1/jugadores/" + idJugador)
                .bodyValue(java.util.Map.of("nivelActual", nuevoNivel))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> Mono.empty())
                .bodyToMono(Void.class)
                .block();
        } catch (Exception e) {
            log.error("Error al subir nivel del jugador: {}", e.getMessage());
        }
    }
    
    
    private void actualizarPuntosJugador(Integer idJugador, Integer puntosGanados) {
    try {
        webClientBuilder.build()
            .patch()
            .uri("http://jugadores/api/v1/jugadores/" + idJugador + "/sumar-puntos?puntos=" + puntosGanados)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, r -> Mono.empty())
            .bodyToMono(Void.class)
            .block();
        log.info("Puntos actualizados para jugador {}: +{}", idJugador, puntosGanados);
    } catch (Exception e) {
        log.error("Error al actualizar puntos del jugador: {}", e.getMessage());
        }
    }




    
}

package com.jugador.jugadores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJugador;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
    @Column(nullable = false, length = 20, unique = true)
    private String nombre;

    @NotBlank(message = "El origen es obligatorio")
    @Size(min = 3, max = 30, message = "El origen debe tener entre 3 y 30 caracteres")
    @Column(nullable = false, length = 30)
    private String origen;

    @Builder.Default
    @Column(nullable = false)
    private Integer puntos = 0;

    @Builder.Default
    @Column(name = "nivel_actual", nullable = false)
    private Integer nivelActual = 1;
    
    private Integer idArma;
}

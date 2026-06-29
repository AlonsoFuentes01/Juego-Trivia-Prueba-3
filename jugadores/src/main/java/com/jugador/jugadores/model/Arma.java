package com.jugador.jugadores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "armas")
public class Arma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El jugador es obligatorio")
    @Column(name = "jugador_id", nullable = false)
    private Integer jugadorId;

    @NotBlank(message = "El nombre del arma es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre del arma debe tener entre 2 y 50 caracteres")
    @Column(name = "nombre_arma", nullable = false, length = 50)
    private String nombreArma;
}

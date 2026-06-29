package com.partida.partidas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "niveles")
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNivel;

    @Min(value = 1, message = "El número del nivel debe ser mayor a 0")
    @Column(nullable = false)
    private Integer numero;

    @Min(value = 0, message = "El puntaje mínimo no puede ser negativo")
    @Max(value = 100, message = "El puntaje no puede ser superior a 100!")
    @Column(nullable = false)
    private Integer puntajeMinimo;
    
    @NotBlank(message = "La dificultad es obligatoria")
    @Size(min = 10, max = 10, message = "La difilcultad debe tener minimo 10  caracteres")
    @Column(nullable = false)
    private String dificultad;

}

package com.pregunta.preguntas.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="preguntas")
public class Pregunta {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_pregunta")
    private Integer idPregunta;

    @NotBlank(message="El enunciado es obligatorio")
    private String enunciado;

    @Min(value = 1, message = "El puntaje debe ser mayor a 0")
    @Column(nullable = false)
    private Integer puntaje;

    @Column(nullable = false)
    private String dificultad;

    private String categoria;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "nivel_id", nullable = false)
    private Integer idNivel;

}

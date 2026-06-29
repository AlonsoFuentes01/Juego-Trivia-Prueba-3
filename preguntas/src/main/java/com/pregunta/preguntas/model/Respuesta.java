package com.pregunta.preguntas.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "respuestas")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRespuesta;

    @NotBlank(message = "La respuesta es obligatoria")
    @Column(nullable = false)
    private String contenido;

    @Builder.Default
    @Column(nullable = false)
    private Boolean correcta = false;

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta;
}
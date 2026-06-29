package com.partida.partidas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "jefes")
public class Jefe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJefe;

    @NotBlank(message = "El nombre del jefe es obligatorio")
    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombreJefe;

    @ManyToOne
    @JoinColumn(name = "id_nivel", nullable = false)
    @NotBlank(message = "El nivel es obligatorio")
    private Nivel nivel;

    @Min(value = 0, message = "La recompensa no puede ser negativa")
    @Max(value = 1, message = "la recompensa debe ser mayor a 1 ")
    @Column(nullable = false)
    private Integer recompensa;


}

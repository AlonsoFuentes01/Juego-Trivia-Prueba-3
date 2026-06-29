package com.jugador.jugadores.repository;

import com.jugador.jugadores.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    @Query("SELECT j FROM Jugador j ORDER BY j.puntos DESC")
    List<Jugador> findAllOrderByPuntosDesc();    
    boolean existsByNombre(String nombre);
}


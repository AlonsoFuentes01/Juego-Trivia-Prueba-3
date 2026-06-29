package com.jugador.jugadores.repository;

import com.jugador.jugadores.model.Arma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmaRepository extends JpaRepository<Arma, Integer> {

    List<Arma> findByJugadorId(Integer jugadorId);
}

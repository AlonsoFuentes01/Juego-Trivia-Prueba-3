package com.partida.partidas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.partida.partidas.model.Jefe;

@Repository
public interface JefeRepository extends JpaRepository<Jefe, Integer> {
    Optional<Jefe> findByNivel_IdNivel(Integer idNivel);
}

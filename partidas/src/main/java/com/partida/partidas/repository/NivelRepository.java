package com.partida.partidas.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.partida.partidas.model.Nivel;



@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer> {

    Optional<Nivel> findByNumero(Integer numero);

}

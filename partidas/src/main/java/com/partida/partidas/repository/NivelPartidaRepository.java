package com.partida.partidas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.partida.partidas.model.NivelPartida;


@Repository
public interface NivelPartidaRepository extends JpaRepository<NivelPartida, Integer> {

}

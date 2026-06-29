package com.partida.partidas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartidaRepository extends JpaRepository<com.partida.partidas.model.Partida, Integer> {

}

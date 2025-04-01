package com.example.tfg.repository;

import com.example.tfg.model.Player;
import com.example.tfg.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
    // Método buscar por nombre jugador.
    @Query("SELECT p FROM Player p WHERE p.name = :name")
    List<Player> findByName(@Param("name") String name);
    // Método buscar por posición jugador.
    @Query("SELECT p FROM Player p WHERE p.position = :position")
    List<Player> findByPosition(@Param("position") String position);
    // Busca jugadores marcados como titulares por defecto
    List<Player> findByIsDefaultTrue();
    // Método buscar por apodo jugador.
    @Query("SELECT p FROM Player p WHERE p.apodo = :apodo")
    Player findByApodo(@Param("apodo") String apodo);
    @Query("SELECT p FROM Player p WHERE p.team = :team AND p.position = :position")
    List<Player> findByPositionAndTeam(@Param("position") String position,
                                       @Param("team") Team team);
}

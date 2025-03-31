package com.example.tfg.repository;

import com.example.tfg.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
    // Método buscar por nombre jugador.
    @Query("SELECT p FROM Player p WHERE p.name = :name")
    List<Player> findByName(@Param("name") String name);
}

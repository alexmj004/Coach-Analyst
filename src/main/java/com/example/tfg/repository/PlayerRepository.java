package com.example.tfg.repository;

import com.example.tfg.model.Player;
import com.example.tfg.model.Tournament;
import com.example.tfg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
    // Método buscar por nombre jugador.
    @Query("SELECT p FROM Player p WHERE p.name = :name")
    Player findByPlayerName(@Param("name") String name);

}

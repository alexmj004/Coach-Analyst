package com.example.tfg.repository;

import com.example.tfg.model.Player;
import com.example.tfg.model.Team;
import com.example.tfg.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRespository extends JpaRepository<Team,Integer> {
    // Método buscar por nombre equipo.
    @Query("SELECT t FROM Team t WHERE t.name = :name")
    Team findByTeamName(@Param("name") String name);
}

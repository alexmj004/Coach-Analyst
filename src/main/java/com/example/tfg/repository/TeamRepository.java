package com.example.tfg.repository;

import com.example.tfg.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team,Integer> {
    // Obtener equipo por nombre
    @Query("SELECT t FROM Team t WHERE t.name = :name")
    Team findByName(String name);
}

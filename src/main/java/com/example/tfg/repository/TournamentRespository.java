package com.example.tfg.repository;

import com.example.tfg.model.Player;
import com.example.tfg.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TournamentRespository extends JpaRepository<Tournament,Integer> {
    // Método buscar por nombre torneo.
    @Query("SELECT t FROM Tournament t WHERE t.name = :name")
    Tournament findByTournamentName(@Param("name") String name);

}

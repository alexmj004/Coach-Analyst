package com.example.tfg.repository;

import com.example.tfg.model.Team;
import com.example.tfg.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament,Integer> {
    // Método para devolver todos los equipos de un torneo
    @Query("SELECT team FROM Tournament t JOIN t.teams team WHERE t.id = :tournamentId ORDER BY team.position ASC")
    List<Team> findTeamsByTournamentId(@Param("tournamentId") int tournamentId);
}

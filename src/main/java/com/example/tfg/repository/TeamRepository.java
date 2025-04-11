package com.example.tfg.repository;

import com.example.tfg.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Integer> {
    // Obtener equipo por nombre
    @Query("SELECT t FROM Team t WHERE t.name = :name")
    Team findByName(String name);
    List<Team> findAllByOrderByPositionAsc();
    @Query("SELECT DISTINCT t FROM Team t JOIN FETCH t.tournaments tour WHERE tour.type = :tournamentType ORDER BY t.position ASC")
    List<Team> findByTournamentsTypeOrderByPositionAsc(@Param("tournamentType") String tournamentType);
    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.tournaments WHERE t.id = :id")
    Team findWithTournamentsById(@Param("id") int id);
}

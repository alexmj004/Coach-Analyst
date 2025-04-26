package com.example.tfg.repository;

import com.example.tfg.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ResultsRepository  extends JpaRepository<Match, Integer> {
    Match findById(int id);

    List<Match> findByDateTime(Timestamp dateTime);

    //QUery para encontrar los partidos que se han jugado en un torneo dandole solamente el id de uun equipo
    @Query(value = "SELECT m.* FROM `match` m " +
            "JOIN teams h ON m.home_team_id = h.id " +
            "JOIN teams a ON m.away_team_id = a.id " +
            "JOIN teams_tournaments ht ON h.id = ht.team_id " +
            "JOIN teams_tournaments at ON a.id = at.team_id " +
            "JOIN teams_tournaments mt ON mt.team_id = :teamId " +
            "WHERE (ht.tournament_id = mt.tournament_id OR at.tournament_id = mt.tournament_id) " +
            "AND (h.id = :teamId OR a.id = :teamId OR " +
            "h.id IN (SELECT t.team_id FROM teams_tournaments t WHERE t.tournament_id IN " +
            "(SELECT tt.tournament_id FROM teams_tournaments tt WHERE tt.team_id = :teamId)) OR " +
            "a.id IN (SELECT t.team_id FROM teams_tournaments t WHERE t.tournament_id IN " +
            "(SELECT tt.tournament_id FROM teams_tournaments tt WHERE tt.team_id = :teamId)))",
            nativeQuery = true)
    List<Match> findMatchesByTeamsInSameTournaments(@Param("teamId") int teamId);
}

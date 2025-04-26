package com.example.tfg.service;

import com.example.tfg.model.Team;
import com.example.tfg.model.Tournament;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TournamentService {
    Tournament add(Tournament tournament);

    List<Team> findTeamsByTournamentId(int tournamentId);
    List<Team> findTeamsByTeamTournaments(@Param("teamId") int teamId);
}

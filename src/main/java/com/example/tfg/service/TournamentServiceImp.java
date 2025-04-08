package com.example.tfg.service;

import com.example.tfg.model.Tournament;
import com.example.tfg.repository.TeamRepository;
import com.example.tfg.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentServiceImp implements TournamentService{
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Tournament add(Tournament tournament) {
        Tournament newTournament = new Tournament(
                tournament.getName(),
                tournament.getStartDate(),
                tournament.getEndDate(),
                tournament.getType(),
                tournament.getLocation()
        );

        if (tournament.getTeams() != null) {
            tournament.getTeams().forEach(team -> {
                teamRepository.findById(team.getId()).ifPresent(newTournament::addTeam);
            });
        }

        return tournamentRepository.save(newTournament);
    }
}

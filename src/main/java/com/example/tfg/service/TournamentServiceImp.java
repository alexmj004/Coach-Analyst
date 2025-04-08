package com.example.tfg.service;

import com.example.tfg.model.Team;
import com.example.tfg.model.Tournament;
import com.example.tfg.repository.TeamRepository;
import com.example.tfg.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentServiceImp implements TournamentService{
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private TeamRepository teamRepository;


    @Override
    public Tournament add(Tournament tournament) {
        // Creamos un nuevo torneo con los datos básicos
        Tournament newTournament = new Tournament(
                tournament.getName(),
                tournament.getType(),
                tournament.getLocation(),
                tournament.getSeason()
        );

        // Si el JSON contiene equipos con IDs, los buscamos y los añadimos
        if (tournament.getTeams() != null) {
            List<Team> validTeams = new ArrayList<>();
            for (Team team : tournament.getTeams()) {
                teamRepository.findById(team.getId()).ifPresent(validTeams::add);
            }
            newTournament.setTeams(validTeams);
        }

        // Guardamos el nuevo torneo
        return tournamentRepository.save(newTournament);
    }
}

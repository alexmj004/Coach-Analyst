package com.example.tfg.service;

import com.example.tfg.model.Tournament;
import com.example.tfg.repository.TournamentRespository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TournamentServiceImple implements TournamentService{

    @Autowired
    TournamentRespository tournamentRespository;

    @Override
    public Tournament add(Tournament tournament) {
        return tournamentRespository.save(tournament);
    }

    @Override
    public Optional<Tournament> findById(int id) {
        return tournamentRespository.findById(id);
    }

    @Override
    public Tournament findByName(String name) {
        return tournamentRespository.findByTournamentName(name);
    }

    @Override
    public List<Tournament> findAll() {
        return tournamentRespository.findAll();
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public Boolean update(Tournament tournament) {
        return null;
    }
}

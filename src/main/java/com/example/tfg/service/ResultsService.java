package com.example.tfg.service;

import com.example.tfg.model.Match;

import java.util.List;

public interface ResultsService {

    Match findById(int id);

    List<Match> findAll();

    List<Match> findByDate_time(java.sql.Timestamp date_time);

    //metodo para encontrar los resultados de los equipos que participan en un torneo concreto
    List<Match> findMatchesByTeamsInSameTournaments(int teamId);
}

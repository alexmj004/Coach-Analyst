package com.example.tfg.service;

import com.example.tfg.model.Team;
import com.example.tfg.repository.TeamRespository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamRespository teamRespository;

    @Override
    public Team add(Team team) {
        return teamRespository.save(team);
    }

    @Override
    public Optional<Team> findById(int id) {
        return teamRespository.findById(id);
    }

    @Override
    public Team findByName(String name) {
        return teamRespository.findByTeamName(name);
    }

    @Override
    public List<Team> findAll() {
        return teamRespository.findAll();
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public Boolean update(Team team) {
        return null;
    }
}

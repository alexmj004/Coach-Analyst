package com.example.tfg.service;

import com.example.tfg.model.Team;
import com.example.tfg.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServImpl implements TeamService{
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(int id) {
        return teamRepository.findById(id);
    }

    @Override
    public List<Team> findAllOrderedByPosition() {
        return teamRepository.findAllByOrderByPositionAsc();
    }
}

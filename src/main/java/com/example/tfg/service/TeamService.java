package com.example.tfg.service;

import com.example.tfg.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    // Añadir un nuevo equipo.
    Team addTeam(Team team);

    // Obtener equipo por nombre.
    Team findByName(String name);
    // Obtener todos.
    List<Team> findAll();
    // Obtener un equipo por su ID
    Optional<Team> findById(int id);

}

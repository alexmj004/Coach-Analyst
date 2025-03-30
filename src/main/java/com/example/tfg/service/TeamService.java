package com.example.tfg.service;

import com.example.tfg.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    // Añadir un nuevo equipo
    Team add(Team team);
    // Obtener un equipo por su ID
    Optional<Team> findById(int id);
    // Obtener equipo por su nombre
    Team findByName(String name);
    // Obtener todos los equipos
    List<Team> findAll();
    // Eliminar un equipo por su ID
    Boolean delete(int id);
    // Actualizar un equipo
    Boolean update(Team team);
}

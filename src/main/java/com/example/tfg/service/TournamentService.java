package com.example.tfg.service;


import com.example.tfg.model.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentService {
    // Añadir un nuevo torneo
    Tournament add(Tournament tournament);
    // Obtener un torneo por su ID
    Optional<Tournament> findById(int id);
    // Obtener por nombre
    Tournament findByName(String name);
    // Obtener todos los torneos
    List<Tournament> findAll();
    // Eliminar un torneo por su ID
    Boolean delete(int id);
    // Actualizar un torneo
    Boolean update(Tournament tournament);
}

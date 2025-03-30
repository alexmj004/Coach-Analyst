package com.example.tfg.service;

import com.example.tfg.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    // Añadir un nuevo jugador
    Player add(Player player);

    // Obtener un jugador por su ID
    Optional<Player> findById(int id);

    // Obtener jugador por nombre.
    Player findByName(String name);

    // Obtener todos los jugadores
    List<Player> findAll();

    // Eliminar un jugador por su ID
    Boolean deleteById(int id);

    // Actualizar un jugador
    Boolean update(Player player);
}

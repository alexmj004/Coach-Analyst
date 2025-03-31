package com.example.tfg.service;

import com.example.tfg.model.Player;
import java.util.List;
import java.util.Optional;

public interface PlayerService {
    // Añadir un nuevo jugador.
    Player addPlayer(Player player);
    // Obtener jugador por nombre.
    List<Player> findByName(String name);
    // Obtener todos.
    List<Player> findAll();
    // Obtener un jugador por su ID
    Optional<Player> findById(int id);
    // Eliminar un jugador por su ID
    Boolean deleteById(int id);
    // Actualizar un jugador
    Boolean update(Player player);
}

package com.example.tfg.service;

import com.example.tfg.model.Player;
import com.example.tfg.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService{
    @Autowired
    PlayerRepository playerRepository;

    @Override
    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> findByName(String name) {

        return playerRepository.findByName(name);
    }

    @Override
    public List<Player> findByPosition(String position) {
        return playerRepository.findByPosition(position);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> findById(int id) {
        return playerRepository.findById(id);
    }

    @Override
    public Boolean deleteById(int id) {
        return null;
    }

    @Override
    public Boolean update(Player player) {
        return null;
    }

    @Override
    public List<Player> findDefaultPlayers() {
        // Busca jugadores marcados como titulares y los ordena por posición
        List<Player> defaultPlayers = playerRepository.findByIsDefaultTrue();

        // Orden personalizado para asegurar el orden correcto en la alineación
        defaultPlayers.sort((p1, p2) -> {
            // Definir el orden de las posiciones
            Map<String, Integer> positionOrder = Map.of(
                    "POR", 1,
                    "LI", 2,
                    "DFC", 3,
                    "LD", 4,
                    "MED", 5,
                    "EI", 6,
                    "DC", 7,
                    "ED", 8
            );

            return Integer.compare(
                    positionOrder.getOrDefault(p1.getPosition(), 99),
                    positionOrder.getOrDefault(p2.getPosition(), 99)
            );
        });
        return defaultPlayers;
    }
}

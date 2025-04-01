package com.example.tfg.service;

import com.example.tfg.model.Player;
import com.example.tfg.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService{
    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);
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
        try {
            // Normaliza la posición (mayúsculas, sin espacios)
            String normalizedPosition = position.toUpperCase().trim();
            logger.info("Buscando jugadores para posición: {}", normalizedPosition);

            List<Player> players = playerRepository.findByPosition(normalizedPosition);
            logger.info("Jugadores encontrados: {}", players.size());

            return players;
        } catch (Exception e) {
            logger.error("Error al buscar jugadores por posición", e);
            return Collections.emptyList();
        }
    }
    // Método específico para wingers (LW y RW)
    public Map<String, List<Player>> getWingers() {
        Map<String, List<Player>> wingers = new HashMap<>();

        List<Player> lwPlayers = findByPosition("LW");
        List<Player> rwPlayers = findByPosition("RW");

        wingers.put("LW", lwPlayers);
        wingers.put("RW", rwPlayers);

        return wingers;
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

    @Override
    public Player findByApodo(String apodo) {
        return playerRepository.findByApodo(apodo);
    }
}

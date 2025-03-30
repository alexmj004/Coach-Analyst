package com.example.tfg.service;

import com.example.tfg.model.Player;
import com.example.tfg.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerServiceImpl implements PlayerService{

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public Player add(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> findById(int id) {
        return playerRepository.findById(id);
    }


    @Override
    public Player findByName(String name) {
        return playerRepository.findByPlayerName(name);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Boolean deleteById(int id) {
        return null;
    }

    @Override
    public Boolean update(Player player) {
        return null;
    }


}

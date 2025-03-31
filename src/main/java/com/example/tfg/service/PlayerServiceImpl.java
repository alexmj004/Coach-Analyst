package com.example.tfg.service;

import com.example.tfg.model.Player;
import com.example.tfg.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}

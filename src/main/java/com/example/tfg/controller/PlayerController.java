package com.example.tfg.controller;

import com.example.tfg.model.Player;
import com.example.tfg.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/add")
    public ResponseEntity<String> addPlayer(@RequestBody List<Player> players) {
        for (Player player : players) {
            playerService.addPlayer(player);
        }
        return new ResponseEntity<>("Jugadores añadidos correctamente", HttpStatus.CREATED);
    }


    @GetMapping("/get")
    public ResponseEntity<Optional<Player>> getPlayer(@RequestParam int id){
        return new ResponseEntity<>(playerService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getByName")
    public ResponseEntity<List<Player>> getPlayer(@RequestParam String name) {
        List<Player> players = playerService.findByName(name);
        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Player>> getAllPlayers(){
        return new ResponseEntity<>(playerService.findAll(),HttpStatus.OK);
    }
}

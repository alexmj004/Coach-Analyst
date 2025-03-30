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
    PlayerService playerService;

    @PostMapping("/add")
    public String addPlayer(@RequestBody Player player){
        return "Jugador añadido correctamente: "+player.getName()+" "+player.getSurname();
    }

    @GetMapping("/get")
    public ResponseEntity<Optional<Player>> getPlayer(@RequestParam int id){
        return new ResponseEntity<>(playerService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Player> getByrName(@RequestParam String name){
        return new ResponseEntity<>(playerService.findByName(name),HttpStatus.OK);
    }

    // Obtener todos los jugadores
    @GetMapping("/getAll")
    public ResponseEntity<List<Player>> getAllPlayers(){
        return new ResponseEntity<>(playerService.findAll(),HttpStatus.OK);
    }

    // Eliminar jugador por id
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlayer(@RequestParam int id){
        boolean isDeleted = playerService.deleteById(id);

        if (isDeleted) {
            return new ResponseEntity<>("Jugador eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar jugador
    @PutMapping("/update")
    public ResponseEntity<String> updatePlayer(@RequestBody Player player) {
        boolean isUpdated = playerService.update(player); // Llama al servicio para actualizar al usuario

        if (isUpdated) {
            return new ResponseEntity<>("Jugador actualizado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
        }
    }

}

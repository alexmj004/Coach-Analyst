package com.example.tfg.controller;

import com.example.tfg.model.Tournament;
import com.example.tfg.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @PostMapping("/add")
    public String addTournament(@RequestBody Tournament tournament){
        return "Usuario añadido correctamente: "+tournament.getName();
    }


    @GetMapping("/get")
    public ResponseEntity<Optional<Tournament>> getTournament(@RequestParam int id){
        return new ResponseEntity<>(tournamentService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Tournament> getTournamentByName(@RequestParam String name){
        return new ResponseEntity<>(tournamentService.findByName(name),HttpStatus.OK);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Tournament>> getAllTournaments(){
        return new ResponseEntity<>(tournamentService.findAll(),HttpStatus.OK);
    }

    // Eliminar usuario por id
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTournament(@RequestParam int id){
        boolean isDeleted = tournamentService.delete(id);

        if (isDeleted) {
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar usuario
    @PutMapping("/update")
    public ResponseEntity<String> updateTournament(@RequestBody Tournament tournament) {
        boolean isUpdated = tournamentService.update(tournament); // Llama al servicio para actualizar al usuario

        if (isUpdated) {
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }


}

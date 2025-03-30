package com.example.tfg.controller;

import com.example.tfg.model.Team;
import com.example.tfg.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/add")
    public String addTeam(@RequestBody Team team){
        return "Equipo añadido correctamente: "+team.getName();
    }

    @GetMapping("/get")
    public ResponseEntity<Optional<Team>> getTeam(@RequestParam int id){
        return new ResponseEntity<>(teamService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Team> getByName(@RequestParam String name){
        return new ResponseEntity<>(teamService.findByName(name),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Team>> getAllTeams(){
        return new ResponseEntity<>(teamService.findAll(),HttpStatus.OK);
    }

    // Eliminar equipo por id
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTeam(@RequestParam int id){
        boolean isDeleted = teamService.delete(id);

        if (isDeleted) {
            return new ResponseEntity<>("Equipo eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Equipo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar equipo
    @PutMapping("/update")
    public ResponseEntity<String> updateEquipo(@RequestBody Team team) {
        boolean isUpdated = teamService.update(team); // Llama al servicio para actualizar al equipo

        if (isUpdated) {
            return new ResponseEntity<>("Equipo actualizado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Equipo no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}

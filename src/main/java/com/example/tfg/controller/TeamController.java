package com.example.tfg.controller;


import com.example.tfg.model.Team;
import com.example.tfg.service.TeamServImpl;
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
    private TeamServImpl teamServImpl;

    @PostMapping("/add")
    public String addTeam(@RequestBody Team team){
        return "Equipo añadido correctamente: "+team.getName();
    }


    @GetMapping("/get")
    public ResponseEntity<Optional<Team>> getTeam(@RequestParam int id){
        return new ResponseEntity<>(teamServImpl.findById(id),HttpStatus.OK);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Team> getByName(@RequestParam String name){
        return new ResponseEntity<>(teamServImpl.findByName(name),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Team>> getAllTeams(){
        return new ResponseEntity<>(teamServImpl.findAll(),HttpStatus.OK);
    }


}

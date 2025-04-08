package com.example.tfg.controller;


import com.example.tfg.model.Tournament;
import com.example.tfg.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @PostMapping("/add")
    public String add(@RequestBody Tournament tournament){
        Tournament tour = tournamentService.add(tournament);
        return "Se ha añadido el torneo de "+tour.getName();
    }
}

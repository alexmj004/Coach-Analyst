package com.example.tfg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "teams")
public class Team implements Serializable {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private String season;

    @OneToMany (mappedBy = "homeTeam", cascade = CascadeType.ALL)
    private List<Match> homeMatches = new ArrayList<>();

    @OneToMany (mappedBy = "awayTeam", cascade = CascadeType.ALL)
    private List<Match> awayMatches = new ArrayList<>();

    @OneToMany (mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> players;

    @ManyToMany(mappedBy = "teams")
    private List<Tournament> tournaments = new ArrayList<>();

    public Team(String name, String category, String season) {
        this.name = name;
        this.category = category;
        this.season = season;
    }

    //metodo auxiliar para añadir un jugador a un equipo
    public void addPlayer(Player player){
        players.add(player);
        player.setTeam(this);
    }
    public void removePlayer(Player player){
        players.remove(player);
        player.setTeam(null);
    }

    //metodo auxiliar para añadir un partido como local
    public void addHomeMatch(Match match){
        homeMatches.add(match);
        match.setHomeTeam(this);
    }
    public void addAwayMatch(Match match){
        awayMatches.add(match);
        match.setAwayTeam(this);
    }
}

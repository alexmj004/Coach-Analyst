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
    private String competition;
    @Column
    private String season;
    @Column
    private int position;
    @Column
    private int points;
    @Column
    private int gf;
    @Column
    private int gc;
    @Column
    private int pg;
    @Column
    private int pe;
    @Column
    private int pp;
    @OneToOne(mappedBy = "team")
    private User user;

    @OneToMany (mappedBy = "homeTeam", cascade = CascadeType.ALL)
    private List<Match> homeMatches = new ArrayList<>();

    @OneToMany (mappedBy = "awayTeam", cascade = CascadeType.ALL)
    private List<Match> awayMatches = new ArrayList<>();

    @OneToMany (mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> players;

    @ManyToMany(mappedBy = "teams")
    private List<Tournament> tournaments = new ArrayList<>();

    public Team(String name, String competition, String season) {
        this.name = name;
        this.competition = competition;
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

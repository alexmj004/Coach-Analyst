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
    private List<Tournament> tournaments;

    public Team(String name, int position, int points, int gf, int gc, int pg, int pe, int pp) {
        this.name = name;
        this.position = position;
        this.points = points;
        this.gf = gf;
        this.gc = gc;
        this.pg = pg;
        this.pe = pe;
        this.pp = pp;
    }
}

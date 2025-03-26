package com.example.tfg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tournaments")

public class Tournament implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private java.sql.Date startDate;
    @Column
    private java.sql.Date endDate;
    @Column
    private String type;
    @Column
    private String location;

    @ManyToMany
    @JoinTable (
        name ="teams_tournaments",
        joinColumns = @JoinColumn(name ="tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    public Tournament(String name, Date startDate, Date endDate, String type, String location) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.location = location;
    }

    //metodo auxiliar para añadir un equipo al torneo
    public void addTeam(Team team){
        teams.add(team);
        team.getTournaments().add(this);
    }
    public void removeTeam(Team team){
        teams.remove(team);
        team.getTournaments().remove(this);
    }
}

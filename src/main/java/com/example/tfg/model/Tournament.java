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
    private String type;
    @Column
    private String location;
    @Column
    private String season;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (
        name ="teams_tournaments",
        joinColumns = @JoinColumn(name ="tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;


    public Tournament(String name, String type, String location, String season) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.season = season;
    }


}

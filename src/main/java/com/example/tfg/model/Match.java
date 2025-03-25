package com.example.tfg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "match")
public class Match implements Serializable {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private java.sql.Timestamp dateTime;
    @Column
    private String location;
    @Column
    private int homeScore;
    @Column
    private int awayScore;
    @Column
    private String notes;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    public Match(Timestamp dateTime, String location, int homeScore, int awayScore, String notes, Team homeTeam, Team awayTeam) {
        this.dateTime = dateTime;
        this.location = location;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.notes = notes;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}

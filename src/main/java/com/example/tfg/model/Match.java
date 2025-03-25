package com.example.tfg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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
    private LocalDate date;
    @Column
    private LocalTime time;
    @Column
    private String location;
    @Column
    private String homeTeam;
    @Column
    private String awayTeam;
    @Column
    private String competition;
    @Column
    private String notes;

    public Match (LocalDate date, LocalTime time, String location, String homeTeam, String awayTeam, String competition, String notes) {
        this.date = date;
        this.time = time;
        this.location = location;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.competition = competition;
        this.notes = notes;
    }
}

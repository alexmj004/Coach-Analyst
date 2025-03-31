package com.example.tfg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "players")
public class Player implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String position;
    @Column
    private java.sql.Date birthDate;
    @Column
    private String contactInfo;
    @Column
    private int goals;
    @Column
    private int assists;
    @Column
    private int yellowCards;
    @Column
    private int redCards;
    @Column(name="is_default")
    private boolean isDefault = false;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


    public Player(String name, String surname, String position, Date birthDate, String contactInfo, int goals, int assists, int yellowCards, int redCards) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.birthDate = birthDate;
        this.contactInfo = contactInfo;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }
}

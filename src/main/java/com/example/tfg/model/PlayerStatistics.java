package com.example.tfg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "player_statistics")
public class PlayerStatistics implements Serializable {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private int gamesPlayed;
    @Column
    private int goals;
    @Column
    private int assist;
    @Column
    private int yellowCards;
    @Column
    private int redCards;
    @Column
    private double minutesPlayed;
    @Column
    private int saves;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;
}

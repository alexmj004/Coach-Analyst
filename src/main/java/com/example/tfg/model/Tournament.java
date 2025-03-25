package com.example.tfg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tournament")

public class Tournament implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private String type;
    @Column
    private String location;

    public Tournament(String name, LocalDate startDate, LocalDate endDate, String type, String location) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.location = location;
    }
}

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
@Table (name = "schedule")
public class Schedule implements Serializable {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private String title;
    @Column
    private LocalDate date;
    @Column
    private LocalTime startTime;
    @Column
    private LocalTime endTime;
    @Column
    private String type;
    @Column
    private String location;
    @Column
    private String description;

    public Schedule(String title, LocalDate date, LocalTime startTime, LocalTime endTime, String type, String location, String description) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.location = location;
        this.description = description;
    }
}

package com.example.tfg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "trainings")
public class Training implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private java.sql.Date date;
    @Column
    private String location;
    @Column
    private String objective;
    @Column
    private String notes;

    public Training(Date date, String location, String objective, String notes) {
        this.date = date;
        this.location = location;
        this.objective = objective;
        this.notes = notes;
    }
}

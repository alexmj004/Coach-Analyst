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
@Table (name = "calendar")
public class Calendar implements Serializable {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private java.sql.Timestamp start;
    @Column
    private java.sql.Timestamp end;
    @Column
    private String location;
    @Column
    private String category;

    public Calendar(String title, String description, Timestamp start, Timestamp end, String location, String category) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.location = location;
        this.category = category;
    }
}

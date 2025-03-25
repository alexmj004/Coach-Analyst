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
@Table (name = "analysis")
public class Analysis implements Serializable {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private LocalDate creationDate;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String analysisType;

    public Analysis(LocalDate creationDate, String title, String description, String analysisType) {
        this.creationDate = creationDate;
        this.title = title;
        this.description = description;
        this.analysisType = analysisType;
    }
}

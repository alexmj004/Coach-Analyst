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
@Table (name = "users")
public class User implements Serializable {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(name="user_name")
    private String userName;
    @Column
    private String email;
    @Column
    private String password;
    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;

    public User(String name, String surname,String userName,String email, String password) {
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}

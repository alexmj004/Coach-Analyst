package com.example.tfg.repository;

import com.example.tfg.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament,Integer> {
}

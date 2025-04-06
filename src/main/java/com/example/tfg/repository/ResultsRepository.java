package com.example.tfg.repository;

import com.example.tfg.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ResultsRepository  extends JpaRepository<Match, Integer> {
    Match findById(int id);

    List<Match> findByDateTime(Timestamp dateTime);
}

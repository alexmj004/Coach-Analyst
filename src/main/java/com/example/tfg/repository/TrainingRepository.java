package com.example.tfg.repository;

import com.example.tfg.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Integer> {
   Optional<Training> findByDate(Date date);
}

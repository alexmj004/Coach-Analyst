package com.example.tfg.service;

import com.example.tfg.model.Calendar;
import com.example.tfg.model.Training;

import java.sql.Date;
import java.util.Optional;

public interface TrainingService {
    //añadir un nuevo evento
    void saveTraining(Training training);
    Optional<Training> findByDate(Date date);
    void updateTraining(Training training);
}

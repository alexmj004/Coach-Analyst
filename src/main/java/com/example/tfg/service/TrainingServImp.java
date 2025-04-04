package com.example.tfg.service;

import com.example.tfg.model.Training;
import com.example.tfg.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class TrainingServImp implements TrainingService {
    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingServImp(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public void saveTraining(Training training) {
        trainingRepository.save(training);
    }

    @Override
    public Optional<Training> findByDate(Date date) {
        return trainingRepository.findByDate(date);
    }

    @Override
    public void updateTraining(Training training) {
        trainingRepository.save(training);
    }

}

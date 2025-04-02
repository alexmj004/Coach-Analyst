package com.example.tfg.service;

import com.example.tfg.model.Training;
import com.example.tfg.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

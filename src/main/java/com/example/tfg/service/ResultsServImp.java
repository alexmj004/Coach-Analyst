package com.example.tfg.service;

import com.example.tfg.model.Match;
import com.example.tfg.repository.ResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ResultsServImp implements ResultsService {
    @Autowired
    ResultsRepository resultsRepository;

    @Override
    public Match findById(int id) {
        return resultsRepository.findById(id);
    }

    @Override
    public List<Match> findAll() {
        return resultsRepository.findAll();
    }

    @Override
    public List<Match> findByDate_time(Timestamp date_time) {
        return resultsRepository.findByDateTime(date_time);
    }
}

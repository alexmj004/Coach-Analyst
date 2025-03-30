package com.example.tfg.service;

import com.example.tfg.model.Calendar;
import com.example.tfg.model.Player;
import com.example.tfg.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class CalendarServImp implements CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;


    @Override
    public Calendar add(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    @Override
    public Player getOne(int id) {
        return null;
    }

    @Override
    public Calendar getByDate(Date date) {
        return calendarRepository.findByCalendarDate(date);
    }

    @Override
    public List<Calendar> getAll() {
        return calendarRepository.findAll();
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public Boolean update(Calendar calendar) {
        return null;
    }
}

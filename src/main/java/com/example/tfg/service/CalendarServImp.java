package com.example.tfg.service;

import com.example.tfg.model.Calendar;
import com.example.tfg.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarServImp implements CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public Calendar save(Calendar calendar) {
        calendarRepository.save(calendar);
        return calendar;
    }
}

package com.example.tfg.service;

import com.example.tfg.model.Calendar;
import com.example.tfg.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarServImp implements CalendarService {
    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarServImp(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Override
    public void saveCalendar(Calendar calendar) {
        calendarRepository.save(calendar);
    }

    @Override
    public List<Calendar> findByDay(LocalDate date) {
        Timestamp startOfDay = Timestamp.valueOf(date.atStartOfDay());
        Timestamp endOfDay = Timestamp.valueOf(date.plusDays(1).atStartOfDay());

        try {
            return calendarRepository.findByStartBetween(startOfDay, endOfDay);
        } catch (Exception e) {
            return calendarRepository.findAll().stream()
                    .filter(event -> {
                        Timestamp eventStart = event.getStart();
                        return !eventStart.before(startOfDay) && eventStart.before(endOfDay);
                    })
                    .toList();
        }
    }
}
package com.example.tfg.service;

import com.example.tfg.model.Calendar;

import java.time.LocalDate;
import java.util.List;

public interface CalendarService {

    //añadir un nuevo evento
    void saveCalendar(Calendar calendar);



    //mostrar eventos de un día completo (LocalDate)
    List<Calendar> findByDay(LocalDate date);
}

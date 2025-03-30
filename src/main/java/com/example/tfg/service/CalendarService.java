package com.example.tfg.service;

import com.example.tfg.model.Calendar;
import com.example.tfg.model.Player;

import java.sql.Date;
import java.util.List;

public interface CalendarService {
    // Añadir un nuevo calendario
    Calendar add(Calendar calendar);

    // Obtener un calendario por su ID
    Player getOne(int id);

    // Obtener calendario por su fecha inicio.
    Calendar getByDate(Date date);

    // Obtener todos los calendarios
    List<Calendar> getAll();

    // Eliminar un calendario por su ID
    Boolean delete(int id);

    // Actualizar un calendario
    Boolean update(Calendar calendar);
}

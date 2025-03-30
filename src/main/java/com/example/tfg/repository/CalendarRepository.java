package com.example.tfg.repository;

import com.example.tfg.model.Calendar;
import com.example.tfg.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    // Método buscar por fecha calendario .
    @Query("SELECT c FROM Calendar c WHERE c.start = :start")
    Calendar findByCalendarDate(@Param("start") Date start);

}

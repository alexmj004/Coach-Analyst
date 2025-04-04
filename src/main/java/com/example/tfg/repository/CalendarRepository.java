package com.example.tfg.repository;

import com.example.tfg.model.Calendar;
import com.example.tfg.model.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    // Método para buscar eventos entre dos fechas
    @Query("SELECT c FROM Calendar c WHERE c.start >= :startDate AND c.start < :endDate")
    List<Calendar> findByStartBetween(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM Calendar c WHERE DATE(c.start) = :date")
    void deleteByDate(@Param("date") LocalDate date);
}
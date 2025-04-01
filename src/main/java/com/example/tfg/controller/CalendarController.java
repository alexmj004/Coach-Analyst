package com.example.tfg.controller;

import com.example.tfg.model.Calendar;
import com.example.tfg.service.CalendarService;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class CalendarController {
    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public void setupCalendarComponents(Scene calendarScene) {
        DatePicker datePicker = (DatePicker) calendarScene.lookup("#date");
        TextField appointmentField = (TextField) calendarScene.lookup("#input");
        TextArea appointmentsArea = (TextArea) calendarScene.lookup("#results");
        Button addButton = (Button) calendarScene.lookup("#add");
        Button resetButton = (Button) calendarScene.lookup("#reset");

        addButton.setOnAction(e -> {
            String appointmentText = appointmentField.getText();
            LocalDate selectedDate = datePicker.getValue();

            if (!appointmentText.isEmpty() && selectedDate != null) {
                addAppointment(appointmentText, selectedDate, appointmentsArea);
                appointmentField.clear();
            } else {
                showAlert("Datos incompletos", "Por favor seleccione una fecha y escriba una descripción");
            }
        });

        resetButton.setOnAction(e -> {
            appointmentField.clear();
            appointmentsArea.clear();
            datePicker.setValue(null);
        });

        datePicker.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                loadAppointmentsByDate(selectedDate, appointmentsArea);
            }
        });
    }
// TODO: implementa la hora de inicio y fin y un desplegable para seleccionar la categoria y la localizacion
    private void addAppointment(String description, LocalDate date, TextArea appointmentsArea) {
        try {
            Calendar newEvent = new Calendar();
            newEvent.setTitle("Evento");
            newEvent.setDescription(description);
            Timestamp startTimestamp = Timestamp.valueOf(date.atStartOfDay());
            newEvent.setStart(startTimestamp);
            Timestamp endTimestamp = Timestamp.valueOf(date.atStartOfDay().plusDays(1));
            newEvent.setEnd(endTimestamp);
            newEvent.setCategory("Appointment");
            newEvent.setLocation("Club");

            calendarService.saveCalendar(newEvent);
            loadAppointmentsByDate(date, appointmentsArea);
            showAlert("Evento agregado", "El evento se ha añadido correctamente al calendario");
        } catch (Exception e) {
            logger.error("Error al añadir evento", e);
            showAlert("Error", "No se pudo añadir el evento: " + e.getMessage());
        }
    }
//TODO implementar lo mismo que en la insercion, categoria, localizacion, hora de inicio y hora de fin
    private void loadAppointmentsByDate(LocalDate date, TextArea appointmentsArea) {
        try {
            List<Calendar> events = calendarService.findByDay(date);
            appointmentsArea.clear();
            if (events.isEmpty()) {
                appointmentsArea.setText("No hay eventos programados para esta fecha.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Calendar event : events) {
                    sb.append("Título: ").append(event.getTitle()).append("\n");
                    sb.append("Descripción: ").append(event.getDescription()).append("\n");
                    sb.append("Hora inicio: ").append(event.getStart().toLocalDateTime().toLocalTime()).append("\n");
                    if (event.getEnd() != null) {
                        sb.append("Hora fin: ").append(event.getEnd().toLocalDateTime().toLocalTime()).append("\n");
                    }
                    sb.append("-----------------------------------\n");
                }
                appointmentsArea.setText(sb.toString());
            }
        } catch (Exception e) {
            logger.error("Error al cargar eventos", e);
            appointmentsArea.setText("Error al cargar los eventos: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
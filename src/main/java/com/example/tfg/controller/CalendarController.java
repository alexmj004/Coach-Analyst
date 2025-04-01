package com.example.tfg.controller;

import com.example.tfg.model.Calendar;
import com.example.tfg.service.CalendarService;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        ChoiceBox<String> categoriaBox = (ChoiceBox<String>) calendarScene.lookup("#categoria");
        TextField locationField = (TextField) calendarScene.lookup("#location");
        ComboBox<String> startTImeBox = (ComboBox<String>) calendarScene.lookup("#startTime");
        ComboBox<String> endTimeBox = (ComboBox<String>) calendarScene.lookup("#endTime");

        //agregar categorias al CHoiceBox
        categoriaBox.getItems().addAll("Match", "Training", "Meeting", "Other");
        //inicializar combobox de horas
        for (int hour = 0; hour < 24; hour++){
            for (int minute = 0; minute <60; minute += 30){
                String time = String.format("%02d:%02d", hour, minute);
                startTImeBox.getItems().add(time);
                endTimeBox.getItems().add(time);
            }
        }


        // mostrar la fecha actual en el DatePicker
        LocalDate today = LocalDate.now();
        datePicker.setValue(today);

        //cargar los appointments del dia actual
        loadAppointmentsByDate(today, appointmentsArea);

        addButton.setOnAction(e -> {
            String appointmentText = appointmentField.getText();
            LocalDate selectedDate = datePicker.getValue();
            String category = categoriaBox.getValue();
            String location = locationField.getText();
            String starTime = startTImeBox.getValue();
            String endTime = endTimeBox.getValue();

            if (!appointmentText.isEmpty() && selectedDate != null && starTime != null && endTime != null) {
                addAppointment(appointmentText, selectedDate, starTime,endTime, appointmentsArea, category, location);
                appointmentField.clear();
            } else {
                showAlert("Datos incompletos", "Por favor seleccione una fecha y escriba una descripción");
            }
        });

        resetButton.setOnAction(e -> {
            appointmentField.clear();
            appointmentsArea.clear();
            datePicker.setValue(null);
            startTImeBox.setValue(null);
            endTimeBox.setValue(null);
        });

        datePicker.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                loadAppointmentsByDate(selectedDate, appointmentsArea);
            }
        });
    }
// TODO: implementa la hora de inicio y fin
    private void addAppointment(String description, LocalDate date,String startTime,String endTime, TextArea appointmentsArea, String category, String location) {
        try {
            Calendar newEvent = new Calendar();
            newEvent.setTitle("Evento");
            newEvent.setDescription(description);
            LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.parse(startTime));
            LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.parse(endTime));
            newEvent.setStart(Timestamp.valueOf(startDateTime));
            newEvent.setEnd(Timestamp.valueOf(endDateTime));
            newEvent.setCategory(category);
            newEvent.setLocation(location);

            calendarService.saveCalendar(newEvent);
            loadAppointmentsByDate(date, appointmentsArea);
            showAlert("Evento agregado", "El evento se ha añadido correctamente al calendario");
        } catch (Exception e) {
            logger.error("Error al añadir evento", e);
            showAlert("Error", "No se pudo añadir el evento: " + e.getMessage());
        }
    }
//TODO implementar lo mismo que en la insercion hora de inicio y hora de fin
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
                    sb.append("Fecha: ").append(event.getStart().toLocalDateTime().toLocalDate()).append("\n");
                    sb.append("Hora inicio: ").append(event.getStart().toLocalDateTime().toLocalTime()).append("\n");
                    sb.append("Hora fin: ").append(event.getEnd().toLocalDateTime().toLocalTime()).append("\n");
                    sb.append("Categoría: ").append(event.getCategory()).append("\n");
                    sb.append("Localización: ").append(event.getLocation()).append("\n");
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
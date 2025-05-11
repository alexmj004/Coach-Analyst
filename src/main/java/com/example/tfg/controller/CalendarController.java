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
import java.util.Optional;

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
            LocalDate selectedDate = datePicker.getValue();

            if (selectedDate != null) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmar eliminación");
                confirmation.setHeaderText("¿Eliminar todos los eventos del " + selectedDate + "?");
                confirmation.setContentText("Esta acción no se puede deshacer.");

                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            calendarService.deleteByDate(selectedDate);
                            loadAppointmentsByDate(selectedDate, appointmentsArea); // Refrescar la vista
                            showAlert("Éxito", "Eventos del día eliminados correctamente");
                        } catch (Exception ex) {
                            logger.error("Error al eliminar eventos", ex);
                            showAlert("Error", "No se pudieron eliminar los eventos: " + ex.getMessage());
                        }
                    }
                });
            }

            // Limpiar campos después de eliminar
            appointmentField.clear();
            startTImeBox.setValue(null);
            endTimeBox.setValue(null);
            categoriaBox.setValue(null);
            locationField.clear();
        });        datePicker.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                loadAppointmentsByDate(selectedDate, appointmentsArea);
            }
        });
    }

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

    private void loadAppointmentsByDate(LocalDate date, TextArea appointmentsArea) {
        try {
            List<Calendar> events = calendarService.findByDay(date);

            // Ordenar los eventos por hora de inicio
            events.sort((event1, event2) -> {
                LocalTime time1 = event1.getStart().toLocalDateTime().toLocalTime();
                LocalTime time2 = event2.getStart().toLocalDateTime().toLocalTime();
                return time1.compareTo(time2);
            });

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
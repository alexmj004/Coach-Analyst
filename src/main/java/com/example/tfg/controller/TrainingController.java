package com.example.tfg.controller;

import com.example.tfg.model.Training;
import com.example.tfg.service.TrainingService;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    public void setupTrainingComponents(Scene trainingScene) {
        DatePicker datePicker = (DatePicker) trainingScene.lookup("#calendar_training");
        TextField locationField = (TextField) trainingScene.lookup("#location");
        TextField objectiveField = (TextField) trainingScene.lookup("#objective_training");
        TextArea trainingDescription = (TextArea) trainingScene.lookup("#description_training");
        Button addButton = (Button) trainingScene.lookup("#accept_btn");
        Button modifyButton = (Button) trainingScene.lookup("#modify_btn");

        // Desactivar campos por defecto
        locationField.setDisable(true);
        objectiveField.setDisable(true);
        trainingDescription.setDisable(true);

        // Mostrar la fecha actual en el DatePicker
        LocalDate today = LocalDate.now();
        datePicker.setValue(today);

        // Cargar el entrenamiento del día actual
        loadTrainingByDate(today, locationField, objectiveField, trainingDescription);

        addButton.setOnAction(e -> {
            String location = locationField.getText();
            LocalDate eventDate = datePicker.getValue();
            String objective = objectiveField.getText();
            String description = trainingDescription.getText();

            if (!location.isEmpty() && eventDate != null && !objective.isEmpty() && !description.isEmpty()) {
                addOrUpdateTraining(location, eventDate, objective, description);
                showAlert("Entrenamiento guardado", "El entrenamiento se ha guardado correctamente");
                // Desactivar campos por defecto
                locationField.setDisable(true);
                objectiveField.setDisable(true);
                trainingDescription.setDisable(true);
            } else {
                showAlert("Datos incompletos", "Por favor seleccione una fecha y escriba una descripción");
            }
        });

        modifyButton.setOnAction(e -> {
            // Habilitar campos para edición
            locationField.setDisable(false);
            objectiveField.setDisable(false);
            trainingDescription.setDisable(false);
        });

        datePicker.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                loadTrainingByDate(selectedDate, locationField, objectiveField, trainingDescription);
            }
        });
    }

    private void loadTrainingByDate(LocalDate date, TextField locationField, TextField objectiveField, TextArea trainingDescription) {
        Optional<Training> trainingOpt = trainingService.findByDate(java.sql.Date.valueOf(date));
        if (trainingOpt.isPresent()) {
            Training training = trainingOpt.get();
            locationField.setText(training.getLocation());
            objectiveField.setText(training.getObjective());
            trainingDescription.setText(training.getNotes());
        } else {
            locationField.clear();
            objectiveField.clear();
            trainingDescription.clear();
            showAlert("No hay entrenamiento", "No hay entrenamiento programado para esta fecha");
        }
    }

    private void addOrUpdateTraining(String location, LocalDate eventDate, String objective, String description) {
        try {
            java.sql.Date sqlDate = java.sql.Date.valueOf(eventDate);

            //este método busca si ya existe un entrenamiento para la fecha seleccionada, si lo encuentra solo lo modifica
            //si no lo encuentra, crea uno nuevo.  el valor de trainingOpt puede ser un objeto Training o null
            Optional<Training> trainingOpt = trainingService.findByDate(sqlDate);

            Training training;

            // el valor de trainingOpt.isPresent() es true si existe un entrenamiento para la fecha seleccionada
            if (trainingOpt.isPresent()) {
                training = trainingOpt.get();
                training.setLocation(location);
                training.setObjective(objective);
                training.setNotes(description);
                trainingService.updateTraining(training);
                showAlert("Entrenamiento modificado", "El entrenamiento existente se ha modificado correctamente");
            } else {
                training = new Training();
                training.setDate(sqlDate);
                training.setLocation(location);
                training.setObjective(objective);
                training.setNotes(description);
                trainingService.saveTraining(training);
                showAlert("Entrenamiento agregado", "El entrenamiento se ha añadido correctamente");
            }
        } catch (Exception e) {
            showAlert("Error", "No se ha podido añadir o modificar el entrenamiento");
            System.out.println("Error al añadir o modificar entrenamiento: " + e.getMessage());
        }
    }

    private void showAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
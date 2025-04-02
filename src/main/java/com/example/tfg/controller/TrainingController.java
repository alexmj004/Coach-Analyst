package com.example.tfg.controller;

import com.example.tfg.model.Training;
import com.example.tfg.service.TrainingService;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    public void setupTrainingComponents(Scene trainingScene) {
        DatePicker datePicker = (DatePicker) trainingScene.lookup("#calendar_training");
        TextField locationField = (TextField) trainingScene.lookup("#location");
        TextField objectiveField = (TextField) trainingScene.lookup("#objective_training");
        TextField notesField = (TextField) trainingScene.lookup("#notes");
        TextArea trainingDescription = (TextArea) trainingScene.lookup("#description_training");
        Button addButton = (Button) trainingScene.lookup("#accept_btn");
        Button modifyButton = (Button) trainingScene.lookup("#modify_btn");

        // mostrar la fecha actual en el DatePicker
        LocalDate today = LocalDate.now();
        datePicker.setValue(today);

        addButton.setOnAction(e -> {
            String location = locationField.getText();
            LocalDate eventDate = datePicker.getValue();
            String objective = objectiveField.getText();
            String notesText = notesField.getText();

            if (!location.isEmpty() && eventDate != null && !objective.isEmpty()) {
                addTraining(location, eventDate, objective, notesText);
                showAlert("Entrenamiento agregado", "El entrenamiento se ha añadido correctamente");
            } else {
                showAlert("Datos incompletos", "Por favor seleccione una fecha y escriba una descripción");
            }
        });
    }

    private void addTraining(String location, LocalDate eventDate, String objective, String notesText) {
        try {
            Training newTraining = new Training();
            newTraining.setLocation(location);
            java.sql.Date sqlDate = java.sql.Date.valueOf(eventDate);
            newTraining.setDate(sqlDate);
            newTraining.setObjective(objective);
            newTraining.setNotes(notesText);

            trainingService.saveTraining(newTraining);

        } catch (Exception e) {
            showAlert("Error", "No se ha podido añadir el entrenamiento");
            System.out.println("Error al añadir entrenamiento: " + e.getMessage());
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
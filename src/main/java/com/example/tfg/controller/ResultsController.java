package com.example.tfg.controller;

import com.example.tfg.model.Match;
import com.example.tfg.service.ResultsService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ResultsController {
    @Autowired
    private ResultsService resultsService;

    public void setupResultsComponents(Scene resultsScene) {
        // Obtener la referencia al VBox contenedor donde agregaremos los resultados
        AnchorPane sectionResults = (AnchorPane) resultsScene.lookup("#section_results");
        VBox vBoxContainer = (VBox) sectionResults.getChildren().get(0);

        // Obtener el HBox original como plantilla
        HBox templateHBox = (HBox) vBoxContainer.lookup("#result");

        // Obtener el Label de fecha original para referencia
        Label originalFechaLabel = (Label) vBoxContainer.lookup("#fecha");

        // Remover el HBox plantilla y el Label fecha original
        if (templateHBox != null) {
            vBoxContainer.getChildren().remove(templateHBox);
        }
        if (originalFechaLabel != null) {
            vBoxContainer.getChildren().remove(originalFechaLabel);
        }

        // Cargar todos los partidos
        loadAllMatches(vBoxContainer, templateHBox);
    }

    private void loadAllMatches(VBox container, HBox template) {
        try {
            List<Match> matches = resultsService.findAll();

            if (matches == null || matches.isEmpty()) {
                showNoMatchesMessage(container);
                return;
            }

            // Procesar cada partido
            for (Match match : matches) {
                // Crear label para la fecha
                Label fechaLabel = new Label(formatTimestamp(match.getDateTime()));
                fechaLabel.setTextFill(javafx.scene.paint.Color.WHITE);
                fechaLabel.setPadding(new Insets(10, 0, 5, 630));
                container.getChildren().add(fechaLabel);

                // Crear un nuevo HBox para este partido basado en el template
                HBox matchHBox = createMatchHBox(match);
                matchHBox.setPrefWidth(600);
                matchHBox.setPrefHeight(50);
                matchHBox.setAlignment(javafx.geometry.Pos.CENTER);
                matchHBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");
                matchHBox.setSpacing(50);

                // Agregar margen
                HBox.setMargin(matchHBox, new Insets(0, 50, 40, 350));

                // Agregar el HBox al contenedor
                container.getChildren().add(matchHBox);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los partidos: " + e.getMessage());
            e.printStackTrace();
            showErrorMessage(container);
        }
    }

    private HBox createMatchHBox(Match match) {
        HBox matchHBox = new HBox(50);

        // Crear las etiquetas para este partido
        Label teamALabel = new Label(match.getHomeTeam().getName());
        teamALabel.setFont(new Font(20));

        Label homeScoreLabel = new Label(String.valueOf(match.getHomeScore()) + ":");
        homeScoreLabel.setFont(new Font(20));
        homeScoreLabel.setStyle("-fx-font-weight: bolder;");

        Label awayScoreLabel = new Label(String.valueOf(match.getAwayScore()));
        awayScoreLabel.setFont(new Font(20));
        awayScoreLabel.setStyle("-fx-font-weight: bolder;");
        HBox.setMargin(awayScoreLabel, new Insets(0, 0, 0, -50));

        Label teamBLabel = new Label(match.getAwayTeam().getName());
        teamBLabel.setFont(new Font(20));
        teamBLabel.setAlignment(javafx.geometry.Pos.CENTER);

        // Agregar las etiquetas al HBox
        matchHBox.getChildren().addAll(teamALabel, homeScoreLabel, awayScoreLabel, teamBLabel);

        return matchHBox;
    }

    private void showNoMatchesMessage(VBox container) {
        Label noMatchesLabel = new Label("No hay partidos disponibles");
        noMatchesLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        noMatchesLabel.setFont(new Font(20));
        noMatchesLabel.setPadding(new Insets(50, 0, 0, 550));
        container.getChildren().add(noMatchesLabel);
    }

    private void showErrorMessage(VBox container) {
        Label errorLabel = new Label("Error al cargar los partidos");
        errorLabel.setTextFill(javafx.scene.paint.Color.RED);
        errorLabel.setFont(new Font(20));
        errorLabel.setPadding(new Insets(50, 0, 0, 550));
        container.getChildren().add(errorLabel);
    }

    private String formatTimestamp(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(timestamp);
    }
}
package com.example.tfg.controller;

import com.example.tfg.TfgApplication;
import com.example.tfg.model.Match;
import com.example.tfg.service.ResultsService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight; // Asegúrate de importar FontWeight
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ResultsController {
    @Autowired
    private ResultsService resultsService;

    @Autowired
    private TfgApplication tfgApplication;

    @FXML private VBox resultsContainer;
    @FXML private Label nombre_coach;
    @FXML private ImageView img_menu;
    @FXML private ImageView img_calendar;
    @FXML private ImageView img_out;

    @FXML
    public void initialize() {
        // Configurar los manejadores de eventos para las imágenes
        img_menu.setOnMouseClicked(this::handleMenuClick);
        img_calendar.setOnMouseClicked(this::handleCalendarClick);
        img_out.setOnMouseClicked(this::handleLogoutClick);

        loadAllMatches();
    }

    // Métodos de navegación que delegan a TfgApplication
    @FXML
    public void handleClasificationClick(MouseEvent event) {
        tfgApplication.handleClasificationClick(event);
    }

    @FXML
    public void handleResultsClick(MouseEvent event) {
        tfgApplication.handleResultsClick(event);
    }

    @FXML
    public void handleTeamsClick(MouseEvent event) {
        tfgApplication.handleTeamsClick(event);
    }

    @FXML
    public void handleMenuClick(MouseEvent event) {
        tfgApplication.handleImgMenuClick(event);
    }

    @FXML
    public void handleCalendarClick(MouseEvent event) {
        tfgApplication.handleImgCalendarClick(event);
    }

    @FXML
    public void handleLogoutClick(MouseEvent event) {
        tfgApplication.handleImgOutClick(event);
    }

    private void loadAllMatches() {
        try {
            resultsContainer.getChildren().clear();

            List<Match> matches = resultsService.findAll();

            if (matches == null || matches.isEmpty()) {
                showNoMatchesMessage();
                return;
            }

            // Agrupar por jornada
            Label jornadaLabel = new Label("MATCH RESULTS");
            jornadaLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
            jornadaLabel.setTextFill(Color.web("#2C3E50"));
            VBox.setMargin(jornadaLabel, new Insets(0, 0, 20, 50));
            resultsContainer.getChildren().add(jornadaLabel);

            // Procesar cada partido
            for (Match match : matches) {
                addMatchToContainer(match);
            }
        } catch (Exception e) {
            System.err.println("Error loading matches: " + e.getMessage());
            showErrorMessage();
        }
    }

    private void addMatchToContainer(Match match) {
        // Contenedor principal del partido
        VBox matchContainer = new VBox(5);
        matchContainer.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        matchContainer.setPadding(new Insets(10));
        matchContainer.setMaxWidth(1000);

        // Fecha del partido
        Label dateLabel = new Label(formatDate(match.getDateTime()));
        dateLabel.setFont(Font.font(14));
        dateLabel.setTextFill(Color.web("#7F8C8D"));
        matchContainer.getChildren().add(dateLabel);

        // Contenedor del marcador
        HBox scoreBox = new HBox(20);
        scoreBox.setAlignment(javafx.geometry.Pos.CENTER);

        // Equipo local
        Label homeTeamLabel = new Label(match.getHomeTeam().getName());
        homeTeamLabel.setFont(Font.font(18));
        homeTeamLabel.setMaxWidth(300);
        homeTeamLabel.setMinWidth(300);
        homeTeamLabel.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        // Marcador
        HBox scoreLabelBox = new HBox(5);
        scoreLabelBox.setAlignment(javafx.geometry.Pos.CENTER);

        Label homeScore = new Label(String.valueOf(match.getHomeScore()));
        homeScore.setFont(Font.font("System", FontWeight.BOLD, 20)); // Forma correcta

        Label separator = new Label("-");
        separator.setFont(new Font(20)); // Alternativa simple

        Label awayScore = new Label(String.valueOf(match.getAwayScore()));
        awayScore.setFont(Font.font("System", FontWeight.BOLD, 20));

        scoreLabelBox.getChildren().addAll(homeScore, separator, awayScore);

        // Equipo visitante
        Label awayTeamLabel = new Label(match.getAwayTeam().getName());
        awayTeamLabel.setFont(Font.font(18));
        awayTeamLabel.setMaxWidth(300);
        awayTeamLabel.setMinWidth(300);
        awayTeamLabel.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Añadir al contenedor de marcador
        scoreBox.getChildren().addAll(homeTeamLabel, scoreLabelBox, awayTeamLabel);
        matchContainer.getChildren().add(scoreBox);

        // Añadir margen y al contenedor principal
        VBox.setMargin(matchContainer, new Insets(0, 0, 20, 50));
        resultsContainer.getChildren().add(matchContainer);
    }

    private void showNoMatchesMessage() {
        Label noMatchesLabel = new Label("No matches available");
        noMatchesLabel.setTextFill(Color.web("#2C3E50"));
        noMatchesLabel.setFont(Font.font(20));
        VBox.setMargin(noMatchesLabel, new Insets(20, 0, 0, 50));
        resultsContainer.getChildren().add(noMatchesLabel);
    }

    private void showErrorMessage() {
        Label errorLabel = new Label("Error loading matches");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font(20));
        VBox.setMargin(errorLabel, new Insets(20, 0, 0, 50));
        resultsContainer.getChildren().add(errorLabel);
    }

    private String formatDate(java.sql.Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy - HH:mm");
        return dateFormat.format(timestamp);
    }
}
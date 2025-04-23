package com.example.tfg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import java.io.File;

@Component
public class VideosController {
    @FXML private MediaView mediaView;
    @FXML private Label videoTitleLabel;
    @FXML private ListView<String> videosListView;
    @FXML private TextField videoTitle;

    private MediaPlayer mediaPlayer;

    @FXML
    private void initialize() {
        mediaView.setPreserveRatio(true);
        videosListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> loadSelectedVideo(newVal)
        );
    }

    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.flv", "*.m4v", "*.mov"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) mediaView.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            playVideo(selectedFile);
            videoTitleLabel.setText(selectedFile.getName());
        }
    }

    private void playVideo(File file) {
        try {
            cleanup(); // Limpiar recursos anteriores

            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
                videosListView.getItems().add(file.getName());
            });

            mediaPlayer.setOnError(() ->
                    showAlert("Media Error", mediaPlayer.getError().getMessage())
            );

        } catch (Exception e) {
            showAlert("Error", "Unsupported media or corrupt file: " + e.getMessage());
        }
    }

    private void loadSelectedVideo(String fileName) {
        if (fileName != null) {
            File file = new File(fileName); // Ajusta según tu estructura de archivos
            playVideo(file);
        }
    }

    @FXML
    private void handlePlay() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    @FXML
    private void handlePause() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    @FXML
    private void handleStop() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    @FXML
    private void handleUpload() {
        // Implementa lógica para subir el video a un servidor/directorio
        showAlert("Info", "Upload functionality to be implemented.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void cleanup() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
}
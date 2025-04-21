package com.example.tfg.controller;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class VideosController {
    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    @FXML
    private void initialize() {
        // Configuración inicial del MediaView
        mediaView.setPreserveRatio(true);
    }

    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Video");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Video", "*.mp4", "*.flv", "*.m4v"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            playVideo(selectedFile.getAbsolutePath());
        }
    }

    private void playVideo(String videoPath) {
        try {
            // Detener el reproductor actual si existe
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            // Crear nuevo Media y MediaPlayer
            Media media = new Media(new File(videoPath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            // Configurar acciones cuando el video termine
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());

            // Reproducir el video
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
            // Aquí deberías mostrar un mensaje de error al usuario
        }
    }

    @FXML
    private void handlePlay() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    private void handlePause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void handleStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void cleanup() {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
    }
}
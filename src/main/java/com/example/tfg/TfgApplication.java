package com.example.tfg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TfgApplication extends Application {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		// Inicia la aplicación de Spring Boot
		context = SpringApplication.run(TfgApplication.class, args);
		launch(args); // Inicia la aplicación de JavaFX
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Carga el archivo FXML
		var fxmlLoader = new FXMLLoader(getClass().getResource("/TfgApplication.fxml"));
		var scene = new Scene(fxmlLoader.load());
		stage.setScene(scene);
		stage.setTitle("Mi aplicación JavaFX y Spring Boot");
		stage.show();
	}

	@Override
	public void stop() {
		// Cierra la aplicación de Spring Boot cuando se cierra la ventana de JavaFX
		if (context != null) {
			context.close();
		}
	}
}

package com.example.tfg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TfgApplication extends Application {

	private static ConfigurableApplicationContext context;
	private String username;

	public static void main(String[] args) {
		// Inicia la aplicación de Spring Boot
		context = SpringApplication.run(TfgApplication.class, args);
		launch(args); // Inicia la aplicación de JavaFX
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
		var loginScene = new Scene(fxmlLoader.load());

		TextField userField = (TextField) loginScene.lookup("#user");
		PasswordField passField = (PasswordField) loginScene.lookup("#pass");
		Button loginButton = (Button) loginScene.lookup("#login_btn");

		loginButton.setOnAction(e -> handleLoginButtonAction(userField, passField, stage));

		stage.setScene(loginScene);
		stage.setTitle("Inicio de sesión");
		stage.show();
	}

	private void handleLoginButtonAction(TextField userField, PasswordField passField, Stage stage) {
		username = userField.getText();
		String password = passField.getText();

		if (username.equals("alex_mtz004") && password.equals("1234")) {
			try {
				showMenu(stage);  // Cambiar a la ventana del menú si las credenciales son correctas
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error de inicio de sesión");
			alert.setHeaderText("Credenciales inválidas");
			alert.setContentText("Por favor, ingrese un usuario y contraseña válidos.");
			alert.showAndWait();
		}
	}

	public void showMenu(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
		var menuScene = new Scene(fxmlLoader.load());

		stage.setScene(menuScene);  // Establece la escena antes de manipular la UI

		// Usar Platform.runLater para asegurarse de que la UI esté completamente cargada
		Platform.runLater(() -> {
			// Ahora es seguro realizar el lookup, ya que la escena está completamente cargada
			Label nombreCoach = (Label) menuScene.lookup("#nombre_coach");

			if (nombreCoach != null) {
				nombreCoach.setText(username); // Actualiza el texto del Label
			} else {
				System.out.println("No se ha encontrado el Label 'nombre_coach'.");
			}
		});

		stage.setTitle("Menú");
		stage.show();
	}

	@Override
	public void stop() {
		if (context != null) {
			context.close();
		}
	}
}

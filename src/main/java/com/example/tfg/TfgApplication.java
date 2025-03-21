package com.example.tfg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

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
		// Cargar la pantalla de Login al iniciar la aplicación
		showLoginScene(stage);
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

		stage.setScene(menuScene);  // Establece la escena del menú

		// Asignar los eventos de clic a los botones del menú
		setMenuClickListener(menuScene);
		setOutClickListener(menuScene);

		// Asignar los eventos de los botones del menú
		Button trainingButton = (Button) menuScene.lookup("#training_btn");
		if (trainingButton != null) {
			trainingButton.setOnAction(e -> handleTrainingButtonAction(stage));
		}

		Button planningButton = (Button) menuScene.lookup("#planning_btn");
		if (planningButton != null) {
			planningButton.setOnAction(e -> handlePlanningButtonAction(stage));
		}

		// Actualiza el nombre del coach en la interfaz
		Label nombreCoach = (Label) menuScene.lookup("#nombre_coach");
		if (nombreCoach != null) {
			nombreCoach.setText(username); // Muestra el nombre del usuario en el label
		}

		stage.setTitle("Menú");
		stage.show();
	}

	// Método para asignar el clic en img_menu en cualquier escena
	private void setMenuClickListener(Scene scene) {
		ImageView imgMenu = (ImageView) scene.lookup("#img_menu");
		if (imgMenu != null) {
			imgMenu.setOnMouseClicked(this::handleImgMenuClick);
		}
	}

	// Método para asignar el clic en img_out en cualquier escena
	private void setOutClickListener(Scene scene) {
		ImageView imgOut = (ImageView) scene.lookup("#img_out");
		if (imgOut != null) {
			imgOut.setOnMouseClicked(this::handleImgOutClick);
		}
	}

	// Este es el método que se llamará cuando se haga clic en img_menu
	private void handleImgMenuClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en img_menu!");
		try {
			showMenu((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Este es el método que se llamará cuando se haga clic en img_out
	private void handleImgOutClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en img_out!");
		try {
			// Limpiar la sesión (usuario y contraseña)
			username = null; // Reiniciar la variable username
			showLoginScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());  // Volver a la pantalla de login
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showLoginScene(Stage stage) throws IOException {
		// Cargar el archivo FXML de la pantalla de inicio de sesión
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
		var loginScene = new Scene(fxmlLoader.load());

		// Limpiar los campos de texto (usuario y contraseña)
		TextField userField = (TextField) loginScene.lookup("#user");
		PasswordField passField = (PasswordField) loginScene.lookup("#pass");
		userField.clear();
		passField.clear();

		// Asignar el controlador de acción para el botón de login
		Button loginButton = (Button) loginScene.lookup("#login_btn");
		loginButton.setOnAction(e -> handleLoginButtonAction(userField, passField, stage));

		// Asignar la nueva escena al stage
		stage.setScene(loginScene);
		stage.setTitle("Inicio de sesión");

		// Mostrar la nueva escena
		stage.show();
	}

	public void showTrainingScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Training.fxml"));
		var trainingScene = new Scene(fxmlLoader.load());
		stage.setScene(trainingScene);

		setMenuClickListener(trainingScene);
		setOutClickListener(trainingScene);

		stage.setTitle("Training");
		stage.show();
	}

	private void handleTrainingButtonAction(Stage stage) {
		try {
			showTrainingScene(stage);  // Llama a la escena de Training
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showPlanningScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Planning.fxml"));
		var planningScene = new Scene(fxmlLoader.load());
		stage.setScene(planningScene);

		setMenuClickListener(planningScene);
		setOutClickListener(planningScene);

		stage.setTitle("Planning");
		stage.show();
	}

	private void handlePlanningButtonAction(Stage stage) {
		try {
			showPlanningScene(stage);  // Llama a la escena de Planning
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		if (context != null) {
			context.close();
		}
	}
}

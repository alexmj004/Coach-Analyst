package com.example.tfg;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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
		// Primero, iniciar Spring Boot y luego JavaFX
		context = SpringApplication.run(TfgApplication.class, args);
		launch(args); // Lanza la aplicación de JavaFX
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Cargar la pantalla de Login al iniciar la aplicación
		showLoginScene(stage);

		// Maximizar la ventana al iniciar
		stage.setMaximized(true); // Esto maximiza la ventana cuando se abre
	}

	// Definir el stage de la interfaz login.
	private void showLoginScene(Stage stage) throws IOException {
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

	// Método para mostrar la interfaz de menú
	public void showMenu(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
		var menuScene = new Scene(fxmlLoader.load());

		stage.setScene(menuScene);

		// Asignar eventos del menú
		setMenuClickListener(menuScene);
		setOutClickListener(menuScene);

		// Botones de funcionalidad en el menú
		Button calendarButton = (Button) menuScene.lookup("#calendar_btn");
		if (calendarButton != null) {
			calendarButton.setOnAction(e -> handleCalendarButtonAction(stage));
		}

		// Otros botones
		Button trainingButton = (Button) menuScene.lookup("#training_btn");
		if (trainingButton != null) {
			trainingButton.setOnAction(e -> handleTrainingButtonAction(stage));
		}
		Button matchButton = (Button) menuScene.lookup("#match_btn");
		if (matchButton != null) {
			matchButton.setOnAction(e -> handleMatchButtonAction(stage));
		}
		Button analystButton = (Button) menuScene.lookup("#analyst_btn");
		if (analystButton != null) {
			analystButton.setOnAction(e -> handleAnalystButtonAction(stage));
		}
		Button tournamentButton = (Button) menuScene.lookup("#tournament_btn");
		if (tournamentButton != null) {
			tournamentButton.setOnAction(e -> handleTournamentButtonAction(stage));
		}

		// Actualizar el nombre del coach en la interfaz
		Label nombreCoach = (Label) menuScene.lookup("#nombre_coach");
		if (nombreCoach != null) {
			nombreCoach.setText(username); // Muestra el nombre del usuario en el label
		}

		stage.setTitle("Menú");
		stage.show();
	}

	// Métodos eventos click
	private void setMenuClickListener(Scene scene) {
		setImageClickListener(scene, "#img_menu", this::handleImgMenuClick);
		setImageClickListener(scene, "#img_calendar", this::handleImgCalendarClick);
	}

	private void setImageClickListener(Scene scene, String fxId, EventHandler<MouseEvent> handler) {
		ImageView imageView = (ImageView) scene.lookup(fxId);
		if (imageView != null) {
			imageView.setOnMouseClicked(handler);
		}
	}

	private void handleTournamentButtonAction(Stage stage) {
		try {
			showTournamentScene(stage);  // Llama a la escena del torneo
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para manejar el clic en "img_calendar"
	private void handleImgCalendarClick(MouseEvent event) {
		try {
			showCalendarScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para manejar el clic en "img_menu"
	private void handleImgMenuClick(MouseEvent event) {
		try {
			showMenu((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para asignar evento botón de log out.
	private void setOutClickListener(Scene scene) {
		ImageView imgOut = (ImageView) scene.lookup("#img_out");
		if (imgOut != null) {
			imgOut.setOnMouseClicked(this::handleImgOutClick);
		}
	}

	private void handleImgOutClick(MouseEvent event) {
		try {
			username = null; // Reiniciar la variable username
			context.close(); // Asegúrate de cerrar el contexto de Spring al hacer log-out
			showLoginScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());  // Volver a la pantalla de login
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Mostrar escena de la interfaz Tournament.
	public void showTournamentScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tournament.fxml"));
		var tournamentScene = new Scene(fxmlLoader.load());
		stage.setScene(tournamentScene);

		// Asignar los manejadores de clics para la navegación dentro de Tournament
		setNavigationClickListeners(tournamentScene);
		setMenuClickListener(tournamentScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(tournamentScene);   // Asignar el evento de logout

		stage.setTitle("Tournament");
		stage.show();
	}

	public void showResultsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Results.fxml"));
		var resultsScene = new Scene(fxmlLoader.load());
		stage.setScene(resultsScene);

		setNavigationClickListeners(resultsScene);
		setMenuClickListener(resultsScene);  // Añadir funcionalidad de menú
		setOutClickListener(resultsScene);// Añadir funcionalidad de logout
		stage.setTitle("Resultados");
		stage.show();
	}

	public void showTeamsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Teams.fxml"));
		var teamsScene = new Scene(fxmlLoader.load());
		stage.setScene(teamsScene);

		setNavigationClickListeners(teamsScene);
		setMenuClickListener(teamsScene);  // Añadir funcionalidad de menú
		setOutClickListener(teamsScene);   // Añadir funcionalidad de logout
		stage.setTitle("Equipos");
		stage.show();
	}


	public void setNavigationClickListeners(Scene scene) {
		// Asocia los eventos de clic a los textos correspondientes
		Text clasificationText = (Text) scene.lookup("#clasificationText");
		if (clasificationText != null) {
			clasificationText.setOnMouseClicked(this::handleClasificationClick);
		}

		Text resultsText = (Text) scene.lookup("#resultsText");
		if (resultsText != null) {
			resultsText.setOnMouseClicked(this::handleResultsClick);
		}

		Text teamsText = (Text) scene.lookup("#teamsText");
		if (teamsText != null) {
			teamsText.setOnMouseClicked(this::handleTeamsClick);
		}
	}

	public void handleClasificationClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Clasificación!");
		try {
			showTournamentScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleResultsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Resultados!");
		try {
			showResultsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleTeamsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Equipos!");
		try {
			showTeamsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// Métodos para manejar los clics en los botones de las diferentes secciones
	private void handleCalendarButtonAction(Stage stage) {
		try {
			showCalendarScene(stage);  // Llama a la escena del calendario
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleTrainingButtonAction(Stage stage) {
		try {
			showTrainingScene(stage);  // Llama a la escena de entrenamiento
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleMatchButtonAction(Stage stage) {
		try {
			showMatchScene(stage);  // Llama a la escena de partidos
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleAnalystButtonAction(Stage stage) {
		try {
			showAnalystScene(stage);  // Llama a la escena de analistas
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para mostrar la escena del calendario
	public void showCalendarScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
		var calendarScene = new Scene(fxmlLoader.load());
		stage.setScene(calendarScene);

		setMenuClickListener(calendarScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(calendarScene);

		stage.setTitle("Calendario");
		stage.show();
	}

	// Método para mostrar la escena de Entrenamientos
	public void showTrainingScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Training.fxml"));
		var trainingScene = new Scene(fxmlLoader.load());
		stage.setScene(trainingScene);

		setMenuClickListener(trainingScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(trainingScene);

		stage.setTitle("Entrenamientos");
		stage.show();
	}

	// Método para mostrar la escena de Partidos
	public void showMatchScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Match.fxml"));
		var matchScene = new Scene(fxmlLoader.load());
		stage.setScene(matchScene);

		setMenuClickListener(matchScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(matchScene);

		stage.setTitle("Partidos");
		stage.show();
	}

	// Método para mostrar la escena de Analistas
	public void showAnalystScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Analyst.fxml"));
		var analystScene = new Scene(fxmlLoader.load());
		stage.setScene(analystScene);

		setMenuClickListener(analystScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(analystScene);

		stage.setTitle("Analistas");
		stage.show();
	}

	@Override
	public void stop() {
		if (context != null) {
			context.close();
		}
	}
}

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
		// Primero, iniciar Spring Boot y luego JavaFX.
		context = SpringApplication.run(TfgApplication.class, args);
		launch(args); // Lanza la aplicación de JavaFX.
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Cargar la pantalla de Login al iniciar la aplicación.
		showLoginScene(stage);

		// Maximizar la ventana al iniciar.
		stage.setMaximized(true);
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

		// Asignar el controlador de acción para el botón de login.
		Button loginButton = (Button) loginScene.lookup("#login_btn");
		loginButton.setOnAction(e -> handleLoginButtonAction(userField, passField, stage));

		// Asignar la nueva escena al stage.
		stage.setScene(loginScene);
		stage.setTitle("Inicio de sesión");

		// Mostrar la nueva escena.
		stage.show();
	}

	// Definir el stage de la interfaz menú.
	public void showMenu(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
		var menuScene = new Scene(fxmlLoader.load());
		stage.setScene(menuScene);

		// Asignar eventos de la interfaz.
		setMenuClickListener(menuScene); // icono menú.
		setOutClickListener(menuScene); // icono log out.

		// Botón calendario.
		Button calendarButton = (Button) menuScene.lookup("#calendar_btn");
		if (calendarButton != null) {
			calendarButton.setOnAction(e -> handleCalendarButtonAction(stage));
		}
		// Botón training.
		Button trainingButton = (Button) menuScene.lookup("#training_btn");
		if (trainingButton != null) {
			trainingButton.setOnAction(e -> handleTrainingButtonAction(stage));
		}
		// Botón match.
		Button matchButton = (Button) menuScene.lookup("#match_btn");
		if (matchButton != null) {
			matchButton.setOnAction(e -> handleMatchButtonAction(stage));
		}
		// Botón analyst.
		Button analystButton = (Button) menuScene.lookup("#analyst_btn");
		if (analystButton != null) {
			analystButton.setOnAction(e -> handleAnalystButtonAction(stage));
		}
		// Botón tournament.
		Button tournamentButton = (Button) menuScene.lookup("#tournament_btn");
		if (tournamentButton != null) {
			tournamentButton.setOnAction(e -> handleTournamentButtonAction(stage));
		}

		// Asociar nombre usuario introducido en el login, al label de la interfaz.
		Label nombreCoach = (Label) menuScene.lookup("#nombre_coach");
		if (nombreCoach != null) {
			nombreCoach.setText(username);
		}

		stage.setTitle("Menú");
		stage.show();
	}

	// Definir el stage de la interfaz calendar.
	public void showCalendarScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
		var calendarScene = new Scene(fxmlLoader.load());
		stage.setScene(calendarScene);

		setMenuClickListener(calendarScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(calendarScene);

		stage.setTitle("Calendario");
		stage.show();
	}

	// Definir el stage de la interfaz training.
	public void showTrainingScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Training.fxml"));
		var trainingScene = new Scene(fxmlLoader.load());
		stage.setScene(trainingScene);

		setMenuClickListener(trainingScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(trainingScene);

		stage.setTitle("Entrenamientos");
		stage.show();
	}

	// Definir el stage de la interfaz match.
	public void showMatchScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Match.fxml"));
		var matchScene = new Scene(fxmlLoader.load());
		stage.setScene(matchScene);

		setMenuClickListener(matchScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(matchScene);

		stage.setTitle("Partidos");
		stage.show();
	}

	// Definir el stage de la interfaz analyst.
	public void showAnalystScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Analyst.fxml"));
		var analystScene = new Scene(fxmlLoader.load());
		stage.setScene(analystScene);

		// Asignar eventos de la interfaz
		setNavigationClickListeners(analystScene);
		setMenuClickListener(analystScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(analystScene);

		stage.setTitle("Analistas");
		stage.show();
	}

	// Definir el stage de la interfaz assists.
	public void showAssistsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Assists.fxml"));
		var assistsScene = new Scene(fxmlLoader.load());
		stage.setScene(assistsScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(assistsScene);
		setMenuClickListener(assistsScene);
		setOutClickListener(assistsScene);
		stage.setTitle("Asistencias");
		stage.show();
	}

	// Definir el stage de la interfaz cards.
	public void showCardsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Cards.fxml"));
		var cardsScene = new Scene(fxmlLoader.load());
		stage.setScene(cardsScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(cardsScene);
		setMenuClickListener(cardsScene);
		setOutClickListener(cardsScene);
		stage.setTitle("Tarjetas");
		stage.show();
	}

	// Definir el stage de la interfaz saves.
	public void showSavesScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Saves.fxml"));
		var savesScene = new Scene(fxmlLoader.load());
		stage.setScene(savesScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(savesScene);
		setMenuClickListener(savesScene);
		setOutClickListener(savesScene);
		stage.setTitle("Paradas");
		stage.show();
	}

	// Definir el stage de la interfaz tournament.
	public void showTournamentScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tournament.fxml"));
		var tournamentScene = new Scene(fxmlLoader.load());
		stage.setScene(tournamentScene);

		// Asignar eventos de la interfaz
		setNavigationClickListeners(tournamentScene);
		setMenuClickListener(tournamentScene);
		setOutClickListener(tournamentScene);

		stage.setTitle("Tournament");
		stage.show();
	}

	// Definir el stage de la interfaz results.
	public void showResultsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Results.fxml"));
		var resultsScene = new Scene(fxmlLoader.load());
		stage.setScene(resultsScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(resultsScene);
		setMenuClickListener(resultsScene);
		setOutClickListener(resultsScene);
		stage.setTitle("Resultados");
		stage.show();
	}

	// Definir el stage de la interfaz teams.
	public void showTeamsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Teams.fxml"));
		var teamsScene = new Scene(fxmlLoader.load());
		stage.setScene(teamsScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(teamsScene);
		setMenuClickListener(teamsScene);
		setOutClickListener(teamsScene);
		stage.setTitle("Equipos");
		stage.show();
	}


	// Funcionalidad evento btn login.
	private void handleLoginButtonAction(TextField userField, PasswordField passField, Stage stage) {
		username = userField.getText();
		String password = passField.getText();
		// Comprobación credenciales.
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

	// Funcionalidad evento img menú.
	private void handleImgMenuClick(MouseEvent event) {
		try {
			showMenu((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento img calendar.
	private void handleImgCalendarClick(MouseEvent event) {
		try {
			showCalendarScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento img log out.
	private void handleImgOutClick(MouseEvent event) {
		try {
			username = null; // Reiniciar la variable username
			context.close(); // Asegúrate de cerrar el contexto de Spring al hacer log-out
			showLoginScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());  // Volver a la pantalla de login
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento btn training.
	private void handleTrainingButtonAction(Stage stage) {
		try {
			showTrainingScene(stage);  // Llama a la escena de entrenamiento
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento btn match.
	private void handleMatchButtonAction(Stage stage) {
		try {
			showMatchScene(stage);  // Llama a la escena de partidos
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento btn analyst.
	private void handleAnalystButtonAction(Stage stage) {
		try {
			showAnalystScene(stage);  // Llama a la escena de analistas
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento text clasification.
	public void handleScoresClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Goleadores!");
		try {
			showAnalystScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento btn assists.
	public void handleAssiststClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Asistencias!");
		try {
			showAssistsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento btn cards.
	public void handleCardsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Tarjetas!");
		try {
			showCardsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento btn saves.
	public void handleSavesClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Paradas!");
		try {
			showSavesScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento btn tournament.
	private void handleTournamentButtonAction(Stage stage) {
		try {
			showTournamentScene(stage);  // Llama a la escena del torneo
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento text clasification.
	public void handleClasificationClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Clasificación!");
		try {
			showTournamentScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento text results.
	public void handleResultsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Resultados!");
		try {
			showResultsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcionalidad evento text teams.
	public void handleTeamsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Equipos!");
		try {
			showTeamsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para establecer la navegación a través de los text.
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

		Text scoresText = (Text) scene.lookup("#scores");
		if (scoresText != null) {
			scoresText.setOnMouseClicked(this::handleScoresClick);  // Asignamos el evento para navegar a Tournament
		}

		Text assistsText = (Text) scene.lookup("#assists");
		if (assistsText != null) {
			assistsText.setOnMouseClicked(this::handleAssiststClick);  // Asignamos el evento para navegar a Assists
		}

		Text cardsText = (Text) scene.lookup("#cards");
		if (cardsText != null) {
			cardsText.setOnMouseClicked(this::handleCardsClick);  // Asignamos el evento para navegar a Cards
		}

		Text savesText = (Text) scene.lookup("#saves");
		if (savesText != null) {
			savesText.setOnMouseClicked(this::handleSavesClick);  // Asignamos el evento para navegar a Saves
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
	private void setOutClickListener(Scene scene) {
		ImageView imgOut = (ImageView) scene.lookup("#img_out");
		if (imgOut != null) {
			imgOut.setOnMouseClicked(this::handleImgOutClick);
		}
	}



	@Override
	public void stop() {
		if (context != null) {
			context.close();
		}
	}
}

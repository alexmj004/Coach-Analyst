package com.example.tfg;

import javafx.application.Application;
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

	// Cargar la pantalla de Login al iniciar la aplicación
	@Override
	public void start(Stage stage) throws Exception {
		showLoginScene(stage);

		// Maximizar la ventana al iniciar
		stage.setMaximized(true); // Esto maximiza la ventana cuando se abre
	}

	// Definir el stage de la interfaz login.
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

	// Método mostrar interfaz menú..
	public void showMenu(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
		var menuScene = new Scene(fxmlLoader.load());

		stage.setScene(menuScene);

		// Asignar eventos del menú.
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

	// Método para asignar evento de clic de la imagen del menú y calendario.
	private void setMenuClickListener(Scene scene) {
		// Obtener la imagen con fx:id="img_menu" para el evento de clic
		ImageView imgMenu = (ImageView) scene.lookup("#img_menu");
		if (imgMenu != null) {
			imgMenu.setOnMouseClicked(this::handleImgMenuClick);
		}

		// Obtener la imagen con fx:id="img_calendar" para el evento de clic
		ImageView imgCalendar = (ImageView) scene.lookup("#img_calendar");
		if (imgCalendar != null) {
			imgCalendar.setOnMouseClicked(this::handleImgCalendarClick);
		}
	}

	// Método para manejar el clic en "img_calendar"
	private void handleImgCalendarClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en img_calendar!");  // Puedes agregar más lógica aquí si es necesario.
		try {
			showCalendarScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());  // Llama al método para mostrar la escena del calendario
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para manejar el clic en "img_menu"
	private void handleImgMenuClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en img_menu!");
		try {
			showMenu((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para asignar evento botón del log out.
	private void setOutClickListener(Scene scene) {
		ImageView imgOut = (ImageView) scene.lookup("#img_out");
		if (imgOut != null) {
			imgOut.setOnMouseClicked(this::handleImgOutClick);
		}
	}

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

	// Mostrar escena de la interfaz Calendar.
	public void showCalendarScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
		var calendarScene = new Scene(fxmlLoader.load());
		stage.setScene(calendarScene);

		setMenuClickListener(calendarScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(calendarScene);

		stage.setTitle("Calendar");
		stage.show();
	}

	private void handleCalendarButtonAction(Stage stage) {
		try {
			showCalendarScene(stage);  // Llama a la escena de Calendar
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Mostrar escena de la interfaz Training.
	public void showTrainingScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Training.fxml"));
		var trainingScene = new Scene(fxmlLoader.load());
		stage.setScene(trainingScene);

		setMenuClickListener(trainingScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
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

	// Mostrar escena de la interfaz Match.
	public void showMatchScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Match.fxml"));
		var matchScene = new Scene(fxmlLoader.load());
		stage.setScene(matchScene);

		setMenuClickListener(matchScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(matchScene);

		stage.setTitle("Match");
		stage.show();
	}

	private void handleMatchButtonAction(Stage stage) {
		try {
			showMatchScene(stage);  // Llama a la escena de Match
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Mostrar escena de la interfaz Analyst.
	public void showAnalystScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Analyst.fxml"));
		var analystScene = new Scene(fxmlLoader.load());
		stage.setScene(analystScene);

		setMenuClickListener(analystScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(analystScene);

		stage.setTitle("Analyst");
		stage.show();
	}

	private void handleAnalystButtonAction(Stage stage) {
		try {
			showAnalystScene(stage);  // Llama a la escena de Analyst
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Mostrar escena de la interfaz Tournament.
	public void showTournamentScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tournament.fxml"));
		var tournamentScene = new Scene(fxmlLoader.load());
		stage.setScene(tournamentScene);

		setMenuClickListener(tournamentScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(tournamentScene);

		stage.setTitle("Tournament");
		stage.show();
	}

	private void handleTournamentButtonAction(Stage stage) {
		try {
			showTournamentScene(stage);  // Llama a la escena de Tournament
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

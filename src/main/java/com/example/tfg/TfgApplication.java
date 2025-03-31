package com.example.tfg;

import com.example.tfg.model.Player;
import com.example.tfg.model.User;
import com.example.tfg.service.PlayerService;
import com.example.tfg.service.UserService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TfgApplication extends Application {

	private static ConfigurableApplicationContext context;
	private String inputUserName;
	private String inputPassword;

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
	public void showLoginScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
		var loginScene = new Scene(fxmlLoader.load());

		TextField userField = (TextField) loginScene.lookup("#user");
		PasswordField passField = (PasswordField) loginScene.lookup("#pass");

		Button loginButton = (Button) loginScene.lookup("#login_btn");
		loginButton.setOnAction(e -> handleLoginButtonAction(userField, passField, stage));

		Button newUserButton = (Button) loginScene.lookup("#newUser_btn");
		newUserButton.setOnAction(e -> handleNewUserButtonAction(stage));

		stage.setScene(loginScene);
		stage.setTitle("Inicio de sesión");
		stage.show();
	}

	// Definir el stage de la interfaz registry.
	public void showRegistryScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Registry.fxml"));
		var registryScene = new Scene(fxmlLoader.load());

		TextField nameField = (TextField) registryScene.lookup("#name_field");
		TextField surnameField = (TextField) registryScene.lookup("#surname_field");
		TextField emailField = (TextField) registryScene.lookup("#email_field");
		TextField usernameField = (TextField) registryScene.lookup("#username_field");
		PasswordField passwordField = (PasswordField) registryScene.lookup("#password_field");

		Button registryButton = (Button) registryScene.lookup("#registry_btn");
		registryButton.setOnAction(e -> handleRegistryButtonAction(nameField, surnameField, emailField, usernameField, passwordField, stage));

		stage.setScene(registryScene);
		stage.setTitle("Registro de Usuario");
		stage.show();
	}

	// Definir el stage de la interfaz menú.
	public void showMenu(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
		var menuScene = new Scene(fxmlLoader.load());
		stage.setScene(menuScene);

		setMenuClickListener(menuScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(menuScene);


		Button trainingButton = (Button) menuScene.lookup("#training_btn");
		trainingButton.setOnAction(e -> handleTrainingButtonAction(stage));

		Button matchButton = (Button) menuScene.lookup("#match_btn");
		matchButton.setOnAction(e -> handleMatchButtonAction(stage));

		Button analystButton = (Button) menuScene.lookup("#analyst_btn");
		analystButton.setOnAction(e -> handleAnalystButtonAction(stage));

		Button tournamentButton = (Button) menuScene.lookup("#tournament_btn");
		tournamentButton.setOnAction(e -> handleTournamentButtonAction(stage));

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

		// Cargar jugadores en los ComboBox
		loadPlayersToComboBoxes(matchScene);

		setMenuClickListener(matchScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(matchScene);

		stage.setTitle("Partidos");
		stage.show();
	}
	private void loadPlayersToComboBoxes(Scene scene) {
		PlayerService playerService = context.getBean(PlayerService.class);

		// Obtener jugadores por posición
		List<Player> goalkeepers = playerService.findByPosition("GK");
		List<Player> leftBacks = playerService.findByPosition("LI");
		List<Player> rightBacks = playerService.findByPosition("LD");
		List<Player> centerBacks = playerService.findByPosition("DFC");
		List<Player> midfielders = playerService.findByPosition("MC");
		List<Player> leftWingers = playerService.findByPosition("EI");
		List<Player> rightWingers = playerService.findByPosition("ED");
		List<Player> centerForwards = playerService.findByPosition("DC");

		// Obtener los ComboBox de la escena
		ComboBox<Player> gkComboBox = (ComboBox<Player>) scene.lookup("#box_gk");
		ComboBox<Player> dfComboBox = (ComboBox<Player>) scene.lookup("#box_df");
		ComboBox<Player> mdComboBox = (ComboBox<Player>) scene.lookup("#box_md");
		ComboBox<Player> fwComboBox = (ComboBox<Player>) scene.lookup("#box_fw");

		// Obtener las etiquetas específicas de posición
		Label gkLabel = (Label) scene.lookup("#gk");
		Label liLabel = (Label) scene.lookup("#li");
		Label ldLabel = (Label) scene.lookup("#ld");
		Label df1Label = (Label) scene.lookup("#df1");
		Label df2Label = (Label) scene.lookup("#df2");
		Label mc1Label = (Label) scene.lookup("#mc1");
		Label mc2Label = (Label) scene.lookup("#mc2");
		Label mc3Label = (Label) scene.lookup("#mc3");
		Label eiLabel = (Label) scene.lookup("#ei");
		Label edLabel = (Label) scene.lookup("#ed");
		Label dcLabel = (Label) scene.lookup("#dc");

		// Configurar cómo se muestra el nombre del jugador en los ComboBox
		Callback<ListView<Player>, ListCell<Player>> cellFactory = param -> new ListCell<Player>() {
			@Override
			protected void updateItem(Player player, boolean empty) {
				super.updateItem(player, empty);
				if (empty || player == null) {
					setText(null);
				} else {
					setText(player.getName() + " " + player.getSurname() + " (" + player.getPosition() + ")");
				}
			}
		};

		gkComboBox.setButtonCell(new ListCell<Player>() {
			@Override
			protected void updateItem(Player player, boolean empty) {
				super.updateItem(player, empty);
				if (empty || player == null) {
					setText(null);
				} else {
					setText(player.getName() + " " + player.getSurname());
				}
			}
		});

		gkComboBox.setCellFactory(cellFactory);
		gkComboBox.setButtonCell(cellFactory.call(null));

		dfComboBox.setCellFactory(cellFactory);
		dfComboBox.setButtonCell(cellFactory.call(null));

		mdComboBox.setCellFactory(cellFactory);
		mdComboBox.setButtonCell(cellFactory.call(null));

		fwComboBox.setCellFactory(cellFactory);
		fwComboBox.setButtonCell(cellFactory.call(null));

		// Añadir los jugadores a los ComboBox correspondientes
		gkComboBox.getItems().addAll(goalkeepers);
		dfComboBox.getItems().addAll(leftBacks);
		dfComboBox.getItems().addAll(rightBacks);
		dfComboBox.getItems().addAll(centerBacks);
		mdComboBox.getItems().addAll(midfielders);
		fwComboBox.getItems().addAll(leftWingers);
		fwComboBox.getItems().addAll(rightWingers);
		fwComboBox.getItems().addAll(centerForwards);

		// Asignar jugadores específicos a las etiquetas de posición
		assignPlayersToPositionLabels(scene, playerService);
	}

	private void assignPlayersToPositionLabels(Scene scene, PlayerService playerService) {
		// Obtener las etiquetas de posición
		Label gkLabel = (Label) scene.lookup("#gk");
		Label liLabel = (Label) scene.lookup("#li");
		Label ldLabel = (Label) scene.lookup("#ld");
		Label df1Label = (Label) scene.lookup("#df1");
		Label df2Label = (Label) scene.lookup("#df2");
		Label mc1Label = (Label) scene.lookup("#mc1");
		Label mc2Label = (Label) scene.lookup("#mc2");
		Label mc3Label = (Label) scene.lookup("#mc3");
		Label dcLabel = (Label) scene.lookup("#dc");
		Label edLabel = (Label) scene.lookup("#ed");
		Label eiLabel = (Label) scene.lookup("#ei");

		// Inicializar contadores para posiciones múltiples
		final int[] mcCount = {0};

		// Asignar jugadores por defecto o cargados desde BD
		List<Player> defaultPlayers = playerService.findDefaultPlayers();



		defaultPlayers.forEach(player -> {
			switch(player.getPosition()) {
				case "GK":
					gkLabel.setText(player.getName() + " " + player.getSurname());
					break;
				case "LI":
					liLabel.setText(player.getName() + " " + player.getSurname());
					break;
				case "LD":
					ldLabel.setText(player.getName() + " " + player.getSurname());
					break;
				case "DFC":
					// Asignar a df1 o df2 según necesidad
					if (df1Label.getText().equals("DFC")) {
						df1Label.setText(player.getName() + " " + player.getSurname());
					} else {
						df2Label.setText(player.getName() + " " + player.getSurname());
					}
					break;
				case "MC":
					switch (mcCount[0]) {
						case 0:
							mc1Label.setText(player.getName() + " " + player.getSurname());
							break;
						case 1:
							mc2Label.setText(player.getName() + " " + player.getSurname());
							break;
						case 2:
							mc3Label.setText(player.getName() + " " + player.getSurname());
							break;
					}
					mcCount[0]++;
					break;
				case "EI":
					eiLabel.setText(player.getName() + " " + player.getSurname());
					break;
				case "ED":
					edLabel.setText(player.getName() + " " + player.getSurname());
					break;
				case "DC":
					dcLabel.setText(player.getName() + " " + player.getSurname());
					break;
			}
		});
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

	// Funcionalidad para manejar el botón de login
	private void handleLoginButtonAction(TextField userField, PasswordField passField, Stage stage) {

		inputUserName = userField.getText();  // Se captura el valor del TextField para el nombre de usuario
		inputPassword = passField.getText();

		// Comprobar si los campos de usuario y contraseña están vacíos
		if (inputUserName.isEmpty() || inputPassword.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error de inicio de sesión");
			alert.setHeaderText("Campos Vacíos");
			alert.setContentText("Por favor, ingrese un usuario y contraseña.");
			alert.showAndWait();
		} else {
			// Obtener el servicio de usuarios desde el contexto de Spring
			UserService userService = context.getBean(UserService.class);

			// Buscar al usuario por su nombre de usuario
			User user = userService.findByUserName(inputUserName); // Usamos inputUserName para la búsqueda

			// Verificar si el usuario existe y la contraseña es correcta
			if (user != null && user.getPassword().equals(inputPassword)) {
				try {
					System.out.println("Usuario encontrado: " + user.getUserName());
					// Si las credenciales son correctas, mostrar el menú principal
					showMenu(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// Si no se encuentra el usuario o las credenciales son incorrectas
				System.out.println("No se encontró el usuario con el nombre: " + inputUserName);
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error de inicio de sesión");
				alert.setHeaderText("Credenciales inválidas");
				alert.setContentText("Por favor, ingrese un usuario y contraseña válidos.");
				alert.showAndWait();
			}
		}
	}

	// Funcionalidad para manejar el botón de nuevo usuario
	private void handleNewUserButtonAction(Stage stage) {
		try {
			showRegistryScene(stage);
		} catch (Exception e) {
			e.printStackTrace();
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
			inputUserName = null; // Reiniciar la variable username
			inputPassword = null; // Reiniciar la variable username
			
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
	private void handleRegistryButtonAction(TextField nameField, TextField surnameField, TextField emailField, TextField usernameField, PasswordField passwordField, Stage stage) {
		String name = nameField.getText();
		String surname = surnameField.getText();
		String email = emailField.getText();
		String username = usernameField.getText();
		String password = passwordField.getText();

		if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error de Registro");
			alert.setHeaderText("Campos Vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
		} else {
			// Crear el objeto User
			User user = new User(name, surname, username, email, password);

			// Intentar agregar el usuario a la base de datos
			try {
				// Inyectar el servicio UserService y llamar al método para guardar al usuario
				UserService userService = context.getBean(UserService.class);
				userService.addUser(user); // Guardar el usuario en la base de datos

				// Mostrar mensaje de éxito
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Registro Exitoso");
				alert.setHeaderText("El usuario ha sido registrado correctamente.");
				alert.setContentText("Puede iniciar sesión ahora.");
				alert.showAndWait();

				// Volver a la pantalla de login
				showLoginScene(stage);
			} catch (Exception e) {
				// Manejar excepciones si ocurre algún error al guardar el usuario
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error de Registro");
				alert.setHeaderText("No se pudo registrar al usuario");
				alert.setContentText("Hubo un error al guardar los datos. Intente de nuevo.");
				alert.showAndWait();
			}
		}
	}

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

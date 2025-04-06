package com.example.tfg;

import com.example.tfg.controller.CalendarController;
import com.example.tfg.controller.ResultsController;
import com.example.tfg.controller.TrainingController;
import com.example.tfg.model.Player;
import com.example.tfg.model.Team;
import com.example.tfg.model.User;
import com.example.tfg.service.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import java.util.List;


@SpringBootApplication
public class TfgApplication extends Application {

	// Propiedades.
	private static final Logger logger = LoggerFactory.getLogger(TfgApplication.class);
	private static ConfigurableApplicationContext context;
	private String inputUserName;
	private Label nombre_coach;
	private String inputPassword;
	private PlayerServiceImpl playerServiceImpl;
	private CalendarService calendarService;
	private TrainingService trainingService;
	private User loggedInUser;
	private ResultsService resultsService;
	private TeamServImpl teamServ;


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


	@Override
	public void init() throws Exception {
		// Obtener los beans de Spring antes de que inicie JavaFX.
		playerServiceImpl = context.getBean(PlayerServiceImpl.class);
		calendarService = context.getBean(CalendarServImp.class);
		trainingService = context.getBean(TrainingService.class);
		resultsService = context.getBean(ResultsService.class);
		teamServ = context.getBean(TeamServImpl.class);
	}


	// *** INTERFAZ LOGIN ***
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
	// Funcionalidad para manejar el botón de login
	private void handleLoginButtonAction(TextField userField, PasswordField passField, Stage stage) {
		inputUserName = userField.getText();
		inputPassword = passField.getText();

		if (inputUserName.isEmpty() || inputPassword.isEmpty()) {
			showAlert("Error de inicio de sesión", "Campos Vacíos, por favor, ingrese un usuario y contraseña.");
		} else {
			UserService userService = context.getBean(UserService.class);
			User user = userService.findByUserName(inputUserName);

			if (user != null && user.getPassword().equals(inputPassword)) {
				this.loggedInUser = user; // Guardar el usuario logueado
				try {
					showMenu(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				showAlert("Error de inicio de sesión", "Credenciales inválidas por favor, ingrese un usuario y contraseña válidos.");
			}
		}
	}


	// *** INTERFAZ REGISTRY ***
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
	// Funcionalidad para manejar el botón de nuevo usuario.
	private void handleNewUserButtonAction(Stage stage) {
		try {
			showRegistryScene(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Funcionalidad registro de cada campo.
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


	// *** INTERFAZ MENÚ ***
	// Definir el stage de la interfaz menú.
	public void showMenu(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
		var menuScene = new Scene(fxmlLoader.load());
		stage.setScene(menuScene);

		setMenuClickListener(menuScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(menuScene);

		updateCoachNameLabel(menuScene);

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


	// *** INTERFAZ CALENDAR ***
	// Definir el stage de la interfaz calendar.
	public void showCalendarScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
		var calendarScene = new Scene(fxmlLoader.load());
		stage.setScene(calendarScene);

		updateCoachNameLabel(calendarScene);

		CalendarController calendarController = new CalendarController(calendarService);
		calendarController.setupCalendarComponents(calendarScene);

		setMenuClickListener(calendarScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(calendarScene);

		stage.setTitle("Calendario");
		stage.show();
	}


	// *** INTERFAZ TRAINING ***
	// Definir el stage de la interfaz training.
	public void showTrainingScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Training.fxml"));
		var trainingScene = new Scene(fxmlLoader.load());
		stage.setScene(trainingScene);
		updateCoachNameLabel(trainingScene);


		TrainingController trainingController = context.getBean(TrainingController.class);
		trainingController.setupTrainingComponents(trainingScene);

		setMenuClickListener(trainingScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(trainingScene);

		stage.setTitle("Entrenamientos");
		stage.show();
	}
	// Funcionalidad evento btn training.
	private void handleTrainingButtonAction(Stage stage) {
		try {
			showTrainingScene(stage);  // Llama a la escena de entrenamiento
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// *** INTERFAZ MATCH ***
	// Definir el stage de la interfaz match.
	public void showMatchScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Match.fxml"));
		var matchScene = new Scene(fxmlLoader.load());
		stage.setScene(matchScene);
		updateCoachNameLabel(matchScene);

		// Configurar los ComboBox con jugadores por posición
		setupMatchComboboxes(matchScene);

		setMenuClickListener(matchScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(matchScene);

		stage.setTitle("Partidos");
		stage.show();
	}
	// Funcionalidad evento btn match.
	private void handleMatchButtonAction(Stage stage) {
		try {
			showMatchScene(stage);  // Llama a la escena de partidos
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getPositionName(String positionCode) {
		switch (positionCode) {
			case "GK":
				return "Goalkeepers";
			case "DF":
				return "Defenders";
			case "PIV":
				return "Pivots";
			case "LW":
				return "Left Winger";
			case "RW":
				return "Right Winger";
			default:
				return positionCode; // Si no coincide con ninguno, devuelve el código original
		}
	}
	private void setupMatchComboboxes(Scene matchScene) {
		if (playerServiceImpl == null) {
			playerServiceImpl = context.getBean(PlayerServiceImpl.class);
		}

		// Obtener el equipo del entrenador logueado
		Team team = loggedInUser != null ? loggedInUser.getTeam() : null;

		// Configurar ComboBox con filtro de equipo
		setupPositionComboBox(matchScene, "#box_gk", "GK", "#label_gk", team);
		setupPositionComboBox(matchScene, "#box_df", "DF", "#label_df", team);
		setupPositionComboBox(matchScene, "#box_pivot", "PIV", "#label_piv", team);

		// Configurar ComboBox de Wingers
		ComboBox<String> wardsComboBox = (ComboBox<String>) matchScene.lookup("#box_wards");
		if (wardsComboBox != null) {
			wardsComboBox.setPromptText("Wingers (LW/RW)");
			loadWardsPlayers(wardsComboBox, team);

			Label labelLw = (Label) matchScene.lookup("#label_lw");
			Label labelRw = (Label) matchScene.lookup("#label_rw");
			if (labelLw != null && labelRw != null) {
				wardsComboBox.setOnAction(e -> updateWardsLabel(wardsComboBox, labelLw, labelRw));
			}
		}
	}
	private void setupPositionComboBox(Scene scene, String comboId, String position, String labelId, Team team) {
		ComboBox<String> comboBox = (ComboBox<String>) scene.lookup(comboId);
		if (comboBox != null) {
			comboBox.setPromptText(getPositionName(position));
			loadPlayersByPosition(comboBox, position, team);

			Label label = (Label) scene.lookup(labelId);
			if (label != null) {
				comboBox.setOnAction(e -> updateLabel(comboBox, label));
			}
		}
	}
	private void loadWardsPlayers(ComboBox<String> comboBox, Team team) {
		try {
			List<Player> lwPlayers = team != null ?
					playerServiceImpl.findByPositionAndTeamName("LW", team) :
					playerServiceImpl.findByPosition("LW");

			List<Player> rwPlayers = team != null ?
					playerServiceImpl.findByPositionAndTeamName("RW", team) :
					playerServiceImpl.findByPosition("RW");

			comboBox.getItems().clear();

			for (Player player : lwPlayers) {
				comboBox.getItems().add(player.getApodo() + " (LW)");
			}

			for (Player player : rwPlayers) {
				comboBox.getItems().add(player.getApodo() + " (RW)");
			}

			if (comboBox.getItems().isEmpty()) {
				comboBox.setPromptText("No wingers available");
			}
		} catch (Exception e) {
			logger.error("Error loading wingers", e);
			comboBox.setPromptText("Error loading wingers");
		}
	}
	private void loadPlayersByPosition(ComboBox<String> comboBox, String position, Team team) {
		try {
			List<Player> players = team != null ?
					playerServiceImpl.findByPositionAndTeamName(position, team) :
					playerServiceImpl.findByPosition(position);

			comboBox.getItems().clear();

			if (players != null && !players.isEmpty()) {
				for (Player player : players) {
					comboBox.getItems().add(player.getApodo());
				}
			} else {
				comboBox.setPromptText("No " + getPositionName(position) + " available");
			}
		} catch (Exception e) {
			logger.error("Error loading players for position: " + position, e);
			comboBox.setPromptText("Error loading " + getPositionName(position));
		}
	}
	private void updateLabel(ComboBox<String> comboBox, Label label) {
		String selectedApodo = comboBox.getSelectionModel().getSelectedItem();
		if (selectedApodo != null && label != null) {
			// Buscar el jugador por su apodo para obtener el dorsal
			Player player = playerServiceImpl.findByApodo(selectedApodo);
			if (player != null) {
				// Mostrar "Apodo (Dorsal)" en el Label
				label.setText(String.format("%s (%d)", player.getApodo(), player.getDorsal()));
			}
		}
	}
	private void updateWardsLabel(ComboBox<String> comboBox, Label labelLw, Label labelRw) {
		String selectedPlayer = comboBox.getSelectionModel().getSelectedItem();
		if (selectedPlayer != null) {
			// Extraer solo el apodo (eliminando "(LW)" o "(RW)")
			String apodo = selectedPlayer.replace(" (LW)", "").replace(" (RW)", "");

			// Buscar el jugador completo
			Player player = playerServiceImpl.findByApodo(apodo);
			if (player != null) {
				String fullDisplay = String.format("%s (%d)", player.getApodo(), player.getDorsal());

				if (selectedPlayer.contains("(LW)")) {
					// Verificar si el jugador ya está en RW
					if (player.getApodo().equals(labelRw.getText().split(" ")[0])) {
						showAlert("Jugador ya seleccionado", "Este jugador ya está como Right Winger");
						return;
					}
					labelLw.setText(fullDisplay);
				} else if (selectedPlayer.contains("(RW)")) {
					// Verificar si el jugador ya está en LW
					if (player.getApodo().equals(labelLw.getText().split(" ")[0])) {
						showAlert("Jugador ya seleccionado", "Este jugador ya está como Left Winger");
						return;
					}
					labelRw.setText(fullDisplay);
				}
			}
		}
	}


	// *** INTERFAZ ANALYST ***
	public void showAnalystScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Analyst.fxml"));
		var analystScene = new Scene(fxmlLoader.load());
		stage.setScene(analystScene);
		updateCoachNameLabel(analystScene);

		// Asignar eventos de la interfaz
		setNavigationClickListeners(analystScene);
		setMenuClickListener(analystScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(analystScene);

		stage.setTitle("Analistas");
		stage.show();
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


	// *** INTERFAZ ASSISTS ***
	// Definir el stage de la interfaz assists.
	public void showAssistsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Assists.fxml"));
		var assistsScene = new Scene(fxmlLoader.load());
		stage.setScene(assistsScene);
		updateCoachNameLabel(assistsScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(assistsScene);
		setMenuClickListener(assistsScene);
		setOutClickListener(assistsScene);
		stage.setTitle("Asistencias");
		stage.show();
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


	// *** INTERFAZ CARDS ***
	// Definir el stage de la interfaz cards.
	public void showCardsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Cards.fxml"));
		var cardsScene = new Scene(fxmlLoader.load());
		stage.setScene(cardsScene);
		updateCoachNameLabel(cardsScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(cardsScene);
		setMenuClickListener(cardsScene);
		setOutClickListener(cardsScene);
		stage.setTitle("Tarjetas");
		stage.show();
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


	// *** INTERFAZ SAVES ***
	// Definir el stage de la interfaz saves.
	public void showSavesScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Saves.fxml"));
		var savesScene = new Scene(fxmlLoader.load());
		stage.setScene(savesScene);
		updateCoachNameLabel(savesScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(savesScene);
		setMenuClickListener(savesScene);
		setOutClickListener(savesScene);
		stage.setTitle("Paradas");
		stage.show();
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


	// *** INTERFAZ TOURNAMENT ***
	// Definir el stage de la interfaz tournament.
	public void showTournamentScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tournament.fxml"));
		var tournamentScene = new Scene(fxmlLoader.load());
		stage.setScene(tournamentScene);
		updateCoachNameLabel(tournamentScene);

		// Asignar eventos de la interfaz
		setNavigationClickListeners(tournamentScene);
		setMenuClickListener(tournamentScene);
		setOutClickListener(tournamentScene);

		stage.setTitle("Tournament");
		stage.show();
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


	// *** INTERFAZ RESULTS ***
	// Definir el stage de la interfaz results.
	public void showResultsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Results.fxml"));
		var resultsScene = new Scene(fxmlLoader.load());
		stage.setScene(resultsScene);
		updateCoachNameLabel(resultsScene);

		// Configurar el controlador de resultados
		ResultsController resultsController = context.getBean(ResultsController.class);
		resultsController.setupResultsComponents(resultsScene);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(resultsScene);
		setMenuClickListener(resultsScene);
		setOutClickListener(resultsScene);
		stage.setTitle("Resultados");
		stage.show();
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


	// *** INTERFAZ TEAMS ***
	// Definir el stage de la interfaz teams.
	public void showTeamsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Teams.fxml"));
		var teamsScene = new Scene(fxmlLoader.load());
		stage.setScene(teamsScene);
		updateCoachNameLabel(teamsScene);

		// Configurar la tabla de equipos (nueva parte)
		TableView<Team> tablaEquipos = (TableView<Team>) teamsScene.lookup("#tablaEquipos");
		if (tablaEquipos != null) {
			configurarTablaEquipos(tablaEquipos);
		}

		// Asignar eventos de la interfaz (existente)
		setNavigationClickListeners(teamsScene);
		setMenuClickListener(teamsScene);
		setOutClickListener(teamsScene);
		stage.setTitle("Equipos");
		stage.show();
	}	// Funcionalidad evento text teams.
	public void handleTeamsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Equipos!");
		try {
			showTeamsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void configurarTablaEquipos(TableView<Team> tablaEquipos) {
		// Configurar la columna de la tabla
		TableColumn<Team, String> colEquipo = (TableColumn<Team, String>) tablaEquipos.getColumns().get(0);
		colEquipo.setCellValueFactory(new PropertyValueFactory<>("name"));

		// Cargar los equipos desde el servicio
		cargarEquiposEnTabla(tablaEquipos);
	}

	private void cargarEquiposEnTabla(TableView<Team> tablaEquipos) {
		try {
			List<Team> equipos = teamServ.findAll();
			ObservableList<Team> equiposObservable = FXCollections.observableArrayList(equipos);
			tablaEquipos.setItems(equiposObservable);
		} catch (Exception e) {
			logger.error("Error al cargar equipos", e);
			showAlert("Error", "No se pudieron cargar los equipos");
		}
	}

	// Método para actualizar el nombre_usuario del login en cada interfaz.
	private void updateCoachNameLabel(Scene scene) {
		if (scene == null || loggedInUser == null) return;

		Label nombreCoachLabel = (Label) scene.lookup("#nombre_coach");
		if (nombreCoachLabel != null) {
			nombreCoachLabel.setText(loggedInUser.getUserName()); // O usa getUsername() según tu modelo User
		}
	}


	// Método para establecer la navegación a través de los Text.
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


	// Métodos para manejar los eventos acceso a menu.fxml, log_out.fxml, calendar.fxml.
	private void setMenuClickListener(Scene scene) {
		setImageClickListener(scene, "#img_menu", this::handleImgMenuClick);
		setImageClickListener(scene, "#img_calendar", this::handleImgCalendarClick);
	}
	private void handleImgMenuClick(MouseEvent event) {
		try {
			showMenu((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void handleImgCalendarClick(MouseEvent event) {
		try {
			showCalendarScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	private void handleImgOutClick(MouseEvent event) {
		try {
			inputUserName = null; // Reiniciar la variable username
			inputPassword = null; // Reiniciar la variable username

			showLoginScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());  // Volver a la pantalla de login
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// Método para mostrar ventanas de alertas.
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}


	@Override
	public void stop() {
		if (context != null) {
			context.close();
		}
	}
}
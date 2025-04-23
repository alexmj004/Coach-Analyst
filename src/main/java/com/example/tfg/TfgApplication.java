package com.example.tfg;

import com.example.tfg.controller.*;
import com.example.tfg.model.Player;
import com.example.tfg.model.Team;
import com.example.tfg.model.User;
import com.example.tfg.service.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;


@SpringBootApplication
public class TfgApplication extends Application {

	@Autowired
	private VideosController videosController;
	// Propiedades.
	private static final Logger logger = LoggerFactory.getLogger(TfgApplication.class);
	private static ConfigurableApplicationContext context;
	private String inputUserName;
	@FXML
	private String inputPassword;
	private PlayerServiceImpl playerServiceImpl;
	private CalendarService calendarService;
	private TrainingService trainingService;
	private User loggedInUser;
	private ResultsService resultsService;
	private TeamServImpl teamServ;
	private TournamentServiceImp tournamentServ;
	private ResultsController resultsController;
    @Autowired
    private TournamentService tournamentService;


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
		playerServiceImpl = context.getBean(PlayerServiceImpl.class);
		calendarService = context.getBean(CalendarServImp.class);
		trainingService = context.getBean(TrainingService.class);
		resultsService = context.getBean(ResultsService.class);
		teamServ = context.getBean(TeamServImpl.class);
		tournamentServ = context.getBean(TournamentServiceImp.class);
		tournamentService = context.getBean(TournamentService.class);
		resultsController = context.getBean(ResultsController.class);
		videosController = context.getBean(VideosController.class);


	}


	// *** INTERFAZ LOGIN ***
	// Definir el stage de la interfaz login.
	public void showLoginScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login2.fxml"));
		var loginScene = new Scene(fxmlLoader.load());

		TextField userField = (TextField) loginScene.lookup("#user");
		PasswordField passField = (PasswordField) loginScene.lookup("#pass");

		Button loginButton = (Button) loginScene.lookup("#login_btn");
		loginButton.setOnAction(e -> handleLoginButtonAction(userField, passField, stage));

		//TODO cambiar el boton de nuevo usuario por un TExt
		Button newUserButton = (Button) loginScene.lookup("#newUser");
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


	// *** INTERFAZ VIDEOS ***
	public void showVideosScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Videos.fxml"));
		fxmlLoader.setControllerFactory(context::getBean); // Usar Spring para el controlador
		var videosScene = new Scene(fxmlLoader.load());
		stage.setScene(videosScene);
		updateCoachNameLabel(videosScene);

		setMenuClickListener(videosScene);
		setOutClickListener(videosScene);

		stage.setTitle("Video Library");
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
		try {
			// Cargar la escena FXML
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Analyst.fxml"));
			Scene analystScene = new Scene(fxmlLoader.load());
			stage.setScene(analystScene);

			// Configurar elementos de la interfaz
			updateCoachNameLabel(analystScene);
			setNavigationClickListeners(analystScene);
			setMenuClickListener(analystScene);
			setOutClickListener(analystScene);

			// Configurar el gráfico de barras
			configureBarChart(analystScene);

			stage.setTitle("Analyst Dashboard");
			stage.show();
		} catch (IOException e) {
			logger.error("Error al cargar la escena Analyst", e);
			showAlert("Error", "No se pudo cargar la pantalla de análisis");
		}
	}
	private XYChart.Series<String, Number> loadPlayerData() {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Goals");

		try {
			// Obtener jugadores del equipo del coach logueado
			Team team = loggedInUser != null ? loggedInUser.getTeam() : null;
			List<Player> players = team != null ?
					playerServiceImpl.findByTeamName(team) :
					playerServiceImpl.findAll();

			// Ordenar por goles (de mayor a menor) - SIN LÍMITE
			players.sort(Comparator.comparingInt(Player::getGoals).reversed());

			// Añadir datos al gráfico - TODOS los jugadores
			for (Player player : players) {
				String displayName = String.format("%s (%d)",
						player.getApodo().length() > 6 ?
								player.getApodo().substring(0, 5) + "." :
								player.getApodo(),
						player.getDorsal());

				series.getData().add(new XYChart.Data<>(displayName, player.getGoals()));
			}
		} catch (Exception e) {
			logger.error("Error al cargar datos de jugadores", e);

			// Datos de ejemplo en caso de error
			series.getData().add(new XYChart.Data<>("Player 1", 15));
			series.getData().add(new XYChart.Data<>("Player 2", 12));
			series.getData().add(new XYChart.Data<>("Player 3", 8));
			series.getData().add(new XYChart.Data<>("Player 4", 6));
			series.getData().add(new XYChart.Data<>("Player 5", 5));
		}

		return series;
	}
	private void configureBarChart(Scene scene) {
		BarChart<String, Number> barChart = (BarChart<String, Number>) scene.lookup("#grafic_scores");
		if (barChart == null) {
			logger.warn("No se encontró el gráfico con fx:id 'grafic_scores'");
			return;
		}

		// Limpiar datos previos
		barChart.getData().clear();

		// Configuración básica del gráfico
		barChart.setTitle("Top Scorers");
		barChart.setLegendVisible(false);
		barChart.setAnimated(false);
		barChart.setHorizontalGridLinesVisible(true); // Habilitar líneas horizontales
		barChart.setVerticalGridLinesVisible(false);  // Deshabilitar líneas verticales

		// Configuración de espacios entre barras
		barChart.setCategoryGap(1);
		barChart.setBarGap(0);

		// Configuración del eje X
		CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
		xAxis.setLabel("Players");
		xAxis.setTickLabelFill(Paint.valueOf("#f4f2f2"));
		xAxis.setTickLabelFont(Font.font(10));
		xAxis.setTickLabelRotation(270);
		xAxis.setStartMargin(0);
		xAxis.setEndMargin(0);

		// Configuración del eje Y (0-40, incrementos de 5 con líneas intermedias)
		NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
		yAxis.setLabel("Goals");
		yAxis.setTickLabelFill(Paint.valueOf("#f4f2f2"));
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(40);
		yAxis.setTickUnit(5);

		// Configuración para líneas de cuadrícula intermedias
		yAxis.setMinorTickCount(4);       // 4 líneas menores entre cada tick principal (5/5=1)
		yAxis.setMinorTickLength(5);      // Longitud de las líneas menores
		yAxis.setTickMarkVisible(true);   // Asegurar que los ticks sean visibles

		// Estilo CSS para las líneas de cuadrícula
		barChart.setStyle("""
        -fx-horizontal-grid-lines-visible: true;
        -fx-vertical-grid-lines-visible: false;
        -fx-horizontal-minor-grid-lines-visible: true;
        -fx-chart-horizontal-grid-lines: #505050;
        -fx-chart-horizontal-minor-grid-lines: #303030;
        """);

		// Cargar datos de jugadores
		XYChart.Series<String, Number> series = loadPlayerData();
		barChart.getData().add(series);

		// Ajustes finales
		Platform.runLater(() -> {
			// Ajustar ancho del gráfico
			int barWidth = 30;
			int minWidth = 800;
			int calculatedWidth = Math.max(minWidth, series.getData().size() * barWidth);
			barChart.setPrefWidth(calculatedWidth);

			// Aplicar estilos a las barras
			for (XYChart.Data<String, Number> data : series.getData()) {
				Node node = data.getNode();
				if (node != null) {
					node.setStyle(
							"-fx-bar-fill: #00ffd5; "
									+ "-fx-background-radius: 2 2 0 0; "
									+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 1, 0, 0, 1);"
									+ "-fx-padding: 0;"
									+ "-fx-min-width: 20px; "
									+ "-fx-max-width: 20px; "
					);
				}
			}

			// Forzar redibujado
			barChart.requestLayout();
		});
	}
	private void handleAnalystButtonAction(Stage stage) {
		try {
			showAnalystScene(stage);  // Llama a la escena de analistas
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void handleScoresClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Goleadores!");
		try {
			showAnalystScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// *** INTERFAZ ASSISTS ***
	public void showAssistsScene(Stage stage) throws IOException {
		try {
			// Cargar la escena FXML
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Assists.fxml"));
			Scene assistsScene = new Scene(fxmlLoader.load());
			stage.setScene(assistsScene);

			// Configurar elementos de la interfaz
			updateCoachNameLabel(assistsScene);
			setNavigationClickListeners(assistsScene);
			setMenuClickListener(assistsScene);
			setOutClickListener(assistsScene);

			// Configurar el gráfico de barras para asistencias
			configureAssistsBarChart(assistsScene);

			stage.setTitle("Asistencias");
			stage.show();
		} catch (IOException e) {
			logger.error("Error al cargar la escena Assists", e);
			showAlert("Error", "No se pudo cargar la pantalla de asistencias");
		}
	}
	private XYChart.Series<String, Number> loadAssistsData() {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Assists");

		try {
			// Obtener jugadores del equipo del coach logueado
			Team team = loggedInUser != null ? loggedInUser.getTeam() : null;
			List<Player> players = team != null ?
					playerServiceImpl.findByTeamName(team) :
					playerServiceImpl.findAll();

			// Ordenar por asistencias (de mayor a menor)
			players.sort(Comparator.comparingInt(Player::getAssists).reversed());

			// Añadir datos al gráfico
			for (Player player : players) {
				String displayName = String.format("%s (%d)",
						player.getApodo().length() > 6 ?
								player.getApodo().substring(0, 5) + "." :
								player.getApodo(),
						player.getDorsal());

				series.getData().add(new XYChart.Data<>(displayName, player.getAssists()));
			}
		} catch (Exception e) {
			logger.error("Error al cargar datos de asistencias", e);

			// Datos de ejemplo en caso de error
			series.getData().add(new XYChart.Data<>("Player 1", 10));
			series.getData().add(new XYChart.Data<>("Player 2", 8));
			series.getData().add(new XYChart.Data<>("Player 3", 6));
			series.getData().add(new XYChart.Data<>("Player 4", 5));
			series.getData().add(new XYChart.Data<>("Player 5", 4));
		}

		return series;
	}
	private void configureAssistsBarChart(Scene scene) {
		BarChart<String, Number> barChart = (BarChart<String, Number>) scene.lookup("#grafic_assists");
		if (barChart == null) {
			logger.warn("No se encontró el gráfico con fx:id 'grafic_assists'");
			return;
		}

		// Limpiar datos previos
		barChart.getData().clear();

		// Configuración básica del gráfico
		barChart.setTitle("Top Assists");
		barChart.setLegendVisible(false);
		barChart.setAnimated(false);
		barChart.setHorizontalGridLinesVisible(true);
		barChart.setVerticalGridLinesVisible(false);

		// Configuración de espacios entre barras
		barChart.setCategoryGap(1);
		barChart.setBarGap(0);

		// Configuración del eje X
		CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
		xAxis.setLabel("Players");
		xAxis.setTickLabelFill(Paint.valueOf("#f4f2f2"));
		xAxis.setTickLabelFont(Font.font(10));
		xAxis.setTickLabelRotation(270);
		xAxis.setStartMargin(0);
		xAxis.setEndMargin(0);

		// Configuración del eje Y (0-30, incrementos de 5 con líneas intermedias)
		NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
		yAxis.setLabel("Assists");
		yAxis.setTickLabelFill(Paint.valueOf("#f4f2f2"));
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(30);
		yAxis.setTickUnit(5);
		yAxis.setMinorTickCount(4);
		yAxis.setMinorTickLength(5);
		yAxis.setTickMarkVisible(true);

		// Estilo CSS para las líneas de cuadrícula
		barChart.setStyle("""
        -fx-horizontal-grid-lines-visible: true;
        -fx-vertical-grid-lines-visible: false;
        -fx-horizontal-minor-grid-lines-visible: true;
        -fx-chart-horizontal-grid-lines: #505050;
        -fx-chart-horizontal-minor-grid-lines: #303030;
        """);

		// Cargar datos de asistencias
		XYChart.Series<String, Number> series = loadAssistsData();
		barChart.getData().add(series);

		// Ajustes finales
		Platform.runLater(() -> {
			// Ajustar ancho del gráfico
			int barWidth = 30;
			int minWidth = 800;
			int calculatedWidth = Math.max(minWidth, series.getData().size() * barWidth);
			barChart.setPrefWidth(calculatedWidth);

			// Aplicar estilos a las barras
			for (XYChart.Data<String, Number> data : series.getData()) {
				Node node = data.getNode();
				if (node != null) {
					node.setStyle(
							"-fx-bar-fill: #00ffd5; "
									+ "-fx-background-radius: 2 2 0 0; "
									+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 1, 0, 0, 1);"
									+ "-fx-padding: 0;"
									+ "-fx-min-width: 20px; "
									+ "-fx-max-width: 20px; "
					);
				}
			}

			// Forzar redibujado
			barChart.requestLayout();
		});
	}
	public void handleAssistsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Asistencias!");
		try {
			showAssistsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// *** INTERFAZ CARDS ***
	public void showCardsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Cards.fxml"));
		var cardsScene = new Scene(fxmlLoader.load());
		stage.setScene(cardsScene);
		updateCoachNameLabel(cardsScene);

		// Inicializar el controlador GraphicCardsController
		GraphicCardsController graphicCardsController = context.getBean(GraphicCardsController.class);
		graphicCardsController.setupCardsChart(cardsScene, this.loggedInUser);

		// Asignar eventos de la interfaz.
		setNavigationClickListeners(cardsScene);
		setMenuClickListener(cardsScene);
		setOutClickListener(cardsScene);
		stage.setTitle("Tarjetas");
		stage.show();
	}
	public void handleCardsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Tarjetas!");
		try {
			showCardsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// *** INTERFAZ SAVES ***
	public void showSavesScene(Stage stage) throws IOException {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Saves.fxml"));
			Scene savesScene = new Scene(fxmlLoader.load());
			stage.setScene(savesScene);

			// Configurar elementos de la interfaz
			updateCoachNameLabel(savesScene);
			setNavigationClickListeners(savesScene);
			setMenuClickListener(savesScene);
			setOutClickListener(savesScene);

			// Configurar eventos de navegación
			Text scoresText = (Text) savesScene.lookup("#scores");
			Text assistsText = (Text) savesScene.lookup("#assists");
			Text cardsText = (Text) savesScene.lookup("#cards");

			if (scoresText != null) scoresText.setOnMouseClicked(this::handleScoresClick);
			if (assistsText != null) assistsText.setOnMouseClicked(this::handleAssistsClick);
			if (cardsText != null) cardsText.setOnMouseClicked(this::handleCardsClick);

			// Configurar el gráfico de barras para paradas
			configureSavesBarChart(savesScene);

			stage.setTitle("Paradas de Portero");
			stage.show();
		} catch (IOException e) {
			logger.error("Error al cargar la escena Saves", e);
			showAlert("Error", "No se pudo cargar la pantalla de paradas");
		}
	}
	private XYChart.Series<String, Number> loadSavesData() {
	    XYChart.Series<String, Number> series = new XYChart.Series<>();
	    series.setName("Saves");

	    try {
	        // Obtener solo los porteros del equipo del coach logueado
	        Team team = loggedInUser != null ? loggedInUser.getTeam() : null;
	        List<Player> goalkeepers = team != null ?
	                playerServiceImpl.findByPositionAndTeamName("GK", team) :
	                playerServiceImpl.findByPosition("GK");

	        // Comprobar si la lista está vacía
	        if (goalkeepers.isEmpty()) {
	            logger.warn("No se encontraron porteros");
	            return getExampleSavesData();
	        }

	        // Filtrar jugadores nulos y ordenar por paradas (de mayor a menor)
	        goalkeepers.sort((p1, p2) -> {
	            // Manejo seguro de valores nulos
	            int saves1 = p1.getSaves() != null ? p1.getSaves() : 0;
	            int saves2 = p2.getSaves() != null ? p2.getSaves() : 0;
	            return Integer.compare(saves2, saves1); // Orden descendente
	        });

	        // Añadir datos al gráfico
	        for (Player goalkeeper : goalkeepers) {
	            String displayName = String.format("%s (%d)",
	                    goalkeeper.getApodo().length() > 6 ?
	                            goalkeeper.getApodo().substring(0, 5) + "." :
	                            goalkeeper.getApodo(),
	                    goalkeeper.getDorsal());

	            // Manejar valores nulos de saves
	            int saves = goalkeeper.getSaves() != null ? goalkeeper.getSaves() : 0;
	            series.getData().add(new XYChart.Data<>(displayName, saves));
	        }

	        if (series.getData().isEmpty()) {
	            return getExampleSavesData();
	        }

	        return series;

	    } catch (Exception e) {
	        logger.error("Error al cargar datos de paradas", e);
	        return getExampleSavesData();
	    }
	}
	private XYChart.Series<String, Number> getExampleSavesData() {
	    XYChart.Series<String, Number> series = new XYChart.Series<>();
	    series.setName("Saves");

	    // Datos de ejemplo en caso de error
	    series.getData().add(new XYChart.Data<>("Portero 1", 25));
	    series.getData().add(new XYChart.Data<>("Portero 2", 18));
	    series.getData().add(new XYChart.Data<>("Portero 3", 12));

	    return series;
	}
	private void configureSavesBarChart(Scene scene) {
		BarChart<String, Number> barChart = (BarChart<String, Number>) scene.lookup("#grafic_saves");
		if (barChart == null) {
			logger.warn("No se encontró el gráfico en Saves.fxml");
			return;
		}

		// Limpiar datos previos
		barChart.getData().clear();

		// Configuración básica del gráfico
		barChart.setTitle("Top Saves (Goalkeepers)");
		barChart.setLegendVisible(false);
		barChart.setAnimated(false);
		barChart.setHorizontalGridLinesVisible(true);
		barChart.setVerticalGridLinesVisible(false);

		// Configuración de espacios entre barras
		barChart.setCategoryGap(1);
		barChart.setBarGap(0);

		// Configuración del eje X
		CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
		xAxis.setLabel("Goalkeepers");
		xAxis.setTickLabelFill(Paint.valueOf("#f4f2f2"));
		xAxis.setTickLabelFont(Font.font(10));
		xAxis.setTickLabelRotation(270);
		xAxis.setStartMargin(0);
		xAxis.setEndMargin(0);

		// Configuración del eje Y (0-100, incrementos de 10 con líneas intermedias)
		NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
		yAxis.setLabel("Saves");
		yAxis.setTickLabelFill(Paint.valueOf("#f4f2f2"));
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(100);
		yAxis.setTickUnit(10);
		yAxis.setMinorTickCount(4);
		yAxis.setMinorTickLength(5);
		yAxis.setTickMarkVisible(true);

		// Estilo CSS para las líneas de cuadrícula
		barChart.setStyle("""
        -fx-horizontal-grid-lines-visible: true;
        -fx-vertical-grid-lines-visible: false;
        -fx-horizontal-minor-grid-lines-visible: true;
        -fx-chart-horizontal-grid-lines: #505050;
        -fx-chart-horizontal-minor-grid-lines: #303030;
        """);

		// Cargar datos de paradas
		XYChart.Series<String, Number> series = loadSavesData();
		barChart.getData().add(series);

		// Ajustes finales
		Platform.runLater(() -> {
			// Ajustar ancho del gráfico
			int barWidth = 30;
			int minWidth = 800;
			int calculatedWidth = Math.max(minWidth, series.getData().size() * barWidth);
			barChart.setPrefWidth(calculatedWidth);

			// Aplicar estilos a las barras
			for (XYChart.Data<String, Number> data : series.getData()) {
				Node node = data.getNode();
				if (node != null) {
					node.setStyle(
							"-fx-bar-fill: #00ffd5; "
									+ "-fx-background-radius: 2 2 0 0; "
									+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 1, 0, 0, 1);"
									+ "-fx-padding: 0;"
									+ "-fx-min-width: 20px; "
									+ "-fx-max-width: 20px; "
					);
				}
			}

			// Forzar redibujado
			barChart.requestLayout();
		});
	}
	public void handleSavesClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Paradas!");
		try {
			showSavesScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// *** INTERFAZ TOURNAMENT ***
	public void showTournamentScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tournament.fxml"));
		var tournamentScene = new Scene(fxmlLoader.load());
		stage.setScene(tournamentScene);
		updateCoachNameLabel(tournamentScene);


		// Configurar la tabla de clasificación
		configurarTablaClasificacion(tournamentScene);

		// Asignar eventos de la interfaz
		setNavigationClickListeners(tournamentScene);
		setMenuClickListener(tournamentScene);
		setOutClickListener(tournamentScene);

		stage.setTitle("Tournament");
		stage.show();
	}
	private void handleTournamentButtonAction(Stage stage) {
		try {
			showTournamentScene(stage);  // Llama a la escena del torneo
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void handleClasificationClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Clasificación!");
		try {
			showTournamentScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Error", "No se pudo cargar la clasificación");
		}
	}
	private void configurarTablaClasificacion(Scene scene) {
		TableView<Team> tablaClasificacion = (TableView<Team>) scene.lookup("#tablaClasificacion");

		if (tablaClasificacion != null) {
			TableColumn<Team, Integer> colPos = (TableColumn<Team, Integer>) tablaClasificacion.getColumns().get(0);
			TableColumn<Team, String> colTeam = (TableColumn<Team, String>) tablaClasificacion.getColumns().get(1);
			TableColumn<Team, Integer> colPts = (TableColumn<Team, Integer>) tablaClasificacion.getColumns().get(2);
			TableColumn<Team, Integer> colGF = (TableColumn<Team, Integer>) tablaClasificacion.getColumns().get(3);
			TableColumn<Team, Integer> colGC = (TableColumn<Team, Integer>) tablaClasificacion.getColumns().get(4);
			TableColumn<Team, Integer> colPG = (TableColumn<Team, Integer>) tablaClasificacion.getColumns().get(5);
			TableColumn<Team, Integer> colPE = (TableColumn<Team, Integer>) tablaClasificacion.getColumns().get(6);
			TableColumn<Team, Integer> colPP = (TableColumn<Team, Integer>) tablaClasificacion.getColumns().get(7);

			colPos.setCellValueFactory(new PropertyValueFactory<>("position"));
			colTeam.setCellValueFactory(new PropertyValueFactory<>("name"));
			colPts.setCellValueFactory(new PropertyValueFactory<>("points"));
			colGF.setCellValueFactory(new PropertyValueFactory<>("gf"));
			colGC.setCellValueFactory(new PropertyValueFactory<>("gc"));
			colPG.setCellValueFactory(new PropertyValueFactory<>("pg"));
			colPE.setCellValueFactory(new PropertyValueFactory<>("pe"));
			colPP.setCellValueFactory(new PropertyValueFactory<>("pp"));

			cargarClasificacionEnTabla(tablaClasificacion);
		}
	}
	private void cargarClasificacionEnTabla(TableView<Team> tablaClasificacion) {
		try {
			List<Team> clasificacion = tournamentService.findTeamsByTournamentId(1); // Si tienes esto
			ObservableList<Team> datos = FXCollections.observableArrayList(clasificacion);
			tablaClasificacion.setItems(datos);
		} catch (Exception e) {
			logger.error("Error al cargar la clasificación", e);
			showAlert("Error", "No se pudo cargar la clasificación");
		}
	}
	public void showResultsScene(Stage stage) throws IOException {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Results.fxml"));
	    var resultsScene = new Scene(fxmlLoader.load());
	    stage.setScene(resultsScene);


	    // Actualizar el nombre del coach
	    updateCoachNameLabel(resultsScene);

	    // Usar el controlador para ver los resultados
		resultsController.setupResultsView(resultsScene);

	    // Asignar eventos de la interfaz
	    setNavigationClickListeners(resultsScene);
	    setMenuClickListener(resultsScene);
	    setOutClickListener(resultsScene);

	    stage.setTitle("Resultados");
	    stage.show();
	}


	// *** INTERFAZ TEAMS ***
	public void showTeamsScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Teams.fxml"));
		var teamsScene = new Scene(fxmlLoader.load());
		stage.setScene(teamsScene);
		updateCoachNameLabel(teamsScene);
		TableView<Team> tablaEquipos = (TableView<Team>) teamsScene.lookup("#tablaEquipos");
		if (tablaEquipos != null) {
			configurarTablaEquipos(tablaEquipos);
		}
		setNavigationClickListeners(teamsScene);
		setMenuClickListener(teamsScene);
		setOutClickListener(teamsScene);
		stage.setTitle("Equipos");
		stage.show();
	}
	public void handleTeamsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Equipos!");
		try {
			showTeamsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Error", "No se pudo cargar los equipos");
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


	// *** MÉTODOS ***
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
			assistsText.setOnMouseClicked(this::handleAssistsClick);  // Asignamos el evento para navegar a Assists
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
	@FXML
	private void handleResultsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Resultados!");
		try {
			showResultsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Error", "No se pudo cargar la pantalla de resultados");
		}
	}
	// Métodos para manejar los eventos acceso a menu.fxml, log_out.fxml, calendar.fxml.
	public void setMenuClickListener(Scene scene) {
		setImageClickListener(scene, "#img_menu", this::handleImgMenuClick);
		setImageClickListener(scene, "#img_calendar", this::handleImgCalendarClick);
		setImageClickListener(scene, "#img_films", this::handleImgFilmsClick);
	}
	public void handleImgFilmsClick(MouseEvent event) {
		try {
			showVideosScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Error", "No se pudo cargar la pantalla de videos");
		}
	}
	public void handleImgMenuClick(MouseEvent event) {
		try {
			showMenu((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void handleImgCalendarClick(MouseEvent event) {
		try {
			showCalendarScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setImageClickListener(Scene scene, String fxId, EventHandler<MouseEvent> handler) {
		ImageView imageView = (ImageView) scene.lookup(fxId);
		if (imageView != null) {
			imageView.setOnMouseClicked(handler);
		}
	}
	public void setOutClickListener(Scene scene) {
		ImageView imgOut = (ImageView) scene.lookup("#img_out");
		if (imgOut != null) {
			imgOut.setOnMouseClicked(this::handleImgOutClick);
		}
	}
	public void handleImgOutClick(MouseEvent event) {
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
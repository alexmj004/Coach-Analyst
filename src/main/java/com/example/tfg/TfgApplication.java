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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class TfgApplication extends Application {

	/** Controlador para manejar la navegación y lógica de la vista de vídeos. */
	@Autowired
	private VideosController videosController;
	/** Servicio para gestionar torneos, como cargar clasificaciones o equipos por torneo. */
	@Autowired
	private TournamentService tournamentService;

	/** Logger para registrar información, advertencias y errores de la aplicación. */
	private static final Logger logger = LoggerFactory.getLogger(TfgApplication.class);
	/** Contexto configurable de la aplicación Spring Boot, utilizado para cerrar o reiniciar el contexto. */
	private static ConfigurableApplicationContext context;
	/** Nombre de usuario introducido en la pantalla de login. */
	private String inputUserName;
	/** Contraseña introducida en la pantalla de login. */
	@FXML
	private String inputPassword;
	/** Servicio para operaciones relacionadas con jugadores (crear, listar, buscar, etc.). */
	private PlayerServiceImpl playerServiceImpl;
	/** Servicio para gestionar eventos y lógica relacionada con el calendario. */
	private CalendarService calendarService;
	/** Servicio para gestionar entrenamientos del equipo. */
	private TrainingService trainingService;
	/** Usuario actualmente logueado en la aplicación. */
	private User loggedInUser;
	/** Servicio para obtener y mostrar los resultados de los partidos. */
	private ResultsService resultsService;
	/** Servicio para operaciones con equipos (listar, buscar por torneo, etc.). */
	private TeamServImpl teamServ;
	/** Implementación del servicio de torneos, posiblemente con lógica adicional específica. */
	private TournamentServiceImp tournamentServ;
	/** Controlador encargado de manejar la lógica y vista de resultados. */
	private ResultsController resultsController;

	/** Portero seleccionado en la interfaz de creación de partidos. */
	private String selectedGk;
	/** Defensa seleccionado en la interfaz de creación de partidos. */
	private String selectedDf;
	/** Pívot seleccionado en la interfaz de creación de partidos. */
	private String selectedPiv;
	/** Lateral izquierdo seleccionado en la interfaz de creación de partidos. */
	private String selectedLw;
	/** Lateral derecho seleccionado en la interfaz de creación de partidos. */
	private String selectedRw;

	/**
	 * Método principal que inicia la aplicación.
	 * - Primero arranca el contexto de Spring Boot para cargar la configuración y los beans.
	 * - Luego lanza la aplicación JavaFX.
	 */
	public static void main(String[] args) {
		// Primero, iniciar Spring Boot y luego JavaFX.
		context = SpringApplication.run(TfgApplication.class, args);
		launch(args); // Lanza la aplicación de JavaFX.
	}


	/**
	 * Método start() de JavaFX: configura la ventana principal (Stage).
	 * - Carga la escena de Login como pantalla inicial.
	 * - Maximiza la ventana automáticamente al iniciar.
	 *
	 * @param stage Ventana principal proporcionada por JavaFX.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Cargar la pantalla de Login al iniciar la aplicación.
		showLoginScene(stage);

		// Maximizar la ventana al iniciar.
		stage.setMaximized(true);
	}


	/**
	 * Método init() de JavaFX: inicializa los servicios y controladores.
	 * - Obtiene las instancias de los beans de Spring después de que el contexto esté listo.
	 * - Aquí se inyectan las dependencias necesarias para toda la aplicación.
	 */
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


// ==================== INTERFAZ LOGIN ====================

	/**
	 * Configura y muestra la escena de Login.
	 * - Carga el FXML y configura los elementos de la interfaz.
	 * - Mantiene el estado de la ventana (tamaño/maximizado) al cambiar de escena.
	 * - Configura los eventos para los botones de login y registro.
	 *
	 * @param stage Ventana principal donde se cargará la escena
	 * @throws IOException Si hay error al cargar el archivo FXML
	 */
	public void showLoginScene(Stage stage) throws IOException {
		// Carga el archivo FXML que define la interfaz de login
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
		Parent root = fxmlLoader.load();

		// Guarda el estado actual de la ventana antes de modificarla
		boolean wasMaximized = stage.isMaximized();
		double currentWidth = stage.getWidth();
		double currentHeight = stage.getHeight();

		// Crea y establece la nueva escena
		Scene loginScene = new Scene(root);
		stage.setScene(loginScene);

		// Restaura el estado anterior de la ventana
		if (wasMaximized) {
			stage.setMaximized(true);
		} else {
			stage.setWidth(currentWidth);
			stage.setHeight(currentHeight);
		}

		// Obtiene referencias a los elementos de la interfaz mediante la jerarquía de nodos
		HBox hbox = (HBox) root.getChildrenUnmodifiable().get(2);
		VBox vbox = (VBox) hbox.getChildren().get(1);

		// Busca el botón de nuevo usuario
		Button newUserButton = null;
		for (Node node : vbox.getChildren()) {
			if (node instanceof Button && "newUser".equals(node.getId())) {
				newUserButton = (Button) node;
				break;
			}
		}

		// Validación crítica del botón
		if (newUserButton == null) {
			throw new RuntimeException("Error crítico: No se encontró el botón 'newUser' en la jerarquía");
		}

		// Configura el evento para el botón de nuevo usuario
		newUserButton.setOnAction(e -> {
			try {
				handleNewUserButtonAction(stage);
			} catch (IOException ex) {
				System.out.println(e.getEventType());
			}
		});

		// Obtiene referencias a los campos de texto y botón de login
		TextField userField = (TextField) vbox.lookup("#user");
		PasswordField passField = (PasswordField) vbox.lookup("#pass");
		Button loginButton = (Button) vbox.lookup("#login_btn");

		// Configura el evento para el botón de login
		loginButton.setOnAction(e -> handleLoginButtonAction(userField, passField, stage));

		stage.setTitle("Inicio de sesión");
		stage.show();
	}

	/**
	 * Maneja la acción del botón de login:
	 * - Valida que los campos no estén vacíos
	 * - Verifica las credenciales con el servicio de usuarios
	 * - Si son válidas, guarda el usuario logueado y muestra el menú principal
	 * - Si no son válidas, muestra un mensaje de error
	 *
	 * @param userField Campo de texto con el nombre de usuario
	 * @param passField Campo de contraseña
	 * @param stage Ventana principal
	 */
	private void handleLoginButtonAction(TextField userField, PasswordField passField, Stage stage) {
		inputUserName = userField.getText();
		inputPassword = passField.getText();

		if (inputUserName.isEmpty() || inputPassword.isEmpty()) {
			showAlert("Error de inicio de sesión", "Campos Vacíos, por favor, ingrese un usuario y contraseña.");
		} else {
			UserService userService = context.getBean(UserService.class);
			User user = userService.findByUserName(inputUserName);

			if (user != null && user.getPassword().equals(inputPassword)) {
				this.loggedInUser = user; // Guarda el usuario logueado
				try {
					showMenu(stage); // Navega al menú principal
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				showAlert("Error de inicio de sesión", "Credenciales inválidas por favor, ingrese un usuario y contraseña válidos.");
			}
		}
	}

	/**
	 * Obtiene el ID del equipo asociado al usuario logueado.
	 *
	 * @return ID del equipo
	 * @throws NullPointerException Si el usuario no está logueado o no tiene equipo
	 */
	public int obtenerIdEquipo(){
		if (loggedInUser != null && loggedInUser.getTeam() != null){
			return loggedInUser.getTeam().getId();
		}
		throw new NullPointerException("El usuario no está logueado o no tiene equipo asignado");
	}


	// ==================== INTERFAZ REGISTRO ====================

	/**
	 * Maneja la acción del botón para nuevo usuario:
	 * - Carga la interfaz de registro
	 * - Configura todos los campos y botones
	 * - Maneja la navegación entre pantallas
	 *
	 * @param stage Ventana principal
	 * @throws IOException Si hay error al cargar el FXML
	 */
	private void handleNewUserButtonAction(Stage stage) throws IOException {
		// Carga la interfaz de registro desde FXML
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Registry.fxml"));
		Parent root = fxmlLoader.load();

		// Guarda el estado actual de la ventana
		boolean wasMaximized = stage.isMaximized();
		double currentWidth = stage.getWidth();
		double currentHeight = stage.getHeight();

		// Obtiene referencias a los elementos del formulario
		SplitPane splitPane = (SplitPane) root;
		VBox rightVBox = (VBox) splitPane.getItems().get(1);

		// Busca todos los campos del formulario
		TextField nameField = findTextFieldInVBox(rightVBox, "name_field");
		TextField surnameField = findTextFieldInVBox(rightVBox, "surname_field");
		TextField emailField = findTextFieldInVBox(rightVBox, "email_field");
		TextField usernameField = findTextFieldInVBox(rightVBox, "username_field");
		PasswordField passwordField = findPasswordFieldInVBox(rightVBox, "password_field");
		Button registryButton = findButtonInVBox(rightVBox, "registry_btn");
		Button cancelButton = findButtonInVBox(rightVBox, "cancel_btn");

		// Configura los eventos de los botones
		registryButton.setOnAction(e -> handleRegistryButtonAction(
				nameField, surnameField, emailField, usernameField, passwordField, stage
		));

		cancelButton.setOnAction(e -> {
			try {
				showLoginScene(stage); // Vuelve al login
			} catch (IOException ex) {
				logger.error("Error al cargar login", ex);
				showAlert("Error", "No se pudo cargar la pantalla de login");
			}
		});

		// Muestra la nueva escena
		Scene registryScene = new Scene(root);
		stage.setScene(registryScene);

		// Restaura el estado de la ventana
		if (wasMaximized) {
			stage.setMaximized(true);
		} else {
			stage.setWidth(currentWidth);
			stage.setHeight(currentHeight);
		}

		stage.setTitle("Registro de Usuario");
		stage.show();
	}

	/**
	 * Busca un TextField dentro de un VBox por su ID
	 *
	 * @param vbox Contenedor donde buscar
	 * @param id ID del campo a buscar
	 * @return Referencia al TextField encontrado
	 * @throws RuntimeException Si no encuentra el campo
	 */
	private TextField findTextFieldInVBox(VBox vbox, String id) {
		for (Node node : vbox.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof TextField && id.equals(child.getId())) {
						return (TextField) child;
					}
				}
			}
		}
		throw new RuntimeException("No se encontró el TextField con id: " + id);
	}
	// Métodos para PasswordField y Button
	/**
	 * Busca un PasswordField dentro de un VBox por su ID
	 */
	private PasswordField findPasswordFieldInVBox(VBox vbox, String id) {
		for (Node node : vbox.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof PasswordField && id.equals(child.getId())) {
						return (PasswordField) child;
					}
				}
			}
		}
		throw new RuntimeException("No se encontró el PasswordField con id: " + id);
	}
	/**
	 * Busca un Button dentro de un VBox por su ID
	 */
	private Button findButtonInVBox(VBox vbox, String id) {
		for (Node node : vbox.getChildren()) {
			if (node instanceof HBox) {
				for (Node child : ((HBox) node).getChildren()) {
					if (child instanceof Button && id.equals(child.getId())) {
						return (Button) child;
					}
				}
			}
		}
		throw new RuntimeException("No se encontró el Button con id: " + id);
	}
	/**
	 * Maneja el registro de un nuevo usuario:
	 * - Valida que todos los campos estén completos
	 * - Crea un nuevo objeto User con los datos
	 * - Intenta guardarlo mediante el servicio
	 * - Muestra feedback al usuario
	 *
	 * @param nameField Campo con el nombre
	 * @param surnameField Campo con el apellido
	 * @param emailField Campo con el email
	 * @param usernameField Campo con el nombre de usuario
	 * @param passwordField Campo con la contraseña
	 * @param stage Ventana principal
	 */
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


// ==================== INTERFAZ MENÚ PRINCIPAL ====================

	/**
	 * Muestra la interfaz del menú principal:
	 * - Carga el FXML del menú
	 * - Configura todos los botones de navegación
	 * - Actualiza la información del usuario logueado
	 *
	 * @param stage Ventana principal
	 * @throws IOException Si hay error al cargar el FXML
	 */
	public void showMenu(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
		Parent root = fxmlLoader.load();

		// Guardar estado actual antes de cambiar
		boolean wasMaximized = stage.isMaximized();
		double currentWidth = stage.getWidth();
		double currentHeight = stage.getHeight();

		Scene menuScene = new Scene(root);
		stage.setScene(menuScene);

		// Restaurar estado
		if (wasMaximized) {
			stage.setMaximized(true);
		} else {
			stage.setWidth(currentWidth);
			stage.setHeight(currentHeight);
		}

		// Configuración de eventos y elementos
		setMenuClickListener(menuScene);
		setOutClickListener(menuScene);
		updateCoachNameLabel(menuScene);

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

		stage.setTitle("Menú");
		stage.show();
	}


	// ==================== INTERFAZ VIDEOS ====================

	/**
	 * Muestra la escena de gestión de videos:
	 * - Carga el layout desde el archivo FXML
	 * - Configura el controlador usando el contexto de Spring
	 * - Establece los listeners para el menú y logout
	 * - Actualiza la información del entrenador logueado
	 * - Gestiona la liberación de recursos al cerrar la ventana
	 *
	 * @param stage Ventana principal donde se mostrará la escena
	 * @throws IOException Si hay error al cargar el archivo FXML
	 */
	public void showVideosScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Videos.fxml"));
		fxmlLoader.setControllerFactory(context::getBean);

		Scene videosScene = new Scene(fxmlLoader.load());
		stage.setScene(videosScene);

		setMenuClickListener(videosScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(videosScene);

		updateCoachNameLabel(videosScene);

		// Configura el cierre para liberar recursos
		stage.setOnCloseRequest(e -> {
			VideosController controller = fxmlLoader.getController();
			controller.cleanup();
		});

		stage.setTitle("Video Library");
		stage.show();
	}


// ==================== INTERFAZ CALENDARIO ====================

	/**
	 * Muestra la interfaz de calendario:
	 * - Carga el layout desde FXML
	 * - Configura los componentes del calendario
	 * - Establece los listeners comunes
	 * - Actualiza la información del usuario
	 *
	 * @param stage Ventana principal donde se mostrará la escena
	 * @throws IOException Si hay error al cargar el archivo FXML
	 */
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


	// ==================== INTERFAZ ENTRENAMIENTOS ====================

	/**
	 * Muestra la interfaz de gestión de entrenamientos:
	 * - Carga el layout desde FXML
	 * - Obtiene el controlador desde el contexto de Spring
	 * - Configura los componentes específicos de entrenamientos
	 * - Establece los listeners comunes
	 *
	 * @param stage Ventana principal donde se mostrará la escena
	 * @throws IOException Si hay error al cargar el archivo FXML
	 */
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
	/**
	 * Maneja el evento del botón de entrenamientos:
	 * - Navega a la escena de gestión de entrenamientos
	 * - Captura y loguea cualquier error durante la transición
	 *
	 * @param stage Ventana principal donde se mostrará la escena
	 */
	private void handleTrainingButtonAction(Stage stage) {
		try {
			showTrainingScene(stage);  // Llama a la escena de entrenamiento
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// ==================== INTERFAZ PARTIDOS ====================

	/**
	 * Muestra la escena de gestión de partidos:
	 * - Carga el layout desde FXML
	 * - Configura los ComboBox con jugadores por posición
	 * - Restaura selecciones previas de alineación
	 * - Establece listeners para guardar selecciones
	 * - Configura elementos comunes de la interfaz
	 *
	 * @param stage Ventana principal donde se mostrará la escena
	 * @throws IOException Si hay error al cargar el archivo FXML
	 */
	public void showMatchScene(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Match.fxml"));
		var matchScene = new Scene(fxmlLoader.load());
		stage.setScene(matchScene);
		updateCoachNameLabel(matchScene);

		// Configurar los ComboBox con jugadores por posición
		setupMatchComboboxes(matchScene);

		//restaurar selecciones previas
		restoreMatchSelections(matchScene);

		//listeners para guardar las selecciones
		setupSelectionListeners(matchScene);

		setMenuClickListener(matchScene);  // Reutilizamos el mismo método para agregar la funcionalidad a esta escena
		setOutClickListener(matchScene);

		stage.setTitle("Partidos");
		stage.show();
	}
	/**
	 * Configura listeners para guardar las selecciones de jugadores:
	 * - Guarda en variables de clase las selecciones actuales
	 * - Actualiza cuando cambia la selección en los ComboBox
	 *
	 * @param scene Escena actual que contiene los ComboBox
	 */	private void setupSelectionListeners(Scene scene) {
		ComboBox<String> gkComboBox = (ComboBox<String>) scene.lookup("#box_gk");
		if (gkComboBox != null) {
			gkComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) {
					selectedGk = newVal;
				}
			});
		}

		ComboBox<String> dfComboBox = (ComboBox<String>) scene.lookup("#box_df");
		if (dfComboBox != null) {
			dfComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) {
					selectedDf = newVal;
				}
			});
		}

		ComboBox<String> pivComboBox = (ComboBox<String>) scene.lookup("#box_pivot");
		if (pivComboBox != null) {
			pivComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) {
					selectedPiv = newVal;
				}
			});
		}

		ComboBox<String> wardsComboBox = (ComboBox<String>) scene.lookup("#box_wards");
		if (wardsComboBox != null) {
			wardsComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) {
					if (newVal.contains("(LW)")) {
						selectedLw = newVal;
					} else if (newVal.contains("(RW)")) {
						selectedRw = newVal;
					}
				}
			});
		}
	}
	/**
	 * Restaura las selecciones guardadas de jugadores:
	 * - Recupera las selecciones de variables de clase
	 * - Actualiza los ComboBox y labels correspondientes
	 *
	 * @param scene Escena actual donde se restaurarán las selecciones
	 */	private void restoreMatchSelections(Scene scene) {
		// Restaurar portero
		if (selectedGk != null) {
			ComboBox<String> gkComboBox = (ComboBox<String>) scene.lookup("#box_gk");
			if (gkComboBox != null) {
				gkComboBox.setValue(selectedGk);
				updateLabel(gkComboBox, (Label) scene.lookup("#label_gk"));
			}
		}

		// Restaurar defensa
		if (selectedDf != null) {
			ComboBox<String> dfComboBox = (ComboBox<String>) scene.lookup("#box_df");
			if (dfComboBox != null) {
				dfComboBox.setValue(selectedDf);
				updateLabel(dfComboBox, (Label) scene.lookup("#label_df"));
			}
		}

		// Restaurar pivot
		if (selectedPiv != null) {
			ComboBox<String> pivComboBox = (ComboBox<String>) scene.lookup("#box_pivot");
			if (pivComboBox != null) {
				pivComboBox.setValue(selectedPiv);
				updateLabel(pivComboBox, (Label) scene.lookup("#label_piv"));
			}
		}

		// Restaurar extremos
		Label labelLw = (Label) scene.lookup("#label_lw");
		Label labelRw = (Label) scene.lookup("#label_rw");

		// Restaurar LW
		if (selectedLw != null && labelLw != null) {
			String apodo = selectedLw.replace(" (LW)", "");
			Player player = playerServiceImpl.findByApodo(apodo);
			if (player != null) {
				labelLw.setText(String.format("%s (%d)", player.getApodo(), player.getDorsal()));
			}
		}

		// Restaurar RW
		if (selectedRw != null && labelRw != null) {
			String apodo = selectedRw.replace(" (RW)", "");
			Player player = playerServiceImpl.findByApodo(apodo);
			if (player != null) {
				labelRw.setText(String.format("%s (%d)", player.getApodo(), player.getDorsal()));
			}
		}
	}
	/**
	 * Maneja el evento del botón de partidos:
	 * - Navega a la escena de gestión de partidos
	 * - Captura y loguea cualquier error durante la transición
	 *
	 * @param stage Ventana principal donde se mostrará la escena
	 */
	private void handleMatchButtonAction(Stage stage) {
		try {
			showMatchScene(stage);  // Llama a la escena de match
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Obtiene el nombre completo de una posición a partir de su código:
	 * - Convierte códigos como "GK" a "Goalkeepers"
	 *
	 * @param positionCode Código de posición (GK, DF, PIV, etc.)
	 * @return Nombre completo de la posición
	 */
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
				return positionCode;
		}
	}
	/**
	 * Configura todos los ComboBox de la interfaz de partidos:
	 * - Inicializa el servicio de jugadores si es necesario
	 * - Obtiene el equipo del usuario logueado
	 * - Configura cada ComboBox por posición
	 *
	 * @param matchScene Escena actual que contiene los ComboBox
	 */
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
	/**
	 * Configura un ComboBox para una posición específica:
	 * - Establece el texto de prompt
	 * - Carga los jugadores para esa posición
	 * - Configura el evento para actualizar el label asociado
	 *
	 * @param scene Escena actual
	 * @param comboId ID del ComboBox
	 * @param position Posición a cargar (GK, DF, etc.)
	 * @param labelId ID del label asociado
	 * @param team Equipo para filtrar jugadores
	 */
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
	/**
	 * Carga jugadores extremos (LW/RW) en un ComboBox:
	 * - Combina jugadores de ambas posiciones
	 * - Marca cada jugador con su posición
	 * - Filtra por equipo si se especifica
	 *
	 * @param comboBox ComboBox a llenar
	 * @param team Equipo para filtrar (null para todos)
	 */
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
	/**
	 * Carga jugadores para una posición específica:
	 * - Filtra por equipo si se especifica
	 * - Maneja errores y muestra feedback en el ComboBox
	 *
	 * @param comboBox ComboBox a llenar
	 * @param position Posición a cargar
	 * @param team Equipo para filtrar (null para todos)
	 */
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
	/**
	 * Actualiza un label con la selección actual de un ComboBox:
	 * - Muestra el apodo y dorsal del jugador seleccionado
	 *
	 * @param comboBox ComboBox con la selección
	 * @param label Label a actualizar
	 */
	private void updateLabel(ComboBox<String> comboBox, Label label) {
		String selectedApodo = comboBox.getSelectionModel().getSelectedItem();
		if (selectedApodo != null && label != null) {
			// Buscar el jugador por su apodo para obtener el dorsal
			Player player = playerServiceImpl.findByApodo(selectedApodo);
			if (player != null) {
				// Actualizar el label con el apodo y dorsal
				label.setText(String.format("%s (%d)", player.getApodo(), player.getDorsal()));
			}
		}
	}
	/**
	 * Actualiza los labels de extremos (LW/RW):
	 * - Detecta la posición del jugador seleccionado
	 * - Previene duplicados en ambas posiciones
	 * - Actualiza el label correspondiente
	 *
	 * @param comboBox ComboBox de extremos
	 * @param labelLw Label para extremo izquierdo
	 * @param labelRw Label para extremo derecho
	 */
	private void updateWardsLabel(ComboBox<String> comboBox, Label labelLw, Label labelRw) {
		String selectedPlayer = comboBox.getSelectionModel().getSelectedItem();
		if (selectedPlayer != null) {
			// Extraer solo el apodo sin la posición
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

	// ==================== INTERFAZ ANALYST ====================

	/**
	 * Muestra la escena del panel de análisis (goleadores) en el stage proporcionado.
	 * Carga la interfaz desde el archivo FXML, configura los elementos de navegación
	 * y genera un gráfico de barras con los máximos goleadores.
	 *
	 * @param stage la ventana principal donde se mostrará la escena
	 * @throws IOException si ocurre un error al cargar el archivo FXML
	 */
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

	/**
	 * Carga los datos de goles de los jugadores, ordenados de mayor a menor.
	 * Si hay un equipo asignado al usuario logueado, se filtra por ese equipo.
	 * En caso de error, carga datos de ejemplo.
	 *
	 * @return una serie de datos para el gráfico de barras
	 */
	private XYChart.Series<String, Number> loadPlayerData() {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Goals");

		try {
			// Obtener jugadores del equipo del coach logueado
			Team team = loggedInUser != null ? loggedInUser.getTeam() : null;
			List<Player> players = team != null ?
					playerServiceImpl.findByTeamName(team) :
					playerServiceImpl.findAll();

			// Ordenar por goles
			players.sort(Comparator.comparingInt(Player::getGoals).reversed());

			// Añadir datos al gráfico
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
	/**
	 * Configura el gráfico de barras de goleadores en la escena.
	 * Personaliza ejes, estilos, animaciones y carga la serie de datos correspondiente.
	 *
	 * @param scene la escena que contiene el gráfico
	 */
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
		xAxis.setTickLabelRotation(270);
		xAxis.setStartMargin(0);
		xAxis.setEndMargin(0);
		xAxis.setTickLabelFont(Font.font(16));
		xAxis.setTickLabelFill(Color.BLACK);

		// Configuración del eje Y
		NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
		yAxis.setLabel("Goals");
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(40);
		yAxis.setTickUnit(5);
		yAxis.setTickLabelFont(Font.font(20));
		yAxis.setTickLabelFill(Color.BLACK);

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
	/**
	 * Maneja la acción del botón para acceder a la interfaz Analyst.
	 * Llama al método encargado de cargar y mostrar dicha escena.
	 *
	 * @param stage la ventana actual en la que se mostrará la escena
	 */
	private void handleAnalystButtonAction(Stage stage) {
		try {
			showAnalystScene(stage);  // Llama a la escena de analistas
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Maneja el clic en el texto "Goleadores" desde otras escenas.
	 * Carga la escena de goleadores en la misma ventana.
	 *
	 * @param event el evento de clic del mouse
	 */
	public void handleScoresClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Goleadores!");
		try {
			showAnalystScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// ==================== INTERFAZ ASISTENCIAS ====================

	/**
	 * Muestra la escena de asistencias en el stage proporcionado.
	 * Carga el archivo FXML, configura la interfaz y genera el gráfico de barras
	 * con los jugadores con más asistencias.
	 *
	 * @param stage la ventana donde se mostrará la escena
	 * @throws IOException si ocurre un error al cargar la interfaz
	 */
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
	/**
	 * Carga los datos de asistencias de los jugadores, ordenados de mayor a menor.
	 * Si hay un equipo asignado al usuario logueado, se filtra por ese equipo.
	 * En caso de error, carga datos de ejemplo.
	 *
	 * @return una serie de datos con las asistencias
	 */
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
	/**
	 * Configura el gráfico de barras para mostrar los máximos asistentes.
	 * Personaliza ejes, estilo visual y carga la serie de datos correspondiente.
	 *
	 * @param scene la escena donde se encuentra el gráfico
	 */
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
		xAxis.setTickLabelRotation(270);
		xAxis.setStartMargin(0);
		xAxis.setEndMargin(0);
		xAxis.setTickLabelFont(Font.font(16));
		xAxis.setTickLabelFill(Color.BLACK);

		// Configuración del eje Y
		NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
		yAxis.setLabel("Assists");
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(30);
		yAxis.setTickUnit(5);
		yAxis.setMinorTickCount(4);
		yAxis.setMinorTickLength(5);
		yAxis.setTickMarkVisible(true);
		yAxis.setTickLabelFont(Font.font(20));
		yAxis.setTickLabelFill(Color.BLACK);

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
	/**
	 * Maneja el clic sobre el texto "Asistencias" desde otras interfaces.
	 * Carga la escena de asistencias.
	 *
	 * @param event el evento de clic del mouse
	 */
	public void handleAssistsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Asistencias!");
		try {
			showAssistsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// ==================== INTERFAZ TARJETAS ====================

	/**
	 * Muestra la escena de tarjetas en el stage proporcionado.
	 * Carga el archivo FXML, actualiza elementos de interfaz y llama al
	 * controlador especializado para configurar el gráfico de tarjetas.
	 *
	 * @param stage la ventana principal donde se mostrará la escena
	 * @throws IOException si ocurre un error al cargar la interfaz
	 */
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
	/**
	 * Maneja el clic sobre el texto "Tarjetas".
	 * Llama al método que muestra la escena de tarjetas.
	 *
	 * @param event el evento de clic del mouse
	 */
	public void handleCardsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Tarjetas!");
		try {
			showCardsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// ==================== INTERFAZ PARADAS ====================

	/**
	 * Muestra la escena de paradas de portero.
	 * Carga el archivo FXML, actualiza la interfaz, configura navegación entre escenas
	 * y genera el gráfico de barras con los datos de paradas.
	 *
	 * @param stage la ventana principal donde se mostrará la escena
	 * @throws IOException si ocurre un error al cargar la escena
	 */
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
	/**
	 * Carga los datos de paradas de los porteros, filtrando por el equipo del usuario logueado.
	 * Si no hay porteros disponibles, puede cargar datos de prueba.
	 *
	 * @return una serie con los datos de paradas de los porteros
	 */
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

	        // Filtrar jugadores nulos y ordenar por paradas
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
		xAxis.setTickLabelRotation(270);
		xAxis.setStartMargin(0);
		xAxis.setEndMargin(0);
		xAxis.setTickLabelFont(Font.font(16));
		xAxis.setTickLabelFill(Color.BLACK);

		// Configuración del eje Y 
		NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
		yAxis.setLabel("Saves");
		yAxis.setTickLabelFont(Font.font(20));
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(100);
		yAxis.setTickUnit(10);
		yAxis.setMinorTickCount(4);
		yAxis.setMinorTickLength(5);
		yAxis.setTickMarkVisible(true);
		yAxis.setTickLabelFont(Font.font(20));
		yAxis.setTickLabelFill(Color.BLACK);

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
	/**
	 * Maneja el clic sobre el texto "Paradas".
	 * Este método no está incluido en tu código pero es recomendable añadir uno
	 * similar a los de goles/asistencias/tarjetas para navegación.
	 *
	 * @param event el evento de clic del mouse
	 */
	public void handleSavesClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Paradas!");
		try {
			showSavesScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// ==================== INTERFAZ TOURNAMENT ====================
	/**
	 * Muestra la escena del torneo en el stage proporcionado.
	 * Carga la interfaz desde Tournament.fxml, actualiza elementos y muestra la tabla de clasificación.
	 *
	 * @param stage la ventana principal donde se mostrará la escena del torneo
	 * @throws IOException si ocurre un error al cargar el archivo FXML
	 */
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
	/**
	 * Maneja el evento del botón que accede a la interfaz de torneo.
	 * Llama al método que muestra la escena del torneo.
	 *
	 * @param stage la ventana actual donde se mostrará la escena
	 */
	private void handleTournamentButtonAction(Stage stage) {
		try {
			showTournamentScene(stage);  // Llama a la escena del torneo
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Maneja el clic en el texto "Clasificación".
	 * Carga y muestra la escena del torneo.
	 *
	 * @param event el evento de clic del mouse sobre el texto
	 */
	public void handleClasificationClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Clasificación!");
		try {
			showTournamentScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Error", "No se pudo cargar la clasificación");
		}
	}
	/**
	 * Configura las columnas de la tabla de clasificación.
	 * Asigna las propiedades de los equipos a las columnas correspondientes
	 * y carga los datos en la tabla.
	 *
	 * @param scene la escena que contiene la tabla de clasificación
	 */
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
	/**
	 * Carga los equipos del torneo asociados al equipo del usuario logueado
	 * y los muestra en la tabla de clasificación.
	 *
	 * @param tablaClasificacion la tabla donde se mostrarán los datos de clasificación
	 */
	private void cargarClasificacionEnTabla(TableView<Team> tablaClasificacion) {
		try {
			Team team = loggedInUser.getTeam();
			List<Team> clasificacion = tournamentService.findTeamsByTeamTournaments(team.getId()); // Si tienes esto
			ObservableList<Team> datos = FXCollections.observableArrayList(clasificacion);
			tablaClasificacion.setItems(datos);
		} catch (Exception e) {
			logger.error("Error al cargar la clasificación", e);
			showAlert("Error", "No se pudo cargar la clasificación");
		}
	}


	// ==================== INTERFAZ RESULTS ====================
	/**
	 * Muestra la escena de resultados en el stage proporcionado.
	 * Carga la interfaz desde Results.fxml, actualiza datos del coach,
	 * y muestra los partidos del equipo logueado o todos si no hay equipo asignado.
	 *
	 * @param stage la ventana donde se mostrará la escena de resultados
	 * @throws IOException si ocurre un error al cargar el archivo FXML
	 */
	public void showResultsScene(Stage stage) throws IOException {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Results.fxml"));
	    var resultsScene = new Scene(fxmlLoader.load());
	    stage.setScene(resultsScene);


	    // Actualizar el nombre del coach
	    updateCoachNameLabel(resultsScene);

	    // Usar el controlador para ver los resultados pasandole el ID del equipo logueado
		try{
			int idEquipo = obtenerIdEquipo();
			resultsController.setupResultsView(resultsScene,idEquipo);
		}catch (NullPointerException e){
			//si no hay equipo mostrar todos los partidos
			resultsController.setupResultsView(resultsScene);
		}


	    // Asignar eventos de la interfaz
	    setNavigationClickListeners(resultsScene);
	    setMenuClickListener(resultsScene);
	    setOutClickListener(resultsScene);

	    stage.setTitle("Resultados");
	    stage.show();
	}


	// ==================== INTERFAZ TEAMS ====================
	/**
	 * Muestra la escena de equipos en el stage proporcionado.
	 * Carga Teams.fxml, actualiza el nombre del coach, configura la tabla de equipos
	 * y asigna eventos de navegación.
	 *
	 * @param stage la ventana donde se mostrará la escena de equipos
	 * @throws IOException si ocurre un error al cargar el archivo FXML
	 */
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
	/**
	 * Maneja el clic sobre el texto "Equipos".
	 * Llama al método que muestra la escena de equipos.
	 *
	 * @param event el evento de clic del mouse sobre el texto
	 */
	public void handleTeamsClick(MouseEvent event) {
		System.out.println("¡Se hizo clic en Equipos!");
		try {
			showTeamsScene((Stage) ((Text) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Error", "No se pudo cargar los equipos");
		}
	}

	/**
	 * Configura la tabla de equipos para mostrar el nombre de cada equipo.
	 * Asigna las propiedades necesarias y llama a la carga de datos.
	 *
	 * @param tablaEquipos la tabla donde se configurarán las columnas
	 */
	private void configurarTablaEquipos(TableView<Team> tablaEquipos) {
		// Configurar la columna de la tabla
		TableColumn<Team, String> colEquipo = (TableColumn<Team, String>) tablaEquipos.getColumns().get(0);
		colEquipo.setCellValueFactory(new PropertyValueFactory<>("name"));

		// Cargar los equipos desde el servicio
		cargarEquiposEnTabla(tablaEquipos);
	}

	/**
	 * Carga los equipos del torneo asociado al equipo del usuario logueado
	 * y los muestra en la tabla de equipos.
	 *
	 * @param tablaEquipos la tabla donde se mostrarán los equipos
	 */
	private void cargarEquiposEnTabla(TableView<Team> tablaEquipos) {
		try {
			Team team = loggedInUser.getTeam();
			List<Team> equipos = teamServ.findTeamsByTeamTournaments(team.getId());
			ObservableList<Team> equiposObservable = FXCollections.observableArrayList(equipos);
			tablaEquipos.setItems(equiposObservable);
		} catch (Exception e) {
			logger.error("Error al cargar equipos", e);
			showAlert("Error", "No se pudieron cargar los equipos");
		}
	}




	/**
	 * Actualiza la etiqueta con el nombre del usuario (coach) logueado en la escena actual.
	 *
	 * @param scene la escena en la que se actualizará la etiqueta con el nombre del coach
	 */
	private void updateCoachNameLabel(Scene scene) {
		if (scene == null || loggedInUser == null) return;

		Label nombreCoachLabel = (Label) scene.lookup("#nombre_coach");
		if (nombreCoachLabel != null) {
			nombreCoachLabel.setText(loggedInUser.getUserName()); // O usa getUsername() según tu modelo User
		}
	}
	/**
	 * Establece los eventos de navegación para los distintos textos de la interfaz (clasificación, resultados, equipos, etc.).
	 *
	 * @param scene la escena en la que se asociarán los eventos de navegación
	 */
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
	/**
	 * Maneja el clic en el texto "Resultados" y muestra la escena correspondiente.
	 *
	 * @param event el evento de clic sobre el texto "Resultados"
	 */
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
	/**
	 * Establece los listeners para los iconos del menú, calendario y vídeos.
	 *
	 * @param scene la escena en la que se buscarán los ImageView para asignar los eventos
	 */
	public void setMenuClickListener(Scene scene) {
		setImageClickListener(scene, "#img_menu", this::handleImgMenuClick);
		setImageClickListener(scene, "#img_calendar", this::handleImgCalendarClick);
		setImageClickListener(scene, "#img_films", this::handleImgFilmsClick);
	}
	/**
	 * Maneja el clic sobre el icono de vídeos y navega a la escena correspondiente.
	 *
	 * @param event el evento de clic sobre el icono de vídeos
	 */
	public void handleImgFilmsClick(MouseEvent event) {
		try {
			showVideosScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Error", "No se pudo cargar la pantalla de videos");
		}
	}
	/**
	 * Maneja el clic sobre el icono del menú y muestra la pantalla del menú.
	 *
	 * @param event el evento de clic sobre el icono del menú
	 */
	public void handleImgMenuClick(MouseEvent event) {
		try {
			showMenu((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Maneja el clic sobre el icono del calendario y muestra la pantalla del calendario.
	 *
	 * @param event el evento de clic sobre el icono del calendario
	 */
	public void handleImgCalendarClick(MouseEvent event) {
		try {
			showCalendarScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Asigna un `EventHandler` de clic a una `ImageView` de la escena especificada por su fx:id.
	 *
	 * @param scene la escena en la que se buscará la `ImageView`
	 * @param fxId el identificador del nodo (por ejemplo, "#img_menu")
	 * @param handler el manejador de eventos que se asignará al clic
	 */
	public void setImageClickListener(Scene scene, String fxId, EventHandler<MouseEvent> handler) {
		ImageView imageView = (ImageView) scene.lookup(fxId);
		if (imageView != null) {
			imageView.setOnMouseClicked(handler);
		}
	}
	/**
	 * Asigna el evento de cerrar sesión al icono correspondiente.
	 *
	 * @param scene la escena en la que se encuentra el icono de cerrar sesión
	 */
	public void setOutClickListener(Scene scene) {
		ImageView imgOut = (ImageView) scene.lookup("#img_out");
		if (imgOut != null) {
			imgOut.setOnMouseClicked(this::handleImgOutClick);
		}
	}
	/**
	 * Maneja el clic sobre el icono de cerrar sesión.
	 * Reinicia variables globales, limpia selecciones de partido y vuelve a la pantalla de login.
	 *
	 * @param event el evento de clic sobre el icono de salir
	 */
	public void handleImgOutClick(MouseEvent event) {
		try {
			//reniniciar variables
			inputUserName = null; // Reiniciar la variable username
			inputPassword = null; // Reiniciar la variable username

			//limpiar selecciones de MATCH
			selectedGk = null;
			selectedDf = null;
			selectedPiv = null;
			selectedLw = null;
			selectedRw = null;

			showLoginScene((Stage) ((ImageView) event.getSource()).getScene().getWindow());  // Volver a la pantalla de login
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Muestra una ventana emergente de alerta con un mensaje y un título especificado.
	 *
	 * @param title el título de la alerta
	 * @param message el mensaje que se mostrará en la alerta
	 */
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * Cierra el contexto de la aplicación al detenerla.
	 * Método sobreescrito del ciclo de vida de JavaFX.
	 */
	@Override
	public void stop() {
		if (context != null) {
			context.close();
		}
	}
}
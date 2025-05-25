package com.wiki;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory; // Importar TextField
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador para manejar las vistas y acciones relacionadas con los hábitats.
 * Este controlador incluye métodos para cargar datos, filtrar, cambiar vistas y editar hábitats.
 */
public class habitatController {

    @FXML
    private TableView<Habitat> tableView; // Tabla para mostrar los hábitats

    @FXML
    private TableColumn<Habitat, Integer> colIdHabitat; // Columna para el ID del hábitat

    @FXML
    private TableColumn<Habitat, String> colNombre; // Columna para el nombre del hábitat

    @FXML
    private TextField searchField; // Campo de búsqueda para filtrar hábitats

    private final ObservableList<Habitat> habitatList = FXCollections.observableArrayList(); // Lista observable para los hábitats visibles en la tabla
    private ObservableList<Habitat> allHabitats = FXCollections.observableArrayList(); // Lista para todos los hábitats (sin filtrar)

    /**
     * Constructor por defecto requerido por JavaFX.
     */
    public habitatController() {
        // Constructor vacío
    }

    /**
     * Método de inicialización que se ejecuta automáticamente al cargar la vista.
     * Configura las columnas de la tabla, carga los datos y agrega eventos.
     */
    @FXML
    private void initialize() {
        // Configurar las columnas de la tabla
        colIdHabitat.setCellValueFactory(new PropertyValueFactory<>("idHabitat"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableView.setItems(habitatList);

        // Listener para el campo de búsqueda
        if (searchField != null) { // Asegurarse de que el searchField esté inicializado
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrarHabitats(newValue);
            });
        }

        // Cargar los datos inicialmente
        loadData();

        // Agregar un evento de doble clic en la tabla para editar un hábitat
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Doble clic
                Habitat selectedHabitat = tableView.getSelectionModel().getSelectedItem();
                if (selectedHabitat != null) {
                    editHabitat(selectedHabitat);
                }
            }
        });
    }

    /**
     * Carga los datos de los hábitats desde la base de datos y los agrega a las listas observables.
     */
    private void loadData() {
        allHabitats.clear(); // Limpiar la lista de todos los hábitats antes de cargar
        Habitat.getAll(allHabitats); // Cargar todos los hábitats desde la base de datos

        // Aplicar el filtro actual después de recargar todos los datos
        filtrarHabitats(searchField != null ? searchField.getText() : "");
    }

    /**
     * Abre el formulario para agregar un nuevo hábitat.
     */
    @FXML
    private void openNewHabitatForm() {
        editHabitat(null); // Pasa null para indicar que se está creando un nuevo hábitat
    }

    /**
     * Abre el formulario para editar un hábitat existente o agregar uno nuevo.
     *
     * @param habitat El hábitat que se va a editar. Si es null, se crea un nuevo hábitat.
     */
    private void editHabitat(Habitat habitat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editar_habitat.fxml"));
            Parent root = loader.load();

            editarHabitatController controller = loader.getController();
            controller.setHabitat(habitat);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear interacción con otras ventanas
            stage.setTitle(habitat == null ? "Agregar Habitat" : "Editar Habitat");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Esperar a que se cierre la ventana de edición

            loadData(); // Recargar la tabla después de editar/agregar
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filtra los hábitats en función del texto ingresado en el campo de búsqueda.
     *
     * @param filtro El texto ingresado para filtrar los hábitats.
     */
    private void filtrarHabitats(String filtro) {
        habitatList.clear(); // Limpiar la lista mostrada en la tabla
        if (filtro == null || filtro.isEmpty()) {
            habitatList.addAll(allHabitats); // Mostrar todos si el filtro está vacío
        } else {
            String filtroLowerCase = filtro.toLowerCase();
            for (Habitat habitat : allHabitats) {
                if (habitat.getNombre().toLowerCase().contains(filtroLowerCase)) {
                    habitatList.add(habitat);
                }
            }
        }
    }

    /**
     * Cambia la vista actual a la vista de bestiario.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaBestiario() throws IOException {
        App.setRoot("principal_monstruos");
    }

    /**
     * Cambia la vista actual a la vista de debilidades.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaDebilidades() throws IOException {
        App.setRoot("principal_debilidades");
    }

    /**
     * Cambia la vista actual a la vista de hábitats.
     * Este método es redundante ya que ya estás en esta vista, pero se incluye por consistencia.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaHabitat() throws IOException {
        App.setRoot("principal_habitat");
    }

    /**
     * Cambia la vista actual a la vista de armas.
     * Actualmente solo imprime un mensaje en la consola.
     */
    @FXML
    private void cambiarVistaArmas() {
        System.out.println("Cambiando a la vista de armas");
    }

    /**
     * Cambia la vista actual a la vista de información.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaInfo() throws IOException {
        App.setRoot("principal_info");
    }
}
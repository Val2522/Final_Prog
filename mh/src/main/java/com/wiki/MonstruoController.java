package com.wiki;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controlador para manejar las vistas y acciones relacionadas con los monstruos.
 * Este controlador incluye métodos para cargar datos, filtrar, cambiar vistas y editar monstruos.
 */
public class MonstruoController {

    @FXML
    private TextField searchField; // Campo de búsqueda para filtrar monstruos

    @FXML
    private TableView<Monstruo> tableView; // Tabla para mostrar los monstruos

    @FXML
    private TableColumn<Monstruo, Integer> colIdMonstruo; // Columna para el ID del monstruo

    @FXML
    private TableColumn<Monstruo, String> colNombre; // Columna para el nombre del monstruo

    @FXML
    private TableColumn<Monstruo, String> colTamaño; // Columna para el tamaño del monstruo

    @FXML
    private TableColumn<Monstruo, String> colHabitat; // Columna para el hábitat del monstruo

    @FXML
    private TableColumn<Monstruo, String> colTipo; // Columna para el tipo del monstruo

    private final ObservableList<Monstruo> monstruoList = FXCollections.observableArrayList(); // Lista observable para los monstruos visibles en la tabla
    private ObservableList<Monstruo> allMonstruos = FXCollections.observableArrayList(); // Lista para todos los monstruos (sin filtrar)

    /**
     * Constructor por defecto requerido por JavaFX.
     */
    public MonstruoController() {
        // Constructor vacío
    }

    /**
     * Método de inicialización que se ejecuta automáticamente al cargar la vista.
     * Configura las columnas de la tabla, carga los datos y agrega eventos.
     */
    @FXML
    private void initialize() {
        // Configurar las columnas del TableView
        colIdMonstruo.setCellValueFactory(new PropertyValueFactory<>("idMonstruo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTamaño.setCellValueFactory(new PropertyValueFactory<>("tamaño"));
        colHabitat.setCellValueFactory(new PropertyValueFactory<>("habitat"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("nombreTipo"));

        // Agregar un evento para manejar la selección de filas
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Doble clic en una fila
                Monstruo seleccionado = tableView.getSelectionModel().getSelectedItem();
                if (seleccionado != null) {
                    abrirFormularioEdicion(seleccionado);
                }
            }
        });

        // Listener para el TextField de búsqueda
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarMonstruos(newValue);
        });

        tableView.setItems(monstruoList);

        // Cargar los datos desde la base de datos
        loadData();
    }

    /**
     * Carga los datos de los monstruos desde la base de datos y los agrega a las listas observables.
     */
    private void loadData() {
        Monstruo.getAll(allMonstruos); // Cargar todos los monstruos en allMonstruos
        monstruoList.addAll(allMonstruos); // Mostrar todos los monstruos inicialmente
    }

    /**
     * Abre el formulario para editar un monstruo existente o agregar uno nuevo.
     *
     * @param monstruo El monstruo que se va a editar. Si es null, se crea un nuevo monstruo.
     */
    private void abrirFormularioEdicion(Monstruo monstruo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wiki/editar_monstruo.fxml")); // Ruta al archivo FXML
            Parent root = loader.load();

            editarMonstruoController controller = loader.getController();
            controller.setMainController(this); // Establecer la referencia al controlador principal
            controller.setMonstruo(monstruo);

            Stage stage = new Stage();
            stage.setTitle(monstruo.getIdMonstruo() > 0 ? "Editar Monstruo" : "Agregar Monstruo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo adicional de errores si es necesario
        }
    }

    /**
     * Actualiza la lista de monstruos en la tabla.
     * Recarga los datos desde la base de datos.
     */
    public void refreshMonstruoList() {
        allMonstruos.clear();
        Monstruo.getAll(allMonstruos);
        monstruoList.setAll(allMonstruos); // Actualizar el TableView
    }

    /**
     * Guarda los cambios realizados en un monstruo en la base de datos.
     *
     * @param monstruo El monstruo que se va a guardar.
     * @throws SQLException Si ocurre un error al guardar los datos.
     */
    public void save(Monstruo monstruo) throws SQLException {
        Connection conn = connectionBD.getInstance().getConnection();
        String sql = "UPDATE Monstruos SET nombre = ?, tamaño = ?, lore = ? WHERE id_monstruo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, monstruo.getNombre());
            stmt.setString(2, monstruo.getTamaño());
            stmt.setString(3, monstruo.getLore());
            stmt.setInt(4, monstruo.getIdMonstruo());
            stmt.executeUpdate();
        }
    }

    /**
     * Elimina un monstruo de la base de datos.
     *
     * @param monstruo El monstruo que se va a eliminar.
     * @throws SQLException Si ocurre un error al eliminar los datos.
     */
    public void delete(Monstruo monstruo) throws SQLException {
        Connection conn = connectionBD.getInstance().getConnection();
        String sql = "DELETE FROM Monstruos WHERE id_monstruo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, monstruo.getIdMonstruo());
            stmt.executeUpdate();
        }
    }

    /**
     * Filtra los monstruos en función del texto ingresado en el campo de búsqueda.
     *
     * @param filtro El texto ingresado para filtrar los monstruos.
     */
    private void filtrarMonstruos(String filtro) {
        monstruoList.clear();
        if (filtro == null || filtro.isEmpty()) {
            monstruoList.addAll(allMonstruos); // Mostrar todos si el filtro está vacío
        } else {
            String filtroLowerCase = filtro.toLowerCase();
            for (Monstruo monstruo : allMonstruos) {
                if (monstruo.getNombre().toLowerCase().contains(filtroLowerCase) ||
                        monstruo.getNombreTipo().toLowerCase().contains(filtroLowerCase) ||
                        monstruo.getTamaño().toLowerCase().contains(filtroLowerCase) ||
                        monstruo.getHabitat().toLowerCase().contains(filtroLowerCase)) {
                    monstruoList.add(monstruo);
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
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaHabitat() throws IOException {
        App.setRoot("principal_habitat");
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

    /**
     * Cambia la vista actual a la vista de armas.
     * Actualmente solo imprime un mensaje en la consola.
     */
    @FXML
    private void cambiarVistaArmas() {
        System.out.println("Vista de armas cambiada");
    }

    /**
     * Obtiene el ID del tipo basado en el nombre del tipo.
     *
     * @param nombreTipo El nombre del tipo.
     * @return El ID del tipo, o -1 si no se encuentra.
     */
    public int obtenerTipoIdDesdeNombre(String nombreTipo) {
        int tipoId = -1; // Valor por defecto si no se encuentra
        String query = "SELECT id_tipo FROM Tipos WHERE nombre = ?";
        try (Connection conn = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreTipo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tipoId = rs.getInt("id_tipo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoId;
    }
}
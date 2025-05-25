package com.wiki;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class MonstruoController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Monstruo> tableView;

    @FXML
    private TableColumn<Monstruo, Integer> colIdMonstruo;

    @FXML
    private TableColumn<Monstruo, String> colNombre;

    @FXML
    private TableColumn<Monstruo, String> colTamaño;

    @FXML
    private TableColumn<Monstruo, String> colHabitat;

    @FXML
    private TableColumn<Monstruo, String> colTipo;

    private final ObservableList<Monstruo> monstruoList = FXCollections.observableArrayList();

    public MonstruoController() {
        // Constructor por defecto requerido por JavaFX
    }

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

        tableView.setItems(monstruoList);

        // Cargar los datos desde la base de datos
        loadData();
    }

    private void loadData() {
        Monstruo.getAll(monstruoList);
    }

    private void abrirFormularioEdicion(Monstruo monstruo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wiki/editar_monstruo.fxml")); // Correct path (most likely)
            Parent root = loader.load();
    
            editarMonstruoController controller = loader.getController();
            controller.setMonstruo(monstruo);
    
            Stage stage = new Stage();
            stage.setTitle("Editar Monstruo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Add more error handling here if needed (e.g., a dialog to inform the user)
        }
    }

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

    public void delete(Monstruo monstruo) throws SQLException {
        Connection conn = connectionBD.getInstance().getConnection();
        String sql = "DELETE FROM Monstruos WHERE id_monstruo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, monstruo.getIdMonstruo());
            stmt.executeUpdate();
        }
    }

    @FXML
    private void cambiarVistaBestiario() throws IOException {
        App.setRoot("principal_monstruos");
    }

    @FXML
    private void cambiarVistaDebilidades() throws IOException {
        App.setRoot("principal_debilidades");
    }

    @FXML
    private void cambiarVistaHabitat() throws IOException {
        App.setRoot("principal_habitat");
    }

    @FXML
    private void cambiarVistaInfo() throws IOException {
        App.setRoot("principal_info");
    }

    @FXML
    private void cambiarVistaArmas() {
        System.out.println("Vista de armas cambiada");
    }
}
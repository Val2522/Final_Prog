package com.wiki;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MonstruoController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Monstruo> tableView;

    @FXML
    private TableColumn<Monstruo, Integer> colId_monstruo;

    @FXML
    private TableColumn<Monstruo, String> colNombre;

    @FXML
    private TableColumn<Monstruo, String> colTamaño;

    @FXML
    private TableColumn<Monstruo, String> colHabitat;

    @FXML
    private TableColumn<Monstruo, String> colTipo;

    private final ObservableList<Monstruo> monstruosList = FXCollections.observableArrayList();

    public MonstruoController() {
        // Constructor por defecto requerido por JavaFX
    }

    @FXML
    private void initialize() {

        colIdMonstruo.setCellValueFactory(new PropertyValueFactory<>("idMonstruo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTamaño.setCellValueFactory(new PropertyValueFactory<>("tamaño"));
        colHabitat.setCellValueFactory(new PropertyValueFactory<>("habitat")); 
        colTipo.setCellValueFactory(new PropertyValueFactory<>("nombreTipo"));
    
        // Cargar los datos desde la base de datos
        cargarDatos();
    }

    // Método para cargar datos desde la base de datos y asignarlos al TableView
    private void cargarDatos() {
        try {
            Connection conn = connectionBD.getInstance().getConnection();
            List<Monstruo> lista = cargarMonstruos(conn);
            monstruos = FXCollections.observableArrayList(lista);
            tableView.setItems(monstruos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cargar datos desde la base de datos
    private List<Monstruo> cargarMonstruos(Connection conn) {
        List<Monstruo> lista = new ArrayList<>();
        String sql = "SELECT m.id_monstruo, m.nombre, m.tamaño, m.lore, t.nombre AS tipo " +
                     "FROM Monstruos m " +
                     "INNER JOIN Tipos t ON m.id_tipo = t.id_tipo";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_monstruo");
                String nombre = rs.getString("nombre");
                String tamaño = rs.getString("tamaño");
                String lore = rs.getString("lore");
                String tipo = rs.getString("tipo");

                lista.add(new Monstruo(id, nombre, "", 0, tamaño, lore));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Guardar cambios en la base de datos
    public void save(Monstruo monstruo) throws SQLException {
        Connection conn = connectionBD.getInstance().getConnection();
        String sql = "UPDATE Monstruos SET nombre = ?, tamaño = ?, lore = ? WHERE id_monstruo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, monstruo.getNombre());
            stmt.setString(2, monstruo.getTamaño());
            stmt.setString(3, monstruo.getLore());
            stmt.setInt(4, monstruo.getId_monstruo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un monstruo de la base de datos
    public void delete(Monstruo monstruo) throws SQLException {
        Connection conn = connectionBD.getInstance().getConnection();
        String sql = "DELETE FROM Monstruos WHERE id_monstruo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, monstruo.getId_monstruo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     @FXML
    private void cambiarVistaBestiario() throws IOException, SQLException {
        App.setRoot("principal_monstruos");
        Connection dbInstance = connectionBD.getInstance().getConnection();
        // Use dbInstance for some database operation or remove it if not needed
        if (dbInstance != null) {
            System.out.println("Database connection established.");
        }
    }

    @FXML
    private void cambiarVistaDebilidades() throws IOException, SQLException {
        App.setRoot("principal_debilidades");
        Connection dbInstance = connectionBD.getInstance().getConnection();
        // Use dbInstance for some database operation or remove it if not needed
        if (dbInstance != null) {
            System.out.println("Database connection established.");
        }
    }

    @FXML
    private void cambiarVistaHabitat() throws IOException, SQLException {
        App.setRoot("principal_habitat");
        Connection dbInstance = connectionBD.getInstance().getConnection();
        // Use dbInstance for some database operation or remove it if not needed
        if (dbInstance != null) {
            System.out.println("Database connection established.");
        }
    }

    @FXML
    private void cambiarVistaInfo() throws IOException, SQLException {
        App.setRoot("principal_info");
        Connection dbInstance = connectionBD.getInstance().getConnection();
        // Use dbInstance for some database operation or remove it if not needed
        if (dbInstance != null) {
            System.out.println("Database connection established.");
        }
        initialize(); 
    }

    @FXML
    private void cambiarVistaArmas() {
        System.out.println("Vista de armas cambiada");
        // Aquí puedes agregar la lógica para cambiar la vista o realizar alguna acción
    }
}

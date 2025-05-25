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

public class debilidadController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<DebilidadMonstruo> tableView;

    @FXML
    private TableColumn<DebilidadMonstruo, String> colMonstruo;

    @FXML
    private TableColumn<DebilidadMonstruo, String> colElemento;

    @FXML
    private TableColumn<DebilidadMonstruo, Integer> colIntensidad;

    private final ObservableList<DebilidadMonstruo> debilidadList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colMonstruo.setCellValueFactory(new PropertyValueFactory<>("nombreMonstruo"));
        colElemento.setCellValueFactory(new PropertyValueFactory<>("elemento"));
        colIntensidad.setCellValueFactory(new PropertyValueFactory<>("intensidad"));

        tableView.setItems(debilidadList);

        loadData();

        // Listener para la selección de la tabla (para editar)
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    abrirVentanaEditarDebilidad(newSelection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData() {
        debilidadList.clear();
        String query = "SELECT m.nombre AS nombreMonstruo, d.elemento, md.intensidad " +
                       "FROM Monstruos m " +
                       "JOIN Monstruos_Debilidades md ON m.id_monstruo = md.id_monstruo " +
                       "JOIN Debilidades d ON md.id_debilidad = d.id_debilidad";

        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DebilidadMonstruo dm = new DebilidadMonstruo(
                    rs.getString("nombreMonstruo"),
                    rs.getString("elemento"),
                    rs.getInt("intensidad")
                );
                debilidadList.add(dm);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Clase auxiliar para representar la combinación de Debilidad y Monstruo
    public static class DebilidadMonstruo {
        private String nombreMonstruo;
        private String elemento;
        private int intensidad;

        public DebilidadMonstruo(String nombreMonstruo, String elemento, int intensidad) {
            this.nombreMonstruo = nombreMonstruo;
            this.elemento = elemento;
            this.intensidad = intensidad;
        }

        public String getNombreMonstruo() { return nombreMonstruo; }
        public String getElemento() { return elemento; }
        public int getIntensidad() { return intensidad; }

        public void setNombreMonstruo(String nombreMonstruo) { this.nombreMonstruo = nombreMonstruo; }
        public void setElemento(String elemento) { this.elemento = elemento; }
        public void setIntensidad(int intensidad) { this.intensidad = intensidad; }
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

    private void abrirVentanaEditarDebilidad(DebilidadMonstruo debilidadMonstruo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editar_debilidad.fxml"));
        Parent root = loader.load();
        editarDebilidadController controller = loader.getController();
    
        //  Obtener la Debilidad existente de la base de datos
        Debilidad debilidad = Debilidad.getDebilidadPorElemento(debilidadMonstruo.getElemento());
    
        //  Verificar si se encontró la debilidad (manejar el caso de que no exista)
        if (debilidad == null) {
            System.err.println("Error: No se encontró la debilidad en la base de datos.");
            //  Opcional: Mostrar un mensaje de error al usuario
            return;
        }
    
        controller.setDebilidad(debilidad);
    
        Stage stage = new Stage();
        stage.setTitle("Editar Debilidad");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    
        // Recargar los datos después de editar
        loadData();
    }
}
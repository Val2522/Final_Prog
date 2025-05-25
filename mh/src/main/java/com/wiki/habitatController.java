package com.wiki;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class habitatController {

    @FXML
    private TableView<Habitat> tableView;

    @FXML
    private TableColumn<Habitat, Integer> colIdHabitat;

    @FXML
    private TableColumn<Habitat, String> colNombre;

    private final ObservableList<Habitat> habitatList = FXCollections.observableArrayList();

    public habitatController() {
        // Required empty constructor
    }

    @FXML
    private void initialize() {
        colIdHabitat.setCellValueFactory(new PropertyValueFactory<>("idHabitat"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableView.setItems(habitatList);
        loadData();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Habitat selectedHabitat = tableView.getSelectionModel().getSelectedItem();
                if (selectedHabitat != null) {
                    editHabitat(selectedHabitat);
                }
            }
        });
    }

    private void loadData() {
        Habitat.getAll(habitatList);
    }

    @FXML
    private void openNewHabitatForm() {
        editHabitat(null); // Pass null to indicate "new"
    }

    private void editHabitat(Habitat habitat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editar_habitat.fxml"));
            Parent root = loader.load();

            editarHabitatController controller = loader.getController();
            controller.setHabitat(habitat);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            stage.setTitle(habitat == null ? "Agregar Habitat" : "Editar Habitat");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Wait for the edit window to close

            loadData(); // Refresh the table after editing

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(Habitat habitat) throws SQLException {
        habitat.save();
        loadData();
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
        // Ya estás en esta vista, pero es bueno tener el método por consistencia
        App.setRoot("principal_habitat");
    }

    @FXML
    private void cambiarVistaArmas() {
        // Lógica para cambiar a la vista de armas
        System.out.println("Cambiando a la vista de armas");
    }

    @FXML
    private void cambiarVistaInfo() throws IOException {
        App.setRoot("principal_info");
    }
}
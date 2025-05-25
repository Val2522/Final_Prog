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
    }

    private void loadData() {
        Habitat.getAll(habitatList);
    }

    private void openEditForm(Habitat habitat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editar_habitat.fxml"));
            Parent root = loader.load();

            editarHabitatController controller = loader.getController();
            controller.setHabitat(habitat);

            Stage stage = new Stage();
            stage.setTitle("Edit Habitat");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error opening edit form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void save(Habitat habitat) throws SQLException {
        habitat.save();
        loadData();
    }

    @FXML
    private void switchToDebilidadView() {
        try {
            App.setRoot("principal_debilidades");
        } catch (IOException e) {
            System.err.println("Error switching to debilidad view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToHabitatView() {
        try {
            App.setRoot("principal_habitat");
        } catch (IOException e) {
            System.err.println("Error switching to habitat view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToInfoView() {
        try {
            App.setRoot("principal_info");
        } catch (IOException e) {
            System.err.println("Error switching to info view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRowDoubleClick() {
        Habitat selectedHabitat = tableView.getSelectionModel().getSelectedItem();
        if (selectedHabitat != null) {
            openEditForm(selectedHabitat);
        }
    }
}
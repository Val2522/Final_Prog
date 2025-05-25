package com.wiki;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editarHabitatController {

    @FXML
    private TextField txtNombre;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private Habitat habitat;

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
        updateFields();
    }

    private void updateFields() {
        if (habitat != null) {
            txtNombre.setText(habitat.getNombre());
        } else {
            txtNombre.clear();
        }
    }

    @FXML
    private void guardarCambios() {
        if (habitat == null) {
            habitat = new Habitat(0, txtNombre.getText()); // Create new Habitat
        } else {
            habitat.setNombre(txtNombre.getText()); // Update existing
        }
        habitat.save();
        closeStage();
    }

    @FXML
    private void cancelar() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    private void handleNuevoHabitat(ActionEvent event) {
        habitat = null; // Reset for new entry
        txtNombre.clear();
    }
}
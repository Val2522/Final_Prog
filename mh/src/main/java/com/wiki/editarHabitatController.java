package com.wiki;

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
        if (habitat != null) {
            txtNombre.setText(habitat.getNombre());
        }
    }

    @FXML
    private void guardarCambios() {
        if (habitat != null) {
            habitat.setNombre(txtNombre.getText());
            habitat.save();
        }
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
}
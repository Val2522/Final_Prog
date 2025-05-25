// editarDebilidadController.java
package com.wiki;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editarDebilidadController {

    @FXML
    private TextField txtElemento;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private Debilidad debilidad;

    public void setDebilidad(Debilidad debilidad) {
        System.out.println("setDebilidad llamado con: " + debilidad); // Añade esto
        this.debilidad = debilidad;
        if (debilidad != null) {
            txtElemento.setText(debilidad.getElemento());
        }
    }

    @FXML
    private void guardarCambios() {
        if (debilidad != null) {
            try {
                debilidad.setElemento(txtElemento.getText());
                debilidad.save();
            } catch (Exception e) {
                e.printStackTrace();
                // O muestra un mensaje de error al usuario
                return; // Importante: salir del método si hay un error
            }
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
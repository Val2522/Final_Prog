package com.wiki;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
            habitat = new Habitat(0, txtNombre.getText());
        } else {
            habitat.setNombre(txtNombre.getText());
        }

        int filasAfectadas = habitat.save();
        if (filasAfectadas > 0) {
            mostrarMensaje("Cambios guardados correctamente.");
            closeStage();
        } else {
            mostrarError("No se pudieron guardar los cambios.");
        }
    }

    @FXML
    private void añadirHabitat() {
        String nombreHabitat = txtNombre.getText().trim();

        if (nombreHabitat.isEmpty()) {
            mostrarError("El campo 'Nombre' no puede estar vacío.");
            return;
        }

        Habitat nuevoHabitat = new Habitat(0, nombreHabitat);
        int filasAfectadas = nuevoHabitat.save();

        if (filasAfectadas > 0) {
            mostrarMensaje("Hábitat añadido correctamente.");
            closeStage();
        } else {
            mostrarError("No se pudo añadir el hábitat.");
        }
    }

    @FXML
    private void eliminarHabitat() {
        if (habitat == null || habitat.getIdHabitat() <= 0) {
            mostrarError("No hay un hábitat seleccionado para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar este hábitat?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            int filasAfectadas = habitat.delete();

            if (filasAfectadas > 0) {
                mostrarMensaje("Hábitat eliminado correctamente.");
                closeStage();
            } else {
                mostrarError("No se pudo eliminar el hábitat.");
            }
        }
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

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
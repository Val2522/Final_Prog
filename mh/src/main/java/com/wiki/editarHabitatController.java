package com.wiki;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para manejar la edición de un hábitat.
 * Este controlador permite cargar, modificar, añadir y eliminar hábitats.
 */
public class editarHabitatController {

    @FXML
    private TextField txtNombre; // Campo de texto para el nombre del hábitat

    @FXML
    private Button btnGuardar; // Botón para guardar los cambios

    @FXML
    private Button btnCancelar; // Botón para cancelar la edición

    private Habitat habitat; // Objeto Habitat que se está editando

    /**
     * Establece el hábitat que se va a editar.
     * Si el hábitat no es nulo, carga su información en el campo de texto.
     *
     * @param habitat El objeto Habitat que se va a editar.
     */
    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
        updateFields();
    }

    /**
     * Actualiza los campos del formulario con la información del hábitat.
     * Si no hay un hábitat seleccionado, limpia los campos.
     */
    private void updateFields() {
        if (habitat != null) {
            txtNombre.setText(habitat.getNombre());
        } else {
            txtNombre.clear();
        }
    }

    /**
     * Guarda los cambios realizados en el hábitat.
     * Si el hábitat no existe, lo crea. Si ya existe, lo actualiza.
     */
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

    /**
     * Añade un nuevo hábitat a la base de datos.
     * Valida que el campo de nombre no esté vacío antes de añadirlo.
     */
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

    /**
     * Elimina el hábitat seleccionado de la base de datos.
     * Muestra un cuadro de confirmación antes de eliminarlo.
     */
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

    /**
     * Cancela la edición y cierra la ventana.
     */
    @FXML
    private void cancelar() {
        closeStage();
    }

    /**
     * Cierra la ventana actual.
     */
    private void closeStage() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     *
     * @param mensaje El mensaje de error que se mostrará.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de información en un cuadro de diálogo.
     *
     * @param mensaje El mensaje de información que se mostrará.
     */
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
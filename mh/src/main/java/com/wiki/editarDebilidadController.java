package com.wiki;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para manejar la edición de una debilidad.
 * Este controlador permite cargar, modificar y guardar los datos de una debilidad.
 */
public class editarDebilidadController {

    @FXML
    private TextField txtElemento; // Campo de texto para el elemento de la debilidad

    @FXML
    private Button btnGuardar; // Botón para guardar los cambios

    @FXML
    private Button btnCancelar; // Botón para cancelar la edición

    private Debilidad debilidad; // Objeto Debilidad que se está editando

    /**
     * Establece la debilidad que se va a editar.
     * Si la debilidad no es nula, carga su información en el campo de texto.
     *
     * @param debilidad El objeto Debilidad que se va a editar.
     */
    public void setDebilidad(Debilidad debilidad) {
        System.out.println("setDebilidad llamado con: " + debilidad); // Mensaje para depuración
        this.debilidad = debilidad;
        if (debilidad != null) {
            txtElemento.setText(debilidad.getElemento()); // Cargar el elemento en el campo de texto
        }
    }

    /**
     * Guarda los cambios realizados en la debilidad.
     * Si ocurre un error, se imprime en la consola y no se guarda la información.
     */
    @FXML
    private void guardarCambios() {
        if (debilidad != null) {
            try {
                // Actualizar el elemento de la debilidad con el texto ingresado
                debilidad.setElemento(txtElemento.getText());
                // Guardar los cambios en la base de datos
                debilidad.save();
            } catch (Exception e) {
                e.printStackTrace(); // Imprimir el error en la consola
                return; // Salir del método si ocurre un error
            }
        }
        closeStage(); // Cerrar la ventana después de guardar
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
        Stage stage = (Stage) btnCancelar.getScene().getWindow(); // Obtener la ventana actual
        if (stage != null) {
            stage.close(); // Cerrar la ventana
        }
    }
}
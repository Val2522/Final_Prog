package com.wiki;

import javafx.fxml.FXML;

/**
 * Controlador para manejar la vista de información.
 * Este controlador incluye métodos para navegar de vuelta a la vista principal.
 */
public class infoController {

    /**
     * Cambia la vista actual a la vista principal.
     * Este método se ejecuta cuando el usuario solicita volver a la vista principal.
     */
    @FXML
    private void volverAVistaPrincipal() {
        try {
            App.setRoot("principal_monstruos"); // Cambia a la vista principal
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en la consola si ocurre una excepción
        }
    }
}
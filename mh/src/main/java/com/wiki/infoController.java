package com.wiki;

import javafx.fxml.FXML;

public class infoController {

    @FXML
    private void volverAVistaPrincipal() {
        try {
            App.setRoot("principal_monstruos"); // Cambia a la vista principal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
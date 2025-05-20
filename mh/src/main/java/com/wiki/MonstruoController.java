package com.wiki;

import java.io.IOException;

import javafx.fxml.FXML;

public class MonstruoController {
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}

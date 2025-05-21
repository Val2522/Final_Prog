package com.wiki;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene; // Declarar la escena como estática

    @Override
    public void start(Stage primaryStage) {
        try {
            scene = new Scene(loadFXML("principal_monstruos"));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Monster Hunter Wiki");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el archivo FXML: " + e.getMessage());
        }
    }



    // Método para cambiar la escena a partir de un archivo FXML
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Método para cargar el FXML
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

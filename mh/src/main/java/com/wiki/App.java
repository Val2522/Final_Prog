package com.wiki;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene; // Declarar la escena como estática

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Usa un nombre de archivo más descriptivo y correcto: "primary.fxml"
        scene = new Scene(loadFXML("primary")); // Carga el FXML inicial y asigna la escena
        primaryStage.setScene(scene);
        primaryStage.setTitle("Monster Hunter Wiki");
        showStage(primaryStage); // Llama al método para mostrar el escenario
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
    private void showStage(Stage primaryStage) {
        // Obtener las dimensiones de la pantalla principal
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Establecer la ventana en pantalla completa (sin bordes)
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // Mostrar la ventana
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

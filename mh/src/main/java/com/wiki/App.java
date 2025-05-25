package com.wiki;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación Monster Hunter Wiki.
 * Extiende la clase `Application` de JavaFX para inicializar y manejar la interfaz gráfica.
 */
public class App extends Application {

    // Escena principal de la aplicación, declarada como estática para facilitar el cambio de vistas.
    private static Scene scene;

    /**
     * Método principal de inicio de la aplicación.
     * Este método se ejecuta al iniciar la aplicación y carga la escena inicial.
     *
     * @param primaryStage La ventana principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar la escena inicial desde el archivo FXML "principal_monstruos.fxml"
            scene = new Scene(loadFXML("principal_monstruos"));
            primaryStage.setScene(scene); // Establecer la escena en la ventana principal
            primaryStage.setTitle("Monster Hunter Wiki"); // Título de la ventana
            primaryStage.show(); // Mostrar la ventana principal
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir el error en la consola
            System.err.println("Error al cargar el archivo FXML: " + e.getMessage());
        }
    }

    /**
     * Cambia la raíz de la escena actual a un nuevo archivo FXML.
     * Este método permite cambiar entre diferentes vistas de la aplicación.
     *
     * @param fxml El nombre del archivo FXML (sin extensión) que se desea cargar.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Carga un archivo FXML y devuelve su contenido como un nodo `Parent`.
     *
     * @param fxml El nombre del archivo FXML (sin extensión) que se desea cargar.
     * @return El nodo raíz del archivo FXML cargado.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Método principal de la aplicación.
     * Este método lanza la aplicación JavaFX.
     *
     * @param args Los argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
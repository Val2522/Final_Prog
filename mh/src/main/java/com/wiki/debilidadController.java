package com.wiki;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controlador para manejar las vistas y acciones relacionadas con las debilidades.
 * Este controlador incluye métodos para cargar datos, filtrar, cambiar vistas y editar debilidades.
 */
public class debilidadController {

    @FXML
    private TextField searchField; // Campo de búsqueda para filtrar debilidades

    @FXML
    private TableView<DebilidadMonstruo> tableView; // Tabla para mostrar las debilidades

    @FXML
    private TableColumn<DebilidadMonstruo, String> colMonstruo; // Columna para el nombre del monstruo

    @FXML
    private TableColumn<DebilidadMonstruo, String> colElemento; // Columna para el elemento de la debilidad

    @FXML
    private TableColumn<DebilidadMonstruo, Integer> colIntensidad; // Columna para la intensidad de la debilidad

    // Lista observable para las debilidades visibles en la tabla
    private final ObservableList<DebilidadMonstruo> debilidadList = FXCollections.observableArrayList();

    // Lista observable para todas las debilidades (sin filtrar)
    private final ObservableList<DebilidadMonstruo> allDebilidades = FXCollections.observableArrayList();

    /**
     * Método de inicialización que se ejecuta automáticamente al cargar la vista.
     * Configura las columnas de la tabla, carga los datos y agrega eventos.
     */
    @FXML
    private void initialize() {
        // Configurar las columnas de la tabla
        colMonstruo.setCellValueFactory(new PropertyValueFactory<>("nombreMonstruo"));
        colElemento.setCellValueFactory(new PropertyValueFactory<>("elemento"));
        colIntensidad.setCellValueFactory(new PropertyValueFactory<>("intensidad"));

        // Establecer la lista observable en la tabla
        tableView.setItems(debilidadList);

        // Cargar los datos iniciales
        loadData();

        // Agregar un listener al campo de búsqueda para filtrar los datos
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDebilidades(newValue);
        });

        // Agregar un evento de doble clic en la tabla para editar una debilidad
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Doble clic
                DebilidadMonstruo selectedDebilidad = tableView.getSelectionModel().getSelectedItem();
                if (selectedDebilidad != null) {
                    try {
                        abrirVentanaEditarDebilidad(selectedDebilidad);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Carga los datos de las debilidades desde la base de datos y los agrega a las listas observables.
     */
    private void loadData() {
        debilidadList.clear();
        allDebilidades.clear();
        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT m.nombre AS nombre_monstruo, d.elemento, md.intensidad " +
                             "FROM Monstruos m " +
                             "JOIN Monstruos_Debilidades md ON m.id_monstruo = md.id_monstruo " +
                             "JOIN Debilidades d ON md.id_debilidad = d.id_debilidad")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DebilidadMonstruo debilidadMonstruo = new DebilidadMonstruo(
                        rs.getString("nombre_monstruo"),
                        rs.getString("elemento"),
                        rs.getInt("intensidad")
                );
                debilidadList.add(debilidadMonstruo);
                allDebilidades.add(debilidadMonstruo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filtra las debilidades en función del texto ingresado en el campo de búsqueda.
     *
     * @param searchText El texto ingresado para filtrar las debilidades.
     */
    private void filterDebilidades(String searchText) {
        debilidadList.clear();
        if (searchText == null || searchText.isEmpty()) {
            debilidadList.addAll(allDebilidades);
        } else {
            String lowerCaseSearchText = searchText.toLowerCase();
            for (DebilidadMonstruo debilidadMonstruo : allDebilidades) {
                if (debilidadMonstruo.getNombreMonstruo().toLowerCase().contains(lowerCaseSearchText) ||
                        debilidadMonstruo.getElemento().toLowerCase().contains(lowerCaseSearchText)) {
                    debilidadList.add(debilidadMonstruo);
                }
            }
        }
    }

    /**
     * Clase interna para representar la relación entre una debilidad y un monstruo.
     */
    public static class DebilidadMonstruo {
        private String nombreMonstruo; // Nombre del monstruo
        private String elemento; // Elemento de la debilidad
        private int intensidad; // Intensidad de la debilidad

        /**
         * Constructor de la clase interna DebilidadMonstruo.
         *
         * @param nombreMonstruo El nombre del monstruo.
         * @param elemento El elemento de la debilidad.
         * @param intensidad La intensidad de la debilidad.
         */
        public DebilidadMonstruo(String nombreMonstruo, String elemento, int intensidad) {
            this.nombreMonstruo = nombreMonstruo;
            this.elemento = elemento;
            this.intensidad = intensidad;
        }

        public String getNombreMonstruo() {
            return nombreMonstruo;
        }

        public String getElemento() {
            return elemento;
        }

        public int getIntensidad() {
            return intensidad;
        }

        public void setNombreMonstruo(String nombreMonstruo) {
            this.nombreMonstruo = nombreMonstruo;
        }

        public void setElemento(String elemento) {
            this.elemento = elemento;
        }

        public void setIntensidad(int intensidad) {
            this.intensidad = intensidad;
        }
    }

    /**
     * Cambia la vista actual a la vista de bestiario.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaBestiario() throws IOException {
        App.setRoot("principal_monstruos");
    }

    /**
     * Cambia la vista actual a la vista de debilidades.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaDebilidades() throws IOException {
        App.setRoot("principal_debilidades");
    }

    /**
     * Cambia la vista actual a la vista de hábitats.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaHabitat() throws IOException {
        App.setRoot("principal_habitat");
    }

    /**
     * Cambia la vista actual a la vista de información.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void cambiarVistaInfo() throws IOException {
        App.setRoot("principal_info");
    }

    /**
     * Cambia la vista actual a la vista de armas.
     * Actualmente solo imprime un mensaje en la consola.
     */
    @FXML
    private void cambiarVistaArmas() {
        System.out.println("Vista de armas cambiada");
    }

    /**
     * Abre una ventana para editar una debilidad específica.
     *
     * @param debilidadMonstruo El objeto DebilidadMonstruo que contiene la información de la debilidad seleccionada.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    private void abrirVentanaEditarDebilidad(DebilidadMonstruo debilidadMonstruo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editar_debilidad.fxml"));
        Parent root = loader.load();
        editarDebilidadController controller = loader.getController();

        // Obtener la debilidad existente de la base de datos
        Debilidad debilidad = Debilidad.getDebilidadPorElemento(debilidadMonstruo.getElemento());

        // Verificar si se encontró la debilidad
        if (debilidad == null) {
            System.err.println("Error: No se encontró la debilidad en la base de datos.");
            // Opcional: Mostrar un mensaje de error al usuario
            return;
        }

        // Pasar la debilidad al controlador de la ventana de edición
        controller.setDebilidad(debilidad);

        // Configurar y mostrar la ventana de edición
        Stage stage = new Stage();
        stage.setTitle("Editar Debilidad");
        stage.setScene(new Scene(root));
        stage.showAndWait(); // Esperar a que se cierre la ventana

        // Recargar los datos después de editar
        loadData();
    }
}
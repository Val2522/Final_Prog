package com.wiki;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory; // Importar TextField
import javafx.stage.Modality;
import javafx.stage.Stage;

public class habitatController {

    @FXML
    private TableView<Habitat> tableView;

    @FXML
    private TableColumn<Habitat, Integer> colIdHabitat;

    @FXML
    private TableColumn<Habitat, String> colNombre;

    @FXML
    private TextField searchField; // Asegúrate de que este FXML ID exista en tu .fxml

    private final ObservableList<Habitat> habitatList = FXCollections.observableArrayList();
    private ObservableList<Habitat> allHabitats = FXCollections.observableArrayList(); // Lista para todos los hábitats

    public habitatController() {
        // Constructor por defecto requerido por JavaFX
    }

    @FXML
    private void initialize() {
        colIdHabitat.setCellValueFactory(new PropertyValueFactory<>("idHabitat"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableView.setItems(habitatList);
        
        // Listener para el TextField de búsqueda
        if (searchField != null) { // Asegurarse de que el searchField esté inicializado
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrarHabitats(newValue);
            });
        }

        loadData(); // Cargar los datos inicialmente

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Habitat selectedHabitat = tableView.getSelectionModel().getSelectedItem();
                if (selectedHabitat != null) {
                    editHabitat(selectedHabitat);
                }
            }
        });
    }

    private void loadData() {
        allHabitats.clear(); // Limpiar la lista de todos los hábitats antes de cargar
        Habitat.getAll(allHabitats); // Cargar todos los hábitats desde la base de datos
        
        // Aplicar el filtro actual después de recargar todos los datos
        filtrarHabitats(searchField != null ? searchField.getText() : "");
    }

    @FXML
    private void openNewHabitatForm() {
        editHabitat(null); // Pasa null para indicar "nuevo"
    }

    private void editHabitat(Habitat habitat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editar_habitat.fxml"));
            Parent root = loader.load();

            editarHabitatController controller = loader.getController();
            controller.setHabitat(habitat);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear interacción con otras ventanas
            stage.setTitle(habitat == null ? "Agregar Habitat" : "Editar Habitat");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Esperar a que se cierre la ventana de edición

            loadData(); // Recargar la tabla después de editar/agregar
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Este método ya no es necesario si Habitat.save() maneja la recarga
    // public void save(Habitat habitat) throws SQLException {
    //     habitat.save();
    //     loadData(); // Esto ya se llama desde editHabitat después de showAndWait()
    // }

    private void filtrarHabitats(String filtro) {
        habitatList.clear(); // Limpiar la lista mostrada en la tabla
        if (filtro == null || filtro.isEmpty()) {
            habitatList.addAll(allHabitats); // Mostrar todos si el filtro está vacío
        } else {
            String filtroLowerCase = filtro.toLowerCase();
            for (Habitat habitat : allHabitats) {
                if (habitat.getNombre().toLowerCase().contains(filtroLowerCase)) {
                    habitatList.add(habitat);
                }
            }
        }
    }

    @FXML
    private void cambiarVistaBestiario() throws IOException {
        App.setRoot("principal_monstruos");
    }

    @FXML
    private void cambiarVistaDebilidades() throws IOException {
        App.setRoot("principal_debilidades");
    }

    @FXML
    private void cambiarVistaHabitat() throws IOException {
        // Ya estás en esta vista, pero es bueno tener el método por consistencia
        App.setRoot("principal_habitat");
    }

    @FXML
    private void cambiarVistaArmas() {
        // Lógica para cambiar a la vista de armas
        System.out.println("Cambiando a la vista de armas");
    }

    @FXML
    private void cambiarVistaInfo() throws IOException {
        App.setRoot("principal_info");
    }
}
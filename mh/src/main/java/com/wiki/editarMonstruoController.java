package com.wiki;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection; // Importar Connection
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class editarMonstruoController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTamaño;

    @FXML
    private TextField txtHabitat;

    @FXML
    private TextField txtTipo;

    @FXML
    private ImageView imgMonstruo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnExportarTexto;

    @FXML
    private Button btnNuevoMonstruo;

    @FXML
    private Button btnEliminarMonstruo;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtLore;

    private Monstruo monstruo;

    private MonstruoController mainController; // Reference to the main controller

    public void setMainController(MonstruoController controller) {
        this.mainController = controller;
    }

    public void setMonstruo(Monstruo monstruo) {
        this.monstruo = monstruo;
        if (monstruo != null) {
            txtNombre.setText(monstruo.getNombre());
            txtTamaño.setText(monstruo.getTamaño());
            txtHabitat.setText(monstruo.getHabitat());
            txtTipo.setText(monstruo.getNombreTipo());
            txtLore.setText(monstruo.getLore());

            String imagePath = monstruo.getImagen();
            if (imagePath != null && !imagePath.isEmpty()) {
                URL imageUrl = getClass().getResource(imagePath);
                System.out.println("Image path from DB: " + imagePath);
                System.out.println("Image URL: " + imageUrl);
                if (imageUrl != null) {
                    try {
                        Image image = new Image(imageUrl.toExternalForm());
                        imgMonstruo.setImage(image);
                    } catch (Exception e) {
                        System.err.println("Error loading image: " + imagePath);
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Resource not found: " + imagePath);
                }
            }
        }
    }

    public void nuevoMonstruo() {
        //  Clear the form
        txtNombre.clear();
        txtTamaño.clear();
        txtHabitat.clear();
        txtTipo.clear();
        txtLore.clear();
        imgMonstruo.setImage(null);
        monstruo = new Monstruo(0, "", "", 0, "", "", "", ""); 
    }

    @FXML
 private void guardarCambios() {
  if (mainController == null) {
  System.err.println("Error: mainController is null in guardarCambios()");
  return; // Or throw an exception, handle this critical error!
  }
 
  // Get the type name from the TextField
  String tipoNombre = txtTipo.getText();
  // Get the id_tipo from the database based on the name
  int tipoId = mainController.obtenerTipoIdDesdeNombre(tipoNombre);
  if (tipoId == -1) { // Or whatever value indicates "not found"
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Tipo no encontrado");
      alert.setContentText("El tipo '" + tipoNombre + "' no existe. Por favor, ingrese un tipo válido.");
      alert.showAndWait();
      return; // Do not proceed with saving
  }
  // Update the Monstruo object
  monstruo.setNombre(txtNombre.getText());
  monstruo.setTamaño(txtTamaño.getText());
  monstruo.setHabitat(txtHabitat.getText());
  monstruo.setLore(txtLore.getText());
  monstruo.setIdTipo(tipoId);  // **SET THE CORRECT id_tipo**
  // Save the Monstruo to the database
  monstruo.save();
  // Refresh the Monstruo list in the main controller
  mainController.refreshMonstruoList();
  // Close the edit window
  closeStage();
 }

    @FXML
    private void eliminarMonstruo() {
        if (monstruo != null && monstruo.getIdMonstruo() > 0) {
            try {
                // First, delete related records in ALL dependent tables
                monstruo.deleteDebilidades();
                monstruo.deleteHabitats();
                // Then, delete the monster itself
                int rowsAffected = monstruo.delete();
                if (rowsAffected > 0) {
                    System.out.println("Monstruo eliminado de la base de datos.");
                    mainController.refreshMonstruoList();
                    closeStage();
                } else {
                    System.out.println("No se pudo eliminar el monstruo.");
                    // Consider showing an alert
                }
            } catch (Exception e) {
                System.err.println("Error al eliminar el monstruo: " + e.getMessage());
                e.printStackTrace();
                // Show an error alert to the user
            }
        } else {
            System.out.println("No hay monstruo seleccionado para eliminar.");
            // Consider showing an alert
        }
    }

    @FXML
    private void exportarATexto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar información del monstruo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File file = fileChooser.showSaveDialog(btnExportarTexto.getScene().getWindow());

        if (file != null) {
            guardarTextoEnArchivo(obtenerTextoMonstruo(), file);
        }
    }

    private String obtenerTextoMonstruo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(txtNombre.getText()).append("\n");
        sb.append("Tamaño: ").append(txtTamaño.getText()).append("\n");
        sb.append("Habitat: ").append(txtHabitat.getText()).append("\n");
        sb.append("Tipo: ").append(txtTipo.getText()).append("\n");
        sb.append("Lore: ").append(txtLore.getText()).append("\n");

        // Recuperar y añadir las debilidades del monstruo
        sb.append("Debilidades:\n");
        // Asegúrate de que 'monstruo' no sea nulo antes de llamar a getIdMonstruo()
        if (monstruo != null) {
            obtenerDebilidadesMonstruo(monstruo.getIdMonstruo(), sb);
        } else {
            sb.append("  (No hay monstruo seleccionado para obtener debilidades)\n");
        }


        // Recuperar y añadir los hábitats del monstruo
        sb.append("Hábitats:\n");
        // Asegúrate de que 'monstruo' no sea nulo antes de llamar a getIdMonstruo()
        if (monstruo != null) {
            obtenerHabitatsMonstruo(monstruo.getIdMonstruo(), sb);
        } else {
            sb.append("  (No hay monstruo seleccionado para obtener hábitats)\n");
        }

        return sb.toString();
    }

    private void obtenerDebilidadesMonstruo(int idMonstruo, StringBuilder sb) {
        try (Connection conn = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT d.elemento, md.intensidad " + // Añadir intensidad
                             "FROM Debilidades d " +
                             "JOIN Monstruos_Debilidades md ON d.id_debilidad = md.id_debilidad " +
                             "WHERE md.id_monstruo = ?")) {
            stmt.setInt(1, idMonstruo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica si no hay filas
                sb.append("  (Ninguna debilidad registrada)\n");
            } else {
                while (rs.next()) {
                    sb.append("- ").append(rs.getString("elemento"))
                      .append(" (Intensidad: ").append(rs.getInt("intensidad")).append(")\n"); // Mostrar intensidad
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sb.append("  (Error al obtener las debilidades: ").append(e.getMessage()).append(")\n");
        }
    }

    private void obtenerHabitatsMonstruo(int idMonstruo, StringBuilder sb) {
        try (Connection conn = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT h.nombre " +
                             "FROM Habitats h " +
                             "JOIN Monstruos_Habitats mh ON h.id_habitat = mh.id_habitat " +
                             "WHERE mh.id_monstruo = ?")) {
            stmt.setInt(1, idMonstruo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica si no hay filas
                sb.append("  (Ningún hábitat registrado)\n");
            } else {
                while (rs.next()) {
                    sb.append("- ").append(rs.getString("nombre")).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sb.append("  (Error al obtener los hábitats: ").append(e.getMessage()).append(")\n");
        }
    }

    private void guardarTextoEnArchivo(String contenido, File archivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.print(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, e.g., show an error message to the user
        }
    }

    @FXML
    private void cancelar() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
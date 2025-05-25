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
    private Button btnCancelar;

    @FXML
    private TextField txtLore;

    private Monstruo monstruo;

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

    @FXML
    private void guardarCambios() {
        if (monstruo != null) {
            monstruo.setNombre(txtNombre.getText());
            monstruo.setTamaño(txtTamaño.getText());
            monstruo.setHabitat(txtHabitat.getText());
            monstruo.setNombreTipo(txtTipo.getText());
            monstruo.setLore(txtLore.getText());
            monstruo.save();
        }
        closeStage();
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
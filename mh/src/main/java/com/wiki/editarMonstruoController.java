package com.wiki;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

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
        // You can also include image path if needed
        // sb.append("Imagen: ").append(monstruo.getImagen()).append("\n"); 
        return sb.toString();
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
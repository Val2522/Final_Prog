package com.wiki;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField; // Import Image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; // Import URL
import javafx.stage.Stage; // Import MalformedURLException

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
    private Button btnCancelar;

    private Monstruo monstruo;

    public void setMonstruo(Monstruo monstruo) {
        this.monstruo = monstruo;
        if (monstruo != null) {
            txtNombre.setText(monstruo.getNombre());
            txtTamaño.setText(monstruo.getTamaño());
            txtHabitat.setText(monstruo.getHabitat());
            txtTipo.setText(monstruo.getNombreTipo());

            // Load the image if the URL is valid
            String imageUrl = monstruo.getImagen();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    URL url = new URL(imageUrl);
                    Image image = new Image(url.toExternalForm());
                    imgMonstruo.setImage(image);
                } catch (MalformedURLException e) {
                    System.err.println("Invalid image URL: " + imageUrl);
                    e.printStackTrace();
                    // Optionally, set a placeholder image or leave it empty
                } catch (IllegalArgumentException e) {
                    System.err.println("Error loading image from URL: " + imageUrl);
                    e.printStackTrace();
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
            monstruo.save();
        }
        closeStage();
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
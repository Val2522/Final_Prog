package com.wiki;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image; // Import Image
import javafx.scene.image.ImageView;
import javafx.stage.Stage; // Import URL

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

            // Load the image if the URL is valid
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
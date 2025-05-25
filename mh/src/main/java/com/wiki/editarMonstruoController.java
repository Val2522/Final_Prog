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

/**
 * Controlador para manejar la edición de un monstruo.
 * Este controlador permite cargar, modificar, añadir y eliminar monstruos.
 */
public class editarMonstruoController {

    @FXML
    private TextField txtNombre; // Campo de texto para el nombre del monstruo

    @FXML
    private TextField txtTamaño; // Campo de texto para el tamaño del monstruo

    @FXML
    private TextField txtHabitat; // Campo de texto para el hábitat del monstruo

    @FXML
    private TextField txtTipo; // Campo de texto para el tipo del monstruo

    @FXML
    private ImageView imgMonstruo; // Vista de imagen para mostrar la imagen del monstruo

    @FXML
    private Button btnGuardar; // Botón para guardar los cambios

    @FXML
    private Button btnExportarTexto; // Botón para exportar la información del monstruo a un archivo de texto

    @FXML
    private Button btnNuevoMonstruo; // Botón para limpiar el formulario y crear un nuevo monstruo

    @FXML
    private Button btnEliminarMonstruo; // Botón para eliminar el monstruo seleccionado

    @FXML
    private Button btnCancelar; // Botón para cancelar la edición y cerrar la ventana

    @FXML
    private TextField txtLore; // Campo de texto para el lore del monstruo

    private Monstruo monstruo; // Objeto Monstruo que se está editando

    private MonstruoController mainController; // Referencia al controlador principal

    /**
     * Establece el controlador principal.
     *
     * @param controller El controlador principal.
     */
    public void setMainController(MonstruoController controller) {
        this.mainController = controller;
    }

    /**
     * Establece el monstruo que se va a editar.
     * Si el monstruo no es nulo, carga su información en los campos del formulario.
     *
     * @param monstruo El objeto Monstruo que se va a editar.
     */
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

    /**
     * Limpia el formulario para crear un nuevo monstruo.
     */
    public void nuevoMonstruo() {
        txtNombre.clear();
        txtTamaño.clear();
        txtHabitat.clear();
        txtTipo.clear();
        txtLore.clear();
        imgMonstruo.setImage(null);
        monstruo = new Monstruo(0, "", "", 0, "", "", "", "");
    }

    /**
     * Guarda los cambios realizados en el monstruo.
     * Valida los datos antes de guardar y actualiza la base de datos.
     */
    @FXML
    private void guardarCambios() {
        if (mainController == null) {
            System.err.println("Error: mainController es nulo en guardarCambios()");
            return;
        }

        String tipoNombre = txtTipo.getText();
        int tipoId = mainController.obtenerTipoIdDesdeNombre(tipoNombre);
        if (tipoId == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Tipo no encontrado");
            alert.setContentText("El tipo '" + tipoNombre + "' no existe. Por favor, ingrese un tipo válido.");
            alert.showAndWait();
            return;
        }

        monstruo.setNombre(txtNombre.getText());
        monstruo.setTamaño(txtTamaño.getText());
        monstruo.setHabitat(txtHabitat.getText());
        monstruo.setLore(txtLore.getText());
        monstruo.setIdTipo(tipoId);

        monstruo.save();
        mainController.refreshMonstruoList();
        closeStage();
    }

    /**
     * Elimina el monstruo seleccionado de la base de datos.
     * También elimina las dependencias relacionadas.
     */
    @FXML
    private void eliminarMonstruo() {
        if (monstruo != null && monstruo.getIdMonstruo() > 0) {
            try {
                monstruo.deleteDebilidades();
                monstruo.deleteHabitats();
                int rowsAffected = monstruo.delete();
                if (rowsAffected > 0) {
                    System.out.println("Monstruo eliminado de la base de datos.");
                    mainController.refreshMonstruoList();
                    closeStage();
                } else {
                    System.out.println("No se pudo eliminar el monstruo.");
                }
            } catch (Exception e) {
                System.err.println("Error al eliminar el monstruo: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay monstruo seleccionado para eliminar.");
        }
    }

    /**
     * Exporta la información del monstruo a un archivo de texto.
     */
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

    /**
     * Obtiene la información del monstruo en formato de texto.
     *
     * @return La información del monstruo como una cadena de texto.
     */
    private String obtenerTextoMonstruo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(txtNombre.getText()).append("\n");
        sb.append("Tamaño: ").append(txtTamaño.getText()).append("\n");
        sb.append("Habitat: ").append(txtHabitat.getText()).append("\n");
        sb.append("Tipo: ").append(txtTipo.getText()).append("\n");
        sb.append("Lore: ").append(txtLore.getText()).append("\n");

        sb.append("Debilidades:\n");
        if (monstruo != null) {
            obtenerDebilidadesMonstruo(monstruo.getIdMonstruo(), sb);
        } else {
            sb.append("  (No hay monstruo seleccionado para obtener debilidades)\n");
        }

        sb.append("Hábitats:\n");
        if (monstruo != null) {
            obtenerHabitatsMonstruo(monstruo.getIdMonstruo(), sb);
        } else {
            sb.append("  (No hay monstruo seleccionado para obtener hábitats)\n");
        }

        return sb.toString();
    }

    /**
     * Obtiene las debilidades del monstruo desde la base de datos.
     *
     * @param idMonstruo El ID del monstruo.
     * @param sb El objeto StringBuilder donde se agregará la información.
     */
    private void obtenerDebilidadesMonstruo(int idMonstruo, StringBuilder sb) {
        try (Connection conn = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT d.elemento, md.intensidad " +
                             "FROM Debilidades d " +
                             "JOIN Monstruos_Debilidades md ON d.id_debilidad = md.id_debilidad " +
                             "WHERE md.id_monstruo = ?")) {
            stmt.setInt(1, idMonstruo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                sb.append("  (Ninguna debilidad registrada)\n");
            } else {
                while (rs.next()) {
                    sb.append("- ").append(rs.getString("elemento"))
                      .append(" (Intensidad: ").append(rs.getInt("intensidad")).append(")\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sb.append("  (Error al obtener las debilidades: ").append(e.getMessage()).append(")\n");
        }
    }

    /**
     * Obtiene los hábitats del monstruo desde la base de datos.
     *
     * @param idMonstruo El ID del monstruo.
     * @param sb El objeto StringBuilder donde se agregará la información.
     */
    private void obtenerHabitatsMonstruo(int idMonstruo, StringBuilder sb) {
        try (Connection conn = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT h.nombre " +
                             "FROM Habitats h " +
                             "JOIN Monstruos_Habitats mh ON h.id_habitat = mh.id_habitat " +
                             "WHERE mh.id_monstruo = ?")) {
            stmt.setInt(1, idMonstruo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
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

    /**
     * Guarda el contenido en un archivo de texto.
     *
     * @param contenido El contenido a guardar.
     * @param archivo El archivo donde se guardará el contenido.
     */
    private void guardarTextoEnArchivo(String contenido, File archivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.print(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancela la edición y cierra la ventana.
     */
    @FXML
    private void cancelar() {
        closeStage();
    }

    /**
     * Cierra la ventana actual.
     */
    private void closeStage() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
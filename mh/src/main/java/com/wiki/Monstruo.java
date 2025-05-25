package com.wiki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 * Clase que representa un monstruo en el sistema.
 * Contiene propiedades y métodos para interactuar con la base de datos.
 */
public class Monstruo {

    private SimpleIntegerProperty idMonstruo; // ID único del monstruo
    private SimpleStringProperty nombre; // Nombre del monstruo
    private SimpleStringProperty imagen; // Ruta de la imagen del monstruo
    private SimpleIntegerProperty id_tipo; // ID del tipo del monstruo
    private SimpleStringProperty tamaño; // Tamaño del monstruo
    private SimpleStringProperty lore; // Descripción o historia del monstruo
    private SimpleStringProperty nombre_tipo; // Nombre del tipo del monstruo
    private SimpleStringProperty habitat; // Hábitat del monstruo

    /**
     * Constructor de la clase Monstruo.
     *
     * @param idMonstruo El ID del monstruo.
     * @param nombre El nombre del monstruo.
     * @param imagen La ruta de la imagen del monstruo.
     * @param id_tipo El ID del tipo del monstruo.
     * @param tamaño El tamaño del monstruo.
     * @param lore La descripción o historia del monstruo.
     * @param habitat El hábitat del monstruo.
     * @param nombre_tipo El nombre del tipo del monstruo.
     */
    public Monstruo(int idMonstruo, String nombre, String imagen, int id_tipo, String tamaño, String lore, String habitat, String nombre_tipo) {
        this.idMonstruo = new SimpleIntegerProperty(idMonstruo);
        this.nombre = new SimpleStringProperty(nombre);
        this.imagen = new SimpleStringProperty(imagen);
        this.id_tipo = new SimpleIntegerProperty(id_tipo);
        this.tamaño = new SimpleStringProperty(tamaño);
        this.lore = new SimpleStringProperty(lore);
        this.habitat = new SimpleStringProperty(habitat);
        this.nombre_tipo = new SimpleStringProperty(nombre_tipo);
    }

    // Getters y Setters

    /**
     * Obtiene el ID del monstruo.
     *
     * @return El ID del monstruo.
     */
    public int getIdMonstruo() {
        return idMonstruo.get();
    }

    /**
     * Establece el ID del monstruo.
     *
     * @param idMonstruo El nuevo ID del monstruo.
     */
    public void setIdMonstruo(int idMonstruo) {
        this.idMonstruo.set(idMonstruo);
    }

    /**
     * Obtiene el nombre del monstruo.
     *
     * @return El nombre del monstruo.
     */
    public String getNombre() {
        return nombre.get();
    }

    /**
     * Establece el nombre del monstruo.
     *
     * @param nombre El nuevo nombre del monstruo.
     */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    /**
     * Obtiene la ruta de la imagen del monstruo.
     *
     * @return La ruta de la imagen del monstruo.
     */
    public String getImagen() {
        return imagen.get();
    }

    /**
     * Establece la ruta de la imagen del monstruo.
     *
     * @param imagen La nueva ruta de la imagen del monstruo.
     */
    public void setImagen(String imagen) {
        this.imagen.set(imagen);
    }

    /**
     * Obtiene el ID del tipo del monstruo.
     *
     * @return El ID del tipo del monstruo.
     */
    public int getIdTipo() {
        return id_tipo.get();
    }

    /**
     * Establece el ID del tipo del monstruo.
     *
     * @param id_tipo El nuevo ID del tipo del monstruo.
     */
    public void setIdTipo(int id_tipo) {
        this.id_tipo.set(id_tipo);
    }

    /**
     * Obtiene el tamaño del monstruo.
     *
     * @return El tamaño del monstruo.
     */
    public String getTamaño() {
        return tamaño.get();
    }

    /**
     * Establece el tamaño del monstruo.
     *
     * @param tamaño El nuevo tamaño del monstruo.
     */
    public void setTamaño(String tamaño) {
        this.tamaño.set(tamaño);
    }

    /**
     * Obtiene la descripción o historia del monstruo.
     *
     * @return La descripción o historia del monstruo.
     */
    public String getLore() {
        return lore.get();
    }

    /**
     * Establece la descripción o historia del monstruo.
     *
     * @param lore La nueva descripción o historia del monstruo.
     */
    public void setLore(String lore) {
        this.lore.set(lore);
    }

    /**
     * Obtiene el nombre del tipo del monstruo.
     *
     * @return El nombre del tipo del monstruo.
     */
    public String getNombreTipo() {
        return nombre_tipo.get();
    }

    /**
     * Establece el nombre del tipo del monstruo.
     *
     * @param nombre_tipo El nuevo nombre del tipo del monstruo.
     */
    public void setNombreTipo(String nombre_tipo) {
        this.nombre_tipo.set(nombre_tipo);
    }

    /**
     * Obtiene el hábitat del monstruo.
     *
     * @return El hábitat del monstruo.
     */
    public String getHabitat() {
        return habitat.get();
    }

    /**
     * Establece el hábitat del monstruo.
     *
     * @param habitat El nuevo hábitat del monstruo.
     */
    public void setHabitat(String habitat) {
        this.habitat.set(habitat);
    }

    // Métodos para interactuar con la base de datos

    /**
     * Obtiene todos los monstruos de la base de datos y los agrega a una lista observable.
     *
     * @param monstruoList La lista observable donde se agregarán los monstruos.
     */
    public static void getAll(ObservableList<Monstruo> monstruoList) {
        monstruoList.clear();
        String query = "SELECT Monstruos.id_monstruo AS idMonstruo, Monstruos.nombre, Monstruos.imagen, Monstruos.tamaño, Monstruos.lore, " +
                "Habitats.nombre AS habitat, Tipos.nombre AS nombreTipo, Monstruos.id_tipo AS id_tipo " +
                "FROM Monstruos " +
                "INNER JOIN Tipos ON Monstruos.id_tipo = Tipos.id_tipo " +
                "INNER JOIN Monstruos_Habitats ON Monstruos.id_monstruo = Monstruos_Habitats.id_monstruo " +
                "INNER JOIN Habitats ON Monstruos_Habitats.id_habitat = Habitats.id_habitat;";

        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Monstruo e = new Monstruo(
                        rs.getInt("idMonstruo"),
                        rs.getString("nombre"),
                        rs.getString("imagen"),
                        rs.getInt("id_tipo"),
                        rs.getString("tamaño"),
                        rs.getString("lore"),
                        rs.getString("habitat"),
                        rs.getString("nombreTipo")
                );
                monstruoList.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda o actualiza un monstruo en la base de datos.
     * Si el monstruo ya existe (ID > 0), se actualiza. De lo contrario, se inserta como nuevo.
     *
     * @return El número de filas afectadas por la operación.
     */
    public int save() {
        int filasAfectadas = 0;
        String query;
        try (Connection connection = connectionBD.getInstance().getConnection()) {
            if (this.getIdMonstruo() > 0) {
                // Actualizar un monstruo existente
                query = "UPDATE Monstruos SET nombre = ?, imagen = ?, id_tipo = ?, tamaño = ?, lore = ? WHERE id_monstruo = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, this.getNombre());
                    stmt.setString(2, this.getImagen());
                    stmt.setInt(3, this.getIdTipo());
                    stmt.setString(4, this.getTamaño());
                    stmt.setString(5, this.getLore());
                    stmt.setInt(6, this.getIdMonstruo());
                    filasAfectadas = stmt.executeUpdate();
                }
            } else {
                // Insertar un nuevo monstruo
                query = "INSERT INTO Monstruos (nombre, imagen, id_tipo, tamaño, lore) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, this.getNombre());
                    stmt.setString(2, this.getImagen());
                    stmt.setInt(3, this.getIdTipo());
                    stmt.setString(4, this.getTamaño());
                    stmt.setString(5, this.getLore());
                    filasAfectadas = stmt.executeUpdate();

                    // Recuperar el ID generado automáticamente
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            this.setIdMonstruo(generatedKeys.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    /**
     * Elimina las debilidades asociadas al monstruo de la base de datos.
     *
     * @return El número de filas afectadas por la operación.
     */
    public int deleteDebilidades() {
        int rowsAffected = 0;
        String query = "DELETE FROM Monstruos_Debilidades WHERE id_monstruo = ?";
        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.getIdMonstruo());
            rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " debilidades for Monstruo ID: " + this.getIdMonstruo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * Elimina los hábitats asociados al monstruo de la base de datos.
     *
     * @return El número de filas afectadas por la operación.
     */
    public int deleteHabitats() {
        int rowsAffected = 0;
        String query = "DELETE FROM Monstruos_Habitats WHERE id_monstruo = ?";
        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.getIdMonstruo());
            rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " habitats for Monstruo ID: " + this.getIdMonstruo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * Elimina el monstruo de la base de datos.
     *
     * @return El número de filas afectadas por la operación.
     */
    public int delete() {
        int filasAfectadas = 0;
        String query = "DELETE FROM Monstruos WHERE id_monstruo = ?";
        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.getIdMonstruo());
            System.out.println("Deleting Monstruo with ID: " + this.getIdMonstruo());
            filasAfectadas = stmt.executeUpdate();
            System.out.println("Rows Affected: " + filasAfectadas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }
}
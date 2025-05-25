package com.wiki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;

/**
 * Clase que representa un hábitat en el sistema.
 * Contiene propiedades y métodos para interactuar con la base de datos.
 */
public class Habitat {

    private int idHabitat; // ID único del hábitat
    private String nombre; // Nombre del hábitat

    /**
     * Constructor de la clase Habitat.
     *
     * @param idHabitat El ID del hábitat.
     * @param nombre El nombre del hábitat.
     */
    public Habitat(int idHabitat, String nombre) {
        this.idHabitat = idHabitat;
        this.nombre = nombre;
    }

    // Getters

    /**
     * Obtiene el ID del hábitat.
     *
     * @return El ID del hábitat.
     */
    public int getIdHabitat() {
        return idHabitat;
    }

    /**
     * Obtiene el nombre del hábitat.
     *
     * @return El nombre del hábitat.
     */
    public String getNombre() {
        return nombre;
    }

    // Setters

    /**
     * Establece el ID del hábitat.
     *
     * @param idHabitat El nuevo ID del hábitat.
     */
    public void setIdHabitat(int idHabitat) {
        this.idHabitat = idHabitat;
    }

    /**
     * Establece el nombre del hábitat.
     *
     * @param nombre El nuevo nombre del hábitat.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene todos los hábitats de la base de datos y los agrega a una lista observable.
     *
     * @param listaHabitats La lista observable donde se agregarán los hábitats.
     */
    public static void getAll(ObservableList<Habitat> listaHabitats) {
        listaHabitats.clear();
        String query = "SELECT id_habitat, nombre FROM Habitats";

        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_habitat");
                String nombre = rs.getString("nombre");
                Habitat habitat = new Habitat(id, nombre);
                listaHabitats.add(habitat);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching habitats: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Guarda o actualiza un hábitat en la base de datos.
     * Si el hábitat ya existe (ID > 0), se actualiza. De lo contrario, se inserta como nuevo.
     *
     * @return El número de filas afectadas por la operación.
     */
    public int save() {
        int filasAfectadas = 0;
        String query;

        try (Connection connection = connectionBD.getInstance().getConnection()) {
            if (this.idHabitat > 0) {
                // Actualizar un hábitat existente
                query = "UPDATE Habitats SET nombre = ? WHERE id_habitat = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, this.nombre);
                    stmt.setInt(2, this.idHabitat);
                    filasAfectadas = stmt.executeUpdate();
                }
            } else {
                // Insertar un nuevo hábitat
                query = "INSERT INTO Habitats (id_habitat, nombre) VALUES (?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setInt(1, this.idHabitat);
                    stmt.setString(2, this.nombre);
                    filasAfectadas = stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving habitat: " + e.getMessage());
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    /**
     * Elimina un hábitat de la base de datos.
     *
     * @return El número de filas afectadas por la operación.
     */
    public int delete() {
        int filasAfectadas = 0;
        String query = "DELETE FROM Habitats WHERE id_habitat = ?";

        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.idHabitat);
            filasAfectadas = stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting habitat: " + e.getMessage());
            e.printStackTrace();
        }
        return filasAfectadas;
    }
}
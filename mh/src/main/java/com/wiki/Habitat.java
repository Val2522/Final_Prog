package com.wiki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;

public class Habitat {

    private int idHabitat; // Consistent naming
    private String nombre;

    public Habitat(int idHabitat, String nombre) {
        this.idHabitat = idHabitat;
        this.nombre = nombre;
    }

    // Getters
    public int getIdHabitat() {
        return idHabitat;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setIdHabitat(int idHabitat) {
        this.idHabitat = idHabitat;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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

    // Save or update habitat
    public int save() {
        int filasAfectadas = 0;
        String query;

        try (Connection connection = connectionBD.getInstance().getConnection()) {
            if (this.idHabitat > 0) {
                // Update
                query = "UPDATE Habitats SET nombre = ? WHERE id_habitat = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, this.nombre);
                    stmt.setInt(2, this.idHabitat);
                    filasAfectadas = stmt.executeUpdate();
                }
            } else {
                // Insert
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

    // Delete habitat
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
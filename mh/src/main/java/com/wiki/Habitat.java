package com.wiki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class Habitat {
    private int id_habitat;
    private String nombre;
    private String descripcion;

    public Habitat(int id_habitat, String nombre, String descripcion) {
        this.id_habitat = id_habitat;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /** GETTERS */
    public int getId_habitat() { return id_habitat; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    /** SETTERS */
    public void setId_habitat(int id_habitat) { this.id_habitat = id_habitat; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** Obtener todos los hábitats */
    public static void getAll(ObservableList<Habitat> listaHabitats) {
        listaHabitats.clear();
        try (Connection connection = connectionBD.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Habitats")) {

            while (rs.next()) {
                Habitat h = new Habitat(
                    rs.getInt("id_habitat"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                listaHabitats.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Guardar o actualizar habitat */
    public int save() {
        int filas = 0;
        try (Connection connection = connectionBD.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habitats WHERE id_habitat = " + this.id_habitat);
            if (rs.next()) {
                String update = "UPDATE Habitats SET nombre='" + this.nombre + "', descripcion='" + this.descripcion +
                                "' WHERE id_habitat=" + this.id_habitat;
                filas = stmt.executeUpdate(update);
            } else {
                String insert = "INSERT INTO Habitats (id_habitat, nombre, descripcion) VALUES (" +
                                this.id_habitat + ", '" + this.nombre + "', '" + this.descripcion + "')";
                filas = stmt.executeUpdate(insert);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas;
    }

    /** Eliminar habitat */
    public int delete() {
        int filas = 0;
        try (Connection connection = connectionBD.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            String query = "DELETE FROM Habitats WHERE id_habitat = " + this.id_habitat;
            filas = stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas;
    }
}

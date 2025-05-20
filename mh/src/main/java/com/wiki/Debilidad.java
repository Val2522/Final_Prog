package com.wiki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;

public class Debilidad {
    private int id_debilidad;
    private String nombre;
    private String descripcion;

    public Debilidad(int id_debilidad, String nombre, String descripcion) {
        this.id_debilidad = id_debilidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /** GETTERS */
    public int getId_debilidad() { return id_debilidad; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    /** SETTERS */
    public void setId_debilidad(int id_debilidad) { this.id_debilidad = id_debilidad; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** Obtener todas las debilidades */
    public static void getAll(ObservableList<Debilidad> listaDebilidades) {
        listaDebilidades.clear();
        try (Connection connection = connectionBD.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Debilidades")) {

            while (rs.next()) {
                Debilidad d = new Debilidad(
                    rs.getInt("id_debilidad"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                listaDebilidades.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Guardar o actualizar debilidad */
    public int save() {
        int filas = 0;
        try (Connection connection = connectionBD.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM Debilidades WHERE id_debilidad = " + this.id_debilidad);
            if (rs.next()) {
                String update = "UPDATE Debilidades SET nombre='" + this.nombre + "', descripcion='" + this.descripcion +
                                "' WHERE id_debilidad=" + this.id_debilidad;
                filas = stmt.executeUpdate(update);
            } else {
                String insert = "INSERT INTO Debilidades (id_debilidad, nombre, descripcion) VALUES (" +
                                this.id_debilidad + ", '" + this.nombre + "', '" + this.descripcion + "')";
                filas = stmt.executeUpdate(insert);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas;
    }

    /** Eliminar debilidad */
    public int delete() {
        int filas = 0;
        try (Connection connection = connectionBD.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            String query = "DELETE FROM Debilidades WHERE id_debilidad = " + this.id_debilidad;
            filas = stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas;
    }
}


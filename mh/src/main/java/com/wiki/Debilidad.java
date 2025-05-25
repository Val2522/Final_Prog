
package com.wiki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class Debilidad {
    private SimpleIntegerProperty idDebilidad;
    private SimpleStringProperty elemento;

    // Constructor
    public Debilidad(int idDebilidad, String elemento) {
        this.idDebilidad = new SimpleIntegerProperty(idDebilidad);
        this.elemento = new SimpleStringProperty(elemento);
    }

    // Getters y Setters
    public int getIdDebilidad() {
        return idDebilidad.get();
    }

    public void setIdDebilidad(int idDebilidad) {
        this.idDebilidad.set(idDebilidad);
    }

    public String getElemento() {
        return elemento.get();
    }

    public void setElemento(String elemento) {
        this.elemento.set(elemento);
    }

    // Obtener todas las debilidades
    public static void getAll(ObservableList<Debilidad> debilidadList) {
        debilidadList.clear();
        String query = "SELECT * FROM Debilidades";

        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idDebilidad = rs.getInt("id_debilidad");
                String elemento = rs.getString("elemento");
                debilidadList.add(new Debilidad(idDebilidad, elemento));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int save() {
        int filasAfectadas = 0;
        String query;
        try (Connection connection = connectionBD.getInstance().getConnection()) {
            if (getIdDebilidad() > 0) {
                // Update
                query = "UPDATE Debilidades SET elemento = ? WHERE id_debilidad = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, getElemento());
                    stmt.setInt(2, getIdDebilidad());
                    filasAfectadas = stmt.executeUpdate();
                }
            } else {
                // Insert
                query = "INSERT INTO Debilidades (elemento) VALUES (?)";
                try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, getElemento());
                    filasAfectadas = stmt.executeUpdate();

                    // Recuperar el ID generado automáticamente
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            setIdDebilidad(generatedKeys.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    // Eliminar debilidad
    public int delete() {
        int filasAfectadas = 0;
        String query = "DELETE FROM Debilidades WHERE id_debilidad = ?";
        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, getIdDebilidad());
            filasAfectadas = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    public static Debilidad getDebilidadPorElemento(String elemento) {
        Debilidad debilidad = null;
        String query = "SELECT id_debilidad, elemento FROM Debilidades WHERE elemento = ?";

        try (Connection connection = connectionBD.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, elemento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idDebilidad = rs.getInt("id_debilidad");
                String elementoDb = rs.getString("elemento");
                debilidad = new Debilidad(idDebilidad, elementoDb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return debilidad;
    }
}
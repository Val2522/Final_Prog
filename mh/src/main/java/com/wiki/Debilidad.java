package com.wiki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 * Clase que representa una debilidad en el sistema.
 * Contiene propiedades y métodos para interactuar con la base de datos.
 */
public class Debilidad {

    // Propiedad que representa el ID de la debilidad
    private SimpleIntegerProperty idDebilidad;

    // Propiedad que representa el elemento asociado a la debilidad
    private SimpleStringProperty elemento;

    /**
     * Constructor de la clase Debilidad.
     *
     * @param idDebilidad El ID de la debilidad.
     * @param elemento El elemento asociado a la debilidad.
     */
    public Debilidad(int idDebilidad, String elemento) {
        this.idDebilidad = new SimpleIntegerProperty(idDebilidad);
        this.elemento = new SimpleStringProperty(elemento);
    }

    /**
     * Obtiene el ID de la debilidad.
     *
     * @return El ID de la debilidad.
     */
    public int getIdDebilidad() {
        return idDebilidad.get();
    }

    /**
     * Establece el ID de la debilidad.
     *
     * @param idDebilidad El nuevo ID de la debilidad.
     */
    public void setIdDebilidad(int idDebilidad) {
        this.idDebilidad.set(idDebilidad);
    }

    /**
     * Obtiene el elemento asociado a la debilidad.
     *
     * @return El elemento asociado a la debilidad.
     */
    public String getElemento() {
        return elemento.get();
    }

    /**
     * Establece el elemento asociado a la debilidad.
     *
     * @param elemento El nuevo elemento asociado a la debilidad.
     */
    public void setElemento(String elemento) {
        this.elemento.set(elemento);
    }

    /**
     * Obtiene todas las debilidades de la base de datos y las agrega a una lista observable.
     *
     * @param debilidadList La lista observable donde se agregarán las debilidades.
     */
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

    /**
     * Guarda la debilidad en la base de datos.
     * Si la debilidad ya existe (ID > 0), se actualiza. De lo contrario, se inserta como nueva.
     *
     * @return El número de filas afectadas por la operación.
     */
    public int save() {
        int filasAfectadas = 0;
        String query;
        try (Connection connection = connectionBD.getInstance().getConnection()) {
            if (getIdDebilidad() > 0) {
                // Actualizar una debilidad existente
                query = "UPDATE Debilidades SET elemento = ? WHERE id_debilidad = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, getElemento());
                    stmt.setInt(2, getIdDebilidad());
                    filasAfectadas = stmt.executeUpdate();
                }
            } else {
                // Insertar una nueva debilidad
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

    /**
     * Elimina la debilidad de la base de datos.
     *
     * @return El número de filas afectadas por la operación.
     */
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

    /**
     * Obtiene una debilidad de la base de datos basada en el elemento proporcionado.
     *
     * @param elemento El elemento de la debilidad que se desea buscar.
     * @return Un objeto Debilidad si se encuentra, o null si no existe.
     */
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
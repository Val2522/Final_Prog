package com.wiki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Monstruo {
    private int id_monstruo;
    private String nombre;
    private String imagen;
    private int id_tipo;
    private String tamaño;
    private String lore;
    private String nombre_tipo;


    /**Constructor */
    public Monstruo(int id_monstruo, String nombre, String imagen, int id_tipo, String tamaño, String lore) {
        this.id_monstruo = id_monstruo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.id_tipo = id_tipo;
        this.tamaño = tamaño;
        this.lore = lore;
    }

    /** GETTERS **/
    public int getId_monstruo() { 
        return id_monstruo; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public String getImagen() { 
        return imagen; 
    }

    public int getIdTipo() { 
        return id_tipo;
    }

    public String getTamaño() { 
        return tamaño; 
    }

    public String getLore() { 
        return lore; 
    }

    public String getNombreTipo() {  
        return nombre_tipo; 
    }



    /** SETTERS **/
    public void setId_monstruo(int id_monstruo) { 
        this.id_monstruo = id_monstruo; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public void setImagen(String imagen) { 
        this.imagen = imagen; 
    }

    public void setId_tipo(int id_tipo) { 
        this.id_tipo = id_tipo;
    }

    public void setTamaño(String tamaño) { 
        this.tamaño = tamaño;
    }

    public void setLore(String lore) { 
        this.lore = lore; 
    }

    public void setNombreTipo(String nombre_tipo) { 
        this.nombre_tipo = nombre_tipo; 
    }


    /** Método para cargar monstruos desde la base de datos **/
    public static ObservableList<Monstruo> cargarMonstruos(Connection connection) {
        ObservableList<Monstruo> monstruos = FXCollections.observableArrayList();
        String query = "SELECT m.id_monstruo, m.nombre, m.imagen, m.id_tipo, m.tamaño, m.lore, t.nombre AS nombre_tipo " +
                       "FROM monstruos m " +
                       "JOIN tipos t ON m.id_tipo = t.id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id_monstruo = resultSet.getInt("id_monstruo");
                String nombre = resultSet.getString("nombre");
                String imagen = resultSet.getString("imagen");
                int id_tipo = resultSet.getInt("id_tipo");
                String tamaño = resultSet.getString("tamaño");
                String lore = resultSet.getString("lore");
                String nombre_tipo = resultSet.getString("nombre_tipo");

                Monstruo monstruo = new Monstruo(id_monstruo, nombre, imagen, id_tipo, tamaño, lore);
                monstruo.setNombreTipo(nombre_tipo);
                monstruos.add(monstruo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monstruos;
    }


    /** Eliminar monstruo **/
    public int delete() {
        int filasAfectadas = 0;
        try (Connection connection = connectionBD.getInstance().getConnection();
                Statement st = connection.createStatement()) {

            String query = "DELETE FROM Monstruos WHERE id_monstruo = " + this.getId_monstruo();
            filasAfectadas = st.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }


    /** Guardar o actualizar monstruo **/
    public int save() {
        int filasAfectadas = 0;
        try (Connection connection = connectionBD.getInstance().getConnection();
             Statement st = connection.createStatement()) {

            String checkQuery = "SELECT * FROM Monstruos WHERE id_monstruo = " + this.getId_monstruo();
            ResultSet rs = st.executeQuery(checkQuery);
            if (rs.next()) {
                // Update
                String updateQuery = "UPDATE Monstruos SET nombre='" + this.getNombre() + 
                    "', imagen='" + this.getImagen() + 
                    "', id_tipo=" + this.getIdTipo() + 
                    ", tamaño='" + this.getTamaño() + 
                    "', lore='" + this.getLore() + 
                    "' WHERE id_monstruo=" + this.getId_monstruo();
                filasAfectadas = st.executeUpdate(updateQuery);
            } else {
                // Insert
                String insertQuery = "INSERT INTO Monstruos (id_monstruo, nombre, imagen, id_tipo, tamaño, lore) VALUES (" +
                    this.getId_monstruo() + ", '" + this.getNombre() + "', '" + this.getImagen() + "', " +
                    this.getIdTipo() + ", '" + this.getTamaño() + "', '" + this.getLore() + "')";
                filasAfectadas = st.executeUpdate(insertQuery);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }
}
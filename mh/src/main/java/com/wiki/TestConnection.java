package com.wiki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnection {

    public static void main(String[] args) {
        try {
            connectionBD dbInstance = connectionBD.getInstance();
            Connection connection = dbInstance.getConnection();

            // Ejecutar una consulta simple para verificar la conexión
            String query = "SELECT 1";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("Conexion y consulta exitosa: " + rs.getInt(1));
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
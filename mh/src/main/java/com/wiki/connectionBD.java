package com.wiki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionBD {
    private static connectionBD instance;
    private Connection connection = null;
    private String url = "jdbc:mariadb://localhost:3307/mh_freedom2";
    private String username = "root";
    private String password = "root";

    private connectionBD() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Conexion fallida");
            throw new SQLException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static connectionBD getInstance() throws SQLException {
        if (instance == null) {
            instance = new connectionBD();
        } else if (instance.getConnection().isClosed()) {
            instance = new connectionBD();
        }

        return instance;
    }
}

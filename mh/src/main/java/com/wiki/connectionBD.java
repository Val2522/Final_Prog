package com.wiki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionBD {

    // Instancia única de la clase connectionBD
    private static connectionBD instance;

    // Objeto Connection que representa la conexión a la base de datos
    private Connection connection = null;

    // URL de la base de datos
    private String url = "jdbc:mariadb://localhost:3307/mh_freedom2";

    // Nombre de usuario para la conexión
    private String username = "root";

    // Contraseña para la conexión
    private String password = "root";

    /**
     * Constructor privado para evitar la creación de múltiples instancias.
     * Este constructor inicializa la conexión a la base de datos.
     *
     * @throws SQLException Si ocurre un error al cargar el controlador o al conectar a la base de datos.
     */
    private connectionBD() throws SQLException {
        try {
            // Cargar el controlador de MariaDB
            Class.forName("org.mariadb.jdbc.Driver");
            // Establecer la conexión a la base de datos
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Conexion fallida");
            throw new SQLException(e);
        }
    }

    /**
     * Devuelve la conexión actual a la base de datos.
     *
     * @return Objeto Connection que representa la conexión a la base de datos.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Devuelve la instancia única de la clase connectionBD.
     * Si la instancia no existe o la conexión está cerrada, se crea una nueva instancia.
     *
     * @return Instancia única de la clase connectionBD.
     * @throws SQLException Si ocurre un error al crear la conexión.
     */
    public static connectionBD getInstance() throws SQLException {
        if (instance == null) {
            // Crear una nueva instancia si no existe
            instance = new connectionBD();
        } else if (instance.getConnection().isClosed()) {
            // Crear una nueva instancia si la conexión está cerrada
            instance = new connectionBD();
        }

        return instance;
    }
}
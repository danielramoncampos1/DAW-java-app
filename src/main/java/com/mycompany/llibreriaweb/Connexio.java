package com.mycompany.llibreriaweb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexio {

    private static final String URL = "jdbc:mariadb://192.168.118.181:3306/practica3";
    private static final String USER = "admin";
    private static final String PASSWORD = "123456";

    public static Connection obtenirConnexio() throws SQLException {
        try {
            // Registrar el driver JDBC
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver JDBC de MariaDB", e);
        }
        // Conectar a la base de datos
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


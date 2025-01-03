package com.mycompany.llibreriaweb;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Danny
 */
public class MariaDBConnectionTest {

    public static void main(String[] args) {
        String url = "jdbc:mariadb://192.168.118.181:3306/practica3";
        String usuario = "admin";
        String contraseña = "123456";

        try {
            // Cargar el controlador MariaDB
            Class.forName("org.mariadb.jdbc.Driver");

            // Establecer conexión
            Connection connection = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión establecida con la base de datos MariaDB");

            String query = "SELECT * FROM usuarios";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("nombre");
                String email = resultSet.getString("email");
                String telefono = resultSet.getString("telefono");

                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Email: " + email + ", Teléfono: " + telefono);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el controlador MariaDB: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}

package com.mycompany.llibreriaweb;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

public class EliminarLlibresServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ISBN del libro que se quiere eliminar
        String isbn = request.getParameter("isbn");
        
        // Parámetros de conexión a la base de datos
        String url = "jdbc:mariadb://192.168.118.181:3306/practica3";
        String usuari = "admin";
        String contrasenya = "123456";
        
        try {
            // Establecer la conexión con la base de datos
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
            
            // Ejecutar la consulta DELETE para eliminar el libro
            String deleteQuery = "DELETE FROM llibres WHERE isbn = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setString(1, isbn);
            
            int filasEliminadas = pstmt.executeUpdate();
            
            if (filasEliminadas > 0) {
                // Redirigir a la página principal si el libro se eliminó con éxito
                response.sendRedirect("llibreria.jsp");
            } else {
                // Si no se eliminó ningún libro, mostrar un mensaje de error
                response.getWriter().println("Error al eliminar el libro.");
            }
            
            pstmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error al eliminar el libro: " + e.getMessage());
        }
    }
}

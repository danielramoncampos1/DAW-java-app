package com.mycompany.llibreriaweb;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;


public class EditarLlibresServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        String isbn = request.getParameter("isbn");
        String titol = request.getParameter("titol");
        String autor = request.getParameter("autor");
        int any = Integer.parseInt(request.getParameter("any"));
        
        // Paràmetres de connexió a la base de dades
        String url = "jdbc:mariadb://192.168.118.181:3306/practica3";
        String usuari = "admin";
        String contrasenya = "123456";
        
        try {
            // Establecer la conexión
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
            
            // Actualizar el libro en la base de datos
            String updateQuery = "UPDATE llibres SET titol = ?, autor = ?, any = ? WHERE isbn = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, titol);
            pstmt.setString(2, autor);
            pstmt.setInt(3, any);
            pstmt.setString(4, isbn);
            
            int filasActualizadas = pstmt.executeUpdate();
            
            if (filasActualizadas > 0) {
                response.sendRedirect("llibreria.jsp"); // Redirigir a la página principal
            } else {
                response.getWriter().println("Error al actualizar el libro.");
            }
            
            pstmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error al actualizar el libro: " + e.getMessage());
        }
    }
}

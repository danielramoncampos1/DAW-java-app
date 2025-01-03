package com.mycompany.llibreriaweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Definición del servlet con su URL de mapeo
@WebServlet(name = "InsertarLlibreServlet", urlPatterns = {"/insertarLlibre"})
public class InsertarLlibreServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Configuración del tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        // Obtener parámetros del formulario
        String titol = request.getParameter("titol");
        String autor = request.getParameter("autor");
        String isbn = request.getParameter("isbn");
        String any = request.getParameter("any");

        try (PrintWriter out = response.getWriter()) {
            // Conexión a la base de datos utilizando la clase Connexio
            try (Connection connection = Connexio.obtenirConnexio()) {
                // SQL para insertar datos en la tabla 'llibres'
                String sql = "INSERT INTO llibres (titol, autor, isbn, any) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titol);
                preparedStatement.setString(2, autor);
                preparedStatement.setString(3, isbn);
                preparedStatement.setInt(4, Integer.parseInt(any));

                // Ejecutar la sentencia
                int rowsInserted = preparedStatement.executeUpdate();

                // Generar respuesta HTML
                out.println("<html>");
                out.println("<head><title>Insertar Libro</title></head>");
                out.println("<body>");
                if (rowsInserted > 0) {
                    out.println("<h2>Libro insertado correctamente</h2>");
                } else {
                    out.println("<h2>Error al insertar el libro</h2>");
                }
                out.println("<a href='insertarLlibre.html'>Volver</a>");
                out.println("</body>");
                out.println("</html>");
                
                // Cerrar recursos
                preparedStatement.close();
            } catch (SQLException e) {
                // Manejo de errores de la base de datos
                e.printStackTrace();
                out.println("<p>Error al conectar con la base de datos: " + e.getMessage() + "</p>");
            }
        } catch (Exception e) {
            // Manejo de errores generales
            e.printStackTrace();
            response.getWriter().println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}

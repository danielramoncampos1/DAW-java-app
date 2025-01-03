package com.mycompany.llibreriaweb;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * Servlet para listar usuarios desde MariaDB.
 */
@WebServlet(name = "UsersServlet", urlPatterns = {"/UsersServlet"})
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parámetros de conexión
        String url = "jdbc:mariadb://192.168.118.181:3306/practica3";
        String usuario = "admin";
        String contraseña = "123456";

        // Establecer el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        // Obtener el flujo de salida para enviar los datos
        PrintWriter out = response.getWriter();

        try {
            // Cargar el controlador MariaDB
            Class.forName("org.mariadb.jdbc.Driver");

            // Establecer conexión
            Connection connection = DriverManager.getConnection(url, usuario, contraseña);
            String query = "SELECT * FROM usuarios";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Generar la salida HTML con Bootstrap
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Usuarios</title>");
            out.println("<link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body class=\"bg-light\">");
            out.println("<div class=\"container mt-5\">");
            out.println("<h2 class=\"text-center mb-4\">Lista de Usuarios</h2>");
            out.println("<table class=\"table table-bordered table-striped\">");
            out.println("<thead class=\"thead-dark\"><tr><th>ID</th><th>Nombre</th><th>Email</th><th>Teléfono</th></tr></thead>");
            out.println("<tbody>");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("nombre");
                String email = resultSet.getString("email");
                String telefono = resultSet.getString("telefono");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + nombre + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + telefono + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            out.println("<p>Error: No se encontró el controlador MariaDB: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p>Error al conectar con la base de datos: " + e.getMessage() + "</p>");
        } finally {
            out.close();
        }
    }
}

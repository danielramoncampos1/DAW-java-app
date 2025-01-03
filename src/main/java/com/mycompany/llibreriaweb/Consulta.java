package com.mycompany.llibreriaweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Definición del servlet con su URL de mapeo
@WebServlet(name = "Consulta", urlPatterns = {"/consulta"})
public class Consulta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Configuración del tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Conexión a la base de datos utilizando la clase Connexio
            Connection connection = Connexio.obtenirConnexio();
            String query = "SELECT * FROM llibres WHERE any = 2001"; // Consulta a la tabla 'llibres'
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Generar respuesta HTML con Bootstrap
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Consulta de Llibres</title>");
            out.println("<link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body class=\"bg-light\">");
            out.println("<div class=\"container mt-5\">");
            out.println("<h2 class=\"text-center mb-4\">Llibres disponibles del 2001</h2>");
            out.println("<table class=\"table table-bordered table-striped\">");
            out.println("<thead class=\"thead-dark\"><tr><th>Títol</th><th>Autor</th><th>ISBN</th><th>Any</th></tr></thead>");
            out.println("<tbody>");

            // Iterar sobre los resultados de la consulta
            while (resultSet.next()) {
                String titol = resultSet.getString("titol");
                String autor = resultSet.getString("autor");
                String isbn = resultSet.getString("isbn");
                int any = resultSet.getInt("any");

                out.println("<tr>");
                out.println("<td>" + titol + "</td>");
                out.println("<td>" + autor + "</td>");
                out.println("<td>" + isbn + "</td>");
                out.println("<td>" + any + "</td>");
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
        } catch (Exception e) {
            // Imprimir el error en la respuesta para facilitar la depuración
            e.printStackTrace();
            response.getWriter().println("<p>Error al realizar la consulta: " + e.getMessage() + "</p>");
        }
    }
}

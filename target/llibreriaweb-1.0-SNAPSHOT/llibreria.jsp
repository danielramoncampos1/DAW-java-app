<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Llibres Disponibles</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="text-center mb-4">Llibres Disponibles</h1>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Títol</th>
                    <th>Autor</th>
                    <th>ISBN</th>
                    <th>Any</th>
                    <th>Accions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String url = "jdbc:mariadb://192.168.118.181:3306/practica3";
                    String usuari = "admin";
                    String contrasenya = "123456";

                    try {
                        Class.forName("org.mariadb.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
                        String consulta = "SELECT * FROM llibres";
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(consulta);

                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String titol = rs.getString("titol");
                            String autor = rs.getString("autor");
                            String isbn = rs.getString("isbn");
                            int any = rs.getInt("any");
                %>
                <tr>
                    <td><%= id %></td>
                    <td><%= titol %></td>
                    <td><%= autor %></td>
                    <td><%= isbn %></td>
                    <td><%= any %></td>
                    <td>
                        <form action="editarLlibreForm.jsp" method="get" style="display:inline;">
                            <input type="hidden" name="isbn" value="<%= isbn %>">
                            <button type="submit" class="btn btn-success">Editar</button>
                        </form>
                        <form action="eliminarLlibres" method="post" style="display:inline;">
                            <input type="hidden" name="isbn" value="<%= isbn %>">
                            <button type="submit" class="btn btn-danger">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                        rs.close();
                        stmt.close();
                        conn.close();
                    } catch (ClassNotFoundException e) {
                        out.println("<tr><td colspan='6'>Error: No s'ha trobat el controlador MariaDB</td></tr>");
                    } catch (SQLException e) {
                        out.println("<tr><td colspan='6'>Error al connectar amb la base de dades: " + e.getMessage() + "</td></tr>");
                    }
                %>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>

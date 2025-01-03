<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Llibre</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Editar Llibre</h1>
        <form action="editarLlibres" method="post">
            <%
                String isbn = request.getParameter("isbn");
                String url = "jdbc:mariadb://192.168.118.181:3306/practica3";
                String usuari = "admin";
                String contrasenya = "123456";

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
                    String consulta = "SELECT * FROM llibres WHERE isbn = ?";
                    PreparedStatement pstmt = conn.prepareStatement(consulta);
                    pstmt.setString(1, isbn);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        String titol = rs.getString("titol");
                        String autor = rs.getString("autor");
                        int any = rs.getInt("any");
            %>
            <input type="hidden" name="isbn" value="<%= isbn %>">
            <div class="mb-3">
                <label for="titol" class="form-label">Títol:</label>
                <input type="text" id="titol" name="titol" class="form-control" value="<%= titol %>" required>
            </div>
            <div class="mb-3">
                <label for="autor" class="form-label">Autor:</label>
                <input type="text" id="autor" name="autor" class="form-control" value="<%= autor %>" required>
            </div>
            <div class="mb-3">
                <label for="any" class="form-label">Any:</label>
                <input type="number" id="any" name="any" class="form-control" value="<%= any %>" required>
            </div>
            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            <%
                    }
                    rs.close();
                    pstmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException e) {
                    out.println("<p class='text-danger'>Error al obtener los datos del libro: " + e.getMessage() + "</p>");
                }
            %>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>

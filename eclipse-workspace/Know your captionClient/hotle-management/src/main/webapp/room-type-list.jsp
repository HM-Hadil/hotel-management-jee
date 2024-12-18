<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.RoomType"%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Types de Chambres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Types de Chambres</h1>
            <a href="addRoomType.jsp" class="btn btn-primary">
                <i class="fas fa-plus"></i> Ajouter un type
            </a>
        </div>

        <div class="card shadow-sm">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>Label</th>
                                <th>Capacité</th>
                                <th>Prix</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                        List<RoomType> rooms = (List<RoomType>) request.getAttribute("rooms");
                        if (rooms != null && !rooms.isEmpty()) {
                            for (RoomType room : rooms) {
                        %>
                            <tr>
                                <td><%= room.getLabel() %></td>
                                <td><%= room.getCapacity() %> personnes</td>
                                <td><%= room.getPrice() %> €</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="EditRoomTypeList?id=<%= room.getId() %>" 
                                           class="btn btn-warning btn-sm">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        
                                        <a href="DeleteRoomTypeServlet?id=<%= room.getId() %>" 
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Confirmer la suppression ?')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        <%
                            }
                        } else {
                        %>
                            <tr>
                                <td colspan="4" class="text-center">
                                    <i class="fas fa-info-circle"></i> Aucune chambre disponible
                                </td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
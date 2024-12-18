<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.Hotel"%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Hôtels</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Liste des Hôtels</h1>
            <a href="addHotel.jsp" class="btn btn-primary">
                <i class="fas fa-plus"></i> Ajouter un Hôtel
            </a>
        </div>

        <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
            <div class="alert alert-danger" role="alert">
                <%= errorMessage %>
            </div>
        <% } %>

        <div class="row row-cols-1 row-cols-md-3 g-4">
            <%
            List<Hotel> hotels = (List<Hotel>) request.getAttribute("hotels");
            if (hotels != null && !hotels.isEmpty()) {
                for (Hotel hotel : hotels) {
            %>
                <div class="col">
                    <div class="card h-100 shadow-sm">
                        <img src="<%= hotel.getImagePath() != null ? hotel.getImagePath() : "images/default-hotel.jpg" %>" 
                             class="card-img-top" alt="Image de <%= hotel.getName() %>" 
                             style="height: 200px; object-fit: cover;">
                        <div class="card-body">
                            <h5 class="card-title"><%= hotel.getName() %></h5>
                            <p class="card-text">
                                <i class="fas fa-map-marker-alt"></i> <%= hotel.getCity() %><br>
                                <i class="fas fa-star text-warning"></i> <%= hotel.getStars() %> étoiles<br>
                                <%= hotel.getDescription() %>
                            </p>
                        </div>
                        <div class="card-footer bg-transparent">
                            <div class="d-flex justify-content-between">
                                <a href="EditHotelServlet?id=<%= hotel.getId() %>" class="btn btn-warning btn-sm">
                                    <i class="fas fa-edit"></i> Modifier
                                </a>
                                <a href="DeleteHotelServlet?id=<%= hotel.getId() %>" 
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet hôtel ?')">
                                    <i class="fas fa-trash"></i> Supprimer
                                </a>
                                <a href="HotelDetailsServlet?id=<%= hotel.getId() %>" class="btn btn-info btn-sm">
                                    <i class="fas fa-eye"></i> Détails
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            <%
                }
            } else {
            %>
                <div class="col-12">
                    <div class="alert alert-info" role="alert">
                        <i class="fas fa-info-circle"></i> Aucun hôtel trouvé.
                    </div>
                </div>
            <% } %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
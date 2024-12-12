<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="java.util.List" %>
<%@ page import="beans.Hotel" %>
<!DOCTYPE html>
<html>
<head>
<title>Liste des Hôtels</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Liste des Hôtels</h1>

    <% 
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) { 
    %>
        <div class="alert alert-danger"><%= errorMessage %></div>
    <% } %>

    <a href="addHotel.jsp" class="btn btn-primary mb-3">Ajouter un Hôtel</a>

    <div class="row">
    <% 
        List<Hotel> hotels = (List<Hotel>) request.getAttribute("hotels");
        if (hotels != null && !hotels.isEmpty()) {
            for (Hotel hotel : hotels) { 
    %>
        <div class="col-md-4 mb-4">
            <div class="card">
                <img src="<%= hotel.getImagePath() %>" class="card-img-top" alt="Image de l'hôtel" style="height: 200px; object-fit: cover;">
                <div class="card-body">
                    <h5 class="card-title"><%= hotel.getName() %></h5>
                    <p class="card-text">
                        <strong>Ville :</strong> <%= hotel.getCity() %><br>
                        <strong>Étoiles :</strong> <%= hotel.getStars() %><br>
                        <strong>Description :</strong> <%= hotel.getDescription() %>
                    </p>
                    <a href="EditHotelServlet?id=<%= hotel.getId() %>" class="btn btn-warning btn-sm">Modifier</a>
                    <a href="DeleteHotelServlet?id=<%= hotel.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet hôtel ?')">Supprimer</a>
                    <a href="HotelDetailsServlet?id=<%= hotel.getId() %>">Voir Détails</a>
             
                </div>
            </div>
        </div>
    <% 
            } 
        } else {
    %>
        <div class="col-12">
            <p class="text-center text-muted">Aucun hôtel trouvé.</p>
        </div>
    <% 
        }
    %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

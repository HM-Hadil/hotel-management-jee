<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.Hotel, beans.RoomType" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>Modifier un Hôtel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Modifier un Hôtel</h1>

    <% 
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null && !errorMessage.isEmpty()) { 
    %>
        <div class="alert alert-danger"><%= errorMessage %></div>
    <% } %>

    <% 
    Hotel hotel = (Hotel) request.getAttribute("hotel");
    if (hotel == null) { 
    %>
        <div class="alert alert-danger">Aucun hôtel trouvé</div>
    <% 
        return;
    } 
    %>

    <form action="EditHotelServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<%= hotel.getId() %>">
        
        <div class="mb-3">
            <label for="name" class="form-label">Nom de l'hôtel</label>
            <input type="text" class="form-control" id="name" name="name" value="<%= hotel.getName() %>" required>
        </div>
        
        <div class="mb-3">
            <label for="city" class="form-label">Ville</label>
            <input type="text" class="form-control" id="city" name="city" value="<%= hotel.getCity() %>" required>
        </div>
        
        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3"><%= hotel.getDescription() != null ? hotel.getDescription() : "" %></textarea>
        </div>
        
        <div class="mb-3">
            <label for="stars" class="form-label">Nombre d'étoiles</label>
            <input type="number" class="form-control" id="stars" name="stars" value="<%= hotel.getStars() %>" min="1" max="5" required>
        </div>

        <div class="mb-3">
            <label for="image" class="form-label">Image de l'Hôtel</label>
            <input type="file" class="form-control" id="image" name="image">
            <% if (hotel.getImagePath() != null) { %>
                <div class="mt-3">
                    <img src="<%= hotel.getImagePath() %>" alt="Image de l'Hôtel" class="img-fluid" width="150">
                </div>
            <% } %>
        </div>
        
        <div class="mb-3">
            <label for="roomType" class="form-label">Sélectionner un Type de Chambre</label>
            <select class="form-control" id="roomType" name="roomType" required>
                <% 
                List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("roomTypes");
                if (roomTypes != null) {
                    for (RoomType roomType : roomTypes) {
                %>
                    <option value="<%= roomType.getId() %>" <%= hotel.getRoomTypes().stream().anyMatch(rt -> rt.getId() == roomType.getId()) ? "selected" : "" %> >
                        <%= roomType.getLabel() %>
                    </option>
                <% 
                    }
                }
                %>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Mettre à jour</button>
        <a href="ListHotelsServlet" class="btn btn-secondary">Annuler</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

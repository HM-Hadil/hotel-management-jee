<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="beans.RoomType" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Hôtel</title>
    <!-- Lien vers Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #343a40;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Ajouter un Hôtel</h1>
        <form action="AddHotelServlet" method="post" enctype="multipart/form-data">
            <!-- Nom de l'hôtel -->
            <div class="mb-3">
                <label for="hotelName" class="form-label">Nom de l'hôtel</label>
                <input type="text" class="form-control" id="hotelName" name="hotelName" placeholder="Entrez le nom de l'hôtel" required>
            </div>

            <!-- Ville -->
            <div class="mb-3">
                <label for="city" class="form-label">Ville</label>
                <input type="text" class="form-control" id="city" name="city" placeholder="Entrez la ville" required>
            </div>

            <!-- Description -->
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" rows="4" placeholder="Entrez une description" required></textarea>
            </div>

            <!-- Étoiles -->
            <div class="mb-3">
                <label for="stars" class="form-label">Étoiles</label>
                <input type="number" class="form-control" id="stars" name="stars" min="1" max="5" placeholder="Entrez le nombre d'étoiles (1-5)" required>
            </div>

            <!-- Type de chambre -->
             <div class="form-group">
                    <label>Types de Chambres Disponibles :</label>
                    <div class="room-types-container">
                        <% 
                        List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("roomTypes");
                        if (roomTypes != null && !roomTypes.isEmpty()) {
                            for (RoomType roomType : roomTypes) {
                        %>
                            <div class="room-type-option">
                                <input type="checkbox" name="roomTypes" value="<%= roomType.getId() %>" 
                                       id="roomType<%= roomType.getId() %>">
                                <label for="roomType<%= roomType.getId() %>">
                                    <%= roomType.getLabel() %> - Capacité: <%= roomType.getCapacity() %> - 
                                    Prix: <%= roomType.getPrice() %>€
                                </label>
                            </div>
                        <% 
                            }
                        }
                        %>
                    </div>
                </div>
                

            <!-- Image -->
            <div class="mb-3">
                <label for="image" class="form-label">Image</label>
                <input type="file" class="form-control" id="image" name="image" accept="image/*">
            </div>

            <!-- Bouton soumettre -->
            <div class="d-grid">
                <button type="submit" class="btn btn-primary">Ajouter l'hôtel</button>
            </div>
        </form>
    </div>

    <!-- Lien vers le JS de Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
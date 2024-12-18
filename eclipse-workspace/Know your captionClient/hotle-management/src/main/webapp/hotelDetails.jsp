<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.RoomType" %>
<%@ page import="beans.Hotel" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails de l'Hôtel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #007BFF;
            --secondary-color: #6c757d;
            --background-light: #f4f4f4;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: var(--background-light);
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            color: var(--primary-color);
            text-align: center;
        }

        .hotel-info {
            margin-bottom: 30px;
        }

        .hotel-info img {
            max-width: 100%;
            height: auto;
            object-fit: cover;
            border-radius: 8px;
        }

        .details {
            margin-top: 15px;
        }

        .room-types {
            margin-top: 30px;
        }

        .room-type {
            background-color: #f9f9f9;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 15px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease-in-out;
        }

        .room-type:hover {
            transform: scale(1.05);
        }

        .room-type h3 {
            color: var(--primary-color);
        }

        .btn-back {
            display: inline-block;
            padding: 10px 20px;
            background-color: var(--primary-color);
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 20px;
            transition: background-color 0.3s;
        }

        .btn-back:hover {
            background-color: #0056b3;
        }

        .alert {
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Détails de l'Hôtel</h1>

    <% 
        Hotel hotel = (Hotel) request.getAttribute("hotel");
        if (hotel != null) {
    %>
        <div class="hotel-info row">
            <!-- Affichage de l'image de l'hôtel -->
            <div class="col-md-4">
                <img src="<%= hotel.getImagePath() %>" alt="Image de l'hôtel">
            </div>
            <div class="details col-md-8">
                <h2><%= hotel.getName() %></h2>
                <p><strong>Ville:</strong> <%= hotel.getCity() %></p>
                <p><strong>Description:</strong> <%= hotel.getDescription() %></p>
                <p><strong>Étoiles:</strong> <%= hotel.getStars() %></p>
            </div>
        </div>

        <div class="room-types">
            <h2>Types de Chambres</h2>
            <% 
                List<RoomType> roomTypes = hotel.getRoomTypes();
                if (roomTypes != null && !roomTypes.isEmpty()) {
                    for (RoomType roomType : roomTypes) {
            %>
                <div class="room-type">
                    <h3><i class="fas fa-bed"></i> <%= roomType.getLabel() %></h3>
                    <p><strong>Capacité:</strong> <%= roomType.getCapacity() %> personnes</p>
                    <p><strong>Prix:</strong> <%= roomType.getPrice() %> €</p>
                </div>
            <% 
                    }
                } else {
            %>
                <p class="text-center alert alert-info">Aucun type de chambre disponible.</p>
            <% 
                }
            %>
        </div>

        <a href="index.jsp" class="btn-back">Retour à la liste des hôtels</a>
    <% 
        } else {
    %>
        <p class="text-center alert alert-warning">Aucun hôtel trouvé.</p>
    <% 
        }
    %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

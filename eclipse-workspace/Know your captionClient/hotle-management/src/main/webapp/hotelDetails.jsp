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
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            color: #007BFF;
        }

        .hotel-info {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .hotel-info img {
            width: 250px;
            height: 200px;
            object-fit: cover;
            border-radius: 8px;
        }

        .details {
            flex: 1;
            margin-left: 20px;
        }

        .room-types {
            margin-top: 30px;
        }

        .room-type {
            background-color: #f9f9f9;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 15px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }

        .room-type h3 {
            color: #007BFF;
        }

        .room-type p {
            margin: 5px 0;
        }

        .btn-back {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 20px;
        }

        .btn-back:hover {
            background-color: #0056b3;
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
        <div class="hotel-info">
            <!-- Affichage de l'image de l'hôtel -->
            <img src="<%= hotel.getImagePath() %>" alt="Image de l'hôtel">
            <div class="details">
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
                for (RoomType roomType : roomTypes) {
            %>
                <div class="room-type">
                    <h3><%= roomType.getLabel() %></h3>
                    <p><strong>Capacité:</strong> <%= roomType.getCapacity() %> personnes</p>
                    <p><strong>Prix:</strong> <%= roomType.getPrice() %> €</p>
                </div>
            <% 
                }
            %>
        </div>

        <a href="index.jsp" class="btn-back">Retour à la liste des hôtels</a>
    <% 
        } else {
    %>
        <p>Aucun hôtel trouvé.</p>
    <% 
        }
    %>

</div>

</body>
</html>

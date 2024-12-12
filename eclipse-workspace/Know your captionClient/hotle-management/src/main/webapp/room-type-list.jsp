<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.RoomType" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Types de Chambre</title>
    
    <!-- Lien vers le CSS de Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
    
    <!-- Vous pouvez aussi ajouter votre propre fichier CSS si nécessaire -->
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
    <h2>Liste des Chambres</h2>
   <table class="table table-bordered table-striped">
    <thead class="thead-dark">
        <tr>
            <th>Label</th>
            <th>Capacité</th>
            <th>Prix</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>

        <% 
        // Récupération de l'attribut 'rooms' passé dans la requête
        List<RoomType> rooms = (List<RoomType>) request.getAttribute("rooms");

        // Si la liste des chambres n'est pas vide ou nulle, on la parcourt
        if (rooms != null && !rooms.isEmpty()) {
            for (RoomType room : rooms) {
        %>

            <tr>
                <td><%= room.getLabel() %></td>
                <td><%= room.getCapacity() %></td>
                <td><%= room.getPrice() %></td>
       <td>
    <!-- Formulaire pour supprimer la chambre -->
    <form action="DeleteTypeRoomServlet" method="post" style="display:inline;">
        <input type="hidden" name="action" value="deleteRoom">
        <input type="hidden" name="roomId" value="<%= room.getId() %>">
        <button type="submit" class="btn btn-danger btn-sm">Supprimer</button>
    </form>

 <a href="EditRoomTypeList?id=<%= room.getId() %>" class="btn btn-sm btn-warning">Modifier</a>

</td>

            </tr>

        <% 
            }
        } else {
        %>
            <tr>
                <td colspan="4">Aucune chambre disponible.</td>
            </tr>
        <% 
        }
        %>
    </table>

    <!-- Lien vers le JS de Bootstrap -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>

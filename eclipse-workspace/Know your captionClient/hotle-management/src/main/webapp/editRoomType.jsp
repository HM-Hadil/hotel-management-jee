<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="beans.RoomType" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier Type de Chambre</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
</head>
<body>
    <div class="container mt-5">
        <h2>Modifier Type de Chambre</h2>
        <%
            RoomType roomType = (RoomType) request.getAttribute("roomType");
            if (roomType != null) {
        %>
        <form action="EditRoomTypeList" method="post">
            <input type="hidden" name="id" value="<%= roomType.getId() %>" />
            <div class="form-group">
                <label for="label">Label</label>
                <input type="text" class="form-control" id="label" name="label" value="<%= roomType.getLabel() %>" required />
            </div>
            <div class="form-group">
                <label for="capacity">Capacité</label>
                <input type="number" class="form-control" id="capacity" name="capacity" value="<%= roomType.getCapacity() %>" required />
            </div>
            <div class="form-group">
                <label for="price">Prix</label>
                <input type="number" class="form-control" id="price" name="price" value="<%= roomType.getPrice() %>" required />
            </div>
            <button type="submit" class="btn btn-primary">Enregistrer</button>
        </form>
        <% } else { %>
        <div class="alert alert-danger">Le type de chambre spécifié est introuvable.</div>
        <% } %>
    </div>
</body>
</html>

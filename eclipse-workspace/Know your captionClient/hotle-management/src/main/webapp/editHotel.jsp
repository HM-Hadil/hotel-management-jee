<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.Hotel, beans.RoomType, java.util.List"%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier l'hôtel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow">
                    <div class="card-body">
                        <h1 class="card-title text-center mb-4">
                            <i class="fas fa-hotel"></i> Modifier l'hôtel
                        </h1>

                        <%-- Messages d'erreur --%>
                        <% if (request.getAttribute("errorMessage") != null) { %>
                            <div class="alert alert-danger">
                                <%= request.getAttribute("errorMessage") %>
                            </div>
                        <% } %>

                        <%-- Vérification de l'hôtel --%>
                        <% 
                        Hotel hotel = (Hotel) request.getAttribute("hotel");
                        if (hotel == null) {
                        %>
                            <div class="alert alert-danger">
                                <i class="fas fa-exclamation-triangle"></i> Hôtel non trouvé
                            </div>
                        <% } else { %>

                        <form action="EditHotelServlet" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="id" value="<%= hotel.getId() %>">

                            <div class="mb-3">
                                <label for="name" class="form-label">
                                    <i class="fas fa-signature"></i> Nom de l'hôtel
                                </label>
                                <input type="text" class="form-control" id="name" name="name" 
                                       value="<%= hotel.getName() %>" required>
                            </div>

                            <div class="mb-3">
                                <label for="city" class="form-label">
                                    <i class="fas fa-city"></i> Ville
                                </label>
                                <input type="text" class="form-control" id="city" name="city" 
                                       value="<%= hotel.getCity() %>" required>
                            </div>

                            <div class="mb-3">
                                <label for="stars" class="form-label">
                                    <i class="fas fa-star"></i> Étoiles
                                </label>
                                <select class="form-control" id="stars" name="stars" required>
                                    <% for(int i = 1; i <= 5; i++) { %>
                                        <option value="<%= i %>" <%= hotel.getStars() == i ? "selected" : "" %>>
                                            <%= i %> étoile<%= i > 1 ? "s" : "" %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="description" class="form-label">
                                    <i class="fas fa-align-left"></i> Description
                                </label>
                                <textarea class="form-control" id="description" name="description" 
                                          rows="4" required><%= hotel.getDescription() %></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="image" class="form-label">
                                    <i class="fas fa-image"></i> Image
                                </label>
                                <input type="file" class="form-control" id="image" name="image" 
                                       accept="image/*">
                                
                                <% if (hotel.getImagePath() != null && !hotel.getImagePath().isEmpty()) { %>
                                    <div class="mt-2">
                                        <img src="<%= hotel.getImagePath() %>" 
                                             alt="Image actuelle" 
                                             class="img-thumbnail" 
                                             style="max-height: 200px;">
                                    </div>
                                <% } %>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Enregistrer
                                </button>
                                <a href="ListHotelsServlet" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left"></i> Retour
                                </a>
                            </div>
                        </form>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
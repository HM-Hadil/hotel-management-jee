<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="beans.Hotel" %>
<%@ page import="beans.RoomType" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <title>Recherche d'Hôtels</title>
      <style>
        :root {
            --primary-color: #2c3e50;
            --secondary-color: #e74c3c;
            --accent-color: #3498db;
            --background-color: #f5f6fa;
            --text-color: #2c3e50;
            --card-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            --header-height: 70px;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--background-color);
            color: var(--text-color);
            line-height: 1.6;
            padding-top: var(--header-height);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        /* Header Styles */
        .main-header {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            height: var(--header-height);
            background-color: var(--primary-color);
            color: white;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            z-index: 1000;
        }

        .logo {
            font-size: 1.5rem;
            font-weight: bold;
            color: white;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .nav-links {
            display: flex;
            gap: 2rem;
            align-items: center;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .nav-links a:hover {
            color: var(--accent-color);
        }

        .auth-buttons a {
            padding: 8px 16px;
            border-radius: 5px;
            transition: all 0.3s ease;
        }

        .login-btn {
            background-color: transparent;
            border: 1px solid white;
        }

        .register-btn {
            background-color: var(--secondary-color);
        }

        .login-btn:hover {
            background-color: rgba(255,255,255,0.1);
        }

        .register-btn:hover {
            background-color: #c0392b;
        }

        /* Main Content Styles */
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 20px;
            flex: 1;
        }

        .main-title {
            text-align: center;
            margin-bottom: 2rem;
            color: var(--primary-color);
        }

        .search-form {
            background-color: white;
            border-radius: 15px;
            box-shadow: var(--card-shadow);
            padding: 2rem;
            margin-bottom: 2rem;
        }

        .search-form form {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
        }

        .form-group label {
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 0.5rem;
            display: block;
        }

        .form-group select, 
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
        }

        .form-group select:focus, 
        .form-group input:focus {
            border-color: var(--accent-color);
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
        }

        .search-btn {
            background-color: var(--secondary-color);
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            width: 100%;
            margin-top: 1.5rem;
        }

        .search-btn:hover {
            background-color: #c0392b;
            transform: translateY(-2px);
        }

        /* Hotels Grid */
        .hotels-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 2rem;
            padding: 1rem 0;
        }

        .hotel-card {
            background-color: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: var(--card-shadow);
            transition: transform 0.3s ease;
        }

        .hotel-card:hover {
            transform: translateY(-5px);
        }

        .hotel-card-header {
            background-color: var(--primary-color);
            padding: 1rem;
            text-align: center;
        }

        .hotel-card-header h2 {
            color: white;
            font-size: 1.5rem;
        }

        .hotel-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .hotel-card-body {
            padding: 1.5rem;
        }

        .hotel-details p {
            margin-bottom: 0.5rem;
        }

        .room-types {
            margin-top: 1rem;
            border-top: 1px solid #eee;
            padding-top: 1rem;
        }

        .room-type-item {
            background-color: #f8f9fa;
            padding: 0.8rem;
            border-radius: 8px;
            margin-bottom: 0.5rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        /* Footer Styles */
        .footer {
            background-color: var(--primary-color);
            color: white;
            padding: 2rem 0;
            margin-top: auto;
        }

        .footer-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 2rem;
        }

        .footer-section h3 {
            margin-bottom: 1rem;
            font-size: 1.2rem;
        }

        .footer-section p {
            margin-bottom: 0.5rem;
        }

        .social-links {
            display: flex;
            gap: 1rem;
            margin-top: 1rem;
        }

        .social-links a {
            color: white;
            font-size: 1.5rem;
            transition: color 0.3s ease;
        }

        .social-links a:hover {
            color: var(--accent-color);
        }

        .footer-bottom {
            text-align: center;
            padding-top: 2rem;
            margin-top: 2rem;
            border-top: 1px solid rgba(255,255,255,0.1);
        }

        @media (max-width: 768px) {
            .nav-links {
                gap: 1rem;
            }

            .auth-buttons {
                display: flex;
                flex-direction: column;
                gap: 0.5rem;
            }

            .main-header {
                padding: 1rem;
            }
        }
        .nav-left {
    display: flex;
    align-items: center;
    gap: 2rem;
}

.nav-left .nav-link {
    color: white;
    text-decoration: none;
    font-size: 1rem;
    font-weight: bold;
    padding-left: 1rem;
}

.nav-left .nav-link:hover {
    color: var(--accent-color);
}
     .auth-buttons {
    display: flex;
    gap: 10px; /* Espace entre les boutons */
}

.auth-buttons a {
    padding: 8px 16px;
    border-radius: 5px;
    transition: all 0.3s ease;
    font-weight: bold;
    text-align: center; /* Alignement centré */
}

.login-btn {
    background-color: transparent;
    border: 1px solid white;
    color: white;
    text-decoration: none;
}

.register-btn {
    background-color: var(--secondary-color);
    color: white;
    text-decoration: none;
}

.login-btn:hover {
    background-color: rgba(255, 255, 255, 0.2);
}

.register-btn:hover {
    background-color: #c0392b;
}
        
    </style>
</head>
<body>
<header class="main-header">
    <a href="hotels.jsp" class="logo">
        <i class="fas fa-hotel"></i>
        HotelBooking
    </a>
    <nav class="nav-links">
        <a href="hotels.jsp" class="nav-link"><i class="fas fa-home"></i> Accueil</a>
        <div class="auth-buttons">
            <a href="login.jsp" class="login-btn"><i class="fas fa-sign-in-alt"></i> Connexion</a>
            <a href="register.jsp" class="register-btn"><i class="fas fa-user-plus"></i> Inscription</a>
        </div>
    </nav>
</header>

    <div class="container">
        <h1 class="main-title">Découvrez nos Hôtels</h1>
        

        <div class="search-form">
        <form action="HotelSearchServlet" method="get">     
           <div class="form-group">
                    <label>Nom de l'Hôtel :</label>
                    <input type="text" name="hotelName" placeholder="Rechercher par nom">
                </div>
                <div class="form-group">
                    <label>Ville :</label>
                    <select name="city">
                        <option value="">Toutes les villes</option>
                        <% 
                        List<String> cities = (List<String>) request.getAttribute("cities");
                        if (cities != null) {
                            for (String city : cities) {
                        %>
                            <option value="<%= city %>"><%= city %></option>
                        <% 
                            }
                        }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label>Nombre d'Étoiles :</label>
                    <select name="stars">
                        <option value="">Toutes les étoiles</option>
                        <% 
                        List<Integer> availableStars = (List<Integer>) request.getAttribute("availableStars");
                        if (availableStars != null) {
                            for (Integer star : availableStars) {
                        %>
                            <option value="<%= star %>"><%= star %> ★</option>
                        <% 
                            }
                        }
                        %>
                    </select>
                </div>
                <div class="form-group">
    <label>Type de Chambre :</label>
    <select name="roomType">
        <option value="">Tous les types</option>
        <% 
        List<String> roomTypes = (List<String>) request.getAttribute("roomTypes");
        if (roomTypes != null) {
            for (String type : roomTypes) {
        %>
            <option value="<%= type %>" <%= type.equals(request.getAttribute("searchRoomType")) ? "selected" : "" %>><%= type %></option>
        <% 
            }
        }
        %>
    </select>
    
</div>
                <div class="form-group">
                    <label>Prix Minimum :</label>
                    <input type="number" name="minPrice" 
                           min="<%= request.getAttribute("minPrice") %>" 
                           max="<%= request.getAttribute("maxPrice") %>" 
                           placeholder="Prix minimum">
                </div>
                <div class="form-group">
                    <label>Prix Maximum :</label>
                    <input type="number" name="maxPrice" 
                           min="<%= request.getAttribute("minPrice") %>" 
                           max="<%= request.getAttribute("maxPrice") %>" 
                           placeholder="Prix maximum">
                </div>
                <button type="submit" class="search-btn">Rechercher</button>
            </form>
        </div>

        <!-- Ajoutez ce code après votre div search-form -->
<div class="hotels-container">
    <% 
    List<Hotel> hotels = (List<Hotel>) request.getAttribute("hotels");
    if (hotels != null && !hotels.isEmpty()) {
        for (Hotel hotel : hotels) {
    %>
        <div class="hotel-card">
            <div class="hotel-card-header">
                <h2><%= hotel.getName() %></h2>
            </div>
            <img src="<%= hotel.getImagePath() %>" alt="<%= hotel.getName() %>">
         <div class="hotel-card-body">
    <p><strong>Ville :</strong> <%= hotel.getCity() %></p>
    <p><strong>Étoiles :</strong> <%= hotel.getStars() %> ★</p>
    <p><strong>Types de chambre :</strong> 
    <% 
    if (hotel.getRoomTypes() != null && !hotel.getRoomTypes().isEmpty()) {
        for (int i = 0; i < hotel.getRoomTypes().size(); i++) {
            out.print(hotel.getRoomTypes().get(i).getLabel());
            if (i < hotel.getRoomTypes().size() - 1) {
                out.print(", ");
            }
        }
    } else {
        out.print("Non spécifié");
    }
    %>
    </p>
     <a href="HotelDetailsServlet?id=<%= hotel.getId() %>" class="btn btn-info btn-sm">
                                    <i class="fas fa-eye"></i> Voir Détails
                                </a>
</div>
        </div>
    <% 
        }
    } else {
    %>
        <p>Aucun hôtel trouvé.</p>
    <% 
    }
    %>
</div>

    <!-- Footer -->
    <footer class="footer">
        <div class="footer-content">
            <div class="footer-section">
                <h3>À propos de nous</h3>
                <p>HotelBooking vous propose les meilleures offres d'hébergement pour vos voyages et vacances.</p>
            </div>
            <div class="footer-section">
                <h3>Contact</h3>
                <p>Email: contact@hotelbooking.com</p>
                <p>Téléphone: +33 1 23 45 67 89</p>
            </div>
            <div class="footer-section">
                <h3>Suivez-nous</h3>
                <div class="social-links">
                    <a href="#"><i class="fab fa-facebook"></i></a>
                    <a href="#"><i class="fab fa-twitter"></i></a>
                    <a href="#"><i class="fab fa-instagram"></i></a>
                    <a href="#"><i class="fab fa-linkedin"></i></a>
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2024 HotelBooking. Tous droits réservés.</p>
        </div>
    </footer>
</body>
</html>
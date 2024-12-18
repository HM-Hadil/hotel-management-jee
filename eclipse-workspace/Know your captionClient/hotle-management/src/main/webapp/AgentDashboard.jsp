<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Agent</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #4a90e2;
            --secondary-color: #f5f6fa;
            --accent-color: #5c6ac4;
            --text-color: #2d3436;
            --sidebar-width: 280px;
        }

        /* Layout */
        body {
            min-height: 100vh;
            background-color: #f8f9fa;
        }

        .dashboard-container {
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar Styles */
        .sidebar {
            width: var(--sidebar-width);
            background: linear-gradient(180deg, #2c3e50 0%, #3498db 100%);
            color: white;
            padding: 2rem;
            position: fixed;
            height: 100vh;
            left: 0;
            top: 0;
            overflow-y: auto;
        }

        .profile-section {
            text-align: center;
            padding: 1.5rem 0;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            margin-bottom: 2rem;
        }

        .profile-image {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            margin-bottom: 1rem;
            border: 3px solid white;
        }

        .nav-menu {
            list-style: none;
            padding: 0;
        }

        .nav-item {
            margin-bottom: 0.5rem;
        }

        .nav-link {
            display: flex;
            align-items: center;
            padding: 0.75rem 1rem;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: all 0.3s ease;
        }

        .nav-link:hover, .nav-link.active {
            background-color: rgba(255, 255, 255, 0.1);
            transform: translateX(5px);
        }

        .nav-link i {
            margin-right: 10px;
            width: 20px;
        }

        /* Main Content */
        .main-content {
            margin-left: var(--sidebar-width);
            padding: 2rem;
            width: calc(100% - var(--sidebar-width));
        }

        /* Cards */
        .dashboard-card {
            background: white;
            border-radius: 15px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .dashboard-card:hover {
            transform: translateY(-5px);
        }

        /* Hotel List */
        .hotel-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 1.5rem;
        }

        .hotel-card {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            transition: transform 0.3s ease;
        }

        .hotel-card:hover {
            transform: translateY(-5px);
        }

        .hotel-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        /* Forms */
        .custom-form {
            background: white;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e9ecef;
            padding: 0.75rem;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(74, 144, 226, 0.25);
        }

        /* Tables */
        .custom-table {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .custom-table th {
            background-color: var(--primary-color);
            color: white;
        }

        /* Buttons */
        .dashboard-btn {
            padding: 0.75rem 1.5rem;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .dashboard-btn:hover {
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="profile-section">
                <h4>
                    <%
                        String agentName = (String) session.getAttribute("agentName");
                        out.print(agentName != null ? agentName : "Agent");
                    %>
                </h4>
                <p class="text-light">Agent Hôtelier</p>
            </div>

            <ul class="nav-menu">
             
                <li class="nav-item">
                    <a href="#hotels" class="nav-link" data-content="hotels">
                        <i class="fas fa-hotel"></i>
                        Gestion des Hôtels
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#rooms" class="nav-link" data-content="rooms">
                        <i class="fas fa-bed"></i>
                        Types de Chambres
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#add-hotel" class="nav-link" data-content="add-hotel">
                        <i class="fas fa-plus-circle"></i>
                        Ajouter un Hôtel
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#add-room" class="nav-link" data-content="add-room">
                        <i class="fas fa-door-open"></i>
                        Ajouter un Type de Chambre
                    </a>
                </li>
                <li class="nav-item mt-4">
                    <a href="login.jsp" class="nav-link text-danger">
                        <i class="fas fa-sign-out-alt"></i>
                        Déconnexion
                    </a>
                </li>
            </ul>
        </aside>

        <!-- Main Content -->
        <main class="main-content">
            <div id="main-content">
                <!-- Le contenu sera chargé dynamiquement ici -->
            </div>
        </main>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const mainContent = document.getElementById('main-content');
            const navLinks = document.querySelectorAll('.nav-link');

            // Fonction pour charger le contenu
            const loadContent = (contentType) => {
                let url;
                switch(contentType) {
                   
                    case 'hotels':
                        url = 'ListHotelsServlet';
                        break;
                    case 'rooms':
                        url = 'RoomTypeListServlet';
                        break;
                    case 'add-hotel':
                        url = 'AddHotelServlet';
                        break;
                    case 'add-room':
                        url = 'addRoomType.jsp';
                        break;
                    default:
                        url = 'ListHotelsServlet';
                }

                fetch(url)
                    .then(response => response.text())
                    .then(html => {
                        mainContent.innerHTML = html;
                    })
                    .catch(error => {
                        mainContent.innerHTML = `<div class="alert alert-danger">Erreur de chargement: ${error.message}</div>`;
                    });
            };

            // Gestionnaire d'événements pour les liens
            navLinks.forEach(link => {
                link.addEventListener('click', (e) => {
                    e.preventDefault();
                    
                    // Mise à jour de la classe active
                    navLinks.forEach(l => l.classList.remove('active'));
                    link.classList.add('active');

                    // Chargement du contenu
                    const contentType = link.getAttribute('data-content');
                    loadContent(contentType);
                });
            });

            // Charger le tableau de bord par défaut
            loadContent('ListHotelsServlet');
        });
    </script>
</body>
</html>
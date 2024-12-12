<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agent Dashboard</title>
    <link rel="stylesheet" href="styles.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
                <div class="sidebar-sticky">
                    <h4>Bienvenue, Agent !</h4>
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="dashboardContent.jsp">
                                Tableau de bord
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="AddHotelServlet">
                                Ajouter un Hôtel
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="ListHotelsServlet">
                                Liste des Hôtels
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="addRoomType.jsp">
                                Ajouter un Type de Chambre
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="RoomTypeListServlet">
                                Liste des Types de Chambres
                            </a>
                        </li>
                    </ul>
                    <button class="btn btn-danger mt-3" onclick="window.location.href='logout.jsp'">Déconnexion</button>
                </div>
            </nav>

            <!-- Main Content -->
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                <div id="main-content">
                    <!-- Contenu par défaut -->
                    <h2>Gestion des Hôtels et Types de Chambres</h2>
                    <div class="row mt-4">
                        <div class="col-md-6">
                            <a href="addHotel.jsp" class="btn btn-primary btn-block">Ajouter un Hôtel</a>
                        </div>
                        <div class="col-md-6">
                            <a href="addRoomType.jsp" class="btn btn-secondary btn-block">Ajouter un Type de Chambre</a>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script>
    document.addEventListener('DOMContentLoaded', () => {
        // Get all sidebar links
        const sidebarLinks = document.querySelectorAll('.nav-link');
        
        // Attach click event listeners to each link
        sidebarLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault(); // Prevent default link behavior
                
                const href = this.getAttribute('href'); // Get the link's href
                if (href && href !== "#") {
                    // Fetch the content of the target page
                    fetch(href)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(`Error: ${response.statusText}`);
                            }
                            return response.text();
                        })
                        .then(html => {
                            // Replace main content with the fetched HTML
                            document.getElementById('main-content').innerHTML = html;

                            // Update active link
                            sidebarLinks.forEach(link => link.classList.remove('active'));
                            this.classList.add('active');
                        })
                        .catch(error => {
                            console.error(error);
                            document.getElementById('main-content').innerHTML = 
                                `<div class="alert alert-danger">Échec du chargement du contenu.</div>`;
                        });
                }
            });
        });
    });

    </script>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

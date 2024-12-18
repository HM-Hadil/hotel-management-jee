<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.Users" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="styles.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Highlight the active link */
        .nav-link.active {
            font-weight: bold;
            color: #007bff;
        }
        .sidebar {
            height: 100vh;
            position: sticky;
            top: 0;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
                <div class="sidebar-sticky">
                    <h4>Welcome, <c:out value="${sessionScope.adminName}"/>!</h4>
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="#" id="dashboard-link">
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="addAgent.jsp" id="add-agent-link">
                                Add Agent
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="ListAgentServlet" id="list-agent-link">
                                List Agents
                            </a>
                        </li>
                    </ul>
                    <button class="btn btn-danger mt-3" onclick="window.location.href='logout.jsp'">Logout</button>
                </div>
            </nav>

            <!-- Main content area -->
            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                <h2>Admin Dashboard</h2>
                <div id="main-content" class="container mt-4">
                    <p>Welcome to the admin dashboard! Select an option from the sidebar.</p>
                </div>
            </main>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
                                    `<div class="alert alert-danger">Failed to load content.</div>`;
                            });
                    }
                });
            });
        });
    </script>
</body>
</html>

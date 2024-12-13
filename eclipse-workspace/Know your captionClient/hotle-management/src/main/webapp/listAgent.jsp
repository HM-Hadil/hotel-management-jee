<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.Users"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of Agents</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>List of Agents</h1>
        
        <!-- Table to display agents -->
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Iterate through the list of agents using Java code -->
                <%
                    List<Users> agents = (List<Users>) request.getAttribute("agents");
                    if (agents != null && !agents.isEmpty()) {
                        for (Users agent : agents) {
                %>
                    <tr>
                        <td><%= agent.getUsername() %></td>
                        <td><%= agent.getEmail() %></td>
                        <td><%= agent.getRole() %></td>
                        <td>
                            <!-- Edit Button -->
                            <a href="EditUserServlet?id=<%= agent.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                            <!-- Delete Button -->
                            <a href="DeleteAgentServlet?id=<%= agent.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this agent?');">Delete</a>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="4" class="text-center">No agents found</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        
        <a href="adminDashboard.jsp" class="btn btn-primary">Back to Dashboard</a>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

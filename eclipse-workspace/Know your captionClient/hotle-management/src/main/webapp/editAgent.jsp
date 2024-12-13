<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List"%>
<%@ page import="beans.Users"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Agent</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Edit Agent</h1>

        <!-- Form to edit agent -->
        <form action="EditUserServlet" method="post">
            <% Users agent = (Users) request.getAttribute("agent"); %>
            <input type="hidden" name="id" value="<%= agent.getId() %>"/>
            
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" name="username" value="<%= agent.getUsername() %>" required />
            </div>
            
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" name="email" value="<%= agent.getEmail() %>" required />
            </div>

            <div class="form-group">
                <label for="role">Role</label>
                <select name="role" class="form-control">
                    <option value="AGENT" <%= "AGENT".equals(agent.getRole()) ? "selected" : "" %>>Agent</option>
                    <option value="ADMIN" <%= "ADMIN".equals(agent.getRole()) ? "selected" : "" %>>Admin</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Save Changes</button>
            <a href="listAgent.jsp" class="btn btn-secondary">Cancel</a>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

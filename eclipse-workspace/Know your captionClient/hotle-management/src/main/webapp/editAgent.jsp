<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Edit Agent</h1>

        <!-- Form to edit agent -->
        <form action="UpdateAgentServlet" method="post">
            <input type="hidden" name="id" value="${agent.id}"/>
            
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" name="username" value="${agent.username}" required />
            </div>
            
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" name="email" value="${agent.email}" required />
            </div>

            <div class="form-group">
                <label for="role">Role</label>
                <select name="role" class="form-control">
                    <option value="AGENT" <c:if test="${agent.role == 'AGENT'}">selected</c:if>>Agent</option>
                    <option value="ADMIN" <c:if test="${agent.role == 'ADMIN'}">selected</c:if>>Admin</option>
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
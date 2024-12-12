<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="jakarta.servlet.http.HttpSession" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
    // Invalidate session to log the user out
    session.invalidate();
    
    // Redirect to login page
    response.sendRedirect("login.jsp");
%>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/19/2023
  Time: 8:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
</head>
<body>


<%= request.getParameter("email") == null ? "" : "Welcome dear " + request.getParameter("email")%>


<h1>
    Home Page
</h1>

<a href="http://localhost:8080/register.jsp">Register</a><br><br>
<a href="http://localhost:8080/login.jsp">Login</a><br><br>
<% if (request.getAttribute("email") != null || request.getParameter("email") != null) {%>
<a href="http://localhost:8080/index.jsp">Logout</a><br><br>
<a href="http://localhost:8080/deleteAccaunt.jsp?email=<%=request.getAttribute("email")%>">Delete Account</a><br><br>
<a href="http://localhost:8080/changePassword.jsp?email=<%=request.getAttribute("email")%>">Change Password</a><br><br>
<% }%>
<a href="http://localhost:8080/product.jsp">Product</a><br><br>

</body>
</html>

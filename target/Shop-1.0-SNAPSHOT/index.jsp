<%@ page import="am.smartCode.jdbc.util.constants.Strings" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/19/2023
  Time: 9:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
</head>
<body>

<h1>Login</h1>

<%= request.getAttribute(Strings.MESSAGE) == null ? "" : request.getAttribute(Strings.MESSAGE)%>

<form method="post" action="/login">
    Email: <input type="text" name="email"/><br><br>
    Password: <input type="password" name="password"/><br><br>
    Remember me: <input type="checkbox" name="rememberMe"/><br><br>
    <input type="submit"/><br><br>
</form>
Don't have an account? <a href="http://localhost:8080/register.jsp">Sign up</a><br><br>

</body>
</html>
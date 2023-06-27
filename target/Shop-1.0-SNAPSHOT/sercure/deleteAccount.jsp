<%@ page import="am.smartCode.jdbc.util.constants.Strings" %>
Created by IntelliJ IDEA.
  User: Admin
  Date: 6/20/2023
  Time: 2:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Account</title>
</head>
<body>

<%= request.getAttribute(Strings.MESSAGE) == null ? "" : request.getAttribute(Strings.MESSAGE)%>

<form method="post" action="/secure/deleteAccount">
    Please verify your password: <input type="password" name="password"/><br><br>
    <input type="submit"/><br><br>

    <a href="http://localhost:8080/secure/home.jsp">Home Page</a><br><br>
</form>

</body>
</html>
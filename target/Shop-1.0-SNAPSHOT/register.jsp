<%@ page import="am.smartCode.jdbc.util.constants.Strings" %>
Created by IntelliJ IDEA.
  User: Admin
  Date: 6/19/2023
  Time: 9:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
<%= request.getAttribute(Strings.MESSAGE) == null ? "" : request.getAttribute(Strings.MESSAGE)%>


<h1>Please enter your information</h1>

<form method="post" action="/register">
    Name: <input type="text" name="name"/><br><br>
    Lastname: <input type="text" name="lastname"/><br><br>
    Email: <input type="text" name="email"/><br><br>
    Password: <input type="password" name="password"/><br><br>
    Age: <input type="number" name="age"/><br><br>
    Balance: <input type="number" name="balance"/><br><br>
    <input type="submit"/>

</form>

</body>
</html>
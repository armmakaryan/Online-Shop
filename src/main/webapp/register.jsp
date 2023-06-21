<%--
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
    <title>Register Page</title>
</head>
<body>
<%= request.getAttribute("message") == null ? "" : request.getAttribute("message")%>


<h1>Please enter your information</h1>

<form method="post" action="/register">
    name: <input type="text" name="name"/><br><br>
    lastname: <input type="text" name="lastname"/><br><br>
    email: <input type="text" name="email"/><br><br>
    password: <input type="password" name="password"/><br><br>
    age: <input type="number" name="age"/><br><br>
    balance: <input type="number" name="balance"/><br><br>
    <input type="submit"/>

</form>

</body>
</html>

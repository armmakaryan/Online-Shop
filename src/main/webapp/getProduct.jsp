<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/20/2023
  Time: 3:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Get Product</title>
</head>
<body>
<%= request.getAttribute("message") == null ? "" : request.getAttribute("message")%>

<form method="post" action="/getProduct">

    id: <input type="number" name="id"/><br><br>
    <input type="submit"/>

</form>

</body>
</html>

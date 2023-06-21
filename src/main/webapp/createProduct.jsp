<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/20/2023
  Time: 2:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Product</title>
</head>
<body>
<%= request.getAttribute("message") == null ? "" : request.getAttribute("message")%>
<%= request.getAttribute("name") == null ? "" : "Product " + request.getAttribute("name") + " created"%>

<form method="post" action="/createProduct">

    Category: <input type="text" name="category"/><br><br>
    Name: <input type="text" name="name"/><br><br>
    Price: <input type="number" name="price"/><br><br>
    <input type="submit"/><br><br>

    <a href="http://localhost:8080/product.jsp">Product Page</a><br><br>

</form>
</body>
</html>

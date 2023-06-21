<%@ page import="am.smartCode.jdbc.model.Product" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/20/2023
  Time: 3:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<style>
    table, th, td {
        border:1px solid black;
    }
</style>
<head>
    <meta charset="UTF-8">
    <title>Product you are searching...</title>
</head>
<body>
<%Product product = (Product) request.getAttribute("product");%>

<table style="width:100%">
    <tr>
        <th>Id</th>
        <th>Category</th>
        <th>Name</th>
        <th>Price</th>
    </tr>


    <%if (product != null) {%>
    <tr>
        <td><%= product.getId()%></td>
        <td><%= product.getCategory()%></td>
        <td><%= product.getName()%></td>
        <td><%= product.getPrice()%></td>
    </tr>
    <%}%>
</table>

<br><a href="http://localhost:8080/product.jsp">Product Page</a><br><br>


</body>
</html>

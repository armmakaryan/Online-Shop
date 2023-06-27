<%@ page import="am.smartCode.jdbc.model.Product" %>
<%@ page import="am.smartCode.jdbc.util.constants.Strings" %>
Created by IntelliJ IDEA.
  User: Admin
  Date: 6/20/2023
  Time: 3:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<style>
  table, th, td {
    border: 1px solid black;
  }
</style>
<head>
  <meta charset="UTF-8">
  <title>Get Product</title>
</head>
<body>
<a style="display: flex;justify-content: right" href="http://localhost:8080/secure/logout">Log out</a><br><br>
<%= request.getAttribute(Strings.MESSAGE) == null ? "" : request.getAttribute(Strings.MESSAGE)%>
<% Product product =(Product) request.getAttribute(Strings.PRODUCT);%>

<form method="post" action="/secure/getProduct">
  id: <input type="number" name="id"/><br><br>
  <input type="submit"/><br><br>
</form>
<%if (product != null) {%>
<table style="width:100%">
  <tr>
    <th>Id</th>
    <th>Category</th>
    <th>Name</th>
    <th>Price</th>
  </tr>
  <tr>
    <td><%= product.getId()%></td>
    <td><%= product.getCategory()%></td>
    <td><%= product.getName()%></td>
    <td><%= product.getPrice()%></td>
  </tr>
  <%}%>
</table><br><br>
<a href="http://localhost:8080/secure/product.jsp">Product Page</a><br><br>
</body>
</html>
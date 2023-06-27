<%@ page import="am.smartCode.jdbc.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="am.smartCode.jdbc.util.constants.Strings" %>
Created by IntelliJ IDEA.
User: Admin
Date: 6/20/2023
Time: 1:51 PM
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
    <title>Product Page</title>
</head>
<body>
<a style="display: flex;justify-content: right" href="http://localhost:8080/secure/logout">Log out</a><br><br>
<%= request.getAttribute(Strings.MESSAGE) == null ? "" : request.getAttribute(Strings.MESSAGE)%>


<% List<Product> list = (List) request.getAttribute(Strings.LIST);%>

<a href="http://localhost:8080/secure/createProduct.jsp">Create Product</a><br><br>
<a href="http://localhost:8080/secure/updateProduct.jsp">Update Product</a><br><br>
<a href="http://localhost:8080/secure/getProduct.jsp">Get Product</a><br><br>
<a href="http://localhost:8080/secure/deleteProduct.jsp">Delete Product</a><br><br>
<% if (list == null) {%>
<a href="http://localhost:8080/secure/productList">Open products</a><br><br>
<%} else {%>
<a href="http://localhost:8080/secure/product.jsp">Close products</a><br><br>
<table style="width:100%">
    <tr>
        <th>Id</th>
        <th>Category</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <%for (Product product : list) {%>
    <tr>
        <td><%= product.getId()%>
        </td>
        <td><%= product.getCategory()%>
        </td>
        <td><%= product.getName()%>
        </td>
        <td><%= product.getPrice()%>
        </td>
    </tr>
    <% }
    }%>
</table>
<a href="http://localhost:8080/secure/home.jsp">Home Page</a><br><br>
</body>
</html>
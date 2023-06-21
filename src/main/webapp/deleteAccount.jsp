<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 6/21/2023
  Time: 1:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Account</title>
</head>
<body>

<%=(String) request.getAttribute("message") != null ? request.getAttribute("message") : ""%>
<form method="post" action="/deleteAccount">
    <input type="hidden" name="email" value="<%=request.getAttribute("email")%>"><br><br>
    Enter your password : <input type="password" name="password"><br><br>
    <input type="submit"><br><br>

    <a href="http://localhost:8080/index.jsp?email=<%=request.getParameter("email")%>">Home Page</a><br><br>
</form>

</body>
</html>

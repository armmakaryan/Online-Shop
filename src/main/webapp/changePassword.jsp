<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/20/2023
  Time: 1:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Password</title>
</head>
<body>

<%= request.getAttribute("message") == null ? "" : request.getAttribute("message")%>

<form method="post" action="/changePassword">
    <input type="hidden" name="email" value="<%=request.getParameter("email")%>"/><br><br>
    New password: <input type="password" name="newPassword"/><br><br>
    Repeat password: <input type="password" name="repeatPassword"/><br><br>
    <input type="submit"/>
</form>


</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String name = request.getParameter("name");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Matan Magen, Yael Moshkovich, Roy Kalfon, Ofri Kuperberg - DevOps Final</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 0 20px; }
        input[type=text] { padding: 8px; width: 250px; }
        button { padding: 8px 16px; cursor: pointer; }
        .greeting { color: green; font-weight: bold; }
    </style>
</head>
<body>
    <h1>MTA DevOps Final - JSP App</h1>
    <p>Team: Matan Magen, Yael Moshkovich, Roy Kalfon, Ofri Kuperberg</p>
    <p>Server time: <%= new java.util.Date() %></p>

    <!-- Requirement: one input text box + one button -->
    <form method="get" action="index.jsp">
        <input type="text" name="name" placeholder="Enter your name">
        <button type="submit">Say Hello</button>
    </form>

    <% if (name != null && !name.isEmpty()) { %>
        <p class="greeting">Hello, <%= name %>!</p>
    <% } %>

    <!-- Requirement: one link -->
    <p><a href="https://www.mta.ac.il">MTA College Website</a></p>
</body>
</html>

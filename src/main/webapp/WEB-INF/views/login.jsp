<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация</title>
</head>
<body>
<h1>Авторизация</h1>
<form method="post" action="${pageContext.request.contextPath}/login">
    <label for="username">Имя пользователя:</label><br>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Пароль:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Войти</button>
</form>
<a href="${pageContext.request.contextPath}/registration">Регистрация</a>
</body>
</html>

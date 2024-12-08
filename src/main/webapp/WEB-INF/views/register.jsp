<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h1>Регистрация</h1>
<form method="post" action="${pageContext.request.contextPath}/register">
    <label for="username">Имя пользователя:</label><br>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Пароль:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Зарегистрироваться</button>
</form>
</body>
</html>

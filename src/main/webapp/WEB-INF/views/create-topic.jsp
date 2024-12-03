<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание нового топика</title>
</head>
<body>
<header>
  <h1>Создание нового топика</h1>
</header>
<main>
  <form method="post" action="${pageContext.request.contextPath}/home/save-topic">
    <label for="title">Название:</label><br>
    <input type="text" id="title" name="title" required><br><br>

    <label for="description">Содержание:</label><br>
    <textarea id="description" name="description" rows="10" cols="50" required></textarea><br><br>

    <button type="submit">Сохранить</button>
  </form>

  <!-- Кнопка возврата на главную -->
  <a href="${pageContext.request.contextPath}/home">Вернуться на главную</a>
</main>
</body>
</html>

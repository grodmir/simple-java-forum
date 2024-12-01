<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${topic.title}</title>
</head>
<body>
<header>
    <h1>${topic.title}</h1>
</header>
<main>
    <p>${topic.description}</p>
    <p>Дата создания: ${topic.createdAt}</p>
    <p>Понравилось: ${topic.likes}. Не понравилось: ${topic.dislikes}</p>

    <!-- Кнопка возврата на главную -->
    <a href="${pageContext.request.contextPath}/home">Вернуться на главную</a>
</main>
</body>
</html>

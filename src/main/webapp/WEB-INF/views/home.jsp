<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
    <header>
        <h1>Добро пожаловать в наш Форум!</h1>
        <!-- Заглушка для авторизации -->
        <a href="login">Войти</a> | <a href="register">Регистрация</a>
    </header>
    <main>
        <!-- Заглушка для сортировки -->
        <form method="get" action="${pageContext.request.contextPath}/home">
            <label for="sort">Сортировка:</label>
            <select name="sort" id="sort">
                <option value="date" ${param.sort == 'date' ? 'selected' : ''}>По дате</option>
                <option value="likes" ${param.sort == 'likes' ? 'selected' : ''}>По популярности</option>
            </select>
            <button type="submit">Применить</button>
        </form>

        <h2>Темы:</h2>
        <ul>
            <c:forEach var="topic" items="${topics}">
                <li>
                    <h3>${topic.title}</h3>
                    <p>${topic.description}</p>
                    <p>Дата создания: ${topic.createdAt}</p>
                    <p>Понравилось: ${topic.likes}. Не понравилось: ${topic.dislikes}</p>
                </li>
            </c:forEach>
        </ul>

        <!-- Пагинация -->
        <div class="pagination">
            <c:if test = "${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/home?page=${currentPage - 1}&sort=${param.sort}">Назад</a>
            </c:if>

            <span>Страница ${currentPage}</span>

            <c:if test="${hasNextPage}">
                <a href="${pageContext.request.contextPath}/home?page=${currentPage + 1}&sort=${param.sort}">Вперёд</a>
            </c:if>
        </div>
    </main>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true" %>

<html>
<head>
    <title>Главная страница</title>
</head>
<body>
    <header>
        <h1>Добро пожаловать на наш форум
            <c:if test="${not empty username}">
                , ${username}
            </c:if>
        </h1>
        <!-- Заглушка для авторизации -->
        <a href="${pageContext.request.contextPath}/login">Войти</a> | <a href="${pageContext.request.contextPath}/register">Регистрация</a><br>
        <a href = ${pageContext.request.contextPath}/logout>Выйти из учётной записи</a>
    </header>
    <main>
        <!-- Кнопка для перехода к созданию нового топика -->
        <div>
            <a href="${pageContext.request.contextPath}/home/create-topic">
                <button>Создать новый топик</button>
            </a>
        </div> <!-- HAHAHHA NEGRI PIDORI -->
        <hr>
        <!-- Сортировка по критериям -->
        <form method="get" action="${pageContext.request.contextPath}/home">
            <label for="sort">Сортировка:</label>
            <select name="sort" id="sort">
                <option value="date" ${param.sort == 'date' ? 'selected' : ''}>По дате</option>
                <option value="likes" ${param.sort == 'likes' ? 'selected' : ''}>По количеству лайков</option>
                <option value="dislikes" ${param.sort == 'likes' ? 'selected' : ''}>По количеству дизлайков</option>
            </select>
            <button type="submit">Применить</button>
        </form>

        <!-- Вывод всех тем, содержащихся в базе данных -->
        <h2>Темы:</h2>
        <ul>
            <c:forEach var="topic" items="${topics}">
                <li>
                    <!-- Логика для вывода первых 500 символов содержания топика -->
                    <h3><a href = ${pageContext.request.contextPath}/home/topic?id=${topic.id}> ${topic.title} </a></h3>
                    <p>
                        <c:choose>
                            <c:when test="${fn:length(topic.description) > 500}">
                                ${fn:substring(topic.description, 0, 500)}...
                            </c:when>
                            <c:otherwise>
                                ${topic.description}
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p>Дата создания: ${topic.createdAt}</p>
                    <p>Понравилось: ${topic.likes} | Не понравилось: ${topic.dislikes}</p>
                </li>
                <hr>
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
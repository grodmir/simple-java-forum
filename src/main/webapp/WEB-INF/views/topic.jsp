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
    <p>Дата создания: ${topic.formattedCreatedAt}</p>
    <p>Понравилось: ${topic.likes}. Не понравилось: ${topic.dislikes}</p>
    <p>Автор: ${topic.author.username}</p>
    <h3>
        <div style="display: inline-block; margin-right: 10px;">
            <form method="post" action="${pageContext.request.contextPath}/home/topic/vote" style="display:inline;">
                <input type="hidden" name="id" value="${topic.id}">
                <input type="hidden" name="vote" value="like">
                <button type="submit">+</button>
            </form>
            |
            <form method="post" action="${pageContext.request.contextPath}/home/topic/vote" style="display:inline;">
                <input type="hidden" name="id" value="${topic.id}">
                <input type="hidden" name="vote" value="dislike">
                <button type="submit">--</button>
            </form>
        </div>
    </h3>
    <!-- Кнопка возврата на главную -->
    <a href="${pageContext.request.contextPath}/home">Вернуться на главную</a>

    <hr>
    <!-- Форма добавления комментария -->
    <h3>Добавить комментарий:</h3>
    <form method="post" action="${pageContext.request.contextPath}/home/topic/comment">
        <textarea name="content" rows="5" cols="50" placeholder="Введите ваш комментарий..." required></textarea><br>
        <input type="hidden" name="topicId" value="${topic.id}">
        <button type="submit">Отправить</button>
    </form>

    <!-- Список комментариев -->
    <ul>
        <c:forEach var="comment" items="${comments}">
            <li>
                <p>${comment.text}</p>
                <p><i>Автор: ${comment.author.username} | Дата: ${comment.formattedCreatedAt}</i></p>
            </li>
        </c:forEach>
    </ul>
</main>
</body>
</html>

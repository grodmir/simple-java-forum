### **README: Online Forum Application**

---

#### **Описание проекта**
Простое веб-приложение форума, разработанное с использованием Jakarta EE и PostgreSQL. Приложение позволяет создавать темы,
оставлять комментарии, оценивать их, сортировать темы и управлять базовыми функциями через интерфейс JSP.
---

### **Функционал приложения**

#### 1. **Основной**
- **Возможность добавления и просмотра тем:**
- **Написание комментариев к определённым темам**
- **Сортировка тем по определённым критериям**
- **Возможность оценки тем и комментариев к ним**

#### 2. **Дополнительный**
- **Регистрация новых пользователей, авторизация существующих**
- **Возможность добавления ролей модерирования, удаления тем, комментариев**
- **Уведомление о новых комментариях к твоим темам**
---

### **Архитектура приложения**

#### **Бэкенд:**
1. **Слои:**
    - **Контроллеры (Servlets)**
    - **Сервисы**
    - **DAO (Data Access Object)**
    - **Модели**

2. **Используемые технологии:**
    - Jakarta EE:
        - Servlets
        - JSP для фронтенда.
        - JPA для работы с базой данных.
    - PostgreSQL для хранения данных.
    - Slf4j, logback и Lombok для логгирования

#### **Фронтенд:**
1. **Будет реализован через JSP. Возможные страницы:**
    - Главная страница, содержащая все темы (с пагинацией) рядом будет отображаться оценки, количество комментариев. Поле для сортировки тем.
    - При нажатии на тему будет разворачиваться е полноценный вариант с возможностью оценки, чтения полных комментариев, написание комментариев, кнопкой возвращения на главную страницу.
    - Окно для регистрации/авторизации
    - Окно для создания новой тесы (форма)
---

### **Примерная структура базы данных**

1. **users:**
    - id - `SERIAL`
    - username - `VARCHAR`
    - password_hash - `VARCHAR`
    - role - `VARCHAR DEAFAULT 'user'`
2. **topics:**
    - id - `SERIAL`
    - title - `VARCHAR`
    - description - `TEXT`
    - created_at - `TIMESTAMP`
    - likes - `INT`
    - dislikes - `INT`
    - users_id - `INT`
3. **comments:**
    - id - `SERIAL`
    - topic_id - `INT`
    - text - `TEXT`
    - created_at - `TIMESTAMP`
4. **notifications:**
    - id - `SERIAL`
    - topic_id - `INT`
    - message - `TEXT`
    - created_at - `TIMESTAMP`


### **План разработки проекта и примерные даты выполнения:**

1. Инициализация проекта, настройка базы данных (создание БД и её таблиц), подключение необходимых зависимостей, настройка подключений JPA. (Дата: 28.11 - 28.11 (1 день))

2. Реализация главной страницы, подготовка базовой структуры сервлетов и JSP, подключение Hibernate: (Дата: 29.11 - 30.11 (2 дня))
   - структура проекта (организация по модели MVC)
   - настройка сервлетов (создать HomeServlet)
   - настройка JSP (home.jsp и настройка JSTL)
   - настройка БД и подключение через Hibernate(конфигурация), создание сущностей в коде и реализация DAO

3. Реализация добавления новых тем, логика для кнопки +/- (с сохранением в БД). Создание страницы детального отображения темы (отображение заголовка, описания темы, даты создания, всех комментариев к теме). Реализация добавления комментариев к теме (Дата: 01.12 - 04.12 (4 дня))

4. Авторизация и регистрация: создание функционала регистрации, хеширования паролей, валидации данных пользователя, создание страницы авторизации/регистрации (Дата: 05.12 - 08.12 (4 дня))

5. Реализация авторизации, т.е. сессия для авторизированных пользователей, ограничение доступа функционала для гостей (добавление тем/комментариев) (Дата: 09.12 - 10.12 (2 дня))

6. Дополнительные улучшения: Реализация уведомлений о новых комментариях. Финальная полировка. Написание README (Дата: 11.12 - 12.12 (2 дня))
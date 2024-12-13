-- Таблица пользователей
CREATE TABLE users (
    id SERIAL PRIMARY KEY,                 -- Уникальный идентификатор пользователя
    username VARCHAR(50) UNIQUE NOT NULL,  -- Имя пользователя
    password_hash VARCHAR(255) NOT NULL,   -- Хэш пароля
    role VARCHAR(20) DEFAULT 'user' NOT NULL, -- Роль (по умолчанию "user")
    CONSTRAINT check_user_role CHECK (role IN ('user', 'moderator')) -- Проверка допустимых значений
);

-- Таблица тем форума
CREATE TABLE topics (
    id SERIAL PRIMARY KEY,                 -- Уникальный идентификатор темы
    title VARCHAR(100) NOT NULL,           -- Заголовок темы
    description TEXT NOT NULL,             -- Описание темы
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Дата и время создания темы
    likes INT DEFAULT 0 NOT NULL,                   -- Количество лайков
    dislikes INT DEFAULT 0 NOT NULL,                -- Количество дизлайков
    user_id INT REFERENCES users(id) ON DELETE CASCADE NOT NULL -- Связь с пользователем (автор темы)
);

-- Таблица комментариев
CREATE TABLE comments (
    id SERIAL PRIMARY KEY,                 -- Уникальный идентификатор комментария
    topic_id INT REFERENCES topics(id) ON DELETE CASCADE NOT NULL, -- Связь с темой
    user_id INT REFERENCES users(id) ON DELETE CASCADE NOT NULL,   -- Связь с пользователем (автор комментария)
    text TEXT NOT NULL,                    -- Текст комментария
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL -- Дата и время создания комментария
);

-- Таблица уведомлений
CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,                 -- Уникальный идентификатор уведомления
    topic_id INT REFERENCES topics(id) ON DELETE CASCADE NOT NULL, -- Связь с темой
    message TEXT NOT NULL,                 -- Текст уведомления
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL -- Дата и время создания уведомления
);

-- Таблица для хранения оценок пользователей
CREATE TABLE topic_votes (
    id SERIAL PRIMARY KEY,                -- Уникальный идентификатор
    user_id INT REFERENCES users(id) ON DELETE CASCADE NOT NULL, -- Пользователь, оставивший оценку
    topic_id INT REFERENCES topics(id) ON DELETE CASCADE NOT NULL, -- Тема, которой выставлена оценка
    vote_type VARCHAR(10) NOT NULL,       -- Тип оценки ('like' или 'dislike')
    CONSTRAINT unique_user_topic_vote UNIQUE (user_id, topic_id) -- Уникальность оценки для пары пользователь/тема
);
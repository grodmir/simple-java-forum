# Используем базовый образ Apache Tomcat
FROM tomcat:10.1-jdk21

# Копируем PostgreSQL драйвер в папку libs
ADD https://jdbc.postgresql.org/download/postgresql-42.6.0.jar /usr/local/tomcat/lib/

# Копируем ваш .war файл в папку веб-приложений
COPY target/simple-java-forum-1.0.war /usr/local/tomcat/webapps/

# Открываем порт 8080 для доступа
EXPOSE 8080

# Запуск Tomcat
CMD ["catalina.sh", "run"]

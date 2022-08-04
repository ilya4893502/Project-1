# Электронная библиотека.

## Используемые технологии. 
Java, Spring Core, Spring MVC, Spring Data JPA, Spring Boot, Hibernate, HTML, PostgreSQL.

## Запуск.
Приложение запускается в классе Project1Application.

## Описание.
В БД 2 таблицы: Person и Book.
Для этих таблиц реализованы модели, репозитории, сервисы, контроллеры и представления.


По адресу: http://localhost:8080/people
находится представление с людьми. Тут можно добавлять читателя.

На странице читателя указан список его книг, если возврат книги просрочен, то книга помечена красным цветом. Также можно редактировать и удалять человека из БД.
При создании нового человека его имя должно быть уникальным, или выпадет предупреждение.


По адресу: http://localhost:8080/books находится список книг. Тут можно добавлять книгу или искать книгу по названию.

Поиск книги по названию осуществляется по 
первым буквам книги. Если книги не найдено, то выпадает соответствующее сообщение.

На странице книги указан или читатель (если книга занята) и возможность освободить книгу, или возможность назначить эту книгу читателю (если книга свободна).

Есть возможность отсортировать книги по году: http://localhost:8080/books?sort_by_year=true

Есть возможность разделить книги на страницы: http://localhost:8080/books?page=1&books_per_page=3

Можно совместить сортировку и пагинацию: http://localhost:8080/books?page=1&books_per_page=3&sort_by_year=true

INSERT INTO USERS (name, email, password, registered)
VALUES ('User', 'user@yandex.ru', '{noop}password', '2024-01-28 9:00'),
       ('Admin', 'admin@gmail.com', '{noop}admin', '2024-01-28 9:30'),
       ('Guest', 'guest@gmail.com', '{noop}guest', '2024-01-28 10:00');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT(name)
VALUES ('ROSTIKS'),
       ('VKUSNO'),
       ('HACHOPURI'),
       ('TOKIO');

INSERT INTO DISH(date, restaurant_id, price, name)
VALUES ('2024-01-31', 1, 150, 'Dish1'),
       (CURRENT_DATE, 1, 250, 'Dish2'),
       (CURRENT_DATE, 1, 350, 'Dish3'),
       ('2024-01-29', 2, 250, 'Dish1'),
       (CURRENT_DATE, 2, 350, 'Dish2'),
       (CURRENT_DATE, 2, 450, 'Dish3'),
       ('2024-01-30', 3, 350, 'Dish1'),
       (CURRENT_DATE, 3, 450, 'Dish2'),
       (CURRENT_DATE, 3, 550, 'Dish3'),
       ('2024-01-28', 4, 250, 'Dish1'),
       (CURRENT_DATE, 4, 450, 'Dish2'),
       (CURRENT_DATE, 4, 650, 'Dish3');


INSERT INTO vote (vote_date, vote_time, user_id, restaurant_id)
VALUES ('2024-01-28', '10:10:00', 1, 4),
       ('2024-01-29', '10:15:00', 2, 2),
       ('2024-01-30', '10:20:00', 1, 3),
       ('2024-01-31', '10:25:00', 1, 1),
       (CURRENT_DATE, CURRENT_TIME, 1, 4),
       (CURRENT_DATE, CURRENT_TIME, 2, 2);


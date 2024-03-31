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

INSERT INTO MENU_ITEM(date, restaurant_id, price, name)
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


INSERT INTO VOTE (vote_date, user_id, restaurant_id)
VALUES ('2024-01-28', 1, 2),
       ('2024-01-29', 2, 4),
       ('2024-01-30', 1, 1),
       ('2024-01-31', 2, 3),
       (CURRENT_DATE, 3, 1),
       (CURRENT_DATE, 2, 2);


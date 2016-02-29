DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);


DELETE FROM meals;
ALTER SEQUENCE global_seq_meal RESTART WITH 100000;

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2016-02-26 11:00:00', 'Завтрак', 560, 100000);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2016-02-26 14:00:00', 'Обед', 400, 100000);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2016-02-25 18:00:00', 'Ужин', 460, 100000);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2016-02-25 19:00:00', 'Ужин', 520, 100001);

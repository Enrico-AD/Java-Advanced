INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (username, password, role)
VALUES
('admin', '$2a$10$EIXOyih1s0F4qHcnjMZOB.fwrPQFciDqAiH0N70vt4QlOdf6M9fPu', 'ROLE_ADMIN'),
('user',  '$2a$10$yQnT9vK4XbsQKpRYwYXbQOQ83CkcOdOemY7V5yFvziAnzMlwAz1iK', 'ROLE_USER');

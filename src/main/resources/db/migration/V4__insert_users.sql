INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (username, password, role)
VALUES
('admin', '$2a$12$.Cp9kbJt2/VMrTsWh8zoGO9QS.OpvwmWkS/4wopMxmLZVKq4DvBx.', 'ROLE_ADMIN'),
('user',  '$2a$10$yQnT9vK4XbsQKpRYwYXbQOQ83CkcOdOemY7V5yFvziAnzMlwAz1iK', 'ROLE_USER');

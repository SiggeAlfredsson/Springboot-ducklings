DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS invoices (
                 id int AUTO_INCREMENT primary key,
                 owner_username varchar(255),
                 title varchar(255),
                 date date,
                 description text,
                 category varchar(255),
                 price int
);

CREATE TABLE users (
                       id int AUTO_INCREMENT primary key,
                       username varchar(255),
                       password varchar(255),
                       created_at date,
                       created_by varchar(32)
);


INSERT INTO users (username, password, created_at, created_by) VALUES ("Max", "$2a$12$6N.iH9POWVCGEaIhssiC8.Mf0XVAWRESIi7MrsbmlLTjOafsdDOdy", now(), "CONSOLE");
INSERT INTO users (username, password, created_at, created_by) VALUES ("Johan", "$2a$12$6N.iH9POWVCGEaIhssiC8.Mf0XVAWRESIi7MrsbmlLTjOafsdDOdy", now(), "CONSOLE");
INSERT INTO users (username, password, created_at, created_by) VALUES ("Anton", "$2a$12$6N.iH9POWVCGEaIhssiC8.Mf0XVAWRESIi7MrsbmlLTjOafsdDOdy", now(), "CONSOLE");
INSERT INTO users (username, password, created_at, created_by) VALUES ("Julia", "$2a$12$6N.iH9POWVCGEaIhssiC8.Mf0XVAWRESIi7MrsbmlLTjOafsdDOdy", now(), "CONSOLE");
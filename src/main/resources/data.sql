CREATE TABLE IF NOT EXISTS invoices (
                 id int AUTO_INCREMENT primary key,
                 owner_username varchar(255),
                 title varchar(255),
                 date date,
                 description text,
                 category varchar(255),
                 price double
);

CREATE TABLE IF NOT EXISTS users (
                       id int AUTO_INCREMENT primary key,
                       username varchar(255),
                       password varchar(255),
                       created_at date,
                       created_by varchar(32)
);


INSERT INTO users (username, password, created_at, created_by)
SELECT "Max", "", now(), "CONSOLE" FROM DUAL
WHERE NOT EXISTS (SELECT * FROM users WHERE username = "Max");

INSERT INTO users (username, password, created_at, created_by)
SELECT "Johan", "", now(), "CONSOLE" FROM DUAL
WHERE NOT EXISTS (SELECT * FROM users WHERE username = "Johan");

INSERT INTO users (username, password, created_at, created_by)
SELECT "Anton", "", now(), "CONSOLE" FROM DUAL
WHERE NOT EXISTS (SELECT * FROM users WHERE username = "Anton");

INSERT INTO users (username, password, created_at, created_by)
SELECT "Julia", "", now(), "CONSOLE" FROM DUAL
WHERE NOT EXISTS (SELECT * FROM users WHERE username = "Julia");
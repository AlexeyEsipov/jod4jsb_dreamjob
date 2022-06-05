DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS candidate;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS city (
                                    city_id SERIAL PRIMARY KEY ,
                                    city_name TEXT
);
INSERT INTO city(city_name) VALUES ('Москва') ;
INSERT INTO city(city_name) VALUES ('Санкт-Петербург') ;
INSERT INTO city(city_name) VALUES ('Екатеринбург') ;
INSERT INTO city(city_name) VALUES ('Челябинск') ;
CREATE TABLE if not exists post (
                                    post_id SERIAL PRIMARY KEY,
                                    post_name TEXT,
                                    post_description TEXT,
                                    post_visible bool,
                                    post_city_id int
);
CREATE TABLE IF NOT EXISTS candidate (
                                         can_id SERIAL PRIMARY KEY,
                                         can_name TEXT,
                                         can_description TEXT,
                                         can_visible bool,
                                         can_city_id int,
                                         can_photo bytea
);
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name TEXT,
                                     email TEXT,
                                     password TEXT,
                                     CONSTRAINT email_unique UNIQUE (email)
);
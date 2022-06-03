DROP TABLE IF EXISTS post;
CREATE TABLE if not exists post (
                                    id SERIAL PRIMARY KEY,
                                    name TEXT,
                                    description TEXT,
                                    visible bool,
                                    city_id int
);
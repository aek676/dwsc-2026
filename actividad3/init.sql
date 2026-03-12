CREATE TABLE sampleusers
(
username text NOT NULL,
password text NOT NULL,
dni text NOT NULL,
name text NOT NULL,
surnames text NOT NULL,
age integer NOT NULL,
PRIMARY KEY (username)
);

INSERT INTO sampleusers (username, password, dni, name, surnames, age) VALUES
('juan', 'juanpass', '12345678C', 'Juan', 'Lopez Garrido', 30),
('lidia', 'lidiapass', '12345678B', 'Lidia', 'Saez Martinez', 32),
('maría', 'juanpass', '12345678D', 'Maria', 'Lopez Rodriguez', 35),
('javi', 'javipass', '12345678A', 'Javier', 'Criado Rodriguez', 37);

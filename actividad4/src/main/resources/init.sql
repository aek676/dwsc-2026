CREATE TABLE IF NOT EXISTS sampleusers (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    surnames VARCHAR(200) NOT NULL,
    age INT NOT NULL
);

INSERT INTO sampleusers (username, password, dni, name, surnames, age) VALUES
    ('juan', 'juanpass', '12345678D', 'Juan', 'Lopez Garcia', 31),
    ('jose', 'josepass', '12345678B', 'Jose', 'Perez Rodriguez', 33),
    ('javi', 'javipass', '12345678A', 'Javier', 'Criado Rodriguez', 35);

CREATE TABLE jugadores (
    id_jugador INT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(20)  NOT NULL,
    origen     VARCHAR(30)  NOT NULL,
    puntos     INT          NOT NULL DEFAULT 0,
    nivel_actual INT        NOT NULL DEFAULT 1
);

CREATE TABLE armas (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    jugador_id  INT          NOT NULL,
    nombre_arma VARCHAR(50)  NOT NULL,
    CONSTRAINT fk_armas_jugador FOREIGN KEY (jugador_id) REFERENCES jugadores(id_jugador)
);

INSERT INTO jugadores (nombre, origen, puntos, nivel_actual) VALUES
    ('Arthas', 'Lordaeron', 500, 3),
    ('Jaina', 'Kul Tiras', 300, 2);

INSERT INTO armas (jugador_id, nombre_arma) VALUES
    (1, 'Frostmourne'),
    (2, 'Baculo de Tormenta');

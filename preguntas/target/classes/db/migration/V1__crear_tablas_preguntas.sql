-- ============================================================
-- Migración Flyway: V1__crear_tablas_preguntas.sql
-- Microservicio preguntas - MySQL
-- ============================================================
DROP TABLE IF EXISTS preguntas;

CREATE TABLE preguntas (
    id_pregunta  INT AUTO_INCREMENT PRIMARY KEY,
    enunciado    VARCHAR(200) NOT NULL,
    puntaje      INT          NOT NULL,
    dificultad   VARCHAR(10),
    categoria    VARCHAR(20),
    tipo         VARCHAR(20),
    nivel_id     INT          NOT NULL
);

CREATE TABLE respuestas (
    id_respuesta INT AUTO_INCREMENT PRIMARY KEY,
    contenido    VARCHAR(200)       NOT NULL,
    correcta     TINYINT(1) DEFAULT 0 NOT NULL,
    pregunta_id  INT                NOT NULL,
    CONSTRAINT fk_respuesta_pregunta FOREIGN KEY (pregunta_id) REFERENCES preguntas(id_pregunta)
);

CREATE TABLE respuestas_jugador (
    id_respuesta_jugador INT AUTO_INCREMENT PRIMARY KEY,
    respuesta_id         INT        NOT NULL,
    es_correcta          TINYINT(1) NOT NULL,
    partida_id           INT        NOT NULL,
    CONSTRAINT fk_rj_respuesta FOREIGN KEY (respuesta_id) REFERENCES respuestas(id_respuesta)
);

INSERT INTO preguntas (enunciado, puntaje, dificultad, categoria, tipo, nivel_id) VALUES
('¿Cuántos lados tiene un triángulo?',               10, 'FACIL',   'Matematica',  'opcion_multiple', 1),
('¿Cuál es el color del cielo despejado?',           10, 'FACIL',   'General',     'opcion_multiple', 1),
('¿Cuántos días tiene una semana?',                  10, 'FACIL',   'General',     'opcion_multiple', 1),
('¿Cuál es la capital de Japón?',                    20, 'MEDIO',   'Geografia',   'opcion_multiple', 2),
('¿Cuántos huesos tiene el cuerpo humano adulto?',   20, 'MEDIO',   'Ciencias',    'opcion_multiple', 2),
('¿En qué año llegó el hombre a la Luna?',           20, 'MEDIO',   'Historia',    'opcion_multiple', 2),
('¿Cuál es el número de Avogadro?',                  30, 'DIFICIL', 'Ciencias',    'opcion_multiple', 3),
('¿En qué año cayó el Imperio Romano de Occidente?', 30, 'DIFICIL', 'Historia',    'opcion_multiple', 3),
('¿Cuántos bits tiene un byte?',                     30, 'DIFICIL', 'Tecnologia',  'opcion_multiple', 3);

-- P1: ¿Cuántos lados tiene un triángulo?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('3', 1, 1);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('4', 0, 1);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('5', 0, 1);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('6', 0, 1);

-- P2: ¿Color del cielo?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('Azul',     1, 2);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('Verde',    0, 2);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('Rojo',     0, 2);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('Amarillo', 0, 2);

-- P3: ¿Días de la semana?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('7', 1, 3);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('5', 0, 3);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('8', 0, 3);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('6', 0, 3);

-- P4: ¿Capital de Japón?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('Tokio',   1, 4);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('Pekín',   0, 4);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('Seúl',    0, 4);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('Bangkok', 0, 4);

-- P5: ¿Huesos humanos?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('206', 1, 5);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('180', 0, 5);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('215', 0, 5);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('198', 0, 5);

-- P6: ¿Año llegada a la Luna?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('1969', 1, 6);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('1965', 0, 6);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('1972', 0, 6);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('1959', 0, 6);

-- P7: ¿Número de Avogadro?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('6.022 x 10^23', 1, 7);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('3.14 x 10^10',  0, 7);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('1.6 x 10^-19',  0, 7);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('9.8 x 10^22',   0, 7);

-- P8: ¿Caída del Imperio Romano?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('476 d.C.', 1, 8);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('410 d.C.', 0, 8);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('395 d.C.', 0, 8);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('509 d.C.', 0, 8);

-- P9: ¿Bits en un byte?
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('8',  1, 9);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('16', 0, 9);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('4',  0, 9);
INSERT INTO respuestas (contenido, correcta, pregunta_id) VALUES ('32', 0, 9);

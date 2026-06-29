-- ============================================================
-- Migración Flyway: V1_crear-tabla_partidas.sql
-- Generado desde los modelos JPA del microservicio partidas
-- ============================================================
-- Tabla: niveles
-- Entidad: Nivel.java
CREATE TABLE niveles (
  idNivel    INT AUTO_INCREMENT PRIMARY KEY,
  numero    INT     NOT NULL,
  puntajeMinimo INT     NOT NULL,
  dificultad  VARCHAR(10) NOT NULL
);
-- Tabla: jefes
-- Entidad: Jefe.java (FK → niveles)

CREATE TABLE jefes (
  idJefe   INT AUTO_INCREMENT PRIMARY KEY,
  nombreJefe VARCHAR(50) NOT NULL,
  id_nivel  INT     NOT NULL,
  recompensa INT     NOT NULL,
  CONSTRAINT fk_jefe_nivel FOREIGN KEY (id_nivel) REFERENCES niveles(idNivel)
);
-- Tabla: partidas
-- Entidad: Partida.java
CREATE TABLE partidas (
  idPartida  INT AUTO_INCREMENT PRIMARY KEY,
  puntajeTotal INT  NOT NULL DEFAULT 0,
  finalizada  BOOLEAN NOT NULL DEFAULT FALSE,
  idJugador  INT  NOT NULL
);
-- Tabla: nivel_partida (tabla de relación Nivel ↔ Partida)
-- Entidad: NivelPartida.java (FK → niveles, partidas)
CREATE TABLE nivel_partida (
  idNivelPartida INT AUTO_INCREMENT PRIMARY KEY,
  id_nivel    INT NOT NULL,
  id_partida   INT NOT NULL,
  CONSTRAINT fk_np_nivel FOREIGN KEY (id_nivel) REFERENCES niveles(idNivel),
  CONSTRAINT fk_np_partida FOREIGN KEY (id_partida) REFERENCES partidas(idPartida)
);
-- ============================================================
-- Migración Flyway: V2_insert_data_partidas.sql
-- Datos de prueba para el microservicio partidas
-- ============================================================
-- Tabla: niveles
INSERT INTO niveles (numero, puntajeMinimo, dificultad) VALUES
(1, 0, 'FACIL  '),
(2, 30, 'NORMAL  '),
(3, 60, 'DIFICIL '),
(4, 85, 'EXPERTO ');
-- Tabla: jefes (id_nivel referencia niveles)
INSERT INTO jefes (nombreJefe, id_nivel, recompensa) VALUES
('Goblin Rey',  1, 100);
-- Tabla: partidas (idJugador es FK lógica al microservicio jugadores)
INSERT INTO partidas (puntajeTotal, finalizada, idJugador) VALUES
(0, FALSE, 1),
(150, FALSE, 2),
(400, TRUE, 1),
(980, TRUE, 3);
 -- Tabla: nivel_partida (relación Nivel ↔ Partida)
INSERT INTO nivel_partida (id_nivel, id_partida) VALUES
(1, 1),
(1, 2),
(2, 2),
(1, 3),
(2, 3),
(3, 3),
(1, 4),
(2, 4),
(3, 4),
(4, 4);
 
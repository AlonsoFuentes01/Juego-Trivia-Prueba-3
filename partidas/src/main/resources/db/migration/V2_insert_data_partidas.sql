-- ============================================================
-- Migración Flyway: V2_insert_data_partidas.sql
-- Datos de prueba para el microservicio partidas
-- ============================================================

-- Tabla: niveles
INSERT INTO niveles (numero, puntajeMinimo, dificultad) VALUES
(1, 0,  'FACIL     '),
(2, 30, 'NORMAL    '),
(3, 60, 'DIFICIL   '),
(4, 85, 'EXPERTO   ');

-- Tabla: jefes (id_nivel referencia niveles)
INSERT INTO jefes (nombreJefe, id_nivel, recompensa) VALUES
('Goblin Rey',   1, 100),
('Orco Jefe',    2, 200),
('Dragon Menor', 3, 350),
('Dragon Mayor', 4, 500);

-- Tabla: partidas (idJugador es FK lógica al microservicio jugadores)
-- Los jugadores 1 y 2 vienen del seed de jugadores (Arthas y Jaina)
INSERT INTO partidas (puntajeTotal, finalizada, idJugador) VALUES
(0,   FALSE, 1),
(150, FALSE, 2),
(400, TRUE,  1);

-- Tabla: nivel_partida (relación Nivel <-> Partida)
INSERT INTO nivel_partida (id_nivel, id_partida) VALUES
(1, 1),
(1, 2),
(2, 2),
(1, 3),
(2, 3),
(3, 3);

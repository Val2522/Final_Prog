

-- Crear base de datos y usarla
CREATE DATABASE IF NOT EXISTS mh_freedom2;
USE mh_freedom2;

-- Tabla de Tipos de Monstruo
CREATE TABLE Tipos (
    id_tipo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
);

-- Tabla de Monstruos
CREATE TABLE Monstruos (
    id_monstruo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    imagen VARCHAR(200),
    id_tipo INT,
    tamaño VARCHAR(20),
    lore VARCHAR(500),
    FOREIGN KEY (id_tipo) REFERENCES Tipos(id_tipo)
);

-- Tabla de Hábitats
CREATE TABLE Habitats (
    id_habitat INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
);

-- Relación Monstruos ↔ Hábitats
CREATE TABLE Monstruos_Habitats (
    id_monstruo INT,
    id_habitat INT,
    PRIMARY KEY (id_monstruo, id_habitat),
    FOREIGN KEY (id_monstruo) REFERENCES Monstruos(id_monstruo),
    FOREIGN KEY (id_habitat) REFERENCES Habitats(id_habitat)
);

-- Tabla de Debilidades
CREATE TABLE Debilidades (
    id_debilidad INT PRIMARY KEY AUTO_INCREMENT,
    elemento VARCHAR(50) NOT NULL
);

-- Relación Monstruos ↔ Debilidades
CREATE TABLE Monstruos_Debilidades (
    id_monstruo INT,
    id_debilidad INT,
    intensidad INT, -- 1 = baja, 3 = alta
    PRIMARY KEY (id_monstruo, id_debilidad),
    FOREIGN KEY (id_monstruo) REFERENCES Monstruos(id_monstruo),
    FOREIGN KEY (id_debilidad) REFERENCES Debilidades(id_debilidad)
);

-- Inserts en Tipos
INSERT INTO Tipos (nombre) VALUES
('Wyvern volador'),
('Bestia colmillo'),
('Wyvern nadador'),
('Wyvern de tierra'),
('Wyvern pájaro'),
('Dragón anciano');

-- Inserts en Habitats
INSERT INTO Habitats (nombre) VALUES
('Bosque y colina'),
('Montañas'),
('Pantano'),
('Desierto'),
('Volcán'),
('Tundra'),
('Torre');

-- Inserts en Debilidades
INSERT INTO Debilidades (elemento) VALUES
('Fuego'),
('Agua'),
('Hielo'),
('Rayo'),
('Dragón');

-- Inserts en Monstruos
INSERT INTO Monstruos (nombre, imagen, id_tipo, tamaño, lore) VALUES
('Tigrex', 'https://monsterhunter.com/tigrex.png', 4, 'Grande', 'Una bestia brutal que ataca con velocidad y fuerza devastadoras. Su rugido puede aturdir.'),
('Rathalos', 'https://monsterhunter.com/rathalos.png', 1, 'Grande', 'El Rey del Cielo. Patrulla su territorio con ferocidad y lanza fuego desde los cielos.'),
('Blangonga', 'https://monsterhunter.com/blangonga.png', 2, 'Grande', 'Un simio colmilludo que habita en climas fríos. Utiliza ataques de hielo y movimientos ágiles.'),
('Khezu', 'https://monsterhunter.com/khezu.png', 3, 'Grande', 'Un wyvern ciego que se guía por el olfato. Vive en cuevas y ataca con electricidad.'),
('Kushala Daora', 'https://monsterhunter.com/kushala.png', 6, 'Grande', 'Un dragón anciano cubierto de metal que controla el viento a su alrededor.'),
('Yian Kut-Ku', 'https://monsterhunter.com/kutku.png', 5, 'Grande', 'Un wyvern pájaro nervioso, vulnerable pero agresivo.'),
('Gravios', 'https://monsterhunter.com/gravios.png', 4, 'Grande', 'Un wyvern de roca que emite gas somnífero y rayos de calor.');

-- Relación Monstruos ↔ Hábitats
INSERT INTO Monstruos_Habitats VALUES
(1, 2), (1, 6),      -- Tigrex → Montañas, Tundra
(2, 1), (2, 5),      -- Rathalos → Bosque, Volcán
(3, 6),              -- Blangonga → Tundra
(4, 3),              -- Khezu → Pantano
(5, 7),              -- Kushala → Torre
(6, 1),              -- Yian Kut-Ku → Bosque
(7, 5);              -- Gravios → Volcán

-- Relación Monstruos ↔ Debilidades
INSERT INTO Monstruos_Debilidades VALUES
(1, 3, 3),           -- Tigrex → Hielo (alta)
(2, 3, 2), (2, 5, 1),-- Rathalos → Hielo, Dragón
(3, 4, 3),           -- Blangonga → Rayo
(4, 1, 3), (4, 5, 2),-- Khezu → Fuego, Dragón
(5, 2, 2), (5, 5, 3),-- Kushala → Agua, Dragón
(6, 4, 2),           -- Kut-Ku → Rayo
(7, 4, 2);           -- Gravios → Rayo


SELECT Monstruos.*, Tipos.nombre
FROM Monstruos
INNER JOIN Tipos ON Monstruos.id_tipo = Tipos.id_tipo
;
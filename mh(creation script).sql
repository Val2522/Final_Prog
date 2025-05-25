
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


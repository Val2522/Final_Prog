


-- Inserts en Tipos
INSERT INTO Tipos (nombre) VALUES
('Wyvern Marino'),
('Wyvern de Hielo'),
('Wyvern de Fuego'),
('Wyvern de Rayo'),
('Wyvern de Agua'),
('Wyvern de Dragón'),
('Bestia de Pelaje'),
('Bestia de Roca'),
('Bestia de Hueso'),
('Bestia de Escamas'),
('Bestia de Plumas'),
('Bestia de Caparazón'),
('Criatura Subterránea'),
('Monstruo de Baba'),
('Monstruo de Lodo'),
('Monstruo de Roca'),
('Monstruo de Hielo'),
('Monstruo de Fuego'),
('Monstruo de Rayo'),
('Monstruo de Agua'),
('Monstruo de Dragón'),
('Gigante'),
('Coloso'),
('Reptil Grande'),
('Anfibio Gigante'),
('Bestia Acorazada'),
('Bestia Rápida'),
('Monstruo Invisible'),
('Monstruo Psíquico'),
('Monstruo Tóxico');


INSERT INTO Habitats (nombre) VALUES
('Picos Nublados'),
('Cavernas Subterráneas'),
('Ciénaga de la Bruma'),
('Ruinas del Santuario'),
('Desierto Helado'),
('Valle Volcánico'),
('Gran Bosque'),
('Islas Remotas'),
('Tierras Sacras'),
('Bosque Ancestral'),
('Desierto de Dunas'),
('Pantano Cenagoso'),
('Volcán Dormido'),
('Tundra Congelada'),
('Cueva de Cristal'),
('Jungla Profunda'),
('Río Subterráneo'),
('Meseta Elevada'),
('Abismo Marino'),
('Cráter Antiguo'),
('Bosque de Bambú'),
('Glaciar Eterno'),
('Oasis Escondido'),
('Montañas Rocosas'),
('Valle Florecido'),
('Cascada Secreta'),
('Islas Flotantes'),
('Desfiladero del Viento'),
('Páramo Desolado'),
('Cumbres Gemelas');

-- Inserts en Debilidades
INSERT INTO Debilidades (elemento) VALUES
('Veneno'),
('Parálisis'),
('Sueño'),
('Explosión'),
('Aturdimiento'),
('Corte'),
('Impacto'),
('Proyectil'),
('Frenesí'),
('Plaga de Fuego'),
('Plaga de Agua'),
('Plaga de Hielo'),
('Plaga de Rayo'),
('Plaga de Dragón'),
('Nitro'),
('Bomba de Luz'),
('Bomba Sónica'),
('Trampa de Choque'),
('Trampa de Foso'),
('Cansancio'),
('Sangrado'),
('Defensa Baja'),
('Cabeza'),
('Cola'),
('Alas'),
('Patas'),
('Cuernos'),
('Espalda'),
('Pecho'),
('Garras');

-- Inserts en Monstruos
INSERT INTO Monstruos (nombre, imagen, id_tipo, tamaño, lore) VALUES
('Tigrex', '/com/wiki/icons/tigrex.png', 4, 'Grande', 'Una bestia brutal que ataca con velocidad y fuerza devastadoras. Su rugido puede aturdir.'),
('Rathalos', 'https://monsterhunter.com/rathalos.png', 1, 'Grande', 'El Rey del Cielo. Patrulla su territorio con ferocidad y lanza fuego desde los cielos.'),
('Blangonga', 'https://monsterhunter.com/blangonga.png', 2, 'Grande', 'Un simio colmilludo que habita en climas fríos. Utiliza ataques de hielo y movimientos ágiles.'),
('Khezu', 'https://monsterhunter.com/khezu.png', 3, 'Grande', 'Un wyvern ciego que se guía por el olfato. Vive en cuevas y ataca con electricidad.'),
('Kushala Daora', 'https://monsterhunter.com/kushala.png', 6, 'Grande', 'Un dragón anciano cubierto de metal que controla el viento a su alrededor.'),
('Yian Kut-Ku', 'https://monsterhunter.com/kutku.png', 5, 'Grande', 'Un wyvern pájaro nervioso, vulnerable pero agresivo.'),
('Gravios', 'https://monsterhunter.com/gravios.png', 4, 'Grande', 'Un wyvern de roca que emite gas somnífero y rayos de calor.'),
('Rathian', 'https://monsterhunter.com/rathian.png', 1, 'Grande', 'La Reina de la Tierra. Una wyvern voladora que defiende ferozmente su nido y ataca con veneno.'),
('Diablos', 'https://monsterhunter.com/diablos.png', 4, 'Grande', 'El Demonio del Desierto. Un wyvern de tierra territorial con cuernos masivos y cargas devastadoras.'),
('Gypceros', 'https://monsterhunter.com/gypceros.png', 5, 'Mediano', 'Un wyvern pájaro que puede emitir luz cegadora y robar objetos. Su cresta es tóxica.'),
('Basarios', 'https://monsterhunter.com/basarios.png', 4, 'Grande', 'Una cría de Gravios, se camufla como una roca. Emite gases tóxicos y puede endurecer su piel.'),
('Plesioth', 'https://monsterhunter.com/plesioth.png', 3, 'Grande', 'Un wyvern nadador que domina los entornos acuáticos. Puede lanzar chorros de agua a presión.'),
('Cephadrome', 'https://monsterhunter.com/cephadrome.png', 3, 'Grande', 'El líder de los Cephalos. Se desliza bajo la arena y ataca con ráfagas de arena.'),
('Daimyo Hermitaur', 'https://monsterhunter.com/daimyo.png', 16, 'Grande', 'Un cangrejo monstruo que usa el cráneo de Monoblos como caparazón. Ataca con sus enormes garras.'),
('Shogun Ceanataur', 'https://monsterhunter.com/shogun.png', 16, 'Grande', 'Un cangrejo monstruo que usa el cráneo de Gravios. Sus garras son como cuchillas afiladas.'),
('Rajang', 'https://monsterhunter.com/rajang.png', 2, 'Grande', 'Una bestia colmillo extremadamente agresiva y poderosa. Puede entrar en un estado de furia que lo vuelve dorado.'),
('Kirin', 'https://monsterhunter.com/kirin.png', 6, 'Mediano', 'Un dragón anciano místico que invoca rayos. Su piel es dura como un cuerno.'),
('Fatalis', 'https://monsterhunter.com/fatalis.png', 6, 'Enorme', 'Un dragón anciano legendario y aterrador. Se dice que destruyó un reino entero en una noche.'),
('Lao-Shan Lung', 'https://monsterhunter.com/laoshan.png', 6, 'Gigante', 'Un dragón anciano colosal que puede destruir ciudades. Se mueve lentamente pero con una fuerza imparable.'),
('Chameleos', 'https://monsterhunter.com/chameleos.png', 6, 'Grande', 'Un dragón anciano que puede volverse invisible y robar objetos con su lengua.'),
('Teostra', 'https://monsterhunter.com/teostra.png', 6, 'Grande', 'Un dragón anciano que controla el fuego. Puede desatar explosiones de polvo de escamas.'),
('Lunastra', 'https://monsterhunter.com/lunastra.png', 6, 'Grande', 'La pareja de Teostra. También controla el fuego, pero con un aura de calor azul que quema el aire.'),
('Nargacuga', 'https://monsterhunter.com/nargacuga.png', 1, 'Grande', 'Un wyvern volador sigiloso que ataca con velocidad y precisión. Sus ojos brillan en la oscuridad.'),
('Barioth', 'https://monsterhunter.com/barioth.png', 1, 'Grande', 'Un wyvern volador de hielo que se desliza por el terreno helado con sus garras y colmillos.'),
('Lagiacrus', 'https://monsterhunter.com/lagiacrus.png', 9, 'Grande', 'Un leviatán que domina tanto la tierra como el agua. Genera electricidad con su cuerpo.'),
('Zinogre', 'https://monsterhunter.com/zinogre.png', 7, 'Grande', 'Una bestia colmillo que acumula electricidad estática en su pelaje para potenciar sus ataques.'),
('Brachydios', 'https://monsterhunter.com/brachydios.png', 8, 'Grande', 'Un wyvern de tierra que recubre sus puños y cuerno con baba explosiva. Muy agresivo.'),
('Gore Magala', 'https://monsterhunter.com/goremagala.png', 6, 'Grande', 'Un misterioso dragón anciano que propaga el virus Frenzy. No tiene ojos, se guía por la percepción.'),
('Seregios', 'https://monsterhunter.com/seregios.png', 1, 'Grande', 'Un wyvern volador con escamas afiladas que puede lanzar como proyectiles. Muy ágil.'),
('Astalos', 'https://monsterhunter.com/astalos.png', 1, 'Grande', 'Un wyvern volador que genera electricidad en sus alas y cola. Muy rápido y agresivo.'),
('Mizutsune', 'https://monsterhunter.com/mizutsune.png', 9, 'Grande', 'Un leviatán elegante que utiliza burbujas resbaladizas para inmovilizar a sus presas.'),
('Valstrax', 'https://monsterhunter.com/valstrax.png', 6, 'Grande', 'Un dragón anciano con alas propulsoras que le permiten volar a velocidades supersónicas y disparar energía de dragón.'),
('Nergigante', 'https://monsterhunter.com/nergigante.png', 6, 'Grande', 'Un dragón anciano destructivo que se regenera rápidamente y ataca con espinas endurecidas.'),
('Velkhana', 'https://monsterhunter.com/velkhana.png', 6, 'Grande', 'Un dragón anciano que manipula el hielo, creando pilares y ráfagas congelantes.'),
('Safi''jiiva', 'https://monsterhunter.com/safijiiva.png', 6, 'Enorme', 'La forma madura de Xeno''jiiva. Un dragón anciano que absorbe la energía del entorno y dispara un potente rayo.'),
('Alatreon', 'https://monsterhunter.com/alatreon.png', 6, 'Grande', 'Un dragón anciano que domina todos los elementos: fuego, agua, hielo, rayo y dragón.'),
('Dodogama', 'https://monsterhunter.com/dodogama.png', 2, 'Mediano', 'Una bestia colmillo que consume rocas y las escupe como proyectiles explosivos.');

-- Relación Monstruos ↔ Hábitats
INSERT INTO Monstruos_Habitats VALUES
(8, 1), (8, 4),      -- Rathian → Bosque y colina, Desierto
(9, 4), (9, 2),      -- Diablos → Desierto, Montañas
(10, 1), (10, 3),    -- Gypceros → Bosque y colina, Pantano
(11, 5), (11, 4),    -- Basarios → Volcán, Desierto
(12, 3), (12, 12),   -- Plesioth → Pantano, Ciénaga de la Bruma (asumiendo id 12 para Ciénaga de la Bruma)
(13, 4), (13, 11),   -- Cephadrome → Desierto, Cavernas Subterráneas (asumiendo id 11 para Cavernas Subterráneas)
(14, 1), (14, 3),    -- Daimyo Hermitaur → Bosque y colina, Pantano
(15, 5), (15, 11),   -- Shogun Ceanataur → Volcán, Cavernas Subterráneas
(16, 2), (16, 6),    -- Rajang → Montañas, Tundra
(17, 10), (17, 13),  -- Kirin → Picos Nublados, Tierras Sacras (asumiendo id 10 y 13)
(18, 7),             -- Fatalis → Torre
(19, 7),             -- Lao-Shan Lung → Torre
(20, 1), (20, 3),    -- Chameleos → Bosque y colina, Pantano
(21, 5), (21, 7),    -- Teostra → Volcán, Torre
(22, 5), (22, 7),    -- Lunastra → Volcán, Torre
(23, 1), (23, 14),   -- Nargacuga → Bosque y colina, Gran Bosque (asumiendo id 14)
(24, 6), (24, 15),   -- Barioth → Tundra, Desierto Helado (asumiendo id 15)
(25, 1), (25, 3),    -- Lagiacrus → Bosque y colina, Pantano
(26, 1), (26, 2),    -- Zinogre → Bosque y colina, Montañas
(27, 5), (27, 2),    -- Brachydios → Volcán, Montañas
(28, 1), (28, 3),    -- Gore Magala → Bosque y colina, Pantano
(29, 1), (29, 4),    -- Seregios → Bosque y colina, Desierto
(30, 1), (30, 2),    -- Astalos → Bosque y colina, Montañas
(31, 3), (31, 12),   -- Mizutsune → Pantano, Ciénaga de la Bruma
(32, 2), (32, 10),   -- Valstrax → Montañas, Picos Nublados
(33, 2), (33, 5),    -- Nergigante → Montañas, Volcán
(34, 6), (34, 15),   -- Velkhana → Tundra, Desierto Helado
(35, 7),             -- Safi'jiiva → Torre
(36, 7),             -- Alatreon → Torre
(37, 5);             -- Dodogama → Volcán

-- Relación Monstruos ↔ Debilidades
INSERT INTO Monstruos_Debilidades VALUES
(8, 3, 3), (8, 5, 2),    -- Rathian → Hielo (alta), Dragón (media)
(9, 2, 3), (9, 10, 2),   -- Diablos → Agua (alta), Aturdimiento (media)
(10, 1, 3), (10, 7, 2),  -- Gypceros → Fuego (alta), Parálisis (media)
(11, 2, 3), (11, 12, 2), -- Basarios → Agua (alta), Impacto (media)
(12, 4, 3), (12, 1, 2),  -- Plesioth → Rayo (alta), Fuego (media)
(13, 3, 3), (13, 1, 2),  -- Cephadrome → Hielo (alta), Fuego (media)
(14, 4, 3), (14, 11, 2), -- Daimyo Hermitaur → Rayo (alta), Corte (media)
(15, 4, 3), (15, 12, 2), -- Shogun Ceanataur → Rayo (alta), Impacto (media)
(16, 3, 3), (16, 5, 2),  -- Rajang → Hielo (alta), Dragón (media)
(17, 1, 3), (17, 5, 2),  -- Kirin → Fuego (alta), Dragón (media)
(18, 5, 3), (18, 4, 2),  -- Fatalis → Dragón (alta), Rayo (media)
(19, 5, 3), (19, 1, 2),  -- Lao-Shan Lung → Dragón (alta), Fuego (media)
(20, 1, 3), (20, 2, 2),  -- Chameleos → Fuego (alta), Agua (media)
(21, 2, 3), (21, 5, 2),  -- Teostra → Agua (alta), Dragón (media)
(22, 2, 3), (22, 5, 2),  -- Lunastra → Agua (alta), Dragón (media)
(23, 1, 3), (23, 4, 2),  -- Nargacuga → Fuego (alta), Rayo (media)
(24, 1, 3), (24, 4, 2),  -- Barioth → Fuego (alta), Rayo (media)
(25, 1, 3), (25, 3, 2),  -- Lagiacrus → Fuego (alta), Hielo (media)
(26, 2, 3), (26, 3, 2),  -- Zinogre → Agua (alta), Hielo (media)
(27, 2, 3), (27, 3, 2),  -- Brachydios → Agua (alta), Hielo (media)
(28, 1, 3), (28, 4, 2),  -- Gore Magala → Fuego (alta), Rayo (media)
(29, 4, 3), (29, 3, 2),  -- Seregios → Rayo (alta), Hielo (media)
(30, 3, 3), (30, 1, 2),  -- Astalos → Hielo (alta), Fuego (media)
(31, 4, 3), (31, 1, 2),  -- Mizutsune → Rayo (alta), Fuego (media)
(32, 5, 3), (32, 3, 2),  -- Valstrax → Dragón (alta), Hielo (media)
(33, 5, 3), (33, 4, 2),  -- Nergigante → Dragón (alta), Rayo (media)
(34, 1, 3), (34, 4, 2),  -- Velkhana → Fuego (alta), Rayo (media)
(35, 5, 3), (35, 4, 2),  -- Safi'jiiva → Dragón (alta), Rayo (media)
(36, 5, 3), (36, 1, 2),  -- Alatreon → Dragón (alta), Fuego (media)
(37, 2, 3), (37, 3, 2);  -- Dodogama → Agua (alta), Hielo (media)



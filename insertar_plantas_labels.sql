-- ============================================================
-- SQL PARA INSERTAR LAS 93 PLANTAS EXACTAS DEL labels.txt
-- Los IDs coinciden con el índice del modelo de IA
-- ============================================================

-- PRIMERO EJECUTA ESTO:
-- DELETE FROM predictions;
-- DELETE FROM plants;
-- ALTER TABLE plants AUTO_INCREMENT = 1;

INSERT INTO plants (id, common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path, is_active) VALUES

-- ID 1: Aloevera
(1, 'Aloevera', 'Aloe barbadensis miller', 'Asphodelaceae',
'Planta suculenta con hojas carnosas que contienen gel medicinal.',
'Cicatrizante, hidratante, quemaduras, problemas digestivos, cuidado de piel.',
'Gel: Cortar hoja, extraer gel y aplicar. Jugo: Licuar gel con agua.',
'No consumir en exceso. Evitar en embarazo.', 'aloevera.jpg', 1),

-- ID 2: Amla
(2, 'Amla', 'Phyllanthus emblica', 'Phyllanthaceae',
'Grosella espinosa india, rica en vitamina C.',
'Fortalece inmunidad, digestión, cabello, antioxidante.',
'Jugo fresco, polvo con agua o miel.',
'Puede interactuar con medicamentos para diabetes.', 'amla.jpg', 1),

-- ID 3: Amruta_Balli
(3, 'Amruta Balli', 'Tinospora cordifolia', 'Menispermaceae',
'Planta trepadora medicinal conocida como Guduchi.',
'Inmunidad, fiebre, diabetes, desintoxicante.',
'Decocción del tallo, polvo, jugo fresco.',
'Puede reducir azúcar en sangre.', 'amruta_balli.jpg', 1),

-- ID 4: Amruthaballi
(4, 'Amruthaballi', 'Tinospora cordifolia', 'Menispermaceae',
'Variante de Guduchi, planta trepadora medicinal.',
'Inmunidad, antipirético, artritis.',
'Decocción, polvo, jugo del tallo.',
'Consultar médico si toma medicamentos.', 'amruthaballi.jpg', 1),

-- ID 5: Arali
(5, 'Arali', 'Nerium oleander', 'Apocynaceae',
'Arbusto ornamental. PRECAUCIÓN: Tóxica.',
'Solo uso externo limitado para piel. NO CONSUMIR.',
'Solo uso externo bajo supervisión.',
'ALTAMENTE TÓXICA. No ingerir.', 'arali.jpg', 1),

-- ID 6: Ashoka
(6, 'Ashoka', 'Saraca asoca', 'Fabaceae',
'Árbol sagrado, corteza valorada para salud femenina.',
'Trastornos menstruales, salud uterina.',
'Decocción de corteza, polvo.',
'No usar en embarazo.', 'ashoka.jpg', 1),

-- ID 7: Ashwagandha
(7, 'Ashwagandha', 'Withania somnifera', 'Solanaceae',
'Hierba adaptógena, "ginseng indio".',
'Reduce estrés, aumenta energía, inmunidad.',
'Polvo con leche tibia, cápsulas.',
'No usar en embarazo.', 'ashwagandha.jpg', 1),

-- ID 8: Astma_weed
(8, 'Astma Weed', 'Euphorbia hirta', 'Euphorbiaceae',
'Hierba para problemas respiratorios.',
'Asma, bronquitis, tos, disentería.',
'Infusión de la planta, decocción.',
'No usar en embarazo.', 'astma_weed.jpg', 1),

-- ID 9: Avacado
(9, 'Avocado', 'Persea americana', 'Lauraceae',
'Fruto nutritivo rico en grasas saludables.',
'Salud cardiovascular, piel, antiinflamatorio.',
'Consumo directo, aceite, mascarillas.',
'Alto en calorías.', 'avacado.jpg', 1),

-- ID 10: Badipala
(10, 'Badipala', 'Sterculia urens', 'Malvaceae',
'Árbol que produce goma karaya.',
'Laxante, digestión, control de peso.',
'Goma disuelta en agua, polvo.',
'Consumir con abundante agua.', 'badipala.jpg', 1),

-- ID 11: Balloon_Vine
(11, 'Balloon Vine', 'Cardiospermum halicacabum', 'Sapindaceae',
'Planta trepadora con frutos en globo.',
'Artritis, reumatismo, piel, antiinflamatorio.',
'Decocción de hojas, pasta tópica.',
'Usar con precaución.', 'balloon_vine.jpg', 1),

-- ID 12: Bamboo
(12, 'Bamboo', 'Bambusoideae', 'Poaceae',
'Gramínea gigante con brotes medicinales.',
'Rico en sílice, huesos, cabello, digestión.',
'Brotes cocidos, té de hojas.',
'Brotes crudos son tóxicos. Cocinar siempre.', 'bamboo.jpg', 1),

-- ID 13: Basale
(13, 'Basale', 'Basella alba', 'Basellaceae',
'Espinaca de Malabar, vegetal nutritivo.',
'Rico en hierro, laxante suave, refrescante.',
'Hojas cocidas, jugo fresco.',
'Moderación si tiene problemas renales.', 'basale.jpg', 1),

-- ID 14: Beans
(14, 'Beans', 'Phaseolus vulgaris', 'Fabaceae',
'Legumbre nutritiva rica en proteínas.',
'Proteína vegetal, control de azúcar, digestión.',
'Cocidos en guisos, sopas.',
'Siempre cocinar bien.', 'beans.jpg', 1),

-- ID 15: Betel
(15, 'Betel', 'Piper betle', 'Piperaceae',
'Hojas masticadas tradicionalmente en Asia.',
'Digestivo, antiséptico bucal, estimulante.',
'Hojas frescas masticadas.',
'Usar hojas solas con moderación.', 'betel.jpg', 1),

-- ID 16: Betel_Nut
(16, 'Betel Nut', 'Areca catechu', 'Arecaceae',
'Semilla de palma de areca.',
'Estimulante, digestivo (uso tradicional).',
'Masticada con hojas de betel.',
'CANCERÍGENA. No recomendado.', 'betel_nut.jpg', 1),

-- ID 17: Bhrami
(17, 'Bhrami', 'Bacopa monnieri', 'Plantaginaceae',
'Hierba para memoria y cognición.',
'Memoria, ansiedad, neuroprotector.',
'Jugo fresco, polvo con leche.',
'Puede causar malestar digestivo.', 'bhrami.jpg', 1),

-- ID 18: Brahmi
(18, 'Brahmi', 'Bacopa monnieri', 'Plantaginaceae',
'Hierba acuática para función cognitiva.',
'Mejora memoria, reduce ansiedad, concentración.',
'Jugo, polvo, cápsulas, aceite.',
'No usar con sedantes.', 'brahmi.jpg', 1),

-- ID 19: Bringaraja
(19, 'Bringaraja', 'Eclipta prostrata', 'Asteraceae',
'Hierba "rey del cabello".',
'Cabello, hígado, piel, antimicrobiano.',
'Aceite para cabello, jugo, polvo.',
'Consultar médico si tiene problemas hepáticos.', 'bringaraja.jpg', 1),

-- ID 20: Caricature
(20, 'Caricature', 'Graptophyllum pictum', 'Acanthaceae',
'Planta ornamental con hojas variegadas.',
'Hemorroides, heridas, inflamación.',
'Decocción de hojas, cataplasma.',
'Uso moderado.', 'caricature.jpg', 1),

-- ID 21: Castor
(21, 'Castor', 'Ricinus communis', 'Euphorbiaceae',
'Planta productora de aceite de ricino.',
'Laxante, piel, cabello, antiinflamatorio.',
'Aceite de ricino oral o tópico.',
'Semillas TÓXICAS. Solo aceite procesado.', 'castor.jpg', 1),

-- ID 22: Catharanthus
(22, 'Catharanthus', 'Catharanthus roseus', 'Apocynaceae',
'Vinca de Madagascar, fuente de alcaloides.',
'Diabetes tradicional. Fuente de fármacos anticáncer.',
'Solo bajo supervisión médica.',
'TÓXICA. No automedicarse.', 'catharanthus.jpg', 1),

-- ID 23: Chakte
(23, 'Chakte', 'Caesalpinia sappan', 'Fabaceae',
'Árbol con madera de tinte rojo.',
'Purificador de sangre, piel, heridas.',
'Decocción de madera, polvo.',
'Usar con moderación.', 'chakte.jpg', 1),

-- ID 24: Chilly
(24, 'Chilly', 'Capsicum annuum', 'Solanaceae',
'Fruto picante rico en capsaicina.',
'Metabolismo, dolor articular, circulación.',
'En comidas, cremas tópicas.',
'Evitar contacto con ojos.', 'chilly.jpg', 1),

-- ID 25: Citron lime (herelikai)
(25, 'Citron Lime', 'Citrus aurantiifolia', 'Rutaceae',
'Cítrico pequeño y ácido.',
'Vitamina C, digestión, desintoxicante, piel.',
'Jugo fresco, ralladura, agua tibia.',
'Puede erosionar esmalte dental.', 'citron_lime.jpg', 1),

-- ID 26: Coffee
(26, 'Coffee', 'Coffea arabica', 'Rubiaceae',
'Semilla tostada, bebida estimulante.',
'Estimulante mental, antioxidante, metabolismo.',
'Bebida por infusión.',
'Puede causar insomnio. Limitar 3-4 tazas.', 'coffee.jpg', 1),

-- ID 27: Common rue(naagdalli)
(27, 'Common Rue', 'Ruta graveolens', 'Rutaceae',
'Hierba aromática de olor fuerte.',
'Cólicos, espasmos, repelente de insectos.',
'Infusión muy diluida, uso externo.',
'TÓXICA en dosis altas. PROHIBIDA en embarazo.', 'common_rue.jpg', 1),

-- ID 28: Coriender
(28, 'Coriander', 'Coriandrum sativum', 'Apiaceae',
'Cilantro, hierba aromática.',
'Digestión, colesterol, azúcar, desintoxicante.',
'Hojas frescas, semillas en infusión.',
'Algunas personas son alérgicas.', 'coriender.jpg', 1),

-- ID 29: Curry
(29, 'Curry', 'Murraya koenigii', 'Rutaceae',
'Hojas de curry aromáticas.',
'Digestión, cabello, diabetes, antioxidante.',
'Hojas frescas en cocina, polvo.',
'Seguro en cantidades culinarias.', 'curry.jpg', 1),

-- ID 30: Curry_Leaf
(30, 'Curry Leaf', 'Murraya koenigii', 'Rutaceae',
'Hoja de curry esencial en cocina asiática.',
'Digestión, cabello saludable, diabetes.',
'Hojas en cocina, aceite para cabello.',
'Moderación en uso medicinal.', 'curry_leaf.jpg', 1),

-- ID 31: Doddapatre
(31, 'Doddapatre', 'Plectranthus amboinicus', 'Lamiaceae',
'Orégano cubano, hierba aromática carnosa.',
'Tos, resfriados, digestión, asma.',
'Hojas masticadas, jugo, infusión.',
'Seguro en uso moderado.', 'doddapatre.jpg', 1),

-- ID 32: Doddpathre
(32, 'Doddpathre', 'Plectranthus amboinicus', 'Lamiaceae',
'Variante de orégano cubano.',
'Infecciones respiratorias, digestión.',
'Hojas frescas, infusión.',
'No exceder dosis recomendadas.', 'doddpathre.jpg', 1),

-- ID 33: Drumstick
(33, 'Drumstick', 'Moringa oleifera', 'Moringaceae',
'Moringa, árbol milagroso nutritivo.',
'Superalimento, diabetes, inflamación, lactancia.',
'Hojas cocidas, polvo, vainas en curry.',
'Raíz puede ser tóxica.', 'drumstick.jpg', 1),

-- ID 34: Ekka
(34, 'Ekka', 'Calotropis gigantea', 'Apocynaceae',
'Algodón de seda, arbusto con látex.',
'Uso externo: heridas, dolor articular.',
'Látex diluido externo, hojas en cataplasma.',
'TÓXICO internamente. Solo uso externo.', 'ekka.jpg', 1),

-- ID 35: Eucalyptus
(35, 'Eucalyptus', 'Eucalyptus globulus', 'Myrtaceae',
'Árbol con hojas aromáticas.',
'Congestión nasal, tos, resfriados, antiséptico.',
'Inhalación de vapor, aceite diluido.',
'No ingerir aceite puro.', 'eucalyptus.jpg', 1),

-- ID 36: Ganigale
(36, 'Ganigale', 'Solanum nigrum', 'Solanaceae',
'Hierba con bayas negras.',
'Hígado, úlceras, fiebre, inflamación.',
'Hojas cocidas, extracto.',
'Bayas verdes TÓXICAS.', 'ganigale.jpg', 1),

-- ID 37: Ganike
(37, 'Ganike', 'Solanum nigrum', 'Solanaceae',
'Variante de hierba mora.',
'Hígado, fiebre, inflamación.',
'Hojas cocidas.',
'Solo bayas maduras cocidas.', 'ganike.jpg', 1),

-- ID 38: Gasagase
(38, 'Gasagase', 'Papaver somniferum', 'Papaveraceae',
'Semilla de amapola para cocina.',
'Sueño, relajación, digestión.',
'Semillas en cocina, pasta.',
'Semillas seguras. Planta controlada.', 'gasagase.jpg', 1),

-- ID 39: Gauva
(39, 'Gauva', 'Psidium guajava', 'Myrtaceae',
'Guayaba, fruta tropical.',
'Digestión, inmunidad, diabetes, diarrea.',
'Fruta fresca, jugo, té de hojas.',
'Semillas pueden causar malestar.', 'gauva.jpg', 1),

-- ID 40: Geranium
(40, 'Geranium', 'Pelargonium graveolens', 'Geraniaceae',
'Planta aromática con aceite esencial.',
'Ansiedad, piel, equilibrio hormonal.',
'Aceite esencial diluido, infusión.',
'Aceite no ingerir.', 'geranium.jpg', 1),

-- ID 41: Ginger
(41, 'Ginger', 'Zingiber officinale', 'Zingiberaceae',
'Jengibre, rizoma aromático y picante.',
'Náuseas, digestión, antiinflamatorio, resfriados.',
'Fresco rallado, té, polvo.',
'Puede interactuar con anticoagulantes.', 'ginger.jpg', 1),

-- ID 42: Globe Amarnath
(42, 'Globe Amaranth', 'Gomphrena globosa', 'Amaranthaceae',
'Planta ornamental con flores redondas.',
'Tos, bronquitis, problemas respiratorios.',
'Infusión de flores secas.',
'Uso moderado.', 'globe_amaranth.jpg', 1),

-- ID 43: Guava
(43, 'Guava', 'Psidium guajava', 'Myrtaceae',
'Guayaba, fruta rica en vitamina C.',
'Digestión, inmunidad, diabetes, piel.',
'Fruta fresca, jugo, té de hojas.',
'Hojas muy astringentes.', 'guava.jpg', 1),

-- ID 44: Henna
(44, 'Henna', 'Lawsonia inermis', 'Lythraceae',
'Planta de tinte natural.',
'Tinte natural, cabello, hongos, heridas.',
'Pasta de hojas para cabello/piel.',
'No usar henna negra.', 'henna.jpg', 1),

-- ID 45: Hibiscus
(45, 'Hibiscus', 'Hibiscus rosa-sinensis', 'Malvaceae',
'Flor tropical para cabello y té.',
'Cabello, presión arterial, antioxidante.',
'Té de flores, aceite, pasta.',
'Puede reducir presión. No en embarazo.', 'hibiscus.jpg', 1),

-- ID 46: Honge
(46, 'Honge', 'Millettia pinnata', 'Fabaceae',
'Pongamia, árbol con aceite medicinal.',
'Piel, artritis, heridas, antiséptico.',
'Aceite externo, pasta de hojas.',
'Solo uso externo. Tóxico internamente.', 'honge.jpg', 1),

-- ID 47: Insulin
(47, 'Insulin Plant', 'Costus igneus', 'Costaceae',
'Planta para control de diabetes.',
'Diabetes, control de azúcar.',
'Hojas frescas masticadas (1-2), jugo.',
'No reemplaza medicamentos. Monitorear azúcar.', 'insulin.jpg', 1),

-- ID 48: Jackfruit
(48, 'Jackfruit', 'Artocarpus heterophyllus', 'Moraceae',
'Yaca, fruta tropical gigante.',
'Energía, digestión, inmunidad, piel.',
'Fruta madura, semillas cocidas.',
'Alergia posible en sensibles al látex.', 'jackfruit.jpg', 1),

-- ID 49: Jasmine
(49, 'Jasmine', 'Jasminum officinale', 'Oleaceae',
'Flor aromática relajante.',
'Relajante, ansiedad, piel, lactancia.',
'Té de flores, aceite esencial.',
'Aceite no ingerir.', 'jasmine.jpg', 1),

-- ID 50: Kambajala
(50, 'Kambajala', 'Croton tiglium', 'Euphorbiaceae',
'Planta con semillas muy potentes.',
'Purgante fuerte (uso histórico).',
'Solo bajo supervisión experta.',
'MUY TÓXICA. No usar sin supervisión.', 'kambajala.jpg', 1),

-- ID 51: Kohlrabi
(51, 'Kohlrabi', 'Brassica oleracea gongylodes', 'Brassicaceae',
'Colirrábano, vegetal crucífero.',
'Fibra, vitamina C, digestión, antioxidante.',
'Crudo en ensaladas, cocido.',
'Puede causar gases.', 'kohlrabi.jpg', 1),

-- ID 52: Lantana
(52, 'Lantana', 'Lantana camara', 'Verbenaceae',
'Planta ornamental. Precaución: tóxica.',
'Solo uso externo para heridas.',
'Hojas machacadas externamente.',
'TÓXICA. No ingerir. Bayas peligrosas.', 'lantana.jpg', 1),

-- ID 53: Lemon
(53, 'Lemon', 'Citrus limon', 'Rutaceae',
'Limón, cítrico versátil.',
'Vitamina C, digestión, desintoxicante, inmunidad.',
'Jugo fresco, ralladura, agua con limón.',
'Puede dañar esmalte dental.', 'lemon.jpg', 1),

-- ID 54: Lemon_grass
(54, 'Lemon Grass', 'Cymbopogon citratus', 'Poaceae',
'Hierba limón aromática.',
'Digestión, fiebre, ansiedad, antimicrobiano.',
'Té de tallos, aceite, en cocina.',
'Puede causar mareo en exceso.', 'lemon_grass.jpg', 1),

-- ID 55: Malabar_Nut
(55, 'Malabar Nut', 'Justicia adhatoda', 'Acanthaceae',
'Vasaka, para problemas respiratorios.',
'Tos, asma, bronquitis, expectorante.',
'Decocción de hojas, jugo, jarabe.',
'No usar en embarazo.', 'malabar_nut.jpg', 1),

-- ID 56: Malabar_Spinach
(56, 'Malabar Spinach', 'Basella alba', 'Basellaceae',
'Espinaca Malabar, vegetal trepador.',
'Laxante suave, anemia, refrescante.',
'Hojas cocidas, en sopas.',
'Moderación por oxalatos.', 'malabar_spinach.jpg', 1),

-- ID 57: Mango
(57, 'Mango', 'Mangifera indica', 'Anacardiaceae',
'Mango, rey de las frutas.',
'Vitamina A y C, digestión, inmunidad, piel.',
'Fruta fresca, jugo, hojas en decocción.',
'Exceso puede causar diarrea.', 'mango.jpg', 1),

-- ID 58: Marigold
(58, 'Marigold', 'Calendula officinalis', 'Asteraceae',
'Caléndula, flor curativa para piel.',
'Heridas, piel, inflamación, antiséptico.',
'Infusión, aceite, cremas.',
'Alergia posible en sensibles a Asteraceae.', 'marigold.jpg', 1),

-- ID 59: Mint
(59, 'Mint', 'Mentha spicata', 'Lamiaceae',
'Menta, hierba aromática refrescante.',
'Digestión, náuseas, dolor de cabeza, resfriados.',
'Hojas frescas, té, aceite.',
'Aceite puede causar acidez.', 'mint.jpg', 1),

-- ID 60: Nagadali
(60, 'Nagadali', 'Ruta graveolens', 'Rutaceae',
'Variedad de ruda medicinal.',
'Digestión, parásitos, menstruación.',
'Uso externo o infusión muy diluida.',
'TÓXICA. PROHIBIDA en embarazo.', 'nagadali.jpg', 1),

-- ID 61: Neem
(61, 'Neem', 'Azadirachta indica', 'Meliaceae',
'Árbol sagrado, "farmacia del pueblo".',
'Antibacteriano, piel, diabetes, dental.',
'Hojas en pasta, aceite, ramitas.',
'Muy amargo. No en embarazo.', 'neem.jpg', 1),

-- ID 62: Nelavembu
(62, 'Nelavembu', 'Andrographis paniculata', 'Acanthaceae',
'Andrographis, "rey de los amargos".',
'Inmunidad, infecciones, fiebre, hígado.',
'Decocción, polvo, cápsulas.',
'Muy amargo. Puede reducir presión.', 'nelavembu.jpg', 1),

-- ID 63: Nerale
(63, 'Nerale', 'Syzygium cumini', 'Myrtaceae',
'Jamun, fruta para diabéticos.',
'Diabetes, digestión, diarrea, salud bucal.',
'Fruta fresca, semillas en polvo.',
'Puede reducir mucho el azúcar.', 'nerale.jpg', 1),

-- ID 64: Nithyapushpa
(64, 'Nithyapushpa', 'Catharanthus roseus', 'Apocynaceae',
'Vinca, flores todo el año.',
'Diabetes tradicional, fuente anticancerígenos.',
'Solo bajo supervisión médica.',
'TÓXICA. Alcaloides muy potentes.', 'nithyapushpa.jpg', 1),

-- ID 65: Nooni
(65, 'Nooni', 'Morinda citrifolia', 'Rubiaceae',
'Noni, fruta medicinal de olor fuerte.',
'Inmunidad, inflamación, dolor, antioxidante.',
'Jugo, fruta seca, cápsulas.',
'Alto en potasio. Evitar si problemas renales.', 'nooni.jpg', 1),

-- ID 66: Onion
(66, 'Onion', 'Allium cepa', 'Amaryllidaceae',
'Cebolla, bulbo esencial en cocina.',
'Inmunidad, corazón, antiinflamatorio, tos.',
'Cruda, cocida, jugo, jarabe con miel.',
'Puede causar acidez.', 'onion.jpg', 1),

-- ID 67: Padri
(67, 'Padri', 'Stereospermum chelonoides', 'Bignoniaceae',
'Árbol ayurvédico tradicional.',
'Fiebre, inflamación, tos, respiratorio.',
'Decocción de corteza y raíz.',
'Consultar profesional ayurvédico.', 'padri.jpg', 1),

-- ID 68: Palak(Spinach)
(68, 'Palak Spinach', 'Spinacia oleracea', 'Amaranthaceae',
'Espinaca, vegetal muy nutritivo.',
'Hierro, anemia, huesos, visión, energía.',
'Cruda, cocida, en smoothies.',
'Alto en oxalatos.', 'palak_spinach.jpg', 1),

-- ID 69: Papaya
(69, 'Papaya', 'Carica papaya', 'Caricaceae',
'Papaya, fruta con enzimas digestivas.',
'Digestión, parásitos, piel, inmunidad.',
'Fruta madura, semillas, hojas en té.',
'Semillas en exceso tóxicas. Evitar verde en embarazo.', 'papaya.jpg', 1),

-- ID 70: Pappaya
(70, 'Pappaya', 'Carica papaya', 'Caricaceae',
'Variante de papaya.',
'Digestión, piel, inflamación.',
'Fruta fresca, jugo.',
'Evitar papaya verde en embarazo.', 'pappaya.jpg', 1),

-- ID 71: Parijatha
(71, 'Parijatha', 'Nyctanthes arbor-tristis', 'Oleaceae',
'Jazmín de noche, flores fragantes.',
'Artritis, fiebre, tos, parásitos.',
'Decocción de hojas, flores, corteza.',
'Uso moderado.', 'parijatha.jpg', 1),

-- ID 72: Pepper
(72, 'Pepper', 'Piper nigrum', 'Piperaceae',
'Pimienta negra, rey de las especias.',
'Digestión, absorción de nutrientes, metabolismo.',
'Granos en comidas, con miel para tos.',
'Puede irritar estómago.', 'pepper.jpg', 1),

-- ID 73: Pomegranate
(73, 'Pomegranate', 'Punica granatum', 'Lythraceae',
'Granada, fruta antioxidante.',
'Corazón, antioxidante, inflamación, memoria.',
'Semillas frescas, jugo, extracto.',
'Puede interactuar con medicamentos.', 'pomegranate.jpg', 1),

-- ID 74: Pomoegranate
(74, 'Pomoegranate', 'Punica granatum', 'Lythraceae',
'Variante de granada.',
'Antioxidante, corazón, piel.',
'Jugo, semillas.',
'Jugo mancha la ropa.', 'pomoegranate.jpg', 1),

-- ID 75: Pumpkin
(75, 'Pumpkin', 'Cucurbita pepo', 'Cucurbitaceae',
'Calabaza, vegetal con semillas medicinales.',
'Visión, inmunidad, próstata, parásitos.',
'Pulpa cocida, semillas tostadas.',
'Diabéticos moderar pulpa dulce.', 'pumpkin.jpg', 1),

-- ID 76: Raktachandini
(76, 'Raktachandini', 'Pterocarpus santalinus', 'Fabaceae',
'Sándalo rojo para piel.',
'Piel, purificador de sangre, heridas.',
'Pasta de madera, polvo externo.',
'Principalmente uso externo.', 'raktachandini.jpg', 1),

-- ID 77: Rose
(77, 'Rose', 'Rosa damascena', 'Rosaceae',
'Rosa, flor aromática.',
'Piel, digestión, relajante, ojos.',
'Agua de rosas, pétalos en té.',
'Diluir aceite esencial.', 'rose.jpg', 1),

-- ID 78: Sampige
(78, 'Sampige', 'Magnolia champaca', 'Magnoliaceae',
'Champak, flores muy fragantes.',
'Fiebre, dolor de cabeza, tónico cardíaco.',
'Flores en infusión, aceite.',
'Uso moderado.', 'sampige.jpg', 1),

-- ID 79: Sapota
(79, 'Sapota', 'Manilkara zapota', 'Sapotaceae',
'Zapote, fruta tropical dulce.',
'Energía, digestión, huesos, piel.',
'Fruta madura, batidos.',
'Semillas no comestibles.', 'sapota.jpg', 1),

-- ID 80: Seethapala
(80, 'Seethapala', 'Annona squamosa', 'Annonaceae',
'Chirimoya, manzana de azúcar.',
'Energía, digestión, piel, cabello.',
'Pulpa fresca, batidos.',
'Semillas TÓXICAS. No consumir.', 'seethapala.jpg', 1),

-- ID 81: Spinach1
(81, 'Spinach', 'Spinacia oleracea', 'Amaranthaceae',
'Espinaca, hoja verde nutritiva.',
'Hierro, energía, visión, huesos.',
'Cruda o cocida.',
'Moderar si tiene cálculos renales.', 'spinach1.jpg', 1),

-- ID 82: Tamarind
(82, 'Tamarind', 'Tamarindus indica', 'Fabaceae',
'Tamarindo, fruta ácida.',
'Digestión, laxante, fiebre, hígado.',
'Pulpa en agua, en cocina.',
'Alto en azúcar.', 'tamarind.jpg', 1),

-- ID 83: Taro
(83, 'Taro', 'Colocasia esculenta', 'Araceae',
'Taro, tubérculo comestible.',
'Digestión, energía, presión arterial.',
'Siempre cocido. Hervido, horneado.',
'TÓXICO CRUDO. Cocinar siempre.', 'taro.jpg', 1),

-- ID 84: Tecoma
(84, 'Tecoma', 'Tecoma stans', 'Bignoniaceae',
'Arbusto amarillo para diabetes.',
'Diabetes, digestión, dolor.',
'Decocción de hojas, flores.',
'Puede reducir azúcar mucho. Monitorear.', 'tecoma.jpg', 1),

-- ID 85: Thumbe
(85, 'Thumbe', 'Leucas aspera', 'Lamiaceae',
'Hierba para resfriados y fiebre.',
'Tos, resfriados, fiebre, heridas.',
'Jugo de hojas, decocción.',
'Uso moderado.', 'thumbe.jpg', 1),

-- ID 86: Tomato
(86, 'Tomato', 'Solanum lycopersicum', 'Solanaceae',
'Tomate, fruto rico en licopeno.',
'Corazón, próstata, piel, antioxidante.',
'Fresco, cocido, jugo.',
'Puede causar acidez.', 'tomato.jpg', 1),

-- ID 87: Tulasi
(87, 'Tulasi', 'Ocimum tenuiflorum', 'Lamiaceae',
'Albahaca sagrada de India.',
'Inmunidad, estrés, respiratorio, digestión.',
'Hojas masticadas, té, jugo.',
'Puede reducir fertilidad. No en embarazo.', 'tulasi.jpg', 1),

-- ID 88: Tulsi
(88, 'Tulsi', 'Ocimum tenuiflorum', 'Lamiaceae',
'Holy Basil, planta sagrada adaptógena.',
'Inmunidad, antibacteriano, estrés.',
'Hojas frescas, té.',
'No en embarazo prolongado.', 'tulsi.jpg', 1),

-- ID 89: Wood_sorel
(89, 'Wood Sorel', 'Oxalis acetosella', 'Oxalidaceae',
'Acedera, hierba de sabor ácido.',
'Digestión, fiebre, refrescante.',
'Hojas frescas en pequeñas cantidades.',
'Alta en oxalatos. Evitar si cálculos.', 'wood_sorel.jpg', 1),

-- ID 90: ashoka
(90, 'Ashoka Tree', 'Saraca asoca', 'Fabaceae',
'Variante de árbol Ashoka.',
'Salud femenina, menstruación.',
'Decocción de corteza.',
'No en embarazo.', 'ashoka_tree.jpg', 1),

-- ID 91: camphor
(91, 'Camphor', 'Cinnamomum camphora', 'Lauraceae',
'Alcanfor, sustancia aromática.',
'Congestión, dolor muscular, tos.',
'Uso externo: ungüentos, inhalación.',
'TÓXICO si se ingiere. Solo externo.', 'camphor.jpg', 1),

-- ID 92: kamakasturi
(92, 'Kamakasturi', 'Abelmoschus moschatus', 'Malvaceae',
'Planta con semillas almizcladas.',
'Ansiedad, digestión, piel.',
'Semillas en polvo, aceite.',
'Uso moderado.', 'kamakasturi.jpg', 1),

-- ID 93: kepala
(93, 'Kepala', 'Momordica dioica', 'Cucurbitaceae',
'Calabaza espinosa pequeña.',
'Diabetes, digestión, fiebre.',
'Vegetal cocido, decocción.',
'Amargo. Introducir gradualmente.', 'kepala.jpg', 1);

-- ============================================================
-- VERIFICAR
-- ============================================================
SELECT COUNT(*) as total_plantas FROM plants;
SELECT id, common_name FROM plants ORDER BY id;

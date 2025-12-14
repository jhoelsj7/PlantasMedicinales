-- ============================================================
-- SQL PARA INSERTAR LAS 94 PLANTAS DEL MODELO DE IA
-- Ejecutar en tu base de datos MySQL/MariaDB
-- ============================================================

-- Primero, eliminar las plantas existentes (OPCIONAL - descomentar si quieres empezar limpio)
-- TRUNCATE TABLE plants;

-- ============================================================
-- INSERTAR PLANTAS DEL MODELO (labels.txt)
-- ============================================================

INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path, is_active) VALUES

-- 1. Aloevera
('Aloe Vera', 'Aloe barbadensis miller', 'Asphodelaceae',
'Planta suculenta conocida por sus hojas carnosas que contienen un gel transparente con múltiples propiedades medicinales.',
'Cicatrizante, hidratante, antiinflamatorio, tratamiento de quemaduras, problemas digestivos, cuidado de la piel.',
'Gel: Cortar una hoja, extraer el gel y aplicar directamente. Jugo: Licuar el gel con agua para consumo interno.',
'No consumir en exceso. Contraindicado en embarazo. Puede causar diarrea en dosis altas.',
'aloevera.jpg', 1),

-- 2. Amla
('Amla', 'Phyllanthus emblica', 'Phyllanthaceae',
'Grosella espinosa india, fruto rico en vitamina C con potentes propiedades antioxidantes.',
'Fortalece el sistema inmune, mejora la digestión, promueve el crecimiento del cabello, antioxidante.',
'Jugo fresco, polvo seco mezclado con agua o miel, aceite para el cabello.',
'Puede interactuar con medicamentos para diabetes. Consumir con moderación.',
'amla.jpg', 1),

-- 3. Amruta Balli / Amruthaballi (Guduchi)
('Guduchi', 'Tinospora cordifolia', 'Menispermaceae',
'Planta trepadora conocida como "néctar divino" en Ayurveda por sus propiedades inmunomoduladoras.',
'Fortalece inmunidad, antipirético, antidiabético, desintoxicante, artritis.',
'Decocción del tallo, polvo, jugo fresco del tallo.',
'Puede reducir azúcar en sangre. Consultar médico si toma medicamentos.',
'guduchi.jpg', 1),

-- 4. Arali (Adelfa)
('Adelfa', 'Nerium oleander', 'Apocynaceae',
'Arbusto ornamental con flores vistosas. PRECAUCIÓN: Todas las partes son tóxicas.',
'Uso externo limitado en medicina tradicional para afecciones de la piel. NO CONSUMIR.',
'Solo uso externo bajo supervisión experta.',
'ALTAMENTE TÓXICA. No ingerir. Mantener alejada de niños y mascotas.',
'arali.jpg', 1),

-- 5. Ashoka
('Ashoka', 'Saraca asoca', 'Fabaceae',
'Árbol sagrado en India, su corteza es muy valorada en medicina ayurvédica para salud femenina.',
'Trastornos menstruales, salud uterina, hemorragias, problemas de piel.',
'Decocción de la corteza, polvo, extracto líquido.',
'No usar durante embarazo. Consultar médico para uso prolongado.',
'ashoka.jpg', 1),

-- 6. Ashwagandha
('Ashwagandha', 'Withania somnifera', 'Solanaceae',
'Hierba adaptógena conocida como "ginseng indio", reduce el estrés y aumenta la energía.',
'Reduce estrés y ansiedad, aumenta energía, mejora concentración, fortalece inmunidad.',
'Polvo con leche tibia, cápsulas, extracto líquido.',
'No usar en embarazo. Puede interactuar con sedantes y medicamentos tiroideos.',
'ashwagandha.jpg', 1),

-- 7. Astma Weed (Euphorbia hirta)
('Hierba del Asma', 'Euphorbia hirta', 'Euphorbiaceae',
'Hierba pequeña utilizada tradicionalmente para problemas respiratorios.',
'Asma, bronquitis, tos, problemas respiratorios, disentería.',
'Infusión de la planta entera, decocción.',
'No usar en embarazo. Consultar médico antes de usar.',
'astma_weed.jpg', 1),

-- 8. Avocado
('Aguacate', 'Persea americana', 'Lauraceae',
'Fruto nutritivo rico en grasas saludables, vitaminas y minerales.',
'Salud cardiovascular, piel saludable, antiinflamatorio, digestión.',
'Consumo directo del fruto, aceite para cocinar y uso tópico, mascarillas.',
'Alto en calorías. Alérgicos al látex pueden reaccionar.',
'avocado.jpg', 1),

-- 9. Badipala
('Badipala', 'Sterculia urens', 'Malvaceae',
'Árbol que produce goma karaya, utilizada en medicina tradicional india.',
'Laxante, problemas digestivos, control de peso, diabetes.',
'Goma disuelta en agua, polvo.',
'Consumir con abundante agua. No exceder dosis recomendada.',
'badipala.jpg', 1),

-- 10. Balloon Vine
('Farolillo', 'Cardiospermum halicacabum', 'Sapindaceae',
'Planta trepadora con frutos en forma de globo, usada en medicina tradicional.',
'Artritis, reumatismo, problemas de piel, antiinflamatorio.',
'Decocción de hojas, pasta para uso tópico, aceite.',
'Usar con precaución. Consultar profesional de salud.',
'balloon_vine.jpg', 1),

-- 11. Bamboo
('Bambú', 'Bambusoideae', 'Poaceae',
'Gramínea gigante con múltiples usos medicinales, especialmente sus brotes y hojas.',
'Rico en sílice, fortalece huesos, cabello y uñas, digestión, desintoxicante.',
'Brotes cocidos, té de hojas, extracto de bambú.',
'Los brotes crudos contienen toxinas. Siempre cocinar antes de consumir.',
'bamboo.jpg', 1),

-- 12. Basale (Espinaca de Malabar)
('Espinaca de Malabar', 'Basella alba', 'Basellaceae',
'Vegetal de hoja verde oscuro, nutritivo y con propiedades medicinales.',
'Rico en hierro, laxante suave, refrescante, problemas urinarios.',
'Hojas cocidas, jugo fresco, en ensaladas.',
'Consumir cocido preferentemente. Moderación si tiene problemas renales.',
'basale.jpg', 1),

-- 13. Beans (Frijoles)
('Frijol', 'Phaseolus vulgaris', 'Fabaceae',
'Legumbre nutritiva rica en proteínas, fibra y minerales.',
'Proteína vegetal, control de azúcar, salud digestiva, energía.',
'Cocidos en guisos, sopas, ensaladas.',
'Siempre cocinar bien. Los crudos contienen lectinas tóxicas.',
'beans.jpg', 1),

-- 14. Betel
('Betel', 'Piper betle', 'Piperaceae',
'Planta trepadora cuyas hojas se mastican tradicionalmente en Asia.',
'Digestivo, antiséptico bucal, refrescante del aliento, estimulante.',
'Hojas frescas masticadas, pasta para uso tópico.',
'Masticar con nuez de areca es cancerígeno. Usar hojas solas con moderación.',
'betel.jpg', 1),

-- 15. Betel Nut
('Nuez de Betel', 'Areca catechu', 'Arecaceae',
'Semilla de la palma de areca, usada tradicionalmente como estimulante.',
'Estimulante, digestivo (uso tradicional).',
'Tradicionalmente masticada con hojas de betel.',
'CLASIFICADA COMO CANCERÍGENA. Su uso no es recomendado.',
'betel_nut.jpg', 1),

-- 16. Brahmi
('Brahmi', 'Bacopa monnieri', 'Plantaginaceae',
'Hierba acuática conocida por mejorar la memoria y función cognitiva.',
'Mejora memoria, reduce ansiedad, neuroprotector, concentración.',
'Jugo fresco, polvo con leche, cápsulas, aceite para cabello.',
'Puede causar malestar digestivo. No usar con sedantes.',
'brahmi.jpg', 1),

-- 17. Bringaraja (Eclipta)
('Bhringraj', 'Eclipta prostrata', 'Asteraceae',
'Hierba conocida como "rey del cabello" en Ayurveda.',
'Crecimiento del cabello, hígado saludable, piel, antimicrobiano.',
'Aceite para cabello, jugo fresco, polvo.',
'Consultar médico si tiene problemas hepáticos o toma medicamentos.',
'bringaraja.jpg', 1),

-- 18. Caricature (Graptophyllum)
('Caricatura', 'Graptophyllum pictum', 'Acanthaceae',
'Planta ornamental con hojas variegadas y propiedades medicinales.',
'Hemorroides, heridas, inflamación, estreñimiento.',
'Decocción de hojas, cataplasma para uso externo.',
'Uso moderado. Consultar profesional de salud.',
'caricature.jpg', 1),

-- 19. Castor
('Ricino', 'Ricinus communis', 'Euphorbiaceae',
'Planta cuyas semillas producen aceite de ricino, ampliamente utilizado.',
'Laxante, piel y cabello, antiinflamatorio, inducción de parto.',
'Aceite de ricino vía oral o tópico.',
'Las semillas son TÓXICAS. Solo usar aceite procesado. No en embarazo.',
'castor.jpg', 1),

-- 20. Catharanthus (Vinca)
('Vinca de Madagascar', 'Catharanthus roseus', 'Apocynaceae',
'Planta ornamental fuente de alcaloides usados en quimioterapia.',
'Diabetes (uso tradicional). Fuente de vincristina y vinblastina para cáncer.',
'Solo bajo supervisión médica estricta.',
'TÓXICA. No automedicarse. Los alcaloides son potentes fármacos.',
'catharanthus.jpg', 1),

-- 21. Chakte
('Chakte', 'Caesalpinia sappan', 'Fabaceae',
'Árbol cuya madera produce tinte rojo y tiene usos medicinales.',
'Purificador de sangre, problemas de piel, heridas, diabetes.',
'Decocción de madera, polvo.',
'Usar con moderación. Consultar profesional de salud.',
'chakte.jpg', 1),

-- 22. Chilly (Chile)
('Chile', 'Capsicum annuum', 'Solanaceae',
'Fruto picante rico en capsaicina con propiedades medicinales.',
'Metabolismo, dolor articular, circulación, digestión.',
'Consumo en comidas, cremas tópicas con capsaicina.',
'Evitar contacto con ojos. Puede irritar estómago sensible.',
'chilly.jpg', 1),

-- 23. Citron Lime
('Lima', 'Citrus aurantiifolia', 'Rutaceae',
'Cítrico pequeño y ácido con múltiples beneficios para la salud.',
'Vitamina C, digestión, desintoxicante, resfriados, piel.',
'Jugo fresco, ralladura, en agua tibia.',
'Puede erosionar esmalte dental. Enjuagar boca después de consumir.',
'citron_lime.jpg', 1),

-- 24. Coffee
('Café', 'Coffea arabica', 'Rubiaceae',
'Semilla tostada que produce la bebida estimulante más popular del mundo.',
'Estimulante mental, antioxidante, rendimiento físico, metabolismo.',
'Bebida preparada por infusión de granos molidos.',
'Puede causar insomnio y ansiedad. Limitar a 3-4 tazas diarias.',
'coffee.jpg', 1),

-- 25. Common Rue
('Ruda', 'Ruta graveolens', 'Rutaceae',
'Hierba aromática con fuerte olor, usada en medicina tradicional.',
'Cólicos, espasmos, repelente de insectos, problemas menstruales.',
'Infusión muy diluida, uso externo.',
'TÓXICA en dosis altas. PROHIBIDA en embarazo. Puede causar fotosensibilidad.',
'common_rue.jpg', 1),

-- 26. Coriander
('Cilantro', 'Coriandrum sativum', 'Apiaceae',
'Hierba aromática cuyas hojas y semillas se usan en cocina y medicina.',
'Digestión, colesterol, azúcar en sangre, desintoxicante.',
'Hojas frescas, semillas en infusión, polvo de semillas.',
'Algunas personas son alérgicas. Usar con moderación.',
'coriander.jpg', 1),

-- 27. Curry Leaf
('Hoja de Curry', 'Murraya koenigii', 'Rutaceae',
'Hojas aromáticas esenciales en cocina del sur de Asia con beneficios medicinales.',
'Digestión, cabello saludable, diabetes, antioxidante.',
'Hojas frescas en cocina, polvo, aceite para cabello.',
'Seguro en cantidades culinarias. Moderación en uso medicinal.',
'curry_leaf.jpg', 1),

-- 28. Doddapatre (Ajwain indio)
('Orégano Cubano', 'Plectranthus amboinicus', 'Lamiaceae',
'Hierba aromática carnosa con sabor a orégano y tomillo.',
'Tos, resfriados, digestión, asma, infecciones respiratorias.',
'Hojas frescas masticadas, jugo, infusión.',
'Seguro en uso moderado. No exceder dosis recomendadas.',
'doddapatre.jpg', 1),

-- 29. Drumstick
('Moringa', 'Moringa oleifera', 'Moringaceae',
'Árbol "milagroso" extremadamente nutritivo, todas sus partes son útiles.',
'Superalimento, diabetes, inflamación, lactancia, desnutrición.',
'Hojas cocidas, polvo de hojas, vainas en curry.',
'Raíz puede ser tóxica. Embarazadas evitar corteza y raíz.',
'drumstick.jpg', 1),

-- 30. Ekka (Calotropis)
('Algodón de Seda', 'Calotropis gigantea', 'Apocynaceae',
'Arbusto con látex blanco, usado en medicina tradicional con precaución.',
'Uso externo: heridas, dolor articular, problemas de piel.',
'Látex diluido para uso externo, hojas en cataplasma.',
'TÓXICO internamente. Solo uso externo. Evitar contacto con ojos.',
'ekka.jpg', 1),

-- 31. Eucalyptus
('Eucalipto', 'Eucalyptus globulus', 'Myrtaceae',
'Árbol alto cuyas hojas producen aceite esencial con propiedades respiratorias.',
'Congestión nasal, tos, resfriados, dolor muscular, antiséptico.',
'Inhalación de vapor, aceite esencial diluido, ungüentos.',
'No ingerir aceite puro. Mantener alejado de niños. Puede irritar piel.',
'eucalyptus.jpg', 1),

-- 32. Ganigale/Ganike
('Ganike', 'Solanum nigrum', 'Solanaceae',
'Hierba con bayas negras, usada en medicina tradicional india.',
'Hígado, úlceras, fiebre, inflamación.',
'Hojas cocidas, extracto.',
'Bayas verdes son TÓXICAS. Solo consumir bayas maduras cocidas.',
'ganike.jpg', 1),

-- 33. Gasagase (Semilla de Amapola)
('Semilla de Amapola', 'Papaver somniferum', 'Papaveraceae',
'Semillas usadas en cocina, diferentes de la planta que produce opio.',
'Sueño, relajación, digestión, dolor (uso tradicional).',
'Semillas en cocina, pasta, leche de semillas.',
'Las semillas son seguras. La planta entera es controlada/ilegal.',
'gasagase.jpg', 1),

-- 34. Guava
('Guayaba', 'Psidium guajava', 'Myrtaceae',
'Fruta tropical rica en vitamina C, fibra y antioxidantes.',
'Digestión, inmunidad, diabetes, diarrea, piel saludable.',
'Fruta fresca, jugo, té de hojas.',
'Semillas pueden causar malestar. Las hojas son muy astringentes.',
'guava.jpg', 1),

-- 35. Geranium
('Geranio', 'Pelargonium graveolens', 'Geraniaceae',
'Planta aromática cuyo aceite esencial tiene múltiples usos.',
'Ansiedad, piel, equilibrio hormonal, repelente de insectos.',
'Aceite esencial diluido, infusión de hojas.',
'Aceite esencial no ingerir. Puede irritar piel sensible.',
'geranium.jpg', 1),

-- 36. Ginger
('Jengibre', 'Zingiber officinale', 'Zingiberaceae',
'Rizoma aromático y picante con potentes propiedades medicinales.',
'Náuseas, digestión, antiinflamatorio, resfriados, dolor.',
'Fresco rallado, té, polvo, cápsulas.',
'Puede interactuar con anticoagulantes. Moderación si tiene cálculos biliares.',
'ginger.jpg', 1),

-- 37. Globe Amaranth
('Amaranto Globo', 'Gomphrena globosa', 'Amaranthaceae',
'Planta ornamental con flores redondas, usada en medicina tradicional.',
'Tos, bronquitis, problemas respiratorios, relajante.',
'Infusión de flores secas.',
'Uso moderado. Poca investigación científica disponible.',
'globe_amaranth.jpg', 1),

-- 38. Henna
('Henna', 'Lawsonia inermis', 'Lythraceae',
'Planta cuyas hojas producen tinte natural para cabello y piel.',
'Tinte natural, cabello saludable, refrescante, hongos, heridas.',
'Pasta de hojas para cabello/piel, polvo.',
'No usar henna negra (contiene químicos dañinos). Hacer prueba de alergia.',
'henna.jpg', 1),

-- 39. Hibiscus
('Hibisco', 'Hibiscus rosa-sinensis', 'Malvaceae',
'Flor tropical grande usada para cabello, piel y té medicinal.',
'Cabello saludable, presión arterial, antioxidante, menstruación.',
'Té de flores, aceite para cabello, pasta para piel.',
'Puede reducir presión arterial. No usar en embarazo.',
'hibiscus.jpg', 1),

-- 40. Honge (Pongamia)
('Pongamia', 'Millettia pinnata', 'Fabaceae',
'Árbol cuyas semillas producen aceite con propiedades medicinales.',
'Problemas de piel, artritis, heridas, antiséptico.',
'Aceite para uso externo, pasta de hojas.',
'Solo uso externo. No ingerir, es tóxico.',
'honge.jpg', 1),

-- 41. Insulin Plant
('Planta de Insulina', 'Costus igneus', 'Costaceae',
'Planta cuyas hojas se usan tradicionalmente para controlar diabetes.',
'Diabetes, control de azúcar en sangre.',
'Hojas frescas masticadas (1-2 diarias), jugo.',
'No reemplaza medicamentos. Monitorear azúcar. Consultar médico.',
'insulin.jpg', 1),

-- 42. Jackfruit
('Yaca', 'Artocarpus heterophyllus', 'Moraceae',
'Fruta tropical gigante, nutritiva con múltiples usos medicinales.',
'Energía, digestión, inmunidad, piel, diabetes.',
'Fruta madura, semillas cocidas, hojas en decocción.',
'Puede causar alergia en sensibles al látex. Diabéticos moderar consumo.',
'jackfruit.jpg', 1),

-- 43. Jasmine
('Jazmín', 'Jasminum officinale', 'Oleaceae',
'Flor aromática usada en perfumería y medicina tradicional.',
'Relajante, ansiedad, piel, afrodisíaco, lactancia.',
'Té de flores, aceite esencial, flores frescas.',
'Aceite esencial no ingerir. Usar diluido en piel.',
'jasmine.jpg', 1),

-- 44. Kambajala
('Kambajala', 'Croton tiglium', 'Euphorbiaceae',
'Planta con semillas muy potentes usada en medicina tradicional.',
'Purgante fuerte (uso histórico).',
'Solo bajo supervisión de experto tradicional.',
'MUY TÓXICA. Las semillas son peligrosas. No usar sin supervisión.',
'kambajala.jpg', 1),

-- 45. Kohlrabi
('Colirrábano', 'Brassica oleracea var. gongylodes', 'Brassicaceae',
'Vegetal crucífero con bulbo comestible rico en nutrientes.',
'Fibra, vitamina C, digestión, pérdida de peso, antioxidante.',
'Crudo en ensaladas, cocido, en sopas.',
'Puede causar gases. Introducir gradualmente en dieta.',
'kohlrabi.jpg', 1),

-- 46. Lantana
('Lantana', 'Lantana camara', 'Verbenaceae',
'Planta ornamental con flores coloridas. Precaución: partes tóxicas.',
'Uso tradicional externo para heridas y problemas de piel.',
'Solo uso externo de hojas machacadas.',
'TÓXICA especialmente bayas. No ingerir. Peligrosa para animales.',
'lantana.jpg', 1),

-- 47. Lemon
('Limón', 'Citrus limon', 'Rutaceae',
'Cítrico ácido extremadamente versátil con muchos beneficios.',
'Vitamina C, digestión, desintoxicante, piel, inmunidad.',
'Jugo fresco, ralladura, agua con limón.',
'Ácido puede dañar esmalte dental. Usar popote y enjuagar.',
'lemon.jpg', 1),

-- 48. Lemongrass
('Hierba Limón', 'Cymbopogon citratus', 'Poaceae',
'Hierba aromática con sabor a limón, usada en cocina y medicina.',
'Digestión, fiebre, ansiedad, colesterol, antimicrobiano.',
'Té de tallos, aceite esencial, en cocina.',
'Puede causar mareo en dosis altas. No usar en embarazo.',
'lemongrass.jpg', 1),

-- 49. Malabar Nut (Vasaka)
('Vasaka', 'Justicia adhatoda', 'Acanthaceae',
'Arbusto cuyas hojas son muy valoradas para problemas respiratorios.',
'Tos, asma, bronquitis, expectorante, tuberculosis.',
'Decocción de hojas, jugo, jarabe.',
'No usar en embarazo. Puede causar náuseas en dosis altas.',
'malabar_nut.jpg', 1),

-- 50. Malabar Spinach
('Espinaca Malabar', 'Basella alba', 'Basellaceae',
'Vegetal trepador de hoja verde, nutritivo y refrescante.',
'Laxante suave, refrescante, anemia, problemas urinarios.',
'Hojas cocidas, en sopas y guisos.',
'Moderación si tiene problemas renales por su contenido de oxalatos.',
'malabar_spinach.jpg', 1),

-- 51. Mango
('Mango', 'Mangifera indica', 'Anacardiaceae',
'Fruta tropical deliciosa conocida como "rey de las frutas".',
'Vitamina A y C, digestión, inmunidad, piel, energía.',
'Fruta fresca, jugo, hojas en decocción para diabetes.',
'Exceso puede causar diarrea. Algunas personas alérgicas a la piel.',
'mango.jpg', 1),

-- 52. Marigold
('Caléndula', 'Tagetes erecta / Calendula officinalis', 'Asteraceae',
'Flor dorada con poderosas propiedades curativas para la piel.',
'Heridas, piel, inflamación, antiséptico, ojos.',
'Infusión, aceite, cremas, cataplasma.',
'Puede causar alergia en sensibles a Asteraceae. Hacer prueba.',
'marigold.jpg', 1),

-- 53. Mint
('Menta', 'Mentha spicata', 'Lamiaceae',
'Hierba aromática refrescante con múltiples usos culinarios y medicinales.',
'Digestión, náuseas, dolor de cabeza, resfriados, aliento fresco.',
'Hojas frescas, té, aceite esencial.',
'El aceite de menta puede causar acidez. No dar a bebés.',
'mint.jpg', 1),

-- 54. Nagadali
('Nagadali', 'Ruta graveolens', 'Rutaceae',
'Variedad de ruda usada en medicina tradicional india.',
'Digestión, parásitos, problemas menstruales.',
'Uso externo o infusión muy diluida.',
'TÓXICA. PROHIBIDA en embarazo. Usar con extrema precaución.',
'nagadali.jpg', 1),

-- 55. Neem
('Neem', 'Azadirachta indica', 'Meliaceae',
'Árbol sagrado en India, conocido como "farmacia del pueblo".',
'Antibacteriano, piel, diabetes, parásitos, dental, purificador.',
'Hojas en pasta o decocción, aceite, ramitas para dientes.',
'Muy amargo. No usar en embarazo. Puede reducir fertilidad.',
'neem.jpg', 1),

-- 56. Nelavembu (Andrographis)
('Andrographis', 'Andrographis paniculata', 'Acanthaceae',
'Hierba extremadamente amarga conocida como "rey de los amargos".',
'Inmunidad, infecciones, fiebre, hígado, resfriados.',
'Decocción, polvo, cápsulas.',
'Muy amargo. No en embarazo. Puede reducir presión arterial.',
'nelavembu.jpg', 1),

-- 57. Nerale (Jamun)
('Jamun', 'Syzygium cumini', 'Myrtaceae',
'Fruta morada oscura especialmente beneficiosa para diabéticos.',
'Diabetes, digestión, diarrea, salud bucal.',
'Fruta fresca, semillas en polvo, vinagre.',
'Puede reducir mucho el azúcar. Diabéticos monitorear niveles.',
'nerale.jpg', 1),

-- 58. Nithyapushpa (Vinca)
('Vinca', 'Catharanthus roseus', 'Apocynaceae',
'Planta con flores que florecen todo el año, contiene alcaloides medicinales.',
'Diabetes (uso tradicional), fuente de fármacos anticancerígenos.',
'Solo bajo supervisión médica.',
'TÓXICA. No automedicarse. Los alcaloides son muy potentes.',
'nithyapushpa.jpg', 1),

-- 59. Nooni (Morinda)
('Noni', 'Morinda citrifolia', 'Rubiaceae',
'Fruta tropical con olor fuerte pero propiedades medicinales notables.',
'Inmunidad, inflamación, dolor, presión arterial, antioxidante.',
'Jugo, fruta seca, cápsulas.',
'Alto en potasio. Evitar si tiene problemas renales. Olor desagradable.',
'nooni.jpg', 1),

-- 60. Onion
('Cebolla', 'Allium cepa', 'Amaryllidaceae',
'Bulbo esencial en cocina mundial con importantes beneficios medicinales.',
'Inmunidad, corazón, antiinflamatorio, tos, azúcar en sangre.',
'Cruda, cocida, jugo, jarabe con miel.',
'Puede causar acidez. Algunas personas son sensibles.',
'onion.jpg', 1),

-- 61. Padri (Stereospermum)
('Padri', 'Stereospermum chelonoides', 'Bignoniaceae',
'Árbol utilizado en medicina ayurvédica tradicional.',
'Fiebre, inflamación, tos, problemas respiratorios.',
'Decocción de corteza y raíz.',
'Consultar profesional ayurvédico para uso apropiado.',
'padri.jpg', 1),

-- 62. Palak/Spinach
('Espinaca', 'Spinacia oleracea', 'Amaranthaceae',
'Vegetal de hoja verde oscuro extremadamente nutritivo.',
'Hierro, anemia, huesos, visión, energía.',
'Cruda en ensaladas, cocida, en smoothies.',
'Alto en oxalatos. Moderar si tiene cálculos renales.',
'spinach.jpg', 1),

-- 63. Papaya
('Papaya', 'Carica papaya', 'Caricaceae',
'Fruta tropical con enzimas digestivas y múltiples beneficios.',
'Digestión, parásitos, piel, inmunidad, inflamación.',
'Fruta madura, semillas, hojas en té (para dengue).',
'Semillas en exceso pueden ser tóxicas. Evitar papaya verde en embarazo.',
'papaya.jpg', 1),

-- 64. Parijatha (Jazmín de Noche)
('Parijat', 'Nyctanthes arbor-tristis', 'Oleaceae',
'Árbol con flores fragantes que caen al amanecer, muy valorado en Ayurveda.',
'Artritis, fiebre, tos, parásitos, piel.',
'Decocción de hojas, flores, corteza.',
'Uso moderado. Consultar profesional de salud.',
'parijatha.jpg', 1),

-- 65. Pepper
('Pimienta Negra', 'Piper nigrum', 'Piperaceae',
'Rey de las especias con potente compuesto activo piperina.',
'Digestión, absorción de nutrientes, metabolismo, respiratorio.',
'Granos enteros o molidos en comidas, con miel para tos.',
'Puede irritar estómago sensible. Moderar en úlceras.',
'pepper.jpg', 1),

-- 66. Pomegranate
('Granada', 'Punica granatum', 'Lythraceae',
'Fruta antigua con poderosos antioxidantes y beneficios cardíacos.',
'Corazón, antioxidante, inflamación, memoria, piel.',
'Semillas frescas, jugo, extracto.',
'Puede interactuar con medicamentos. Jugo mancha.',
'pomegranate.jpg', 1),

-- 67. Pumpkin
('Calabaza', 'Cucurbita pepo', 'Cucurbitaceae',
'Vegetal versátil rico en betacaroteno con semillas medicinales.',
'Visión, inmunidad, próstata (semillas), parásitos.',
'Pulpa cocida, semillas tostadas, aceite de semillas.',
'Las semillas son seguras. Diabéticos moderar pulpa dulce.',
'pumpkin.jpg', 1),

-- 68. Raktachandini
('Raktachandini', 'Pterocarpus santalinus', 'Fabaceae',
'Sándalo rojo con propiedades para la piel y la sangre.',
'Piel, purificador de sangre, heridas, inflamación.',
'Pasta de madera, polvo para uso externo.',
'Principalmente uso externo. Especie protegida en algunas regiones.',
'raktachandini.jpg', 1),

-- 69. Rose
('Rosa', 'Rosa damascena', 'Rosaceae',
'Flor aromática con pétalos comestibles y aceite valioso.',
'Piel, digestión, relajante, ojos, corazón.',
'Agua de rosas, pétalos en té, aceite esencial.',
'Aceite esencial diluir siempre. Elegir rosas orgánicas.',
'rose.jpg', 1),

-- 70. Sampige (Magnolia)
('Champak', 'Magnolia champaca', 'Magnoliaceae',
'Árbol con flores extremadamente fragantes usado en perfumería y medicina.',
'Fiebre, dolores de cabeza, tónico cardíaco.',
'Flores en infusión, aceite esencial.',
'Uso moderado. Aceite diluir antes de aplicar.',
'sampige.jpg', 1),

-- 71. Sapota (Chikoo)
('Zapote', 'Manilkara zapota', 'Sapotaceae',
'Fruta tropical dulce con textura de pera y múltiples beneficios.',
'Energía, digestión, huesos, piel, antioxidante.',
'Fruta madura fresca, batidos.',
'Semillas no comestibles. Fruta inmadura es astringente.',
'sapota.jpg', 1),

-- 72. Seethapala (Chirimoya)
('Chirimoya', 'Annona squamosa', 'Annonaceae',
'Fruta tropical cremosa también conocida como "manzana de azúcar".',
'Energía, digestión, piel, cabello, huesos.',
'Pulpa fresca, batidos.',
'Semillas TÓXICAS. No consumir semillas ni piel.',
'seethapala.jpg', 1),

-- 73. Tamarind
('Tamarindo', 'Tamarindus indica', 'Fabaceae',
'Fruta en vaina con pulpa ácida usada en cocina y medicina.',
'Digestión, laxante, fiebre, inflamación, hígado.',
'Pulpa en agua, concentrado, en cocina.',
'Alto en azúcar. Puede interactuar con anticoagulantes.',
'tamarind.jpg', 1),

-- 74. Taro
('Taro', 'Colocasia esculenta', 'Araceae',
'Tubérculo comestible rico en fibra y almidón resistente.',
'Digestión, energía, presión arterial, corazón.',
'Siempre cocido. Hervido, horneado, frito.',
'TÓXICO CRUDO por oxalatos. Siempre cocinar completamente.',
'taro.jpg', 1),

-- 75. Tecoma
('Tecoma', 'Tecoma stans', 'Bignoniaceae',
'Arbusto con flores amarillas usado en medicina tradicional para diabetes.',
'Diabetes, digestión, dolor, infecciones.',
'Decocción de hojas, flores.',
'Puede reducir azúcar significativamente. Monitorear si es diabético.',
'tecoma.jpg', 1),

-- 76. Thumbe (Leucas)
('Thumbe', 'Leucas aspera', 'Lamiaceae',
'Hierba medicinal común usada para resfriados y fiebre en India.',
'Tos, resfriados, fiebre, dolor de cabeza, heridas.',
'Jugo de hojas, decocción, inhalación de vapor.',
'Uso moderado. Puede irritar estómago en exceso.',
'thumbe.jpg', 1),

-- 77. Tomato
('Tomate', 'Solanum lycopersicum', 'Solanaceae',
'Fruto rico en licopeno con potentes propiedades antioxidantes.',
'Corazón, próstata, piel, visión, antioxidante.',
'Fresco, cocido (más licopeno disponible), jugo.',
'Puede causar acidez. Evitar si tiene reflujo severo.',
'tomato.jpg', 1),

-- 78. Tulasi/Tulsi
('Tulsi', 'Ocimum tenuiflorum', 'Lamiaceae',
'Albahaca sagrada de India, adaptógena y con múltiples beneficios.',
'Inmunidad, estrés, respiratorio, antibacteriano, digestión.',
'Hojas frescas masticadas, té, jugo.',
'Puede reducir fertilidad con uso prolongado. No en embarazo.',
'tulsi.jpg', 1),

-- 79. Wood Sorrel
('Acedera', 'Oxalis acetosella', 'Oxalidaceae',
'Pequeña hierba con sabor ácido, usada en ensaladas y medicina.',
'Digestión, fiebre, refrescante, problemas de piel.',
'Hojas frescas en pequeñas cantidades, infusión.',
'Alta en oxalatos. Evitar si tiene cálculos renales o gota.',
'wood_sorrel.jpg', 1),

-- 80. Camphor (Alcanfor)
('Alcanfor', 'Cinnamomum camphora', 'Lauraceae',
'Sustancia aromática cristalina extraída del árbol de alcanfor.',
'Congestión nasal, dolor muscular, tos, repelente de insectos.',
'Uso externo: ungüentos, inhalación. NUNCA ingerir.',
'TÓXICO si se ingiere. Solo uso externo. Alejar de niños.',
'camphor.jpg', 1),

-- 81. Kamakasturi
('Kamakasturi', 'Abelmoschus moschatus', 'Malvaceae',
'Planta cuyas semillas tienen aroma almizclado, usada en perfumería y medicina.',
'Ansiedad, digestión, piel, afrodisíaco.',
'Semillas en polvo, aceite, infusión.',
'Uso moderado. Consultar profesional de salud.',
'kamakasturi.jpg', 1),

-- 82. Kepala
('Kepala', 'Momordica dioica', 'Cucurbitaceae',
'Calabaza espinosa pequeña usada como vegetal y medicina.',
'Diabetes, digestión, fiebre, inmunidad.',
'Vegetal cocido, decocción de raíces.',
'Amargo. Introducir gradualmente en la dieta.',
'kepala.jpg', 1);

-- ============================================================
-- VERIFICAR INSERCIÓN
-- ============================================================
SELECT COUNT(*) as total_plantas FROM plants;

-- ============================================================
-- ACTUALIZAR EL MAPEO EN LA APP (PredictionService.java)
-- Después de ejecutar este SQL, los IDs serán:
-- ID 1-10: Tus plantas originales (si no las eliminaste)
-- ID 11+: Las nuevas plantas del modelo
--
-- IMPORTANTE: Ajusta los IDs según cómo quede tu tabla
-- ============================================================

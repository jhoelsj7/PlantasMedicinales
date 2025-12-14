<?php
/**
 * Reset de Plantas - Cargar plantas del modelo IA
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Database.php';

$db = Database::getInstance()->getConnection();
$message = '';
$messageType = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['action'])) {

    if ($_POST['action'] === 'reset' && isset($_POST['confirm']) && $_POST['confirm'] === 'CONFIRMAR') {
        try {
            // 1. Eliminar predicciones
            $db->exec("DELETE FROM predictions");

            // 2. Eliminar plantas actuales
            $db->exec("DELETE FROM plants");

            // 3. Resetear auto increment
            $db->exec("ALTER TABLE plants AUTO_INCREMENT = 1");

            // 4. Insertar las 93 plantas del modelo IA
            $sql = "INSERT INTO plants (id, common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path, is_active) VALUES
            (1, 'Aloevera', 'Aloe barbadensis miller', 'Asphodelaceae', 'Planta suculenta con hojas carnosas que contienen gel medicinal.', 'Cicatrizante, hidratante, quemaduras, problemas digestivos, cuidado de piel.', 'Gel: Cortar hoja, extraer gel y aplicar. Jugo: Licuar gel con agua.', 'No consumir en exceso. Evitar en embarazo.', 'aloevera.jpg', 1),
            (2, 'Amla', 'Phyllanthus emblica', 'Phyllanthaceae', 'Grosella espinosa india, rica en vitamina C.', 'Fortalece inmunidad, digestion, cabello, antioxidante.', 'Jugo fresco, polvo con agua o miel.', 'Puede interactuar con medicamentos para diabetes.', 'amla.jpg', 1),
            (3, 'Amruta Balli', 'Tinospora cordifolia', 'Menispermaceae', 'Planta trepadora medicinal conocida como Guduchi.', 'Inmunidad, fiebre, diabetes, desintoxicante.', 'Decoccion del tallo, polvo, jugo fresco.', 'Puede reducir azucar en sangre.', 'amruta_balli.jpg', 1),
            (4, 'Amruthaballi', 'Tinospora cordifolia', 'Menispermaceae', 'Variante de Guduchi, planta trepadora medicinal.', 'Inmunidad, antipiretico, artritis.', 'Decoccion, polvo, jugo del tallo.', 'Consultar medico si toma medicamentos.', 'amruthaballi.jpg', 1),
            (5, 'Arali', 'Nerium oleander', 'Apocynaceae', 'Arbusto ornamental. PRECAUCION: Toxica.', 'Solo uso externo limitado para piel. NO CONSUMIR.', 'Solo uso externo bajo supervision.', 'ALTAMENTE TOXICA. No ingerir.', 'arali.jpg', 1),
            (6, 'Ashoka', 'Saraca asoca', 'Fabaceae', 'Arbol sagrado, corteza valorada para salud femenina.', 'Trastornos menstruales, salud uterina.', 'Decoccion de corteza, polvo.', 'No usar en embarazo.', 'ashoka.jpg', 1),
            (7, 'Ashwagandha', 'Withania somnifera', 'Solanaceae', 'Hierba adaptogena, ginseng indio.', 'Reduce estres, aumenta energia, inmunidad.', 'Polvo con leche tibia, capsulas.', 'No usar en embarazo.', 'ashwagandha.jpg', 1),
            (8, 'Astma Weed', 'Euphorbia hirta', 'Euphorbiaceae', 'Hierba para problemas respiratorios.', 'Asma, bronquitis, tos, disenteria.', 'Infusion de la planta, decoccion.', 'No usar en embarazo.', 'astma_weed.jpg', 1),
            (9, 'Avocado', 'Persea americana', 'Lauraceae', 'Fruto nutritivo rico en grasas saludables.', 'Salud cardiovascular, piel, antiinflamatorio.', 'Consumo directo, aceite, mascarillas.', 'Alto en calorias.', 'avacado.jpg', 1),
            (10, 'Badipala', 'Sterculia urens', 'Malvaceae', 'Arbol que produce goma karaya.', 'Laxante, digestion, control de peso.', 'Goma disuelta en agua, polvo.', 'Consumir con abundante agua.', 'badipala.jpg', 1),
            (11, 'Balloon Vine', 'Cardiospermum halicacabum', 'Sapindaceae', 'Planta trepadora con frutos en globo.', 'Artritis, reumatismo, piel, antiinflamatorio.', 'Decoccion de hojas, pasta topica.', 'Usar con precaucion.', 'balloon_vine.jpg', 1),
            (12, 'Bamboo', 'Bambusoideae', 'Poaceae', 'Graminea gigante con brotes medicinales.', 'Rico en silice, huesos, cabello, digestion.', 'Brotes cocidos, te de hojas.', 'Brotes crudos son toxicos. Cocinar siempre.', 'bamboo.jpg', 1),
            (13, 'Basale', 'Basella alba', 'Basellaceae', 'Espinaca de Malabar, vegetal nutritivo.', 'Rico en hierro, laxante suave, refrescante.', 'Hojas cocidas, jugo fresco.', 'Moderacion si tiene problemas renales.', 'basale.jpg', 1),
            (14, 'Beans', 'Phaseolus vulgaris', 'Fabaceae', 'Legumbre nutritiva rica en proteinas.', 'Proteina vegetal, control de azucar, digestion.', 'Cocidos en guisos, sopas.', 'Siempre cocinar bien.', 'beans.jpg', 1),
            (15, 'Betel', 'Piper betle', 'Piperaceae', 'Hojas masticadas tradicionalmente en Asia.', 'Digestivo, antiseptico bucal, estimulante.', 'Hojas frescas masticadas.', 'Usar hojas solas con moderacion.', 'betel.jpg', 1),
            (16, 'Betel Nut', 'Areca catechu', 'Arecaceae', 'Semilla de palma de areca.', 'Estimulante, digestivo (uso tradicional).', 'Masticada con hojas de betel.', 'CANCERIGENA. No recomendado.', 'betel_nut.jpg', 1),
            (17, 'Bhrami', 'Bacopa monnieri', 'Plantaginaceae', 'Hierba para memoria y cognicion.', 'Memoria, ansiedad, neuroprotector.', 'Jugo fresco, polvo con leche.', 'Puede causar malestar digestivo.', 'bhrami.jpg', 1),
            (18, 'Brahmi', 'Bacopa monnieri', 'Plantaginaceae', 'Hierba acuatica para funcion cognitiva.', 'Mejora memoria, reduce ansiedad, concentracion.', 'Jugo, polvo, capsulas, aceite.', 'No usar con sedantes.', 'brahmi.jpg', 1),
            (19, 'Bringaraja', 'Eclipta prostrata', 'Asteraceae', 'Hierba rey del cabello.', 'Cabello, higado, piel, antimicrobiano.', 'Aceite para cabello, jugo, polvo.', 'Consultar medico si tiene problemas hepaticos.', 'bringaraja.jpg', 1),
            (20, 'Caricature', 'Graptophyllum pictum', 'Acanthaceae', 'Planta ornamental con hojas variegadas.', 'Hemorroides, heridas, inflamacion.', 'Decoccion de hojas, cataplasma.', 'Uso moderado.', 'caricature.jpg', 1),
            (21, 'Castor', 'Ricinus communis', 'Euphorbiaceae', 'Planta productora de aceite de ricino.', 'Laxante, piel, cabello, antiinflamatorio.', 'Aceite de ricino oral o topico.', 'Semillas TOXICAS. Solo aceite procesado.', 'castor.jpg', 1),
            (22, 'Catharanthus', 'Catharanthus roseus', 'Apocynaceae', 'Vinca de Madagascar, fuente de alcaloides.', 'Diabetes tradicional. Fuente de farmacos anticancer.', 'Solo bajo supervision medica.', 'TOXICA. No automedicarse.', 'catharanthus.jpg', 1),
            (23, 'Chakte', 'Caesalpinia sappan', 'Fabaceae', 'Arbol con madera de tinte rojo.', 'Purificador de sangre, piel, heridas.', 'Decoccion de madera, polvo.', 'Usar con moderacion.', 'chakte.jpg', 1),
            (24, 'Chilly', 'Capsicum annuum', 'Solanaceae', 'Fruto picante rico en capsaicina.', 'Metabolismo, dolor articular, circulacion.', 'En comidas, cremas topicas.', 'Evitar contacto con ojos.', 'chilly.jpg', 1),
            (25, 'Citron Lime', 'Citrus aurantiifolia', 'Rutaceae', 'Citrico pequeno y acido.', 'Vitamina C, digestion, desintoxicante, piel.', 'Jugo fresco, ralladura, agua tibia.', 'Puede erosionar esmalte dental.', 'citron_lime.jpg', 1),
            (26, 'Coffee', 'Coffea arabica', 'Rubiaceae', 'Semilla tostada, bebida estimulante.', 'Estimulante mental, antioxidante, metabolismo.', 'Bebida por infusion.', 'Puede causar insomnio. Limitar 3-4 tazas.', 'coffee.jpg', 1),
            (27, 'Common Rue', 'Ruta graveolens', 'Rutaceae', 'Hierba aromatica de olor fuerte.', 'Colicos, espasmos, repelente de insectos.', 'Infusion muy diluida, uso externo.', 'TOXICA en dosis altas. PROHIBIDA en embarazo.', 'common_rue.jpg', 1),
            (28, 'Coriander', 'Coriandrum sativum', 'Apiaceae', 'Cilantro, hierba aromatica.', 'Digestion, colesterol, azucar, desintoxicante.', 'Hojas frescas, semillas en infusion.', 'Algunas personas son alergicas.', 'coriender.jpg', 1),
            (29, 'Curry', 'Murraya koenigii', 'Rutaceae', 'Hojas de curry aromaticas.', 'Digestion, cabello, diabetes, antioxidante.', 'Hojas frescas en cocina, polvo.', 'Seguro en cantidades culinarias.', 'curry.jpg', 1),
            (30, 'Curry Leaf', 'Murraya koenigii', 'Rutaceae', 'Hoja de curry esencial en cocina asiatica.', 'Digestion, cabello saludable, diabetes.', 'Hojas en cocina, aceite para cabello.', 'Moderacion en uso medicinal.', 'curry_leaf.jpg', 1),
            (31, 'Doddapatre', 'Plectranthus amboinicus', 'Lamiaceae', 'Oregano cubano, hierba aromatica carnosa.', 'Tos, resfriados, digestion, asma.', 'Hojas masticadas, jugo, infusion.', 'Seguro en uso moderado.', 'doddapatre.jpg', 1),
            (32, 'Doddpathre', 'Plectranthus amboinicus', 'Lamiaceae', 'Variante de oregano cubano.', 'Infecciones respiratorias, digestion.', 'Hojas frescas, infusion.', 'No exceder dosis recomendadas.', 'doddpathre.jpg', 1),
            (33, 'Drumstick', 'Moringa oleifera', 'Moringaceae', 'Moringa, arbol milagroso nutritivo.', 'Superalimento, diabetes, inflamacion, lactancia.', 'Hojas cocidas, polvo, vainas en curry.', 'Raiz puede ser toxica.', 'drumstick.jpg', 1),
            (34, 'Ekka', 'Calotropis gigantea', 'Apocynaceae', 'Algodon de seda, arbusto con latex.', 'Uso externo: heridas, dolor articular.', 'Latex diluido externo, hojas en cataplasma.', 'TOXICO internamente. Solo uso externo.', 'ekka.jpg', 1),
            (35, 'Eucalyptus', 'Eucalyptus globulus', 'Myrtaceae', 'Arbol con hojas aromaticas.', 'Congestion nasal, tos, resfriados, antiseptico.', 'Inhalacion de vapor, aceite diluido.', 'No ingerir aceite puro.', 'eucalyptus.jpg', 1),
            (36, 'Ganigale', 'Solanum nigrum', 'Solanaceae', 'Hierba con bayas negras.', 'Higado, ulceras, fiebre, inflamacion.', 'Hojas cocidas, extracto.', 'Bayas verdes TOXICAS.', 'ganigale.jpg', 1),
            (37, 'Ganike', 'Solanum nigrum', 'Solanaceae', 'Variante de hierba mora.', 'Higado, fiebre, inflamacion.', 'Hojas cocidas.', 'Solo bayas maduras cocidas.', 'ganike.jpg', 1),
            (38, 'Gasagase', 'Papaver somniferum', 'Papaveraceae', 'Semilla de amapola para cocina.', 'Sueno, relajacion, digestion.', 'Semillas en cocina, pasta.', 'Semillas seguras. Planta controlada.', 'gasagase.jpg', 1),
            (39, 'Gauva', 'Psidium guajava', 'Myrtaceae', 'Guayaba, fruta tropical.', 'Digestion, inmunidad, diabetes, diarrea.', 'Fruta fresca, jugo, te de hojas.', 'Semillas pueden causar malestar.', 'gauva.jpg', 1),
            (40, 'Geranium', 'Pelargonium graveolens', 'Geraniaceae', 'Planta aromatica con aceite esencial.', 'Ansiedad, piel, equilibrio hormonal.', 'Aceite esencial diluido, infusion.', 'Aceite no ingerir.', 'geranium.jpg', 1),
            (41, 'Ginger', 'Zingiber officinale', 'Zingiberaceae', 'Jengibre, rizoma aromatico y picante.', 'Nauseas, digestion, antiinflamatorio, resfriados.', 'Fresco rallado, te, polvo.', 'Puede interactuar con anticoagulantes.', 'ginger.jpg', 1),
            (42, 'Globe Amaranth', 'Gomphrena globosa', 'Amaranthaceae', 'Planta ornamental con flores redondas.', 'Tos, bronquitis, problemas respiratorios.', 'Infusion de flores secas.', 'Uso moderado.', 'globe_amaranth.jpg', 1),
            (43, 'Guava', 'Psidium guajava', 'Myrtaceae', 'Guayaba, fruta rica en vitamina C.', 'Digestion, inmunidad, diabetes, piel.', 'Fruta fresca, jugo, te de hojas.', 'Hojas muy astringentes.', 'guava.jpg', 1),
            (44, 'Henna', 'Lawsonia inermis', 'Lythraceae', 'Planta de tinte natural.', 'Tinte natural, cabello, hongos, heridas.', 'Pasta de hojas para cabello/piel.', 'No usar henna negra.', 'henna.jpg', 1),
            (45, 'Hibiscus', 'Hibiscus rosa-sinensis', 'Malvaceae', 'Flor tropical para cabello y te.', 'Cabello, presion arterial, antioxidante.', 'Te de flores, aceite, pasta.', 'Puede reducir presion. No en embarazo.', 'hibiscus.jpg', 1),
            (46, 'Honge', 'Millettia pinnata', 'Fabaceae', 'Pongamia, arbol con aceite medicinal.', 'Piel, artritis, heridas, antiseptico.', 'Aceite externo, pasta de hojas.', 'Solo uso externo. Toxico internamente.', 'honge.jpg', 1),
            (47, 'Insulin Plant', 'Costus igneus', 'Costaceae', 'Planta para control de diabetes.', 'Diabetes, control de azucar.', 'Hojas frescas masticadas (1-2), jugo.', 'No reemplaza medicamentos. Monitorear azucar.', 'insulin.jpg', 1),
            (48, 'Jackfruit', 'Artocarpus heterophyllus', 'Moraceae', 'Yaca, fruta tropical gigante.', 'Energia, digestion, inmunidad, piel.', 'Fruta madura, semillas cocidas.', 'Alergia posible en sensibles al latex.', 'jackfruit.jpg', 1),
            (49, 'Jasmine', 'Jasminum officinale', 'Oleaceae', 'Flor aromatica relajante.', 'Relajante, ansiedad, piel, lactancia.', 'Te de flores, aceite esencial.', 'Aceite no ingerir.', 'jasmine.jpg', 1),
            (50, 'Kambajala', 'Croton tiglium', 'Euphorbiaceae', 'Planta con semillas muy potentes.', 'Purgante fuerte (uso historico).', 'Solo bajo supervision experta.', 'MUY TOXICA. No usar sin supervision.', 'kambajala.jpg', 1),
            (51, 'Kohlrabi', 'Brassica oleracea gongylodes', 'Brassicaceae', 'Colirrabano, vegetal crucifero.', 'Fibra, vitamina C, digestion, antioxidante.', 'Crudo en ensaladas, cocido.', 'Puede causar gases.', 'kohlrabi.jpg', 1),
            (52, 'Lantana', 'Lantana camara', 'Verbenaceae', 'Planta ornamental. Precaucion: toxica.', 'Solo uso externo para heridas.', 'Hojas machacadas externamente.', 'TOXICA. No ingerir. Bayas peligrosas.', 'lantana.jpg', 1),
            (53, 'Lemon', 'Citrus limon', 'Rutaceae', 'Limon, citrico versatil.', 'Vitamina C, digestion, desintoxicante, inmunidad.', 'Jugo fresco, ralladura, agua con limon.', 'Puede danar esmalte dental.', 'lemon.jpg', 1),
            (54, 'Lemon Grass', 'Cymbopogon citratus', 'Poaceae', 'Hierba limon aromatica.', 'Digestion, fiebre, ansiedad, antimicrobiano.', 'Te de tallos, aceite, en cocina.', 'Puede causar mareo en exceso.', 'lemon_grass.jpg', 1),
            (55, 'Malabar Nut', 'Justicia adhatoda', 'Acanthaceae', 'Vasaka, para problemas respiratorios.', 'Tos, asma, bronquitis, expectorante.', 'Decoccion de hojas, jugo, jarabe.', 'No usar en embarazo.', 'malabar_nut.jpg', 1),
            (56, 'Malabar Spinach', 'Basella alba', 'Basellaceae', 'Espinaca Malabar, vegetal trepador.', 'Laxante suave, anemia, refrescante.', 'Hojas cocidas, en sopas.', 'Moderacion por oxalatos.', 'malabar_spinach.jpg', 1),
            (57, 'Mango', 'Mangifera indica', 'Anacardiaceae', 'Mango, rey de las frutas.', 'Vitamina A y C, digestion, inmunidad, piel.', 'Fruta fresca, jugo, hojas en decoccion.', 'Exceso puede causar diarrea.', 'mango.jpg', 1),
            (58, 'Marigold', 'Calendula officinalis', 'Asteraceae', 'Calendula, flor curativa para piel.', 'Heridas, piel, inflamacion, antiseptico.', 'Infusion, aceite, cremas.', 'Alergia posible en sensibles a Asteraceae.', 'marigold.jpg', 1),
            (59, 'Mint', 'Mentha spicata', 'Lamiaceae', 'Menta, hierba aromatica refrescante.', 'Digestion, nauseas, dolor de cabeza, resfriados.', 'Hojas frescas, te, aceite.', 'Aceite puede causar acidez.', 'mint.jpg', 1),
            (60, 'Nagadali', 'Ruta graveolens', 'Rutaceae', 'Variedad de ruda medicinal.', 'Digestion, parasitos, menstruacion.', 'Uso externo o infusion muy diluida.', 'TOXICA. PROHIBIDA en embarazo.', 'nagadali.jpg', 1),
            (61, 'Neem', 'Azadirachta indica', 'Meliaceae', 'Arbol sagrado, farmacia del pueblo.', 'Antibacteriano, piel, diabetes, dental.', 'Hojas en pasta, aceite, ramitas.', 'Muy amargo. No en embarazo.', 'neem.jpg', 1),
            (62, 'Nelavembu', 'Andrographis paniculata', 'Acanthaceae', 'Andrographis, rey de los amargos.', 'Inmunidad, infecciones, fiebre, higado.', 'Decoccion, polvo, capsulas.', 'Muy amargo. Puede reducir presion.', 'nelavembu.jpg', 1),
            (63, 'Nerale', 'Syzygium cumini', 'Myrtaceae', 'Jamun, fruta para diabeticos.', 'Diabetes, digestion, diarrea, salud bucal.', 'Fruta fresca, semillas en polvo.', 'Puede reducir mucho el azucar.', 'nerale.jpg', 1),
            (64, 'Nithyapushpa', 'Catharanthus roseus', 'Apocynaceae', 'Vinca, flores todo el ano.', 'Diabetes tradicional, fuente anticancerigenos.', 'Solo bajo supervision medica.', 'TOXICA. Alcaloides muy potentes.', 'nithyapushpa.jpg', 1),
            (65, 'Nooni', 'Morinda citrifolia', 'Rubiaceae', 'Noni, fruta medicinal de olor fuerte.', 'Inmunidad, inflamacion, dolor, antioxidante.', 'Jugo, fruta seca, capsulas.', 'Alto en potasio. Evitar si problemas renales.', 'nooni.jpg', 1),
            (66, 'Onion', 'Allium cepa', 'Amaryllidaceae', 'Cebolla, bulbo esencial en cocina.', 'Inmunidad, corazon, antiinflamatorio, tos.', 'Cruda, cocida, jugo, jarabe con miel.', 'Puede causar acidez.', 'onion.jpg', 1),
            (67, 'Padri', 'Stereospermum chelonoides', 'Bignoniaceae', 'Arbol ayurvedico tradicional.', 'Fiebre, inflamacion, tos, respiratorio.', 'Decoccion de corteza y raiz.', 'Consultar profesional ayurvedico.', 'padri.jpg', 1),
            (68, 'Palak Spinach', 'Spinacia oleracea', 'Amaranthaceae', 'Espinaca, vegetal muy nutritivo.', 'Hierro, anemia, huesos, vision, energia.', 'Cruda, cocida, en smoothies.', 'Alto en oxalatos.', 'palak_spinach.jpg', 1),
            (69, 'Papaya', 'Carica papaya', 'Caricaceae', 'Papaya, fruta con enzimas digestivas.', 'Digestion, parasitos, piel, inmunidad.', 'Fruta madura, semillas, hojas en te.', 'Semillas en exceso toxicas. Evitar verde en embarazo.', 'papaya.jpg', 1),
            (70, 'Pappaya', 'Carica papaya', 'Caricaceae', 'Variante de papaya.', 'Digestion, piel, inflamacion.', 'Fruta fresca, jugo.', 'Evitar papaya verde en embarazo.', 'pappaya.jpg', 1),
            (71, 'Parijatha', 'Nyctanthes arbor-tristis', 'Oleaceae', 'Jazmin de noche, flores fragantes.', 'Artritis, fiebre, tos, parasitos.', 'Decoccion de hojas, flores, corteza.', 'Uso moderado.', 'parijatha.jpg', 1),
            (72, 'Pepper', 'Piper nigrum', 'Piperaceae', 'Pimienta negra, rey de las especias.', 'Digestion, absorcion de nutrientes, metabolismo.', 'Granos en comidas, con miel para tos.', 'Puede irritar estomago.', 'pepper.jpg', 1),
            (73, 'Pomegranate', 'Punica granatum', 'Lythraceae', 'Granada, fruta antioxidante.', 'Corazon, antioxidante, inflamacion, memoria.', 'Semillas frescas, jugo, extracto.', 'Puede interactuar con medicamentos.', 'pomegranate.jpg', 1),
            (74, 'Pomoegranate', 'Punica granatum', 'Lythraceae', 'Variante de granada.', 'Antioxidante, corazon, piel.', 'Jugo, semillas.', 'Jugo mancha la ropa.', 'pomoegranate.jpg', 1),
            (75, 'Pumpkin', 'Cucurbita pepo', 'Cucurbitaceae', 'Calabaza, vegetal con semillas medicinales.', 'Vision, inmunidad, prostata, parasitos.', 'Pulpa cocida, semillas tostadas.', 'Diabeticos moderar pulpa dulce.', 'pumpkin.jpg', 1),
            (76, 'Raktachandini', 'Pterocarpus santalinus', 'Fabaceae', 'Sandalo rojo para piel.', 'Piel, purificador de sangre, heridas.', 'Pasta de madera, polvo externo.', 'Principalmente uso externo.', 'raktachandini.jpg', 1),
            (77, 'Rose', 'Rosa damascena', 'Rosaceae', 'Rosa, flor aromatica.', 'Piel, digestion, relajante, ojos.', 'Agua de rosas, petalos en te.', 'Diluir aceite esencial.', 'rose.jpg', 1),
            (78, 'Sampige', 'Magnolia champaca', 'Magnoliaceae', 'Champak, flores muy fragantes.', 'Fiebre, dolor de cabeza, tonico cardiaco.', 'Flores en infusion, aceite.', 'Uso moderado.', 'sampige.jpg', 1),
            (79, 'Sapota', 'Manilkara zapota', 'Sapotaceae', 'Zapote, fruta tropical dulce.', 'Energia, digestion, huesos, piel.', 'Fruta madura, batidos.', 'Semillas no comestibles.', 'sapota.jpg', 1),
            (80, 'Seethapala', 'Annona squamosa', 'Annonaceae', 'Chirimoya, manzana de azucar.', 'Energia, digestion, piel, cabello.', 'Pulpa fresca, batidos.', 'Semillas TOXICAS. No consumir.', 'seethapala.jpg', 1),
            (81, 'Spinach', 'Spinacia oleracea', 'Amaranthaceae', 'Espinaca, hoja verde nutritiva.', 'Hierro, energia, vision, huesos.', 'Cruda o cocida.', 'Moderar si tiene calculos renales.', 'spinach1.jpg', 1),
            (82, 'Tamarind', 'Tamarindus indica', 'Fabaceae', 'Tamarindo, fruta acida.', 'Digestion, laxante, fiebre, higado.', 'Pulpa en agua, en cocina.', 'Alto en azucar.', 'tamarind.jpg', 1),
            (83, 'Taro', 'Colocasia esculenta', 'Araceae', 'Taro, tuberculo comestible.', 'Digestion, energia, presion arterial.', 'Siempre cocido. Hervido, horneado.', 'TOXICO CRUDO. Cocinar siempre.', 'taro.jpg', 1),
            (84, 'Tecoma', 'Tecoma stans', 'Bignoniaceae', 'Arbusto amarillo para diabetes.', 'Diabetes, digestion, dolor.', 'Decoccion de hojas, flores.', 'Puede reducir azucar mucho. Monitorear.', 'tecoma.jpg', 1),
            (85, 'Thumbe', 'Leucas aspera', 'Lamiaceae', 'Hierba para resfriados y fiebre.', 'Tos, resfriados, fiebre, heridas.', 'Jugo de hojas, decoccion.', 'Uso moderado.', 'thumbe.jpg', 1),
            (86, 'Tomato', 'Solanum lycopersicum', 'Solanaceae', 'Tomate, fruto rico en licopeno.', 'Corazon, prostata, piel, antioxidante.', 'Fresco, cocido, jugo.', 'Puede causar acidez.', 'tomato.jpg', 1),
            (87, 'Tulasi', 'Ocimum tenuiflorum', 'Lamiaceae', 'Albahaca sagrada de India.', 'Inmunidad, estres, respiratorio, digestion.', 'Hojas masticadas, te, jugo.', 'Puede reducir fertilidad. No en embarazo.', 'tulasi.jpg', 1),
            (88, 'Tulsi', 'Ocimum tenuiflorum', 'Lamiaceae', 'Holy Basil, planta sagrada adaptogena.', 'Inmunidad, antibacteriano, estres.', 'Hojas frescas, te.', 'No en embarazo prolongado.', 'tulsi.jpg', 1),
            (89, 'Wood Sorel', 'Oxalis acetosella', 'Oxalidaceae', 'Acedera, hierba de sabor acido.', 'Digestion, fiebre, refrescante.', 'Hojas frescas en pequenas cantidades.', 'Alta en oxalatos. Evitar si calculos.', 'wood_sorel.jpg', 1),
            (90, 'Ashoka Tree', 'Saraca asoca', 'Fabaceae', 'Variante de arbol Ashoka.', 'Salud femenina, menstruacion.', 'Decoccion de corteza.', 'No en embarazo.', 'ashoka_tree.jpg', 1),
            (91, 'Camphor', 'Cinnamomum camphora', 'Lauraceae', 'Alcanfor, sustancia aromatica.', 'Congestion, dolor muscular, tos.', 'Uso externo: unguentos, inhalacion.', 'TOXICO si se ingiere. Solo externo.', 'camphor.jpg', 1),
            (92, 'Kamakasturi', 'Abelmoschus moschatus', 'Malvaceae', 'Planta con semillas almizcladas.', 'Ansiedad, digestion, piel.', 'Semillas en polvo, aceite.', 'Uso moderado.', 'kamakasturi.jpg', 1),
            (93, 'Kepala', 'Momordica dioica', 'Cucurbitaceae', 'Calabaza espinosa pequena.', 'Diabetes, digestion, fiebre.', 'Vegetal cocido, decoccion.', 'Amargo. Introducir gradualmente.', 'kepala.jpg', 1)";

            $db->exec($sql);

            $message = "Base de datos reseteada exitosamente. Se cargaron 93 plantas del modelo IA.";
            $messageType = 'success';

        } catch (Exception $e) {
            $message = "Error: " . $e->getMessage();
            $messageType = 'error';
        }
    } else {
        $message = "Debes escribir CONFIRMAR para ejecutar el reset";
        $messageType = 'error';
    }
}

// Contar plantas actuales
$countStmt = $db->query("SELECT COUNT(*) as total FROM plants");
$totalPlantas = $countStmt->fetch()['total'];

// Obtener lista de plantas actuales
$plantasStmt = $db->query("SELECT id, common_name, image_path FROM plants ORDER BY id LIMIT 20");
$plantasActuales = $plantasStmt->fetchAll();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset de Plantas</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f6fa; padding: 30px; }
        .container { max-width: 800px; margin: 0 auto; }
        .card { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 25px; }
        h1 { color: #e74c3c; margin-bottom: 20px; }
        h2 { color: #2c3e50; margin-bottom: 15px; }
        .alert { padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        .alert-success { background: #d4edda; color: #155724; }
        .alert-error { background: #f8d7da; color: #721c24; }
        .alert-warning { background: #fff3cd; color: #856404; }
        .btn { padding: 12px 24px; border: none; border-radius: 8px; cursor: pointer; font-size: 1em; transition: all 0.3s; }
        .btn-danger { background: #e74c3c; color: white; }
        .btn-secondary { background: #6c757d; color: white; text-decoration: none; display: inline-block; }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.2); }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; font-weight: 600; }
        .form-group input { width: 100%; padding: 12px; border: 2px solid #e74c3c; border-radius: 8px; font-size: 1em; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #eee; }
        th { background: #f8f9fa; }
        .warning-box { background: #fff3cd; border: 2px solid #ffc107; padding: 20px; border-radius: 10px; margin-bottom: 20px; }
        .stats { display: flex; gap: 20px; margin-bottom: 20px; }
        .stat { background: #f8f9fa; padding: 15px 25px; border-radius: 10px; text-align: center; }
        .stat strong { font-size: 2em; color: #667eea; display: block; }
    </style>
</head>
<body>
    <div class="container">
        <a href="index.php" class="btn btn-secondary" style="margin-bottom: 20px;">
            <i class="fas fa-arrow-left"></i> Volver al Dashboard
        </a>

        <div class="card">
            <h1><i class="fas fa-exclamation-triangle"></i> Reset de Base de Datos</h1>
            <p style="color: #6c757d;">Carga las 93 plantas del modelo de IA (labels.txt)</p>

            <?php if ($message): ?>
            <div class="alert alert-<?php echo $messageType; ?>" style="margin-top: 20px;">
                <?php echo htmlspecialchars($message); ?>
            </div>
            <?php endif; ?>

            <div class="stats">
                <div class="stat">
                    <strong><?php echo $totalPlantas; ?></strong>
                    <span>Plantas actuales</span>
                </div>
                <div class="stat">
                    <strong>93</strong>
                    <span>Plantas del modelo IA</span>
                </div>
            </div>

            <div class="warning-box">
                <h3><i class="fas fa-warning"></i> ADVERTENCIA</h3>
                <p style="margin-top: 10px;">Esta accion:</p>
                <ul style="margin: 10px 0 0 20px;">
                    <li>Eliminara TODAS las predicciones existentes</li>
                    <li>Eliminara TODAS las plantas actuales</li>
                    <li>Cargara las 93 plantas del modelo de IA</li>
                    <li>Los IDs coincidiran con el modelo (labels.txt)</li>
                </ul>
            </div>

            <form method="POST">
                <input type="hidden" name="action" value="reset">
                <div class="form-group">
                    <label>Escribe CONFIRMAR para ejecutar:</label>
                    <input type="text" name="confirm" placeholder="CONFIRMAR" autocomplete="off">
                </div>
                <button type="submit" class="btn btn-danger">
                    <i class="fas fa-database"></i> Resetear y Cargar Plantas
                </button>
            </form>
        </div>

        <div class="card">
            <h2><i class="fas fa-list"></i> Plantas Actuales (primeras 20)</h2>
            <?php if (empty($plantasActuales)): ?>
            <p style="color: #6c757d; padding: 20px;">No hay plantas en la base de datos</p>
            <?php else: ?>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Imagen</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($plantasActuales as $p): ?>
                    <tr>
                        <td><?php echo $p['id']; ?></td>
                        <td><?php echo htmlspecialchars($p['common_name']); ?></td>
                        <td><?php echo htmlspecialchars($p['image_path']); ?></td>
                    </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
            <?php endif; ?>
        </div>
    </div>
</body>
</html>

-- =====================================================
-- ACTUALIZAR BASE DE DATOS CON LAS 5 NUEVAS PLANTAS
-- =====================================================
-- Ejecutar este SQL en tu dashboard (phpMyAdmin) o desde la app

-- Primero, eliminar plantas antiguas que ya no están en el modelo
DELETE FROM plants WHERE common_name NOT IN ('Alstroemeria', 'Muña', 'Opuntia', 'Romero', 'Sábila');

-- Insertar/Actualizar las 5 plantas del nuevo modelo

-- 1. ALSTROEMERIA
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path, is_active)
VALUES (
    'Alstroemeria',
    'Alstroemeria spp.',
    'Alstroemeriaceae',
    'Planta herbácea perenne originaria de Sudamérica, conocida popularmente como "Lirio de los Incas" o "Amancay". Presenta flores vistosas de diversos colores con manchas características.',
    'Aunque principalmente ornamental, algunas especies se usan tradicionalmente para tratar inflamaciones leves y como diurético natural. Sus raíces tuberosas contienen compuestos con propiedades antiinflamatorias.',
    'Infusión: 1-2 cucharaditas de raíz seca por taza de agua hirviendo. Dejar reposar 10 minutos. Tomar 1-2 veces al día.',
    'No consumir sin supervisión médica. Puede causar reacciones alérgicas en personas sensibles. Evitar durante el embarazo y lactancia.',
    'alstroemeria.jpg',
    TRUE
)
ON DUPLICATE KEY UPDATE
    scientific_name = VALUES(scientific_name),
    family = VALUES(family),
    description = VALUES(description),
    medicinal_uses = VALUES(medicinal_uses),
    preparation = VALUES(preparation),
    precautions = VALUES(precautions),
    is_active = TRUE;

-- 2. MUÑA
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path, is_active)
VALUES (
    'Muña',
    'Minthostachys mollis',
    'Lamiaceae',
    'Planta aromática andina originaria de Perú, Bolivia y Ecuador. Crece entre 2,700 y 3,400 msnm. Tiene hojas pequeñas con un aroma mentolado intenso y flores moradas o blancas.',
    'Excelente para tratar problemas digestivos, gases, cólicos y mal de altura (soroche). Tiene propiedades carminativas, antiespasmódicas y expectorantes. Ayuda a aliviar el dolor de estómago y mejora la digestión.',
    'Infusión: 3-5 hojas frescas o 1 cucharada de hojas secas por taza de agua hirviendo. Dejar reposar 5-7 minutos. Tomar después de las comidas o cuando se sienta malestar estomacal.',
    'No consumir en exceso. Evitar durante el embarazo. Puede interactuar con medicamentos anticoagulantes.',
    'muna.jpg',
    TRUE
)
ON DUPLICATE KEY UPDATE
    scientific_name = VALUES(scientific_name),
    family = VALUES(family),
    description = VALUES(description),
    medicinal_uses = VALUES(medicinal_uses),
    preparation = VALUES(preparation),
    precautions = VALUES(precautions),
    is_active = TRUE;

-- 3. OPUNTIA (Tuna/Nopal)
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path, is_active)
VALUES (
    'Opuntia',
    'Opuntia ficus-indica',
    'Cactaceae',
    'Cactus conocido como "Nopal" o "Tuna". Planta suculenta con tallos aplanados (cladodios) en forma de raqueta, espinas y flores amarillas o rojas. Fruto comestible llamado "tuna".',
    'Excelente para controlar diabetes tipo 2 (reduce glucosa en sangre), tratar colesterol alto, obesidad y problemas digestivos. Rico en fibra, antioxidantes y mucílagos. Ayuda a mejorar la función digestiva.',
    'Consumo fresco: Limpiar las pencas (nopales) eliminando espinas, cortar en trozos y consumir en ensaladas o licuados. Jugo: Licuar 1 penca mediana con agua y limón. Tomar en ayunas.',
    'Personas con diabetes deben monitorear sus niveles de glucosa al consumirlo. Puede causar diarrea si se consume en exceso. Lavar muy bien para eliminar espinas microscópicas.',
    'opuntia.jpg',
    TRUE
)
ON DUPLICATE KEY UPDATE
    scientific_name = VALUES(scientific_name),
    family = VALUES(family),
    description = VALUES(description),
    medicinal_uses = VALUES(medicinal_uses),
    preparation = VALUES(preparation),
    precautions = VALUES(precautions),
    is_active = TRUE;

-- 4. ROMERO
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path, is_active)
VALUES (
    'Romero',
    'Rosmarinus officinalis',
    'Lamiaceae',
    'Arbusto aromático perenne originario del Mediterráneo. Hojas aciculares verde oscuro, muy aromáticas. Flores pequeñas de color azul, violeta o blanco. Puede alcanzar hasta 2 metros de altura.',
    'Estimulante circulatorio, mejora la memoria y concentración. Propiedades antioxidantes, antiinflamatorias y digestivas. Alivia dolores musculares y articulares. Fortalece el cabello y estimula el crecimiento capilar.',
    'Infusión: 1 cucharadita de hojas secas por taza de agua hirviendo. Dejar reposar 10 minutos. Tomar 2-3 veces al día. Uso externo: Decocción concentrada para masajes o baños relajantes.',
    'Evitar durante el embarazo y lactancia. Puede elevar la presión arterial en personas hipertensas. No usar aceite esencial puro sobre la piel sin diluir. Personas con epilepsia deben evitarlo.',
    'romero.jpg',
    TRUE
)
ON DUPLICATE KEY UPDATE
    scientific_name = VALUES(scientific_name),
    family = VALUES(family),
    description = VALUES(description),
    medicinal_uses = VALUES(medicinal_uses),
    preparation = VALUES(preparation),
    precautions = VALUES(precautions),
    is_active = TRUE;

-- 5. SÁBILA (Aloe Vera)
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path, is_active)
VALUES (
    'Sábila',
    'Aloe vera / Aloe barbadensis',
    'Asphodelaceae',
    'Planta suculenta con hojas carnosas verde-grisáceas dispuestas en roseta. Las hojas contienen un gel transparente rico en nutrientes. Puede alcanzar 60-90 cm de altura. Flores tubulares amarillas.',
    'Cicatrizante excepcional para quemaduras, heridas y problemas de piel. Hidratante natural, antiinflamatorio y regenerador celular. Útil para gastritis, estreñimiento, diabetes. Mejora la salud digestiva y fortalece el sistema inmunológico.',
    'Uso tópico: Cortar una hoja, extraer el gel y aplicar directamente sobre la piel. Uso interno: 1-2 cucharadas de gel puro en ayunas (sin la corteza amarilla). Se puede mezclar con jugo de naranja o agua.',
    'NO consumir la corteza amarilla (látex) que es laxante fuerte y tóxico. Evitar durante embarazo y lactancia. Puede bajar los niveles de glucosa, personas con diabetes deben consultar médico. No usar en heridas profundas.',
    'sabila.jpg',
    TRUE
)
ON DUPLICATE KEY UPDATE
    scientific_name = VALUES(scientific_name),
    family = VALUES(family),
    description = VALUES(description),
    medicinal_uses = VALUES(medicinal_uses),
    preparation = VALUES(preparation),
    precautions = VALUES(precautions),
    is_active = TRUE;

-- Verificar las plantas activas
SELECT common_name, scientific_name, family FROM plants WHERE is_active = 1;

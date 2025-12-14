-- =====================================================
-- SCRIPT SQL PARA DASHBOARD - PLANTAS MEDICINALES
-- =====================================================
-- Base de datos: plantas_db
-- Versión: 1.0
-- Fecha: Octubre 2025
-- =====================================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS plantas_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE plantas_db;

-- =====================================================
-- TABLA: users
-- Descripción: Almacena usuarios del sistema
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL COMMENT 'Password hasheado con bcrypt',
    email VARCHAR(100),
    full_name VARCHAR(150),
    role ENUM('admin', 'user') DEFAULT 'user',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: plants
-- Descripción: Catálogo de plantas medicinales
-- =====================================================
CREATE TABLE IF NOT EXISTS plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    common_name VARCHAR(100) NOT NULL COMMENT 'Nombre común de la planta',
    scientific_name VARCHAR(150) COMMENT 'Nombre científico (ej: Matricaria chamomilla)',
    family VARCHAR(100) COMMENT 'Familia botánica (ej: Asteraceae)',
    description TEXT COMMENT 'Descripción general de la planta',
    medicinal_uses TEXT COMMENT 'Usos medicinales y propiedades',
    preparation TEXT COMMENT 'Formas de preparación y dosificación',
    precautions TEXT COMMENT 'Precauciones y contraindicaciones',
    imageUrl VARCHAR(255) COMMENT 'URL de la imagen (absoluta)',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT,
    INDEX idx_common_name (common_name),
    INDEX idx_scientific_name (scientific_name),
    INDEX idx_family (family),
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL,
    FULLTEXT KEY ft_search (common_name, scientific_name, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: sessions
-- Descripción: Tokens de sesión para autenticación
-- =====================================================
CREATE TABLE IF NOT EXISTS sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) UNIQUE NOT NULL,
    device_info VARCHAR(255) COMMENT 'Información del dispositivo',
    ip_address VARCHAR(45),
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_token (token),
    INDEX idx_user_id (user_id),
    INDEX idx_expires_at (expires_at),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: identifications (Opcional - Futuro)
-- Descripción: Historial de identificaciones realizadas
-- =====================================================
CREATE TABLE IF NOT EXISTS identifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    plant_id INT,
    confidence DECIMAL(5,4) COMMENT 'Nivel de confianza (0.0000 - 1.0000)',
    image_path VARCHAR(255) COMMENT 'Ruta de la imagen capturada',
    is_correct BOOLEAN NULL COMMENT 'Feedback del usuario sobre la identificación',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_plant_id (plant_id),
    INDEX idx_created_at (created_at),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: model_versions (Opcional - Futuro)
-- Descripción: Versiones del modelo de IA
-- =====================================================
CREATE TABLE IF NOT EXISTS model_versions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    version_name VARCHAR(50) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    file_size INT COMMENT 'Tamaño en bytes',
    accuracy DECIMAL(5,2) COMMENT 'Precisión del modelo (%)',
    input_size INT COMMENT 'Tamaño de entrada (ej: 128, 224)',
    num_classes INT COMMENT 'Número de clases que puede identificar',
    is_active BOOLEAN DEFAULT FALSE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_version_name (version_name),
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- INSERTAR DATOS DE EJEMPLO
-- =====================================================

-- Usuario admin (password: admin123)
-- Generado con: password_hash('admin123', PASSWORD_BCRYPT)
INSERT INTO users (username, password, email, full_name, role) VALUES
('admin', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin@plantas.com', 'Administrador', 'admin'),
('demo', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'demo@plantas.com', 'Usuario Demo', 'user');

-- Plantas medicinales de ejemplo
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, imageUrl) VALUES

('Manzanilla', 'Matricaria chamomilla', 'Asteraceae',
'Planta herbácea anual de la familia de las asteráceas, con flores blancas y amarillas muy aromáticas. Originaria de Europa y Asia occidental, crece en campos y zonas de cultivo.',
'Propiedades antiinflamatorias, sedantes y digestivas. Útil para tratar insomnio, ansiedad, problemas digestivos como gastritis y cólicos, dolores menstruales, irritaciones de la piel y conjuntivitis.',
'Infusión: Agregar 1 cucharada de flores secas por cada taza de agua hirviendo. Dejar reposar tapado durante 5-10 minutos, colar y beber. Se puede tomar 2-3 tazas al día. Para uso externo, aplicar compresas tibias sobre la zona afectada.',
'No consumir en exceso durante el embarazo. Puede causar reacciones alérgicas en personas sensibles a plantas de la familia Asteraceae (como la ambrosía). Evitar su uso junto con anticoagulantes.',
'http://192.168.18.26/plantas_api/images/manzanilla.jpg'),

('Eucalipto', 'Eucalyptus globulus', 'Myrtaceae',
'Árbol perenne de gran tamaño originario de Australia, con hojas aromáticas alargadas de color verde azulado. Su corteza se desprende en tiras y puede alcanzar hasta 60 metros de altura.',
'Expectorante, antiséptico, alivia problemas respiratorios como tos, bronquitis, sinusitis y asma. Propiedades antibacterianas y antifúngicas. Útil para aliviar dolores musculares y articulares.',
'Inhalación: Hervir 5-10 hojas frescas en 1 litro de agua, retirar del fuego y respirar el vapor cubriendo la cabeza con una toalla durante 10 minutos. Infusión: 1-2 hojas por taza, no consumir más de 2 tazas al día. Para uso tópico, diluir aceite esencial en aceite portador.',
'No consumir aceite esencial internamente sin supervisión médica, puede ser tóxico. Evitar en niños menores de 6 años. No aplicar cerca de los ojos. Puede interactuar con medicamentos metabolizados por el hígado.',
'http://192.168.18.26/plantas_api/images/eucalipto.jpg'),

('Aloe Vera', 'Aloe barbadensis', 'Asphodelaceae',
'Planta suculenta perenne con hojas carnosas y espinosas dispuestas en roseta. Las hojas contienen un gel transparente rico en nutrientes. Originaria de la península arábiga, se cultiva ampliamente en climas cálidos.',
'Cicatrizante, hidratante, antiinflamatorio y laxante. Útil para quemaduras, heridas, úlceras cutáneas, psoriasis, acné, estreñimiento y problemas digestivos. Fortalece el sistema inmunológico.',
'Uso tópico: Cortar una hoja, extraer el gel transparente y aplicar directamente sobre la piel afectada 2-3 veces al día. Uso interno: Consumir 1-2 cucharadas de gel puro en ayunas, mezclado con agua o jugo (consultar con médico primero).',
'No consumir la corteza externa que contiene látex amarillo (aloína), es un laxante muy fuerte y puede causar cólicos. Evitar uso interno durante embarazo, lactancia y en niños menores de 12 años. No consumir si hay enfermedades intestinales.',
'http://192.168.18.26/plantas_api/images/aloe_vera.jpg'),

('Menta', 'Mentha piperita', 'Lamiaceae',
'Planta herbácea perenne aromática con tallos cuadrangulares y hojas dentadas de color verde brillante. Sus flores son pequeñas y de color púrpura. Crece rápidamente y se propaga por rizomas.',
'Propiedades digestivas, antiespasmódicas y analgésicas. Alivia náuseas, indigestión, síndrome de intestino irritable, dolores de cabeza y migrañas. Útil para problemas respiratorios y mal aliento.',
'Infusión: 1 cucharada de hojas frescas o 1 cucharadita de hojas secas por taza de agua hirviendo. Dejar reposar 5-7 minutos y colar. Tomar 2-3 tazas al día después de las comidas. Para inhalación, usar el vapor de la infusión.',
'No usar aceite esencial puro sobre la piel de bebés o niños pequeños. Evitar en personas con reflujo gastroesofágico severo ya que puede empeorar los síntomas. Puede reducir la absorción de hierro.',
'http://192.168.18.26/plantas_api/images/menta.jpg'),

('Lavanda', 'Lavandula angustifolia', 'Lamiaceae',
'Planta aromática perenne de la familia de las lamiáceas, con flores púrpuras agrupadas en espigas. Nativa de la región mediterránea, es muy apreciada por su fragancia característica.',
'Propiedades relajantes, ansiolíticas y antiespasmódicas. Útil para tratar ansiedad, insomnio, dolores de cabeza por tensión, pequeñas quemaduras, picaduras de insectos y problemas de la piel.',
'Infusión: 1-2 cucharaditas de flores secas por taza de agua hirviendo. Dejar reposar 5-10 minutos. Tomar 1-2 tazas al día, preferentemente antes de dormir. Aceite esencial: Aplicar 2-3 gotas diluidas en aceite portador sobre sienes o muñecas.',
'El aceite esencial no debe consumirse internamente. Puede causar somnolencia, evitar antes de conducir. No usar en embarazo sin supervisión médica. Puede interactuar con sedantes.',
'http://192.168.18.26/plantas_api/images/lavanda.jpg'),

('Jengibre', 'Zingiber officinale', 'Zingiberaceae',
'Planta herbácea perenne con rizomas aromáticos y sabor picante característico. Originaria del sudeste asiático, el rizoma (raíz) es la parte utilizada con fines medicinales y culinarios.',
'Propiedades antiinflamatorias, antieméticas y digestivas. Alivia náuseas y vómitos (incluidos los del embarazo y quimioterapia), mejora la digestión, reduce dolores musculares y articulares, combate el mareo.',
'Infusión: Rallar 1-2 cm de rizoma fresco por taza de agua hirviendo, dejar reposar 10 minutos. Tomar 2-3 tazas al día. Fresco: Masticar pequeños trozos. En polvo: 1-2 gramos al día divididos en varias tomas.',
'Puede interactuar con anticoagulantes. No consumir en exceso si hay cálculos biliares. Limitar su consumo durante el embarazo (máximo 1 gramo al día). Puede causar acidez estomacal en personas sensibles.',
'http://192.168.18.26/plantas_api/images/jengibre.jpg'),

('Romero', 'Rosmarinus officinalis', 'Lamiaceae',
'Arbusto aromático perenne de hojas aciculares verde oscuro y flores azuladas. Originario de la región mediterránea, es muy resistente a la sequía y desprende un aroma intenso y característico.',
'Estimulante circulatorio y cerebral, mejora la memoria y concentración. Propiedades antioxidantes, antiinflamatorias y antimicrobianas. Útil para dolores musculares, problemas digestivos y caída del cabello.',
'Infusión: 1 cucharadita de hojas secas por taza de agua hirviendo, reposar 10 minutos. Tomar 2-3 tazas al día. Uso tópico: Infusión concentrada para masajes o como enjuague capilar. Aceite esencial diluido para aromaterapia.',
'Evitar en embarazo y lactancia. No consumir aceite esencial internamente. Puede elevar la presión arterial en personas sensibles. No usar en personas con epilepsia sin supervisión médica.',
'http://192.168.18.26/plantas_api/images/romero.jpg'),

('Valeriana', 'Valeriana officinalis', 'Valerianaceae',
'Planta herbácea perenne con flores rosadas o blancas. La raíz tiene un olor fuerte característico y es la parte utilizada medicinalmente. Crece en zonas húmedas de Europa y Asia.',
'Sedante natural, ansiolítico e inductor del sueño. Útil para tratar insomnio, ansiedad, estrés, nerviosismo y dolores menstruales. No genera dependencia como los medicamentos sintéticos.',
'Infusión: 1 cucharadita de raíz seca triturada por taza de agua hirviendo, dejar reposar 10-15 minutos. Tomar 1 taza 1-2 horas antes de dormir. Tintura: 20-30 gotas en agua, 2-3 veces al día.',
'Puede causar somnolencia, no conducir después de tomarla. No combinar con alcohol o sedantes. Evitar durante el embarazo y lactancia. No usar por más de 4-6 semanas consecutivas sin supervisión médica.',
'http://192.168.18.26/plantas_api/images/valeriana.jpg'),

('Tila', 'Tilia platyphyllos', 'Malvaceae',
'Árbol de gran tamaño con hojas en forma de corazón y flores amarillentas muy aromáticas. Las flores son la parte utilizada medicinalmente. Común en Europa, florece en verano.',
'Sedante suave, ansiolítico, antiespasmódico y diaforético (induce sudoración). Útil para tratar nerviosismo, insomnio leve, hipertensión arterial leve, resfriados y gripes.',
'Infusión: 1-2 cucharaditas de flores secas por taza de agua hirviendo, reposar 10 minutos tapado. Tomar 2-3 tazas al día, la última antes de dormir. Puede endulzarse con miel.',
'Generalmente segura, pero puede causar somnolencia. No combinar con sedantes. El uso prolongado en dosis altas puede afectar el corazón en personas predispuestas. Segura en embarazo en dosis moderadas.',
'http://192.168.18.26/plantas_api/images/tila.jpg'),

('Caléndula', 'Calendula officinalis', 'Asteraceae',
'Planta herbácea anual con flores de color naranja brillante o amarillo. Originaria de la región mediterránea, se cultiva ampliamente con fines ornamentales y medicinales.',
'Cicatrizante, antiinflamatorio, antimicrobiano y antifúngico. Útil para heridas, quemaduras, úlceras cutáneas, eczemas, picaduras de insectos, infecciones bucales y problemas menstruales.',
'Uso tópico: Infusión concentrada (3 cucharadas de flores por taza) para lavar heridas. Ungüento o crema sobre la piel afectada 2-3 veces al día. Enjuague bucal: Infusión tibia para aftas y gingivitis.',
'No usar sobre heridas profundas sin supervisión médica. Puede causar alergias en personas sensibles a la familia Asteraceae. Evitar uso interno durante el embarazo.',
'http://192.168.18.26/plantas_api/images/calendula.jpg'),

('Tomillo', 'Thymus vulgaris', 'Lamiaceae',
'Planta aromática perenne de pequeño tamaño con hojas diminutas y flores rosadas o blancas. Originaria de la región mediterránea, muy utilizada en cocina y medicina tradicional.',
'Expectorante, antiséptico, antibacteriano y antifúngico. Útil para infecciones respiratorias, tos, bronquitis, dolor de garganta, indigestión y como desinfectante bucal.',
'Infusión: 1 cucharadita de hojas secas por taza de agua hirviendo, reposar 10 minutos. Tomar 2-3 tazas al día. Gárgaras: Usar la infusión tibia para dolor de garganta. Aceite esencial diluido para uso tópico.',
'No usar aceite esencial puro sobre la piel. Evitar dosis altas durante el embarazo. Puede interactuar con anticoagulantes. No usar en personas con alergias a la familia Lamiaceae.',
'http://192.168.18.26/plantas_api/images/tomillo.jpg'),

('Salvia', 'Salvia officinalis', 'Lamiaceae',
'Planta aromática perenne con hojas aterciopeladas de color verde grisáceo y flores violetas. Originaria de la región mediterránea, muy valorada desde la antigüedad.',
'Antiséptico, antiinflamatorio, antioxidante y astringente. Útil para infecciones de garganta, aftas bucales, sudoración excesiva, problemas digestivos y menopausia.',
'Infusión: 1 cucharadita de hojas secas por taza de agua hirviendo, reposar 10 minutos. Tomar 2-3 tazas al día. Gárgaras: Infusión concentrada para infecciones bucales. No usar de forma continuada por más de 2-3 semanas.',
'No usar durante embarazo y lactancia (puede reducir la producción de leche). Evitar en personas con epilepsia. No usar aceite esencial internamente. Puede interactuar con medicamentos para diabetes.',
'http://192.168.18.26/plantas_api/images/salvia.jpg'),

('Orégano', 'Origanum vulgare', 'Lamiaceae',
'Planta aromática perenne con pequeñas flores rosadas o púrpuras. Común en la región mediterránea, muy utilizada en cocina y medicina tradicional.',
'Antibacteriano, antifúngico, antioxidante y expectorante. Útil para infecciones respiratorias, problemas digestivos, parásitos intestinales y como antioxidante natural.',
'Infusión: 1 cucharadita de hojas secas por taza de agua hirviendo, reposar 10 minutos. Tomar 2-3 tazas al día. Aceite esencial: 1-2 gotas diluidas en aceite portador para uso tópico.',
'El aceite esencial no debe consumirse sin supervisión médica. Puede causar reacciones alérgicas en personas sensibles. Evitar dosis altas durante el embarazo.',
'http://192.168.18.26/plantas_api/images/oregano.jpg'),

('Diente de león', 'Taraxacum officinale', 'Asteraceae',
'Planta herbácea perenne con flores amarillas y hojas dentadas. Muy común en prados y jardines. Toda la planta es comestible y medicinal.',
'Diurético, depurativo hepático, estimulante digestivo y laxante suave. Útil para retención de líquidos, problemas hepáticos, estreñimiento y como tónico general.',
'Infusión de hojas: 1-2 cucharaditas por taza de agua hirviendo, 3 veces al día. Decocción de raíz: 1 cucharada de raíz seca en 250ml de agua, hervir 10 minutos. Las hojas jóvenes se pueden consumir en ensaladas.',
'Puede causar reacciones alérgicas en personas sensibles a Asteraceae. No usar si hay obstrucción de conductos biliares. Puede interactuar con diuréticos y medicamentos para diabetes.',
'http://192.168.18.26/plantas_api/images/diente_de_leon.jpg'),

('Equinácea', 'Echinacea purpurea', 'Asteraceae',
'Planta herbácea perenne con flores de color púrpura rosado y centro naranja prominente. Originaria de América del Norte, muy utilizada para fortalecer el sistema inmunológico.',
'Inmunoestimulante, antiinflamatorio y antiviral. Útil para prevenir y tratar resfriados, gripes, infecciones respiratorias y estimular las defensas del organismo.',
'Infusión: 1-2 cucharaditas de raíz o hierba seca por taza, 3 veces al día. Tintura: 20-30 gotas, 3 veces al día. Tomar al inicio de síntomas de resfriado. No usar de forma continua por más de 8 semanas.',
'No usar en personas con enfermedades autoinmunes. Evitar en personas alérgicas a la familia Asteraceae. No usar en tratamientos de inmunodepresión. Puede interactuar con medicamentos metabolizados por el hígado.',
'http://192.168.18.26/plantas_api/images/equinacea.jpg'),

('Boldo', 'Peumus boldus', 'Monimiaceae',
'Árbol perenne originario de Chile con hojas aromáticas coriáceas. Las hojas son la parte medicinal y tienen un sabor amargo característico.',
'Colagogo (estimula la vesícula biliar), hepatoprotector, digestivo y diurético suave. Útil para problemas digestivos, hepáticos, vesiculares y flatulencias.',
'Infusión: 1 cucharadita de hojas secas por taza de agua hirviendo, reposar 10 minutos. Tomar 1 taza después de comidas principales, máximo 2-3 tazas al día. No usar por más de 4 semanas consecutivas.',
'No usar en embarazo, lactancia y niños. Contraindicado en obstrucción de vías biliares y enfermedades hepáticas graves. No usar en dosis altas o por tiempo prolongado. Puede interactuar con anticoagulantes.',
'http://192.168.18.26/plantas_api/images/boldo.jpg'),

('Pasiflora', 'Passiflora incarnata', 'Passifloraceae',
'Planta trepadora con flores exóticas de color púrpura y blanco. Originaria de América, produce frutos comestibles (maracuyá). Las hojas y flores tienen propiedades medicinales.',
'Sedante, ansiolítico, antiespasmódico e hipnótico suave. Útil para ansiedad, insomnio, nerviosismo, hipertensión arterial de origen nervioso y dolores de tipo espasmódico.',
'Infusión: 1 cucharadita de hojas y flores secas por taza de agua hirviendo, reposar 10 minutos. Tomar 1-2 tazas al día, preferentemente antes de dormir. Tintura: 15-30 gotas, 2-3 veces al día.',
'Puede causar somnolencia, no conducir después de tomarla. No combinar con alcohol, sedantes o antidepresivos. Evitar durante embarazo y lactancia. No usar antes de cirugías.',
'http://192.168.18.26/plantas_api/images/pasiflora.jpg'),

('Árnica', 'Arnica montana', 'Asteraceae',
'Planta herbácea perenne con flores amarillas similares a margaritas. Crece en zonas montañosas de Europa. MUY IMPORTANTE: Solo uso externo, es tóxica por vía oral.',
'Antiinflamatorio, analgésico, cicatrizante (solo uso tópico). Útil para golpes, moretones, esguinces, dolores musculares, artritis y picaduras de insectos.',
'USO EXTERNO ÚNICAMENTE: Tintura diluida (1:10 en agua) para compresas sobre golpes y moretones. Gel o pomada comercial aplicar 2-3 veces al día sobre la zona afectada. NO aplicar sobre heridas abiertas.',
'NUNCA consumir internamente, es tóxica. No usar sobre heridas abiertas o mucosas. Puede causar dermatitis de contacto en personas sensibles. No usar en embarazo ni lactancia.',
'http://192.168.18.26/plantas_api/images/arnica.jpg'),

('Hierba de San Juan', 'Hypericum perforatum', 'Hypericaceae',
'Planta herbácea perenne con flores amarillas estrelladas. Las hojas tienen pequeños puntos translúcidos que parecen perforaciones. Común en Europa y naturalizada en América.',
'Antidepresivo natural, ansiolítico y cicatrizante. Útil para depresión leve a moderada, ansiedad, trastornos del sueño y heridas (uso tópico).',
'Infusión: 1-2 cucharaditas de flores secas por taza, 2-3 veces al día. Los efectos antidepresivos se notan después de 2-4 semanas de uso continuo. Uso tópico: Aceite de hipérico sobre heridas y quemaduras leves.',
'Aumenta la fotosensibilidad (evitar exposición solar). Interactúa con MUCHOS medicamentos (anticonceptivos, antidepresivos, anticoagulantes, etc.). Consultar con médico antes de usar. No usar en embarazo.',
'http://192.168.18.26/plantas_api/images/hierba_san_juan.jpg'),

('Cúrcuma', 'Curcuma longa', 'Zingiberaceae',
'Planta herbácea perenne con rizomas de color naranja intenso. Originaria del sur de Asia, muy utilizada como especia y medicina tradicional en India.',
'Antiinflamatorio potente, antioxidante, hepatoprotector y anticarcinogénico. Útil para artritis, problemas digestivos, protección hepática y prevención de enfermedades crónicas.',
'Polvo: 1-3 gramos al día en comidas. Infusión: 1 cucharadita de rizoma rallado por taza, hervir 10 minutos. Combinar con pimienta negra para mejorar absorción. Disponible en cápsulas estandarizadas.',
'Puede aumentar el riesgo de cálculos biliares en personas predispuestas. Evitar dosis altas si hay problemas de coagulación. Puede interactuar con anticoagulantes y medicamentos para diabetes.',
'http://192.168.18.26/plantas_api/images/curcuma.jpg');

-- Información del modelo actual
INSERT INTO model_versions (version_name, file_path, file_size, accuracy, input_size, num_classes, is_active, description) VALUES
('modelo_plantas_96acc', 'assets/modelo_plantas_96acc.tflite', 2507472, 96.00, 128, 20, TRUE,
'Modelo CNN entrenado con TensorFlow 2.17.0. Input: [1, 128, 128, 3]. Identifica 20 especies de plantas medicinales con 96% de precisión.');

-- =====================================================
-- CONSULTAS ÚTILES
-- =====================================================

-- Ver todas las plantas
-- SELECT * FROM plants ORDER BY common_name;

-- Buscar plantas por nombre
-- SELECT * FROM plants WHERE common_name LIKE '%manzanilla%' OR scientific_name LIKE '%manzanilla%';

-- Ver sesiones activas
-- SELECT s.*, u.username FROM sessions s JOIN users u ON s.user_id = u.id WHERE s.expires_at > NOW();

-- Limpiar sesiones expiradas
-- DELETE FROM sessions WHERE expires_at < NOW();

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================

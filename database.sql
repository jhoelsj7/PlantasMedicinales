-- ==========================================
-- Base de Datos: Plantas Medicinales
-- ==========================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS plantas_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE plantas_db;

-- ==========================================
-- Tabla: users
-- ==========================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- Tabla: plants
-- ==========================================
CREATE TABLE IF NOT EXISTS plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    common_name VARCHAR(100) NOT NULL,
    scientific_name VARCHAR(150),
    family VARCHAR(100),
    description TEXT,
    medicinal_uses TEXT,
    preparation TEXT,
    precautions TEXT,
    image_path VARCHAR(255),
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_common_name (common_name),
    INDEX idx_family (family),
    INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- Tabla: sessions
-- ==========================================
CREATE TABLE IF NOT EXISTS sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_token (token),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- Tabla: predictions
-- ==========================================
CREATE TABLE IF NOT EXISTS predictions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    plant_id INT,
    image_path VARCHAR(255),
    confidence DECIMAL(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_plant_id (plant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- Tabla: model_versions
-- ==========================================
CREATE TABLE IF NOT EXISTS model_versions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    version VARCHAR(50) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    accuracy DECIMAL(5,2),
    is_active TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- DATOS DE PRUEBA
-- ==========================================

-- Insertar usuarios de prueba
-- admin password: "admin123"
-- testuser password: "test123"
INSERT INTO users (username, email, password, full_name) VALUES
('admin', 'admin@plantas.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Administrador'),
('testuser', 'test@plantas.com', '$2y$10$aAZ2tjty10RjqDb9YaOoaeYi8FGfz7ec9vvR0q/mhbL8oviRsSGRW', 'Usuario de Prueba');

-- Insertar plantas de ejemplo
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path) VALUES
('Manzanilla', 'Matricaria chamomilla', 'Asteraceae',
 'Planta herbácea anual de la familia de las asteráceas, muy conocida por sus propiedades medicinales y su característico olor.',
 'Propiedades antiinflamatorias, digestivas y calmantes. Útil para tratar insomnio, ansiedad, problemas digestivos y afecciones de la piel.',
 'Infusión: 1 cucharada de flores secas por taza de agua caliente. Dejar reposar 5-10 minutos. Tomar 2-3 tazas al día.',
 'No consumir en exceso durante el embarazo. Puede causar reacciones alérgicas en personas sensibles a la familia Asteraceae.',
 'manzanilla.jpg'),

('Aloe Vera', 'Aloe barbadensis', 'Asphodelaceae',
 'Planta suculenta con hojas carnosas que contienen un gel transparente rico en nutrientes y compuestos bioactivos.',
 'Cicatrizante, antiinflamatorio, hidratante. Útil para quemaduras, heridas, problemas digestivos y cuidado de la piel.',
 'Uso tópico: Aplicar el gel directamente sobre la piel. Uso interno: Jugo de aloe (1-2 cucharadas al día, con precaución).',
 'El uso interno debe ser moderado. No usar en embarazo o lactancia. Puede causar diarrea en dosis altas.',
 'aloe_vera.jpg'),

('Hierba Buena', 'Mentha spicata', 'Lamiaceae',
 'Planta aromática perenne de la familia de las mentas, conocida por su aroma refrescante y propiedades digestivas.',
 'Digestivo, antiespasmódico, expectorante. Alivia dolores de estómago, náuseas, gases y problemas respiratorios.',
 'Infusión: 1 cucharadita de hojas frescas o secas por taza de agua caliente. Tomar después de las comidas.',
 'Evitar en personas con reflujo gastroesofágico severo. No dar a bebés menores de 2 años.',
 'hierba_buena.jpg'),

('Eucalipto', 'Eucalyptus globulus', 'Myrtaceae',
 'Árbol de gran tamaño originario de Australia, conocido por sus propiedades expectorantes y antisépticas.',
 'Expectorante, antiséptico, antiinflamatorio. Útil para gripes, resfriados, bronquitis y problemas respiratorios.',
 'Inhalaciones: Hervir hojas y respirar el vapor. Infusión: 1 cucharadita de hojas por taza, 2-3 veces al día.',
 'No ingerir el aceite esencial puro. No usar en niños menores de 6 años. Evitar en embarazo.',
 'eucalipto.jpg'),

('Romero', 'Rosmarinus officinalis', 'Lamiaceae',
 'Arbusto aromático perenne muy utilizado tanto en cocina como en medicina tradicional.',
 'Estimulante, antioxidante, mejora la circulación y la memoria. Útil para dolores musculares y digestión.',
 'Infusión: 1 cucharadita de hojas por taza. Uso tópico: Aceite de romero para masajes.',
 'No usar en exceso durante el embarazo. Puede elevar la presión arterial en personas sensibles.',
 'romero.jpg'),

('Lavanda', 'Lavandula angustifolia', 'Lamiaceae',
 'Planta aromática con flores violetas, ampliamente utilizada por sus propiedades relajantes.',
 'Relajante, ansiolítico, antiséptico. Ayuda con el insomnio, ansiedad, dolores de cabeza y cicatrización.',
 'Infusión: 1 cucharadita de flores por taza. Aromaterapia: Aceite esencial en difusor o almohada.',
 'El aceite esencial no debe ingerirse. Usar con precaución en embarazo.',
 'lavanda.jpg'),

('Jengibre', 'Zingiber officinale', 'Zingiberaceae',
 'Raíz aromática con propiedades medicinales y culinarias, conocida por su sabor picante.',
 'Antiinflamatorio, digestivo, antináuseas. Útil para mareos, náuseas, problemas digestivos y dolores.',
 'Infusión: Rodajas de raíz fresca en agua caliente. Tomar 2-3 tazas al día. También en polvo o fresco.',
 'Puede interactuar con anticoagulantes. Evitar en dosis altas durante el embarazo.',
 'jengibre.jpg'),

('Valeriana', 'Valeriana officinalis', 'Caprifoliaceae',
 'Planta herbácea conocida por sus poderosas propiedades sedantes y relajantes.',
 'Sedante natural, ansiolítico, induce el sueño. Útil para insomnio, ansiedad y estrés.',
 'Infusión: 1-2 cucharaditas de raíz seca por taza. Tomar 1 hora antes de dormir.',
 'No combinar con alcohol o sedantes. Puede causar somnolencia. No usar por más de 4-6 semanas continuas.',
 'valeriana.jpg'),

('Tila', 'Tilia platyphyllos', 'Malvaceae',
 'Árbol cuyas flores son ampliamente utilizadas por sus propiedades calmantes y relajantes.',
 'Sedante suave, ansiolítico, antiespasmódico. Ayuda con el insomnio, ansiedad y nerviosismo.',
 'Infusión: 1 cucharada de flores por taza de agua caliente. Tomar 2-3 veces al día.',
 'Generalmente segura. Evitar uso prolongado sin supervisión médica.',
 'tila.jpg'),

('Caléndula', 'Calendula officinalis', 'Asteraceae',
 'Planta con flores naranjas brillantes, conocida por sus propiedades cicatrizantes y antiinflamatorias.',
 'Cicatrizante, antiinflamatorio, antiséptico. Útil para heridas, quemaduras, inflamaciones de piel.',
 'Uso tópico: Crema o pomada en la zona afectada. Infusión para lavados: 1 cucharada de flores por taza.',
 'No usar en heridas profundas sin supervisión. Puede causar alergias en personas sensibles a Asteraceae.',
 'calendula.jpg');

-- Insertar versión del modelo IA
INSERT INTO model_versions (version, file_path, accuracy, is_active) VALUES
('1.0.0', 'models/modelo_plantas_96acc.tflite', 96.00, 1);

-- ==========================================
-- FIN DEL SCRIPT
-- ==========================================

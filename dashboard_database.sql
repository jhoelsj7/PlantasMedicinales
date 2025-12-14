-- =====================================================
-- SCRIPT SQL COMPLETO PARA DASHBOARD
-- Base de datos: plantas_db
-- =====================================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS plantas_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE plantas_db;

-- =====================================================
-- TABLA: users (Usuarios de la app móvil)
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL COMMENT 'Hash bcrypt',
    email VARCHAR(100),
    full_name VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: sessions (Tokens de autenticación)
-- =====================================================
CREATE TABLE IF NOT EXISTS sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) UNIQUE NOT NULL,
    device_info VARCHAR(255),
    ip_address VARCHAR(45),
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_token (token),
    INDEX idx_user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: plants (Catálogo de plantas medicinales)
-- =====================================================
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
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_common_name (common_name),
    INDEX idx_scientific_name (scientific_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: predictions (Historial de identificaciones)
-- =====================================================
CREATE TABLE IF NOT EXISTS predictions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    plant_id INT,
    predicted_name VARCHAR(100),
    confidence DECIMAL(5,4),
    image_path VARCHAR(255),
    is_correct BOOLEAN NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_plant_id (plant_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: model_versions (Versiones del modelo IA)
-- =====================================================
CREATE TABLE IF NOT EXISTS model_versions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    version VARCHAR(50) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    accuracy DECIMAL(5,2),
    file_size INT,
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_version (version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- INSERTAR USUARIOS DE PRUEBA
-- =====================================================
-- Password para ambos: test123
-- Hash generado con: password_hash('test123', PASSWORD_BCRYPT)
INSERT INTO users (username, password, email, full_name) VALUES
('testuser', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'test@plantas.com', 'Usuario Prueba'),
('admin', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin@plantas.com', 'Administrador');

-- =====================================================
-- INSERTAR PLANTAS MEDICINALES
-- =====================================================
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path) VALUES

('Manzanilla', 'Matricaria chamomilla', 'Asteraceae',
'Planta herbácea anual de la familia de las asteráceas, con flores blancas y amarillas muy aromáticas. Originaria de Europa y Asia occidental.',
'Propiedades antiinflamatorias, sedantes y digestivas. Útil para tratar insomnio, ansiedad, problemas digestivos como gastritis y cólicos, dolores menstruales.',
'Infusión: 1 cucharada de flores secas por taza de agua hirviendo. Dejar reposar tapado 5-10 minutos y colar. Tomar 2-3 tazas al día.',
'No consumir en exceso durante el embarazo. Puede causar alergias en personas sensibles a la familia Asteraceae.',
'manzanilla.jpg'),

('Eucalipto', 'Eucalyptus globulus', 'Myrtaceae',
'Árbol perenne de gran tamaño originario de Australia, con hojas aromáticas ricas en aceites esenciales.',
'Expectorante, antiséptico, alivia problemas respiratorios como tos, bronquitis y sinusitis. Propiedades antibacterianas.',
'Inhalación: Hervir 5-10 hojas en agua y respirar el vapor por 10 minutos. Infusión: 1-2 hojas por taza, máximo 2 tazas al día.',
'No consumir aceite esencial internamente sin supervisión médica. Evitar en niños menores de 6 años.',
'eucalipto.jpg'),

('Aloe Vera', 'Aloe barbadensis', 'Asphodelaceae',
'Planta suculenta con hojas carnosas que contienen un gel transparente rico en nutrientes.',
'Cicatrizante, hidratante, antiinflamatorio. Útil para quemaduras, heridas, problemas digestivos y cuidado de la piel.',
'Uso tópico: Aplicar gel fresco directamente sobre la piel. Uso interno: 1-2 cucharadas de gel puro en ayunas (consultar médico).',
'No consumir la corteza (látex amarillo) ya que es laxante fuerte. Evitar uso interno durante embarazo y lactancia.',
'aloe_vera.jpg'),

('Hierba Buena', 'Mentha spicata', 'Lamiaceae',
'Planta herbácea aromática perenne con tallos cuadrangulares y hojas dentadas de color verde brillante.',
'Propiedades digestivas, antiespasmódicas y analgésicas. Alivia náuseas, indigestión, síndrome de intestino irritable.',
'Infusión: 1 cucharada de hojas frescas o 1 cucharadita de hojas secas por taza de agua hirviendo. Tomar 2-3 tazas al día después de comidas.',
'No usar aceite esencial puro sobre la piel de bebés. Evitar en personas con reflujo gastroesofágico severo.',
'hierba_buena.jpg'),

('Romero', 'Rosmarinus officinalis', 'Lamiaceae',
'Arbusto aromático perenne de hojas aciculares verde oscuro y flores azuladas. Originario de la región mediterránea.',
'Estimulante circulatorio y cerebral, mejora memoria y concentración. Propiedades antioxidantes, antiinflamatorias y antimicrobianas.',
'Infusión: 1 cucharadita de hojas secas por taza de agua hirviendo, reposar 10 minutos. Tomar 2-3 tazas al día.',
'Evitar en embarazo y lactancia. Puede elevar la presión arterial. No usar en personas con epilepsia sin supervisión médica.',
'romero.jpg'),

('Lavanda', 'Lavandula angustifolia', 'Lamiaceae',
'Planta aromática perenne con flores púrpuras agrupadas en espigas. Nativa de la región mediterránea.',
'Propiedades relajantes, ansiolíticas y antiespasmódicas. Útil para ansiedad, insomnio, dolores de cabeza por tensión.',
'Infusión: 1-2 cucharaditas de flores secas por taza. Tomar 1-2 tazas al día, preferentemente antes de dormir.',
'El aceite esencial no debe consumirse internamente. Puede causar somnolencia. No usar en embarazo sin supervisión.',
'lavanda.jpg'),

('Jengibre', 'Zingiber officinale', 'Zingiberaceae',
'Planta herbácea perenne con rizomas aromáticos y sabor picante. Originaria del sudeste asiático.',
'Antiinflamatorio, antiemético. Alivia náuseas, vómitos, mejora digestión, reduce dolores musculares.',
'Infusión: Rallar 1-2 cm de rizoma fresco por taza de agua hirviendo, reposar 10 minutos. Tomar 2-3 tazas al día.',
'Puede interactuar con anticoagulantes. No consumir en exceso si hay cálculos biliares. Limitar durante embarazo.',
'jengibre.jpg'),

('Valeriana', 'Valeriana officinalis', 'Valerianaceae',
'Planta herbácea perenne con flores rosadas o blancas. La raíz tiene olor fuerte y es la parte medicinal.',
'Sedante natural, ansiolítico, inductor del sueño. Útil para insomnio, ansiedad, estrés, nerviosismo.',
'Infusión: 1 cucharadita de raíz seca por taza, reposar 10-15 minutos. Tomar 1 taza 1-2 horas antes de dormir.',
'Puede causar somnolencia. No combinar con alcohol o sedantes. Evitar en embarazo y lactancia.',
'valeriana.jpg'),

('Tila', 'Tilia platyphyllos', 'Malvaceae',
'Árbol de gran tamaño con hojas en forma de corazón y flores amarillentas aromáticas.',
'Sedante suave, ansiolítico, antiespasmódico. Útil para nerviosismo, insomnio leve, hipertensión leve.',
'Infusión: 1-2 cucharaditas de flores secas por taza, reposar 10 minutos. Tomar 2-3 tazas al día.',
'Puede causar somnolencia. No combinar con sedantes. Segura en embarazo en dosis moderadas.',
'tila.jpg'),

('Caléndula', 'Calendula officinalis', 'Asteraceae',
'Planta herbácea anual con flores de color naranja brillante o amarillo.',
'Cicatrizante, antiinflamatorio, antimicrobiano. Útil para heridas, quemaduras, eczemas, picaduras.',
'Uso tópico: Infusión concentrada para lavar heridas. Ungüento sobre piel afectada 2-3 veces al día.',
'No usar sobre heridas profundas sin supervisión. Puede causar alergias en sensibles a Asteraceae.',
'calendula.jpg');

-- =====================================================
-- INSERTAR VERSIÓN DEL MODELO IA
-- =====================================================
INSERT INTO model_versions (version, file_name, file_path, accuracy, file_size, is_active) VALUES
('1.0.0', 'modelo_plantas_96acc.tflite', 'models/modelo_plantas_96acc.tflite', 96.00, 2507472, TRUE);

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================
--
-- Para ejecutar este script:
-- 1. Abrir phpMyAdmin: http://localhost/phpmyadmin
-- 2. Click en "SQL" en el menú superior
-- 3. Copiar y pegar todo este archivo
-- 4. Click en "Continuar"
--
-- O desde terminal:
-- mysql -u root < dashboard_database.sql

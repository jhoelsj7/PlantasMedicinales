-- Script para actualizar la contraseña del usuario testuser a "test123"
-- Ejecutar este script después de aplicar las correcciones

USE plantas_db;

UPDATE users
SET password = '$2y$10$aAZ2tjty10RjqDb9YaOoaeYi8FGfz7ec9vvR0q/mhbL8oviRsSGRW'
WHERE username = 'testuser';

-- Verificar que se actualizó correctamente
SELECT username, email,
       CASE
           WHEN password = '$2y$10$aAZ2tjty10RjqDb9YaOoaeYi8FGfz7ec9vvR0q/mhbL8oviRsSGRW'
           THEN 'Password actualizado correctamente (test123)'
           ELSE 'Password NO actualizado'
       END as status
FROM users
WHERE username = 'testuser';

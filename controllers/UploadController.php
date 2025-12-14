<?php
/**
 * Upload Controller - Mejorado para App Movil
 */
require_once __DIR__ . '/../config/config.php';

class UploadController {

    public function __construct() {
        header('Content-Type: application/json; charset=utf-8');
        header('Access-Control-Allow-Origin: *');
        header('Access-Control-Allow-Methods: POST, OPTIONS');
        header('Access-Control-Allow-Headers: Content-Type, Authorization');

        if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
            http_response_code(200);
            exit;
        }
    }

    /**
     * Subir imagen desde la app movil
     * POST /api/upload_image.php
     *
     * Form-data:
     * - image: archivo (requerido)
     * - filename: nombre sin extension (opcional)
     * - plant_id: ID de planta para vincular (opcional)
     * - type: "prediction" | "plant" (opcional, default: plant)
     */
    public function uploadImage() {
        try {
            if (!isset($_FILES['image'])) {
                http_response_code(400);
                echo json_encode([
                    'success' => false,
                    'error' => 'No se recibio ninguna imagen'
                ]);
                return;
            }

            $file = $_FILES['image'];
            $allowedTypes = ['image/jpeg', 'image/jpg', 'image/png'];
            $maxSize = defined('MAX_UPLOAD_SIZE') ? MAX_UPLOAD_SIZE : 5 * 1024 * 1024;

            // Validar tipo
            if (!in_array($file['type'], $allowedTypes)) {
                http_response_code(400);
                echo json_encode([
                    'success' => false,
                    'error' => 'Solo se permiten imagenes JPG y PNG'
                ]);
                return;
            }

            // Validar tamano
            if ($file['size'] > $maxSize) {
                http_response_code(400);
                echo json_encode([
                    'success' => false,
                    'error' => 'La imagen no debe superar 5MB'
                ]);
                return;
            }

            // Validar errores de subida
            if ($file['error'] !== UPLOAD_ERR_OK) {
                http_response_code(400);
                echo json_encode([
                    'success' => false,
                    'error' => 'Error al subir el archivo: ' . $this->getUploadError($file['error'])
                ]);
                return;
            }

            $ext = strtolower(pathinfo($file['name'], PATHINFO_EXTENSION));
            $type = $_POST['type'] ?? 'plant';

            // Generar nombre de archivo
            if (isset($_POST['filename']) && !empty($_POST['filename'])) {
                $basename = preg_replace('/[^a-zA-Z0-9_-]/', '', $_POST['filename']);
            } else {
                // Generar nombre unico
                $prefix = ($type === 'prediction') ? 'pred_' : 'plant_';
                $basename = $prefix . time() . '_' . uniqid();
            }

            $filename = $basename . '.' . $ext;
            $uploadDir = defined('UPLOAD_PATH') ? UPLOAD_PATH : __DIR__ . '/../public/uploads/';

            // Crear directorio si no existe
            if (!is_dir($uploadDir)) {
                mkdir($uploadDir, 0755, true);
            }

            $fullPath = $uploadDir . $filename;

            // Subir archivo
            if (move_uploaded_file($file['tmp_name'], $fullPath)) {

                // Eliminar versiones anteriores con diferente extension
                foreach (['jpg', 'jpeg', 'png'] as $oldExt) {
                    $oldFile = $uploadDir . $basename . '.' . $oldExt;
                    if (file_exists($oldFile) && $oldFile !== $fullPath) {
                        unlink($oldFile);
                    }
                }

                // Vincular a planta si se especifico
                $linkedPlant = null;
                if (isset($_POST['plant_id']) && !empty($_POST['plant_id'])) {
                    require_once __DIR__ . '/../models/Database.php';
                    $db = Database::getInstance()->getConnection();
                    $stmt = $db->prepare("UPDATE plants SET image_path = ? WHERE id = ?");
                    $stmt->execute([$filename, (int)$_POST['plant_id']]);

                    // Obtener nombre de la planta
                    $stmt = $db->prepare("SELECT common_name FROM plants WHERE id = ?");
                    $stmt->execute([(int)$_POST['plant_id']]);
                    $plant = $stmt->fetch();
                    if ($plant) {
                        $linkedPlant = $plant['common_name'];
                    }
                }

                // Construir URL
                $baseUrl = defined('UPLOAD_URL') ? UPLOAD_URL : 'http://localhost/plantas_api/public/uploads/';
                $imageUrl = $baseUrl . $filename;

                $response = [
                    'success' => true,
                    'message' => 'Imagen subida exitosamente',
                    'data' => [
                        'filename' => $filename,
                        'url' => $imageUrl,
                        'size' => $file['size'],
                        'type' => $file['type']
                    ]
                ];

                if ($linkedPlant) {
                    $response['data']['linked_to'] = $linkedPlant;
                }

                echo json_encode($response);

            } else {
                http_response_code(500);
                echo json_encode([
                    'success' => false,
                    'error' => 'Error al guardar la imagen en el servidor'
                ]);
            }

        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode([
                'success' => false,
                'error' => 'Error interno: ' . $e->getMessage()
            ]);
        }
    }

    /**
     * Subir imagen de prediccion (desde la app al hacer una prediccion)
     */
    public function uploadPredictionImage() {
        $_POST['type'] = 'prediction';
        $this->uploadImage();
    }

    /**
     * Obtener mensaje de error de subida
     */
    private function getUploadError($code) {
        $errors = [
            UPLOAD_ERR_INI_SIZE => 'El archivo excede el tamano maximo permitido por PHP',
            UPLOAD_ERR_FORM_SIZE => 'El archivo excede el tamano maximo del formulario',
            UPLOAD_ERR_PARTIAL => 'El archivo se subio parcialmente',
            UPLOAD_ERR_NO_FILE => 'No se subio ningun archivo',
            UPLOAD_ERR_NO_TMP_DIR => 'Falta la carpeta temporal',
            UPLOAD_ERR_CANT_WRITE => 'Error al escribir el archivo',
            UPLOAD_ERR_EXTENSION => 'Extension de PHP detuvo la subida'
        ];
        return $errors[$code] ?? 'Error desconocido';
    }
}

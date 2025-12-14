<?php
/**
 * API Endpoint: Predicciones
 * POST - Guardar prediccion desde app movil
 * GET - Obtener historial de predicciones
 */
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit;
}

require_once __DIR__ . '/../models/Prediction.php';

$predictionModel = new Prediction();

// POST - Guardar nueva prediccion desde app movil
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = json_decode(file_get_contents('php://input'), true);

    if (!$data) {
        http_response_code(400);
        echo json_encode(['success' => false, 'message' => 'Datos invalidos']);
        exit;
    }

    try {
        if ($predictionModel->create($data)) {
            echo json_encode([
                'success' => true,
                'message' => 'Prediccion guardada exitosamente'
            ]);
        } else {
            throw new Exception('Error al guardar prediccion');
        }
    } catch (Exception $e) {
        http_response_code(500);
        echo json_encode(['success' => false, 'message' => $e->getMessage()]);
    }
    exit;
}

// GET - Obtener historial de predicciones
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $limit = isset($_GET['limit']) ? (int)$_GET['limit'] : 100;
    $offset = isset($_GET['offset']) ? (int)$_GET['offset'] : 0;
    $userId = isset($_GET['user_id']) ? (int)$_GET['user_id'] : null;

    try {
        if ($userId) {
            $predictions = $predictionModel->getByUser($userId, $limit);
        } else {
            $predictions = $predictionModel->getAll($limit, $offset);
        }

        echo json_encode([
            'success' => true,
            'data' => $predictions,
            'count' => count($predictions)
        ]);
    } catch (Exception $e) {
        http_response_code(500);
        echo json_encode(['success' => false, 'message' => $e->getMessage()]);
    }
    exit;
}

http_response_code(405);
echo json_encode(['success' => false, 'message' => 'Metodo no permitido']);

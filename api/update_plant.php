<?php
/**
 * Update Plant API Endpoint
 */
require_once __DIR__ . '/../controllers/PlantController.php';

$id = $_GET['id'] ?? null;

if (!$id) {
    http_response_code(400);
    echo json_encode(['error' => 'ID requerido']);
    exit;
}

$controller = new PlantController();
$controller->update($id);

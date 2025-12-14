<?php
/**
 * Plant API Controller
 */
require_once __DIR__ . '/../models/Plant.php';

class PlantController {
    private $plantModel;

    public function __construct() {
        $this->plantModel = new Plant();
        header('Content-Type: application/json; charset=utf-8');
        header('Access-Control-Allow-Origin: *');
        header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE');
        header('Access-Control-Allow-Headers: Content-Type, Authorization');
    }

    public function getAll() {
        try {
            $plants = $this->plantModel->getAll();
            echo json_encode($plants, JSON_UNESCAPED_UNICODE);
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Error al obtener plantas']);
        }
    }

    public function getById($id) {
        try {
            $plant = $this->plantModel->getById($id);

            if ($plant) {
                echo json_encode($plant, JSON_UNESCAPED_UNICODE);
            } else {
                http_response_code(404);
                echo json_encode(['error' => 'Planta no encontrada']);
            }
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Error al obtener planta']);
        }
    }

    public function search() {
        try {
            $query = $_GET['query'] ?? $_GET['q'] ?? '';
            $family = $_GET['family'] ?? '';
            $page = isset($_GET['page']) ? (int)$_GET['page'] : 1;
            $limit = isset($_GET['limit']) ? (int)$_GET['limit'] : 20;

            $result = $this->plantModel->search($query, $family, $page, $limit);
            echo json_encode($result, JSON_UNESCAPED_UNICODE);
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Error en búsqueda']);
        }
    }

    public function create() {
        try {
            $data = json_decode(file_get_contents('php://input'), true);

            if (!isset($data['common_name']) || !isset($data['scientific_name'])) {
                http_response_code(400);
                echo json_encode(['error' => 'Datos incompletos']);
                return;
            }

            if ($this->plantModel->create($data)) {
                http_response_code(201);
                echo json_encode(['success' => true, 'message' => 'Planta creada exitosamente']);
            } else {
                http_response_code(500);
                echo json_encode(['error' => 'Error al crear planta']);
            }
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Error al procesar solicitud']);
        }
    }

    public function update($id) {
        try {
            $data = json_decode(file_get_contents('php://input'), true);

            if ($this->plantModel->update($id, $data)) {
                echo json_encode(['success' => true, 'message' => 'Planta actualizada exitosamente']);
            } else {
                http_response_code(500);
                echo json_encode(['error' => 'Error al actualizar planta']);
            }
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Error al procesar solicitud']);
        }
    }

    public function delete($id) {
        try {
            if ($this->plantModel->delete($id)) {
                echo json_encode(['success' => true, 'message' => 'Planta eliminada exitosamente']);
            } else {
                http_response_code(500);
                echo json_encode(['error' => 'Error al eliminar planta']);
            }
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Error al procesar solicitud']);
        }
    }
}

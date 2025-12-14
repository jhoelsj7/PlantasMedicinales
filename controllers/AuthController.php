<?php
/**
 * Authentication Controller
 */
require_once __DIR__ . '/../models/User.php';

class AuthController {
    private $userModel;

    public function __construct() {
        $this->userModel = new User();
        header('Content-Type: application/json; charset=utf-8');
        header('Access-Control-Allow-Origin: *');
        header('Access-Control-Allow-Methods: POST');
        header('Access-Control-Allow-Headers: Content-Type, Authorization');
    }

    public function login() {
        try {
            $data = json_decode(file_get_contents('php://input'), true);

            if (!isset($data['username']) || !isset($data['password']) ||
                empty(trim($data['username'])) || empty(trim($data['password']))) {
                http_response_code(400);
                echo json_encode(['error' => 'Usuario y contraseña requeridos']);
                return;
            }

            $user = $this->userModel->authenticate($data['username'], $data['password']);

            if ($user) {
                $token = bin2hex(random_bytes(32));

                echo json_encode([
                    'success' => true,
                    'token' => $token,
                    'user' => [
                        'id' => $user['id'],
                        'username' => $user['username'],
                        'email' => $user['email'],
                        'full_name' => $user['full_name']
                    ]
                ]);
            } else {
                http_response_code(401);
                echo json_encode(['error' => 'Credenciales inválidas']);
            }
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Error al procesar solicitud']);
        }
    }
}

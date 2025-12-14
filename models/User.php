<?php
/**
 * User Model - CRUD Completo
 */
require_once __DIR__ . '/Database.php';

class User {
    private $pdo;

    public function __construct() {
        $this->pdo = Database::getInstance()->getConnection();
    }

    public function authenticate($username, $password) {
        $stmt = $this->pdo->prepare("SELECT * FROM users WHERE username = ? OR email = ?");
        $stmt->execute([$username, $username]);
        $user = $stmt->fetch();

        if ($user && password_verify($password, $user['password'])) {
            $this->updateLastLogin($user['id']);
            unset($user['password']);
            return $user;
        }

        return false;
    }

    public function getAll() {
        $stmt = $this->pdo->query("SELECT id, username, email, full_name, created_at, last_login FROM users ORDER BY created_at DESC");
        return $stmt->fetchAll();
    }

    public function getById($id) {
        $stmt = $this->pdo->prepare("SELECT id, username, email, full_name, created_at, last_login FROM users WHERE id = ?");
        $stmt->execute([$id]);
        return $stmt->fetch();
    }

    private function updateLastLogin($userId) {
        $stmt = $this->pdo->prepare("UPDATE users SET last_login = NOW() WHERE id = ?");
        $stmt->execute([$userId]);
    }

    public function getCount() {
        $stmt = $this->pdo->query("SELECT COUNT(*) as total FROM users");
        return $stmt->fetch()['total'];
    }

    /**
     * Crear nuevo usuario
     */
    public function create($data) {
        // Verificar si username ya existe
        $stmt = $this->pdo->prepare("SELECT id FROM users WHERE username = ?");
        $stmt->execute([$data['username']]);
        if ($stmt->fetch()) {
            throw new Exception("El nombre de usuario ya existe");
        }

        // Verificar si email ya existe
        $stmt = $this->pdo->prepare("SELECT id FROM users WHERE email = ?");
        $stmt->execute([$data['email']]);
        if ($stmt->fetch()) {
            throw new Exception("El email ya está registrado");
        }

        $hashedPassword = password_hash($data['password'], PASSWORD_DEFAULT);

        $stmt = $this->pdo->prepare("INSERT INTO users (username, email, password, full_name, created_at) VALUES (?, ?, ?, ?, NOW())");
        return $stmt->execute([
            $data['username'],
            $data['email'],
            $hashedPassword,
            $data['full_name']
        ]);
    }

    /**
     * Actualizar usuario existente
     */
    public function update($id, $data) {
        // Verificar si username ya existe (excluyendo el usuario actual)
        $stmt = $this->pdo->prepare("SELECT id FROM users WHERE username = ? AND id != ?");
        $stmt->execute([$data['username'], $id]);
        if ($stmt->fetch()) {
            throw new Exception("El nombre de usuario ya existe");
        }

        // Verificar si email ya existe (excluyendo el usuario actual)
        $stmt = $this->pdo->prepare("SELECT id FROM users WHERE email = ? AND id != ?");
        $stmt->execute([$data['email'], $id]);
        if ($stmt->fetch()) {
            throw new Exception("El email ya está registrado");
        }

        // Si se proporciona nueva contraseña, actualizarla
        if (!empty($data['password'])) {
            $hashedPassword = password_hash($data['password'], PASSWORD_DEFAULT);
            $stmt = $this->pdo->prepare("UPDATE users SET username = ?, email = ?, password = ?, full_name = ? WHERE id = ?");
            return $stmt->execute([
                $data['username'],
                $data['email'],
                $hashedPassword,
                $data['full_name'],
                $id
            ]);
        } else {
            $stmt = $this->pdo->prepare("UPDATE users SET username = ?, email = ?, full_name = ? WHERE id = ?");
            return $stmt->execute([
                $data['username'],
                $data['email'],
                $data['full_name'],
                $id
            ]);
        }
    }

    /**
     * Eliminar usuario
     */
    public function delete($id) {
        // No permitir eliminar el propio usuario administrador
        $stmt = $this->pdo->prepare("DELETE FROM users WHERE id = ?");
        return $stmt->execute([$id]);
    }
}

<?php
/**
 * Plant Model
 */
require_once __DIR__ . '/Database.php';
require_once __DIR__ . '/../config/config.php';

class Plant {
    private $pdo;
    private $baseUrl;

    public function __construct() {
        $this->pdo = Database::getInstance()->getConnection();
        $this->baseUrl = defined('BASE_URL') ? BASE_URL : 'http://localhost/plantas_api/';
    }

    public function getAll() {
        $stmt = $this->pdo->query("SELECT * FROM plants WHERE is_active = 1 ORDER BY common_name ASC");
        $plants = $stmt->fetchAll();

        foreach ($plants as &$plant) {
            $plant['imageUrl'] = $this->baseUrl . 'public/uploads/' . $plant['image_path'];
        }

        return $plants;
    }

    public function getById($id) {
        $stmt = $this->pdo->prepare("SELECT * FROM plants WHERE id = ? AND is_active = 1");
        $stmt->execute([$id]);
        $plant = $stmt->fetch();

        if ($plant) {
            $plant['imageUrl'] = $this->baseUrl . 'public/uploads/' . $plant['image_path'];
        }

        return $plant;
    }

    public function search($query = '', $family = '', $page = 1, $limit = 20) {
        $offset = ($page - 1) * $limit;

        $sql = "SELECT * FROM plants WHERE is_active = 1";
        $countSql = "SELECT COUNT(*) as total FROM plants WHERE is_active = 1";
        $params = [];

        if (!empty($query)) {
            $condition = " AND (common_name LIKE ? OR scientific_name LIKE ? OR medicinal_uses LIKE ?)";
            $sql .= $condition;
            $countSql .= $condition;
            $searchTerm = "%{$query}%";
            $params[] = $searchTerm;
            $params[] = $searchTerm;
            $params[] = $searchTerm;
        }

        if (!empty($family)) {
            $sql .= " AND family = ?";
            $countSql .= " AND family = ?";
            $params[] = $family;
        }

        // Get total count
        $countStmt = $this->pdo->prepare($countSql);
        $countStmt->execute($params);
        $total = $countStmt->fetch()['total'];

        // Get paginated results
        $sql .= " ORDER BY common_name ASC LIMIT ? OFFSET ?";
        $stmt = $this->pdo->prepare($sql);

        $allParams = array_merge($params, [$limit, $offset]);
        $stmt->execute($allParams);
        $plants = $stmt->fetchAll();

        foreach ($plants as &$plant) {
            $plant['imageUrl'] = $this->baseUrl . 'public/uploads/' . $plant['image_path'];
        }

        return [
            'data' => $plants,
            'total' => $total,
            'page' => $page,
            'pages' => ceil($total / $limit)
        ];
    }

    public function create($data) {
        $stmt = $this->pdo->prepare("
            INSERT INTO plants (common_name, scientific_name, family, description,
                medicinal_uses, preparation, precautions, image_path)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        ");

        return $stmt->execute([
            $data['common_name'],
            $data['scientific_name'],
            $data['family'],
            $data['description'],
            $data['medicinal_uses'],
            $data['preparation'],
            $data['precautions'],
            $data['image_path'] ?? 'default.jpg'
        ]);
    }

    public function update($id, $data) {
        $fields = [];
        $params = [];

        $allowedFields = ['common_name', 'scientific_name', 'family', 'description',
                          'medicinal_uses', 'preparation', 'precautions', 'image_path'];

        foreach ($allowedFields as $field) {
            if (isset($data[$field])) {
                $fields[] = "{$field} = ?";
                $params[] = $data[$field];
            }
        }

        if (empty($fields)) return false;

        $params[] = $id;
        $sql = "UPDATE plants SET " . implode(', ', $fields) . ", updated_at = NOW() WHERE id = ?";

        $stmt = $this->pdo->prepare($sql);
        return $stmt->execute($params);
    }

    public function delete($id) {
        $stmt = $this->pdo->prepare("UPDATE plants SET is_active = 0 WHERE id = ?");
        return $stmt->execute([$id]);
    }

    public function getStatistics() {
        $stats = [];

        $stmt = $this->pdo->query("SELECT COUNT(*) as total FROM plants WHERE is_active = 1");
        $stats['total_plants'] = $stmt->fetch()['total'];

        $stmt = $this->pdo->query("SELECT family, COUNT(*) as count FROM plants WHERE is_active = 1 GROUP BY family ORDER BY count DESC");
        $stats['by_family'] = $stmt->fetchAll();

        $stmt = $this->pdo->query("SELECT * FROM plants WHERE is_active = 1 ORDER BY created_at DESC LIMIT 10");
        $stats['recent_plants'] = $stmt->fetchAll();

        return $stats;
    }
}

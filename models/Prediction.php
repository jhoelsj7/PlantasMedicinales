<?php
/**
 * Prediction Model - Historial de predicciones
 * Almacena las predicciones enviadas desde la app movil
 */
require_once __DIR__ . '/Database.php';

class Prediction {
    private $pdo;

    public function __construct() {
        $this->pdo = Database::getInstance()->getConnection();
    }

    /**
     * Obtener todas las predicciones con info de planta y usuario
     */
    public function getAll($limit = 100, $offset = 0) {
        $stmt = $this->pdo->prepare("
            SELECT
                p.id,
                p.image_path,
                p.confidence,
                p.created_at,
                u.username,
                u.full_name as user_name,
                pl.common_name as plant_name,
                pl.scientific_name,
                pl.family
            FROM predictions p
            LEFT JOIN users u ON p.user_id = u.id
            LEFT JOIN plants pl ON p.plant_id = pl.id
            ORDER BY p.created_at DESC
            LIMIT ? OFFSET ?
        ");
        $stmt->execute([$limit, $offset]);
        return $stmt->fetchAll();
    }

    /**
     * Obtener predicciones por usuario
     */
    public function getByUser($userId, $limit = 50) {
        $stmt = $this->pdo->prepare("
            SELECT
                p.id,
                p.image_path,
                p.confidence,
                p.created_at,
                pl.common_name as plant_name,
                pl.scientific_name
            FROM predictions p
            LEFT JOIN plants pl ON p.plant_id = pl.id
            WHERE p.user_id = ?
            ORDER BY p.created_at DESC
            LIMIT ?
        ");
        $stmt->execute([$userId, $limit]);
        return $stmt->fetchAll();
    }

    /**
     * Guardar prediccion enviada desde la app movil
     */
    public function create($data) {
        $stmt = $this->pdo->prepare("
            INSERT INTO predictions (user_id, plant_id, image_path, confidence, created_at)
            VALUES (?, ?, ?, ?, NOW())
        ");
        return $stmt->execute([
            $data['user_id'] ?? null,
            $data['plant_id'] ?? null,
            $data['image_path'] ?? null,
            $data['confidence'] ?? 0
        ]);
    }

    /**
     * Obtener estadisticas de predicciones
     */
    public function getStatistics() {
        // Total de predicciones
        $totalStmt = $this->pdo->query("SELECT COUNT(*) as total FROM predictions");
        $total = $totalStmt->fetch()['total'];

        // Confianza promedio
        $avgStmt = $this->pdo->query("SELECT AVG(confidence) as avg_confidence FROM predictions WHERE confidence > 0");
        $avgConfidence = $avgStmt->fetch()['avg_confidence'] ?? 0;

        // Predicciones por planta (top 10)
        $byPlantStmt = $this->pdo->query("
            SELECT
                pl.common_name,
                COUNT(p.id) as count
            FROM predictions p
            JOIN plants pl ON p.plant_id = pl.id
            GROUP BY p.plant_id, pl.common_name
            ORDER BY count DESC
            LIMIT 10
        ");
        $byPlant = $byPlantStmt->fetchAll();

        // Predicciones por dia (ultimos 30 dias)
        $byDayStmt = $this->pdo->query("
            SELECT
                DATE(created_at) as date,
                COUNT(*) as count
            FROM predictions
            WHERE created_at >= DATE_SUB(NOW(), INTERVAL 30 DAY)
            GROUP BY DATE(created_at)
            ORDER BY date ASC
        ");
        $byDay = $byDayStmt->fetchAll();

        // Predicciones de hoy
        $todayStmt = $this->pdo->query("SELECT COUNT(*) as total FROM predictions WHERE DATE(created_at) = CURDATE()");
        $today = $todayStmt->fetch()['total'];

        return [
            'total' => $total,
            'today' => $today,
            'avg_confidence' => round($avgConfidence, 2),
            'by_plant' => $byPlant,
            'by_day' => $byDay
        ];
    }

    /**
     * Obtener conteo total
     */
    public function getCount() {
        $stmt = $this->pdo->query("SELECT COUNT(*) as total FROM predictions");
        return $stmt->fetch()['total'];
    }

    /**
     * Eliminar prediccion
     */
    public function delete($id) {
        $stmt = $this->pdo->prepare("DELETE FROM predictions WHERE id = ?");
        return $stmt->execute([$id]);
    }
}

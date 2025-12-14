<?php
/**
 * Historial de Predicciones - Vista Web
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Prediction.php';
require_once __DIR__ . '/../../models/Plant.php';

$predictionModel = new Prediction();
$plantModel = new Plant();

$predictions = $predictionModel->getAll(100, 0);
$stats = $predictionModel->getStatistics();
$plants = $plantModel->getAll();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Predicciones</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f6fa; }
        .sidebar { position: fixed; left: 0; top: 0; height: 100vh; width: 260px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 20px; color: white; overflow-y: auto; z-index: 1000; }
        .sidebar h2 { font-size: 1.5em; margin-bottom: 30px; text-align: center; padding-bottom: 20px; border-bottom: 2px solid rgba(255,255,255,0.2); }
        .nav-item { padding: 15px 20px; margin: 10px 0; border-radius: 10px; cursor: pointer; transition: all 0.3s; display: flex; align-items: center; gap: 15px; color: white; text-decoration: none; }
        .nav-item:hover, .nav-item.active { background: rgba(255,255,255,0.2); transform: translateX(5px); }
        .nav-item i { width: 20px; text-align: center; }
        .main-content { margin-left: 260px; padding: 30px; }
        .header { background: white; padding: 20px 30px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 30px; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: white; padding: 25px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .stat-card h3 { font-size: 2em; margin-bottom: 5px; }
        .stat-card p { color: #6c757d; font-size: 0.9em; }
        .stat-card.purple { border-left: 4px solid #667eea; }
        .stat-card.green { border-left: 4px solid #2ecc71; }
        .stat-card.orange { border-left: 4px solid #f39c12; }
        .stat-card.blue { border-left: 4px solid #3498db; }
        .content-section { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 25px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th { background: #f8f9fa; padding: 15px; text-align: left; font-weight: 600; color: #2c3e50; border-bottom: 2px solid #e9ecef; }
        td { padding: 15px; border-bottom: 1px solid #f8f9fa; }
        tr:hover { background: #f8f9fa; }
        .badge { padding: 5px 12px; border-radius: 20px; font-size: 0.85em; font-weight: 600; }
        .badge-success { background: #d4edda; color: #155724; }
        .badge-warning { background: #fff3cd; color: #856404; }
        .badge-danger { background: #f8d7da; color: #721c24; }
        .confidence-bar { width: 100px; height: 8px; background: #e9ecef; border-radius: 4px; overflow: hidden; }
        .confidence-fill { height: 100%; border-radius: 4px; }
        .confidence-high { background: #2ecc71; }
        .confidence-medium { background: #f39c12; }
        .confidence-low { background: #e74c3c; }
        .empty-state { text-align: center; padding: 50px; color: #6c757d; }
        .empty-state i { font-size: 4em; margin-bottom: 20px; opacity: 0.5; }
        .plant-img { width: 40px; height: 40px; border-radius: 8px; object-fit: cover; }
        .filter-section { display: flex; gap: 15px; margin-bottom: 20px; flex-wrap: wrap; }
        .filter-section select, .filter-section input { padding: 10px 15px; border: 1px solid #ddd; border-radius: 8px; font-size: 0.95em; }
        .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-size: 0.95em; transition: all 0.3s; text-decoration: none; display: inline-flex; align-items: center; gap: 8px; }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }
        .btn-success { background: #2ecc71; color: white; }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.2); }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <h2><i class="fas fa-leaf"></i> Plantas Admin</h2>
        <a href="index.php" class="nav-item"><i class="fas fa-home"></i><span>Dashboard</span></a>
        <a href="manage_plants.php" class="nav-item"><i class="fas fa-seedling"></i><span>Gestionar Plantas</span></a>
        <a href="manage_users.php" class="nav-item"><i class="fas fa-users"></i><span>Usuarios</span></a>
        <a href="upload_images.php" class="nav-item"><i class="fas fa-images"></i><span>Imagenes</span></a>
        <a href="statistics.php" class="nav-item"><i class="fas fa-chart-bar"></i><span>Estadisticas</span></a>
        <a href="predictions.php" class="nav-item active"><i class="fas fa-history"></i><span>Predicciones</span></a>
        <a href="reports.php" class="nav-item"><i class="fas fa-file-pdf"></i><span>Reportes</span></a>
        <a href="logout.php" class="nav-item" style="margin-top: auto; border-top: 2px solid rgba(255,255,255,0.2); padding-top: 15px;">
            <i class="fas fa-sign-out-alt"></i>
            <span>Cerrar Sesion</span>
        </a>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="header">
            <h1><i class="fas fa-history"></i> Historial de Predicciones</h1>
            <p style="color: #6c757d; margin-top: 5px;">Predicciones realizadas desde la aplicacion movil</p>
        </div>

        <!-- Stats Cards -->
        <div class="stats-grid">
            <div class="stat-card purple">
                <h3><?php echo number_format($stats['total']); ?></h3>
                <p><i class="fas fa-camera"></i> Total Predicciones</p>
            </div>
            <div class="stat-card green">
                <h3><?php echo number_format($stats['today']); ?></h3>
                <p><i class="fas fa-calendar-day"></i> Hoy</p>
            </div>
            <div class="stat-card orange">
                <h3><?php echo $stats['avg_confidence']; ?>%</h3>
                <p><i class="fas fa-chart-line"></i> Confianza Promedio</p>
            </div>
            <div class="stat-card blue">
                <h3><?php echo count($stats['by_plant']); ?></h3>
                <p><i class="fas fa-seedling"></i> Plantas Identificadas</p>
            </div>
        </div>

        <!-- Top Plantas Identificadas -->
        <?php if (!empty($stats['by_plant'])): ?>
        <div class="content-section">
            <h2 style="margin-bottom: 20px;"><i class="fas fa-trophy"></i> Top Plantas Mas Identificadas</h2>
            <div style="display: flex; gap: 15px; flex-wrap: wrap;">
                <?php foreach ($stats['by_plant'] as $index => $plant): ?>
                <div style="background: #f8f9fa; padding: 15px 20px; border-radius: 10px; display: flex; align-items: center; gap: 10px;">
                    <span style="background: linear-gradient(135deg, #667eea, #764ba2); color: white; width: 30px; height: 30px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold;"><?php echo $index + 1; ?></span>
                    <div>
                        <strong><?php echo htmlspecialchars($plant['common_name']); ?></strong>
                        <span style="color: #6c757d; margin-left: 10px;"><?php echo $plant['count']; ?> veces</span>
                    </div>
                </div>
                <?php endforeach; ?>
            </div>
        </div>
        <?php endif; ?>

        <!-- Tabla de Predicciones -->
        <div class="content-section">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                <h2><i class="fas fa-list"></i> Historial Completo</h2>
                <a href="export.php?type=predictions&format=csv" class="btn btn-success">
                    <i class="fas fa-download"></i> Exportar CSV
                </a>
            </div>

            <?php if (empty($predictions)): ?>
            <div class="empty-state">
                <i class="fas fa-camera-retro"></i>
                <h3>No hay predicciones registradas</h3>
                <p>Las predicciones apareceran aqui cuando los usuarios utilicen la app movil</p>
            </div>
            <?php else: ?>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Usuario</th>
                        <th>Planta Identificada</th>
                        <th>Confianza</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($predictions as $pred): ?>
                    <tr>
                        <td>#<?php echo $pred['id']; ?></td>
                        <td>
                            <?php if ($pred['username']): ?>
                                <strong><?php echo htmlspecialchars($pred['username']); ?></strong>
                                <br><small style="color: #6c757d;"><?php echo htmlspecialchars($pred['user_name']); ?></small>
                            <?php else: ?>
                                <span style="color: #6c757d;">Usuario anonimo</span>
                            <?php endif; ?>
                        </td>
                        <td>
                            <?php if ($pred['plant_name']): ?>
                                <strong><?php echo htmlspecialchars($pred['plant_name']); ?></strong>
                                <br><small style="color: #6c757d; font-style: italic;"><?php echo htmlspecialchars($pred['scientific_name']); ?></small>
                            <?php else: ?>
                                <span style="color: #6c757d;">No identificada</span>
                            <?php endif; ?>
                        </td>
                        <td>
                            <?php
                            $confidence = $pred['confidence'] ?? 0;
                            $colorClass = $confidence >= 80 ? 'confidence-high' : ($confidence >= 50 ? 'confidence-medium' : 'confidence-low');
                            $badgeClass = $confidence >= 80 ? 'badge-success' : ($confidence >= 50 ? 'badge-warning' : 'badge-danger');
                            ?>
                            <span class="badge <?php echo $badgeClass; ?>"><?php echo number_format($confidence, 1); ?>%</span>
                            <div class="confidence-bar" style="margin-top: 5px;">
                                <div class="confidence-fill <?php echo $colorClass; ?>" style="width: <?php echo $confidence; ?>%;"></div>
                            </div>
                        </td>
                        <td>
                            <?php echo date('d/m/Y', strtotime($pred['created_at'])); ?>
                            <br><small style="color: #6c757d;"><?php echo date('H:i:s', strtotime($pred['created_at'])); ?></small>
                        </td>
                    </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
            <?php endif; ?>
        </div>
    </div>
</body>
</html>

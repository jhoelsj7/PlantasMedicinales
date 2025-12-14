<?php
/**
 * Dashboard Principal - Plantas Medicinales
 * Panel de administración web
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Plant.php';
require_once __DIR__ . '/../../models/User.php';

// Obtener estadísticas
try {
    $plantModel = new Plant();
    $userModel = new User();

    $stats = $plantModel->getStatistics();
    $totalPlantas = $stats['total_plants'];
    $plantasPorFamilia = $stats['by_family'];
    $ultimasPlantas = array_slice($stats['recent_plants'], 0, 5);
    $totalUsuarios = $userModel->getCount();

} catch (PDOException $e) {
    die("Error: " . $e->getMessage());
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Plantas Medicinales</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f6fa;
        }

        /* Sidebar */
        .sidebar {
            position: fixed;
            left: 0;
            top: 0;
            height: 100vh;
            width: 260px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 20px;
            color: white;
            overflow-y: auto;
            z-index: 1000;
        }

        .sidebar h2 {
            font-size: 1.5em;
            margin-bottom: 30px;
            text-align: center;
            padding-bottom: 20px;
            border-bottom: 2px solid rgba(255,255,255,0.2);
        }

        .sidebar h2 i {
            color: #90EE90;
        }

        .nav-item {
            padding: 15px 20px;
            margin: 10px 0;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            gap: 15px;
            color: white;
            text-decoration: none;
        }

        .nav-item:hover, .nav-item.active {
            background: rgba(255,255,255,0.2);
            transform: translateX(5px);
        }

        .nav-item i {
            width: 20px;
            text-align: center;
        }

        /* Main content */
        .main-content {
            margin-left: 260px;
            padding: 30px;
        }

        .header {
            background: white;
            padding: 20px 30px;
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h1 {
            color: #2c3e50;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-avatar {
            width: 45px;
            height: 45px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: bold;
        }

        /* Stats cards */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 25px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: all 0.3s;
            position: relative;
            overflow: hidden;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }

        .stat-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 4px;
            height: 100%;
        }

        .stat-card.green::before {
            background: #2ecc71;
        }

        .stat-card.blue::before {
            background: #3498db;
        }

        .stat-card.purple::before {
            background: #9b59b6;
        }

        .stat-card.orange::before {
            background: #e67e22;
        }

        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            margin-bottom: 15px;
        }

        .stat-card.green .stat-icon {
            background: rgba(46, 204, 113, 0.1);
            color: #2ecc71;
        }

        .stat-card.blue .stat-icon {
            background: rgba(52, 152, 219, 0.1);
            color: #3498db;
        }

        .stat-card.purple .stat-icon {
            background: rgba(155, 89, 182, 0.1);
            color: #9b59b6;
        }

        .stat-card.orange .stat-icon {
            background: rgba(230, 126, 34, 0.1);
            color: #e67e22;
        }

        .stat-label {
            color: #7f8c8d;
            font-size: 0.9em;
            margin-bottom: 5px;
        }

        .stat-value {
            font-size: 2em;
            font-weight: bold;
            color: #2c3e50;
        }

        /* Content sections */
        .content-section {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 25px;
        }

        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f5f6fa;
        }

        .section-header h2 {
            color: #2c3e50;
            font-size: 1.5em;
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 0.95em;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }

        .btn-success {
            background: #2ecc71;
            color: white;
        }

        .btn-danger {
            background: #e74c3c;
            color: white;
        }

        .btn-warning {
            background: #f39c12;
            color: white;
        }

        /* Table */
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #f8f9fa;
            padding: 15px;
            text-align: left;
            font-weight: 600;
            color: #2c3e50;
            border-bottom: 2px solid #e9ecef;
        }

        td {
            padding: 15px;
            border-bottom: 1px solid #f8f9fa;
        }

        tr:hover {
            background: #f8f9fa;
        }

        .plant-img {
            width: 50px;
            height: 50px;
            border-radius: 8px;
            object-fit: cover;
        }

        .badge {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: 600;
        }

        .badge-success {
            background: #d4edda;
            color: #155724;
        }

        .badge-danger {
            background: #f8d7da;
            color: #721c24;
        }

        /* Chart */
        .chart-container {
            display: flex;
            gap: 15px;
            margin-top: 20px;
        }

        .chart-bar {
            flex: 1;
            background: linear-gradient(to top, #667eea, #764ba2);
            border-radius: 8px 8px 0 0;
            min-height: 50px;
            position: relative;
            display: flex;
            flex-direction: column;
            justify-content: flex-end;
            align-items: center;
            padding: 10px;
            color: white;
            font-weight: bold;
        }

        .chart-label {
            margin-top: 10px;
            text-align: center;
            font-size: 0.85em;
            color: #7f8c8d;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .sidebar {
                width: 70px;
            }

            .sidebar h2, .nav-item span {
                display: none;
            }

            .main-content {
                margin-left: 70px;
            }

            .stats-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <h2><i class="fas fa-leaf"></i> Plantas Admin</h2>
        <a href="index.php" class="nav-item active">
            <i class="fas fa-home"></i>
            <span>Dashboard</span>
        </a>
        <a href="manage_plants.php" class="nav-item">
            <i class="fas fa-seedling"></i>
            <span>Gestionar Plantas</span>
        </a>
        <a href="manage_users.php" class="nav-item">
            <i class="fas fa-users"></i>
            <span>Usuarios</span>
        </a>
        <a href="upload_images.php" class="nav-item">
            <i class="fas fa-images"></i>
            <span>Imágenes</span>
        </a>
        <a href="statistics.php" class="nav-item">
            <i class="fas fa-chart-bar"></i>
            <span>Estadisticas</span>
        </a>
        <a href="predictions.php" class="nav-item">
            <i class="fas fa-history"></i>
            <span>Predicciones</span>
        </a>
        <a href="reports.php" class="nav-item">
            <i class="fas fa-file-pdf"></i>
            <span>Reportes</span>
        </a>
        <a href="logout.php" class="nav-item" style="margin-top: auto; border-top: 2px solid rgba(255,255,255,0.2); padding-top: 15px;">
            <i class="fas fa-sign-out-alt"></i>
            <span>Cerrar Sesion</span>
        </a>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <!-- Header -->
        <div class="header">
            <h1>Dashboard Principal</h1>
            <div class="user-info">
                <div class="user-avatar">
                    <i class="fas fa-user"></i>
                </div>
                <div>
                    <div style="font-weight: 600;">Admin</div>
                    <div style="font-size: 0.85em; color: #7f8c8d;">Administrador</div>
                </div>
            </div>
        </div>

        <!-- Stats Cards -->
        <div class="stats-grid">
            <div class="stat-card green">
                <div class="stat-icon">
                    <i class="fas fa-leaf"></i>
                </div>
                <div class="stat-label">Total de Plantas</div>
                <div class="stat-value"><?php echo $totalPlantas; ?></div>
            </div>

            <div class="stat-card blue">
                <div class="stat-icon">
                    <i class="fas fa-users"></i>
                </div>
                <div class="stat-label">Usuarios Registrados</div>
                <div class="stat-value"><?php echo $totalUsuarios; ?></div>
            </div>

            <div class="stat-card purple">
                <div class="stat-icon">
                    <i class="fas fa-dna"></i>
                </div>
                <div class="stat-label">Familias Botánicas</div>
                <div class="stat-value"><?php echo count($plantasPorFamilia); ?></div>
            </div>

            <div class="stat-card orange">
                <div class="stat-icon">
                    <i class="fas fa-robot"></i>
                </div>
                <div class="stat-label">Precisión del Modelo</div>
                <div class="stat-value">96%</div>
            </div>
        </div>

        <!-- Últimas plantas agregadas -->
        <div class="content-section">
            <div class="section-header">
                <h2><i class="fas fa-clock"></i> Últimas Plantas Agregadas</h2>
                <a href="manage_plants.php" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Nueva Planta
                </a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Imagen</th>
                        <th>Nombre Común</th>
                        <th>Nombre Científico</th>
                        <th>Familia</th>
                        <th>Estado</th>
                        <th>Fecha</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($ultimasPlantas as $planta): ?>
                    <tr>
                        <td>
                            <img src="uploads/<?php echo htmlspecialchars($planta['image_path']); ?>"
                                 alt="<?php echo htmlspecialchars($planta['common_name']); ?>"
                                 class="plant-img">
                        </td>
                        <td><strong><?php echo htmlspecialchars($planta['common_name']); ?></strong></td>
                        <td><em><?php echo htmlspecialchars($planta['scientific_name']); ?></em></td>
                        <td><?php echo htmlspecialchars($planta['family']); ?></td>
                        <td>
                            <?php if ($planta['is_active']): ?>
                                <span class="badge badge-success">Activa</span>
                            <?php else: ?>
                                <span class="badge badge-danger">Inactiva</span>
                            <?php endif; ?>
                        </td>
                        <td><?php echo date('d/m/Y', strtotime($planta['created_at'])); ?></td>
                        <td>
                            <button class="btn btn-warning" style="padding: 5px 12px;">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-danger" style="padding: 5px 12px;">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </div>

        <!-- Plantas por familia -->
        <div class="content-section">
            <div class="section-header">
                <h2><i class="fas fa-chart-pie"></i> Distribución por Familia Botánica</h2>
            </div>

            <div class="chart-container">
                <?php
                $maxCount = max(array_column($plantasPorFamilia, 'count'));
                foreach ($plantasPorFamilia as $familia):
                    $height = ($familia['count'] / $maxCount) * 200;
                ?>
                    <div style="flex: 1; text-align: center;">
                        <div class="chart-bar" style="height: <?php echo $height; ?>px;">
                            <?php echo $familia['count']; ?>
                        </div>
                        <div class="chart-label"><?php echo htmlspecialchars($familia['family']); ?></div>
                    </div>
                <?php endforeach; ?>
            </div>
        </div>
    </div>
</body>
</html>

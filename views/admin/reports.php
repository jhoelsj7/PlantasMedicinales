<?php
/**
 * Reportes Analiticos - Vista principal
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Plant.php';
require_once __DIR__ . '/../../models/User.php';
require_once __DIR__ . '/../../models/Prediction.php';

$plantModel = new Plant();
$userModel = new User();
$predictionModel = new Prediction();

$plantStats = $plantModel->getStatistics();
$predictionStats = $predictionModel->getStatistics();
$totalUsers = $userModel->getCount();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reportes Analiticos</title>
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
        .content-section { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 25px; }
        .btn { padding: 12px 24px; border: none; border-radius: 8px; cursor: pointer; font-size: 0.95em; transition: all 0.3s; text-decoration: none; display: inline-flex; align-items: center; gap: 10px; }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }
        .btn-danger { background: #e74c3c; color: white; }
        .btn-success { background: #2ecc71; color: white; }
        .btn-warning { background: #f39c12; color: white; }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.2); }
        .report-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 25px; }
        .report-card { background: white; border-radius: 15px; padding: 25px; box-shadow: 0 2px 15px rgba(0,0,0,0.08); border: 1px solid #e9ecef; transition: all 0.3s; }
        .report-card:hover { transform: translateY(-5px); box-shadow: 0 10px 30px rgba(0,0,0,0.15); }
        .report-card h3 { margin-bottom: 15px; color: #2c3e50; display: flex; align-items: center; gap: 10px; }
        .report-card p { color: #6c757d; margin-bottom: 20px; font-size: 0.9em; line-height: 1.6; }
        .report-card .icon { width: 50px; height: 50px; border-radius: 12px; display: flex; align-items: center; justify-content: center; font-size: 1.5em; margin-bottom: 15px; }
        .icon-plants { background: linear-gradient(135deg, #2ecc71, #27ae60); color: white; }
        .icon-users { background: linear-gradient(135deg, #3498db, #2980b9); color: white; }
        .icon-predictions { background: linear-gradient(135deg, #9b59b6, #8e44ad); color: white; }
        .icon-summary { background: linear-gradient(135deg, #e74c3c, #c0392b); color: white; }
        .stats-preview { display: flex; gap: 20px; margin-bottom: 20px; flex-wrap: wrap; }
        .stat-mini { background: #f8f9fa; padding: 10px 15px; border-radius: 8px; }
        .stat-mini strong { display: block; font-size: 1.2em; color: #2c3e50; }
        .stat-mini span { font-size: 0.8em; color: #6c757d; }
        .quick-stats { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 30px; }
        .quick-stat { background: white; padding: 20px; border-radius: 12px; text-align: center; box-shadow: 0 2px 10px rgba(0,0,0,0.08); }
        .quick-stat h4 { font-size: 2em; margin-bottom: 5px; background: linear-gradient(135deg, #667eea, #764ba2); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }
        .quick-stat p { color: #6c757d; font-size: 0.9em; }
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
        <a href="predictions.php" class="nav-item"><i class="fas fa-history"></i><span>Predicciones</span></a>
        <a href="reports.php" class="nav-item active"><i class="fas fa-file-pdf"></i><span>Reportes</span></a>
        <a href="logout.php" class="nav-item" style="margin-top: auto; border-top: 2px solid rgba(255,255,255,0.2); padding-top: 15px;">
            <i class="fas fa-sign-out-alt"></i>
            <span>Cerrar Sesion</span>
        </a>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="header">
            <h1><i class="fas fa-file-pdf"></i> Reportes Analiticos</h1>
            <p style="color: #6c757d; margin-top: 5px;">Genera reportes PDF con informacion detallada del sistema</p>
        </div>

        <!-- Quick Stats -->
        <div class="quick-stats">
            <div class="quick-stat">
                <h4><?php echo $plantStats['total_plants']; ?></h4>
                <p><i class="fas fa-seedling"></i> Plantas</p>
            </div>
            <div class="quick-stat">
                <h4><?php echo $totalUsers; ?></h4>
                <p><i class="fas fa-users"></i> Usuarios</p>
            </div>
            <div class="quick-stat">
                <h4><?php echo $predictionStats['total']; ?></h4>
                <p><i class="fas fa-camera"></i> Predicciones</p>
            </div>
            <div class="quick-stat">
                <h4><?php echo count($plantStats['by_family']); ?></h4>
                <p><i class="fas fa-sitemap"></i> Familias</p>
            </div>
        </div>

        <!-- Report Cards -->
        <div class="report-grid">
            <!-- Reporte de Plantas -->
            <div class="report-card">
                <div class="icon icon-plants"><i class="fas fa-seedling"></i></div>
                <h3>Catalogo de Plantas</h3>
                <p>Reporte completo del catalogo de plantas medicinales incluyendo nombre cientifico, familia, usos medicinales y preparacion.</p>
                <div class="stats-preview">
                    <div class="stat-mini">
                        <strong><?php echo $plantStats['total_plants']; ?></strong>
                        <span>Total plantas</span>
                    </div>
                    <div class="stat-mini">
                        <strong><?php echo count($plantStats['by_family']); ?></strong>
                        <span>Familias</span>
                    </div>
                </div>
                <div style="display: flex; gap: 10px;">
                    <a href="generate_pdf.php?report=plants" class="btn btn-danger" target="_blank">
                        <i class="fas fa-file-pdf"></i> Generar PDF
                    </a>
                    <a href="export.php?type=plants&format=csv" class="btn btn-success">
                        <i class="fas fa-file-csv"></i> CSV
                    </a>
                </div>
            </div>

            <!-- Reporte de Usuarios -->
            <div class="report-card">
                <div class="icon icon-users"><i class="fas fa-users"></i></div>
                <h3>Reporte de Usuarios</h3>
                <p>Lista de todos los usuarios registrados con fechas de creacion y ultimo acceso al sistema.</p>
                <div class="stats-preview">
                    <div class="stat-mini">
                        <strong><?php echo $totalUsers; ?></strong>
                        <span>Usuarios registrados</span>
                    </div>
                </div>
                <div style="display: flex; gap: 10px;">
                    <a href="generate_pdf.php?report=users" class="btn btn-danger" target="_blank">
                        <i class="fas fa-file-pdf"></i> Generar PDF
                    </a>
                    <a href="export.php?type=users&format=csv" class="btn btn-success">
                        <i class="fas fa-file-csv"></i> CSV
                    </a>
                </div>
            </div>

            <!-- Reporte de Predicciones -->
            <div class="report-card">
                <div class="icon icon-predictions"><i class="fas fa-brain"></i></div>
                <h3>Historial de Predicciones</h3>
                <p>Reporte del historial de predicciones realizadas por la app movil, incluyendo plantas identificadas y nivel de confianza.</p>
                <div class="stats-preview">
                    <div class="stat-mini">
                        <strong><?php echo $predictionStats['total']; ?></strong>
                        <span>Total predicciones</span>
                    </div>
                    <div class="stat-mini">
                        <strong><?php echo $predictionStats['avg_confidence']; ?>%</strong>
                        <span>Confianza promedio</span>
                    </div>
                </div>
                <div style="display: flex; gap: 10px;">
                    <a href="generate_pdf.php?report=predictions" class="btn btn-danger" target="_blank">
                        <i class="fas fa-file-pdf"></i> Generar PDF
                    </a>
                    <a href="export.php?type=predictions&format=csv" class="btn btn-success">
                        <i class="fas fa-file-csv"></i> CSV
                    </a>
                </div>
            </div>

            <!-- Reporte Resumen General -->
            <div class="report-card">
                <div class="icon icon-summary"><i class="fas fa-chart-pie"></i></div>
                <h3>Resumen Ejecutivo</h3>
                <p>Reporte consolidado con estadisticas generales del sistema, distribucion por familias y metricas de uso.</p>
                <div class="stats-preview">
                    <div class="stat-mini">
                        <strong><?php echo $predictionStats['today']; ?></strong>
                        <span>Predicciones hoy</span>
                    </div>
                </div>
                <a href="generate_pdf.php?report=summary" class="btn btn-danger" target="_blank">
                    <i class="fas fa-file-pdf"></i> Generar PDF
                </a>
            </div>
        </div>

        <!-- Exportacion Masiva -->
        <div class="content-section">
            <h2 style="margin-bottom: 20px;"><i class="fas fa-download"></i> Exportacion Masiva</h2>
            <p style="color: #6c757d; margin-bottom: 20px;">Descarga todos los datos del sistema en diferentes formatos</p>
            <div style="display: flex; gap: 15px; flex-wrap: wrap;">
                <a href="export.php?type=plants&format=excel" class="btn btn-success">
                    <i class="fas fa-file-excel"></i> Plantas (Excel)
                </a>
                <a href="export.php?type=users&format=excel" class="btn btn-success">
                    <i class="fas fa-file-excel"></i> Usuarios (Excel)
                </a>
                <a href="export.php?type=predictions&format=excel" class="btn btn-success">
                    <i class="fas fa-file-excel"></i> Predicciones (Excel)
                </a>
            </div>
        </div>
    </div>
</body>
</html>

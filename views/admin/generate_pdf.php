<?php
/**
 * Generador de Reportes PDF
 * Genera HTML optimizado para imprimir como PDF desde el navegador
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Plant.php';
require_once __DIR__ . '/../../models/User.php';
require_once __DIR__ . '/../../models/Prediction.php';

$report = $_GET['report'] ?? 'summary';

$plantModel = new Plant();
$userModel = new User();
$predictionModel = new Prediction();

// Obtener datos segun el tipo de reporte
$plants = $plantModel->getAll();
$plantStats = $plantModel->getStatistics();
$users = $userModel->getAll();
$predictions = $predictionModel->getAll(500, 0);
$predictionStats = $predictionModel->getStatistics();

$fecha = date('d/m/Y H:i');
$fechaArchivo = date('Y-m-d');
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte - <?php echo ucfirst($report); ?> - <?php echo $fechaArchivo; ?></title>
    <style>
        @media print {
            body { margin: 0; padding: 20px; }
            .no-print { display: none !important; }
            .page-break { page-break-before: always; }
            table { page-break-inside: auto; }
            tr { page-break-inside: avoid; page-break-after: auto; }
        }

        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; font-size: 12px; line-height: 1.4; color: #333; padding: 20px; background: white; }

        .header-report { text-align: center; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 3px solid #667eea; }
        .header-report h1 { color: #667eea; font-size: 24px; margin-bottom: 5px; }
        .header-report p { color: #666; font-size: 12px; }

        .summary-box { background: #f8f9fa; padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        .summary-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 15px; margin-bottom: 20px; }
        .summary-item { background: white; padding: 15px; border-radius: 8px; text-align: center; border: 1px solid #e9ecef; }
        .summary-item h3 { font-size: 24px; color: #667eea; margin-bottom: 5px; }
        .summary-item p { color: #666; font-size: 11px; }

        h2 { color: #2c3e50; font-size: 16px; margin: 25px 0 15px 0; padding-bottom: 8px; border-bottom: 2px solid #667eea; }
        h3 { color: #34495e; font-size: 14px; margin: 20px 0 10px 0; }

        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; font-size: 11px; }
        th { background: #667eea; color: white; padding: 10px 8px; text-align: left; font-weight: 600; }
        td { padding: 8px; border-bottom: 1px solid #e9ecef; }
        tr:nth-child(even) { background: #f8f9fa; }
        tr:hover { background: #e9ecef; }

        .badge { padding: 3px 8px; border-radius: 12px; font-size: 10px; font-weight: 600; }
        .badge-success { background: #d4edda; color: #155724; }
        .badge-warning { background: #fff3cd; color: #856404; }
        .badge-danger { background: #f8d7da; color: #721c24; }

        .footer-report { margin-top: 30px; padding-top: 15px; border-top: 1px solid #e9ecef; text-align: center; color: #666; font-size: 10px; }

        .btn-print { position: fixed; top: 20px; right: 20px; padding: 12px 24px; background: #667eea; color: white; border: none; border-radius: 8px; cursor: pointer; font-size: 14px; box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4); }
        .btn-print:hover { background: #5a6fd6; }

        .plant-card { border: 1px solid #e9ecef; border-radius: 8px; padding: 15px; margin-bottom: 15px; background: #fafafa; }
        .plant-card h4 { color: #667eea; margin-bottom: 5px; }
        .plant-card .scientific { font-style: italic; color: #666; font-size: 11px; margin-bottom: 10px; }
        .plant-info { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; }
        .plant-info div { font-size: 11px; }
        .plant-info strong { color: #2c3e50; }

        .distribution-table { width: 50%; margin: 0 auto; }
    </style>
</head>
<body>

<!-- Boton Imprimir -->
<button class="btn-print no-print" onclick="window.print()">
    <i class="fas fa-print"></i> Imprimir / Guardar PDF
</button>

<!-- Header del Reporte -->
<div class="header-report">
    <h1>Sistema de Plantas Medicinales</h1>
    <p>
        <?php
        switch ($report) {
            case 'plants': echo 'Reporte: Catalogo de Plantas Medicinales'; break;
            case 'users': echo 'Reporte: Usuarios Registrados'; break;
            case 'predictions': echo 'Reporte: Historial de Predicciones'; break;
            default: echo 'Reporte: Resumen Ejecutivo'; break;
        }
        ?>
    </p>
    <p>Generado el: <?php echo $fecha; ?></p>
</div>

<?php if ($report === 'summary' || $report === 'plants'): ?>
<!-- Estadisticas Generales -->
<div class="summary-grid">
    <div class="summary-item">
        <h3><?php echo $plantStats['total_plants']; ?></h3>
        <p>Total Plantas</p>
    </div>
    <div class="summary-item">
        <h3><?php echo count($users); ?></h3>
        <p>Usuarios</p>
    </div>
    <div class="summary-item">
        <h3><?php echo $predictionStats['total']; ?></h3>
        <p>Predicciones</p>
    </div>
    <div class="summary-item">
        <h3><?php echo count($plantStats['by_family']); ?></h3>
        <p>Familias Botanicas</p>
    </div>
</div>
<?php endif; ?>

<?php if ($report === 'plants' || $report === 'summary'): ?>
<!-- Catalogo de Plantas -->
<h2>Catalogo de Plantas Medicinales</h2>

<?php if ($report === 'plants'): ?>
<!-- Vista detallada para reporte de plantas -->
<?php foreach ($plants as $plant): ?>
<div class="plant-card">
    <h4><?php echo htmlspecialchars($plant['common_name']); ?></h4>
    <p class="scientific"><?php echo htmlspecialchars($plant['scientific_name']); ?> - <?php echo htmlspecialchars($plant['family']); ?></p>
    <div class="plant-info">
        <div>
            <strong>Descripcion:</strong><br>
            <?php echo htmlspecialchars(substr($plant['description'], 0, 200)); ?>...
        </div>
        <div>
            <strong>Usos Medicinales:</strong><br>
            <?php echo htmlspecialchars(substr($plant['medicinal_uses'], 0, 200)); ?>...
        </div>
    </div>
    <div class="plant-info" style="margin-top: 10px;">
        <div>
            <strong>Preparacion:</strong><br>
            <?php echo htmlspecialchars(substr($plant['preparation'], 0, 150)); ?>...
        </div>
        <div>
            <strong>Precauciones:</strong><br>
            <?php echo htmlspecialchars(substr($plant['precautions'], 0, 150)); ?>...
        </div>
    </div>
</div>
<?php endforeach; ?>

<?php else: ?>
<!-- Vista tabla para resumen -->
<table>
    <thead>
        <tr>
            <th>Nombre Comun</th>
            <th>Nombre Cientifico</th>
            <th>Familia</th>
            <th>Estado</th>
        </tr>
    </thead>
    <tbody>
        <?php foreach ($plants as $plant): ?>
        <tr>
            <td><strong><?php echo htmlspecialchars($plant['common_name']); ?></strong></td>
            <td><em><?php echo htmlspecialchars($plant['scientific_name']); ?></em></td>
            <td><?php echo htmlspecialchars($plant['family']); ?></td>
            <td><span class="badge badge-success">Activa</span></td>
        </tr>
        <?php endforeach; ?>
    </tbody>
</table>
<?php endif; ?>

<!-- Distribucion por Familia -->
<h3>Distribucion por Familia Botanica</h3>
<table class="distribution-table">
    <thead>
        <tr>
            <th>Familia</th>
            <th>Cantidad</th>
            <th>Porcentaje</th>
        </tr>
    </thead>
    <tbody>
        <?php foreach ($plantStats['by_family'] as $family): ?>
        <tr>
            <td><?php echo htmlspecialchars($family['family']); ?></td>
            <td><?php echo $family['count']; ?></td>
            <td><?php echo round(($family['count'] / $plantStats['total_plants']) * 100, 1); ?>%</td>
        </tr>
        <?php endforeach; ?>
    </tbody>
</table>
<?php endif; ?>

<?php if ($report === 'users' || $report === 'summary'): ?>
<!-- Usuarios -->
<?php if ($report === 'users'): ?>
<h2>Usuarios Registrados</h2>
<?php else: ?>
<div class="page-break"></div>
<h2>Usuarios del Sistema</h2>
<?php endif; ?>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Usuario</th>
            <th>Email</th>
            <th>Nombre Completo</th>
            <th>Fecha Registro</th>
            <th>Ultimo Acceso</th>
        </tr>
    </thead>
    <tbody>
        <?php foreach ($users as $user): ?>
        <tr>
            <td><?php echo $user['id']; ?></td>
            <td><strong><?php echo htmlspecialchars($user['username']); ?></strong></td>
            <td><?php echo htmlspecialchars($user['email']); ?></td>
            <td><?php echo htmlspecialchars($user['full_name']); ?></td>
            <td><?php echo date('d/m/Y', strtotime($user['created_at'])); ?></td>
            <td><?php echo $user['last_login'] ? date('d/m/Y H:i', strtotime($user['last_login'])) : 'Nunca'; ?></td>
        </tr>
        <?php endforeach; ?>
    </tbody>
</table>
<?php endif; ?>

<?php if ($report === 'predictions' || $report === 'summary'): ?>
<!-- Predicciones -->
<?php if ($report === 'predictions'): ?>
<h2>Historial de Predicciones</h2>

<div class="summary-grid">
    <div class="summary-item">
        <h3><?php echo $predictionStats['total']; ?></h3>
        <p>Total Predicciones</p>
    </div>
    <div class="summary-item">
        <h3><?php echo $predictionStats['today']; ?></h3>
        <p>Hoy</p>
    </div>
    <div class="summary-item">
        <h3><?php echo $predictionStats['avg_confidence']; ?>%</h3>
        <p>Confianza Promedio</p>
    </div>
    <div class="summary-item">
        <h3><?php echo count($predictionStats['by_plant']); ?></h3>
        <p>Plantas Identificadas</p>
    </div>
</div>

<?php if (!empty($predictionStats['by_plant'])): ?>
<h3>Top Plantas Mas Identificadas</h3>
<table class="distribution-table">
    <thead>
        <tr>
            <th>#</th>
            <th>Planta</th>
            <th>Identificaciones</th>
        </tr>
    </thead>
    <tbody>
        <?php foreach ($predictionStats['by_plant'] as $index => $plant): ?>
        <tr>
            <td><?php echo $index + 1; ?></td>
            <td><?php echo htmlspecialchars($plant['common_name']); ?></td>
            <td><?php echo $plant['count']; ?></td>
        </tr>
        <?php endforeach; ?>
    </tbody>
</table>
<?php endif; ?>
<?php else: ?>
<div class="page-break"></div>
<h2>Resumen de Predicciones</h2>
<div class="summary-box">
    <p><strong>Total de predicciones:</strong> <?php echo $predictionStats['total']; ?></p>
    <p><strong>Predicciones hoy:</strong> <?php echo $predictionStats['today']; ?></p>
    <p><strong>Confianza promedio:</strong> <?php echo $predictionStats['avg_confidence']; ?>%</p>
</div>
<?php endif; ?>

<?php if ($report === 'predictions' && !empty($predictions)): ?>
<h3>Detalle de Predicciones</h3>
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
            <td><?php echo htmlspecialchars($pred['username'] ?? 'Anonimo'); ?></td>
            <td><?php echo htmlspecialchars($pred['plant_name'] ?? 'No identificada'); ?></td>
            <td>
                <?php
                $conf = $pred['confidence'] ?? 0;
                $badgeClass = $conf >= 80 ? 'badge-success' : ($conf >= 50 ? 'badge-warning' : 'badge-danger');
                ?>
                <span class="badge <?php echo $badgeClass; ?>"><?php echo number_format($conf, 1); ?>%</span>
            </td>
            <td><?php echo date('d/m/Y H:i', strtotime($pred['created_at'])); ?></td>
        </tr>
        <?php endforeach; ?>
    </tbody>
</table>
<?php endif; ?>
<?php endif; ?>

<!-- Footer -->
<div class="footer-report">
    <p>Sistema de Plantas Medicinales - Reporte generado automaticamente</p>
    <p>Fecha de generacion: <?php echo $fecha; ?></p>
</div>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</body>
</html>

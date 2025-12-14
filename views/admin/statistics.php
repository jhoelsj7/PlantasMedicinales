<?php
// Estadísticas
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Plant.php';
require_once __DIR__ . '/../../models/User.php';
$plantModel = new Plant();
$userModel = new User();
$stats = $plantModel->getStatistics();
$totalPlantas = $stats['total_plants'];
$totalUsuarios = $userModel->getCount();
$familias = $stats['by_family'];
$recientes = $stats['recent_plants'];
?>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"><title>Estadísticas</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>* { margin: 0; padding: 0; box-sizing: border-box; } body { font-family: Arial; background: #f5f6fa; }
.sidebar { position: fixed; left: 0; top: 0; height: 100vh; width: 260px; background: linear-gradient(135deg, #667eea, #764ba2); padding: 20px; color: white; overflow-y: auto; }
.sidebar h2 { text-align: center; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 2px solid rgba(255,255,255,0.2); }
.nav-item { padding: 15px 20px; margin: 10px 0; border-radius: 10px; display: flex; align-items: center; gap: 15px; color: white; text-decoration: none; transition: all 0.3s; }
.nav-item:hover, .nav-item.active { background: rgba(255,255,255,0.2); transform: translateX(5px); }
.main-content { margin-left: 260px; padding: 30px; }
.header { background: white; padding: 20px 30px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 30px; }
.content-section { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 25px; }
.stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 25px; margin-bottom: 30px; }
.stat-card { background: white; padding: 25px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); position: relative; overflow: hidden; }
.stat-card::before { content: ''; position: absolute; top: 0; left: 0; width: 4px; height: 100%; background: #667eea; }
.stat-value { font-size: 2.5em; font-weight: bold; color: #2c3e50; margin: 10px 0; }
.stat-label { color: #7f8c8d; font-size: 0.9em; }
table { width: 100%; border-collapse: collapse; }
th { background: #f8f9fa; padding: 15px; text-align: left; font-weight: 600; border-bottom: 2px solid #e9ecef; }
td { padding: 15px; border-bottom: 1px solid #f8f9fa; }
</style></head><body>
<div class="sidebar">
<h2><i class="fas fa-leaf"></i> Plantas Admin</h2>
<a href="index.php" class="nav-item"><i class="fas fa-home"></i><span>Dashboard</span></a>
<a href="manage_plants.php" class="nav-item"><i class="fas fa-seedling"></i><span>Gestionar Plantas</span></a>
<a href="manage_users.php" class="nav-item"><i class="fas fa-users"></i><span>Usuarios</span></a>
<a href="upload_images.php" class="nav-item"><i class="fas fa-images"></i><span>Imágenes</span></a>
<a href="statistics.php" class="nav-item active"><i class="fas fa-chart-bar"></i><span>Estadisticas</span></a>
<a href="predictions.php" class="nav-item"><i class="fas fa-history"></i><span>Predicciones</span></a>
<a href="reports.php" class="nav-item"><i class="fas fa-file-pdf"></i><span>Reportes</span></a>
<a href="logout.php" class="nav-item" style="margin-top: auto; border-top: 2px solid rgba(255,255,255,0.2); padding-top: 15px;">
    <i class="fas fa-sign-out-alt"></i>
    <span>Cerrar Sesion</span>
</a>
</div>
<div class="main-content">
<div class="header"><h1><i class="fas fa-chart-bar"></i> Estadísticas del Sistema</h1></div>
<div class="stats-grid">
<div class="stat-card"><div class="stat-label">Total de Plantas</div><div class="stat-value"><?php echo $totalPlantas; ?></div></div>
<div class="stat-card"><div class="stat-label">Total de Usuarios</div><div class="stat-value"><?php echo $totalUsuarios; ?></div></div>
<div class="stat-card"><div class="stat-label">Familias Botánicas</div><div class="stat-value"><?php echo count($familias); ?></div></div>
<div class="stat-card"><div class="stat-label">Modelo IA Precisión</div><div class="stat-value">96%</div></div>
</div>
<div class="content-section">
<h2>Distribución por Familia Botánica</h2>
<table><thead><tr><th>Familia</th><th>Cantidad de Plantas</th><th>Porcentaje</th></tr></thead>
<tbody>
<?php foreach ($familias as $fam): $percent = round(($fam['count'] / $totalPlantas) * 100, 1); ?>
<tr><td><strong><?php echo htmlspecialchars($fam['family']); ?></strong></td><td><?php echo $fam['count']; ?></td><td><?php echo $percent; ?>%</td></tr>
<?php endforeach; ?>
</tbody></table>
</div>
<div class="content-section">
<h2>Últimas 10 Plantas Agregadas</h2>
<table><thead><tr><th>Nombre</th><th>Familia</th><th>Fecha</th></tr></thead>
<tbody>
<?php foreach ($recientes as $plant): ?>
<tr><td><strong><?php echo htmlspecialchars($plant['common_name']); ?></strong></td><td><?php echo htmlspecialchars($plant['family']); ?></td><td><?php echo date('d/m/Y H:i', strtotime($plant['created_at'])); ?></td></tr>
<?php endforeach; ?>
</tbody></table>
</div>
</div>
</body></html>

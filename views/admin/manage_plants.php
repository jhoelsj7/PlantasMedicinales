<?php
/**
 * Gestionar Plantas - CRUD Completo
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Plant.php';
$plantModel = new Plant();

$message = '';
$messageType = '';

// Procesar acciones (Agregar, Editar, Eliminar)
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // AGREGAR PLANTA
    if (isset($_POST['action']) && $_POST['action'] === 'add') {
        try {
            if ($plantModel->create($_POST)) {
                $message = "✅ Planta agregada exitosamente";
                $messageType = "success";
            }
        } catch (Exception $e) {
            $message = "❌ Error: " . $e->getMessage();
            $messageType = "error";
        }
    }

    // EDITAR PLANTA
    if (isset($_POST['action']) && $_POST['action'] === 'edit') {
        try {
            if ($plantModel->update($_POST['plant_id'], $_POST)) {
                $message = "✅ Planta actualizada exitosamente";
                $messageType = "success";
            }
        } catch (Exception $e) {
            $message = "❌ Error: " . $e->getMessage();
            $messageType = "error";
        }
    }

    // ELIMINAR PLANTA
    if (isset($_POST['action']) && $_POST['action'] === 'delete') {
        try {
            if ($plantModel->delete($_POST['plant_id'])) {
                $message = "✅ Planta eliminada exitosamente";
                $messageType = "success";
            }
        } catch (Exception $e) {
            $message = "❌ Error: " . $e->getMessage();
            $messageType = "error";
        }
    }
}

// Obtener todas las plantas
$search = $_GET['search'] ?? '';
if ($search) {
    $result = $plantModel->search($search);
    $plants = $result['data'];
} else {
    $plants = $plantModel->getAll();
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Plantas</title>
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
        .header { background: white; padding: 20px 30px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 30px; display: flex; justify-content: space-between; align-items: center; }
        .content-section { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); margin-bottom: 25px; }
        .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-size: 0.95em; transition: all 0.3s; text-decoration: none; display: inline-flex; align-items: center; gap: 8px; }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }
        .btn-success { background: #2ecc71; color: white; }
        .btn-danger { background: #e74c3c; color: white; }
        .btn-warning { background: #f39c12; color: white; }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.2); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th { background: #f8f9fa; padding: 15px; text-align: left; font-weight: 600; color: #2c3e50; border-bottom: 2px solid #e9ecef; }
        td { padding: 15px; border-bottom: 1px solid #f8f9fa; }
        tr:hover { background: #f8f9fa; }
        .plant-img { width: 50px; height: 50px; border-radius: 8px; object-fit: cover; }
        .modal { display: none; position: fixed; z-index: 9999; left: 0; top: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); }
        .modal-content { background: white; margin: 3% auto; padding: 30px; border-radius: 15px; width: 80%; max-width: 700px; max-height: 85vh; overflow-y: auto; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; font-weight: 600; color: #2c3e50; }
        .form-group input, .form-group textarea, .form-group select { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; font-size: 0.95em; }
        .form-group textarea { min-height: 100px; resize: vertical; }
        .close { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        .close:hover { color: #000; }
        .search-box { margin-bottom: 20px; }
        .search-box input { padding: 12px; border: 1px solid #ddd; border-radius: 8px; width: 300px; }
        .alert { padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .badge { padding: 5px 12px; border-radius: 20px; font-size: 0.85em; font-weight: 600; }
        .badge-success { background: #d4edda; color: #155724; }
        .badge-danger { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <h2><i class="fas fa-leaf"></i> Plantas Admin</h2>
        <a href="index.php" class="nav-item"><i class="fas fa-home"></i><span>Dashboard</span></a>
        <a href="manage_plants.php" class="nav-item active"><i class="fas fa-seedling"></i><span>Gestionar Plantas</span></a>
        <a href="manage_users.php" class="nav-item"><i class="fas fa-users"></i><span>Usuarios</span></a>
        <a href="upload_images.php" class="nav-item"><i class="fas fa-images"></i><span>Imágenes</span></a>
        <a href="statistics.php" class="nav-item"><i class="fas fa-chart-bar"></i><span>Estadisticas</span></a>
        <a href="predictions.php" class="nav-item"><i class="fas fa-history"></i><span>Predicciones</span></a>
        <a href="reports.php" class="nav-item"><i class="fas fa-file-pdf"></i><span>Reportes</span></a>
        <a href="logout.php" class="nav-item" style="margin-top: auto; border-top: 2px solid rgba(255,255,255,0.2); padding-top: 15px;">
            <i class="fas fa-sign-out-alt"></i>
            <span>Cerrar Sesion</span>
        </a>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="header">
            <h1><i class="fas fa-seedling"></i> Gestionar Plantas</h1>
            <button class="btn btn-primary" onclick="openModal('add')"><i class="fas fa-plus"></i> Nueva Planta</button>
        </div>

        <?php if ($message): ?>
            <div class="alert alert-<?php echo $messageType; ?>"><?php echo $message; ?></div>
        <?php endif; ?>

        <div class="content-section">
            <div class="search-box">
                <form method="GET" style="display: flex; gap: 10px;">
                    <input type="text" name="search" placeholder="Buscar por nombre..." value="<?php echo htmlspecialchars($search); ?>">
                    <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Buscar</button>
                    <?php if ($search): ?>
                        <a href="manage_plants.php" class="btn btn-warning"><i class="fas fa-times"></i> Limpiar</a>
                    <?php endif; ?>
                </form>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Imagen</th>
                        <th>Nombre Común</th>
                        <th>Nombre Científico</th>
                        <th>Familia</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($plants as $plant): ?>
                    <tr>
                        <td><?php echo $plant['id']; ?></td>
                        <td><img src="../../public/uploads/<?php echo htmlspecialchars($plant['image_path']); ?>" class="plant-img" alt="<?php echo htmlspecialchars($plant['common_name']); ?>"></td>
                        <td><strong><?php echo htmlspecialchars($plant['common_name']); ?></strong></td>
                        <td><em><?php echo htmlspecialchars($plant['scientific_name']); ?></em></td>
                        <td><?php echo htmlspecialchars($plant['family']); ?></td>
                        <td><?php echo $plant['is_active'] ? '<span class="badge badge-success">Activa</span>' : '<span class="badge badge-danger">Inactiva</span>'; ?></td>
                        <td>
                            <button class="btn btn-warning" style="padding: 5px 12px;" onclick='editPlant(<?php echo json_encode($plant); ?>)'><i class="fas fa-edit"></i></button>
                            <button class="btn btn-danger" style="padding: 5px 12px;" onclick="deletePlant(<?php echo $plant['id']; ?>, '<?php echo htmlspecialchars($plant['common_name']); ?>')"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Modal Agregar/Editar -->
    <div id="plantModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2 id="modalTitle">Nueva Planta</h2>
            <form method="POST" id="plantForm">
                <input type="hidden" name="action" id="formAction" value="add">
                <input type="hidden" name="plant_id" id="plant_id">

                <div class="form-group">
                    <label>Nombre Común *</label>
                    <input type="text" name="common_name" id="common_name" required>
                </div>

                <div class="form-group">
                    <label>Nombre Científico *</label>
                    <input type="text" name="scientific_name" id="scientific_name" required>
                </div>

                <div class="form-group">
                    <label>Familia Botánica *</label>
                    <input type="text" name="family" id="family" required>
                </div>

                <div class="form-group">
                    <label>Descripción *</label>
                    <textarea name="description" id="description" required></textarea>
                </div>

                <div class="form-group">
                    <label>Usos Medicinales *</label>
                    <textarea name="medicinal_uses" id="medicinal_uses" required></textarea>
                </div>

                <div class="form-group">
                    <label>Preparación *</label>
                    <textarea name="preparation" id="preparation" required></textarea>
                </div>

                <div class="form-group">
                    <label>Precauciones *</label>
                    <textarea name="precautions" id="precautions" required></textarea>
                </div>

                <div class="form-group">
                    <label>Nombre de Imagen</label>
                    <input type="text" name="image_path" id="image_path" placeholder="ejemplo: manzanilla.jpg">
                </div>

                <div style="display: flex; gap: 10px; justify-content: flex-end;">
                    <button type="button" class="btn btn-danger" onclick="closeModal()">Cancelar</button>
                    <button type="submit" class="btn btn-success"><i class="fas fa-save"></i> Guardar</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openModal(action) {
            document.getElementById('plantModal').style.display = 'block';
            document.getElementById('formAction').value = action;
            if (action === 'add') {
                document.getElementById('modalTitle').textContent = 'Nueva Planta';
                document.getElementById('plantForm').reset();
            }
        }

        function closeModal() {
            document.getElementById('plantModal').style.display = 'none';
        }

        function editPlant(plant) {
            openModal('edit');
            document.getElementById('modalTitle').textContent = 'Editar Planta';
            document.getElementById('plant_id').value = plant.id;
            document.getElementById('common_name').value = plant.common_name;
            document.getElementById('scientific_name').value = plant.scientific_name;
            document.getElementById('family').value = plant.family;
            document.getElementById('description').value = plant.description;
            document.getElementById('medicinal_uses').value = plant.medicinal_uses;
            document.getElementById('preparation').value = plant.preparation;
            document.getElementById('precautions').value = plant.precautions;
            document.getElementById('image_path').value = plant.image_path;
        }

        function deletePlant(id, name) {
            if (confirm(`¿Estás seguro de eliminar "${name}"?`)) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.innerHTML = `<input type="hidden" name="action" value="delete"><input type="hidden" name="plant_id" value="${id}">`;
                document.body.appendChild(form);
                form.submit();
            }
        }

        window.onclick = function(event) {
            const modal = document.getElementById('plantModal');
            if (event.target == modal) closeModal();
        }
    </script>
</body>
</html>

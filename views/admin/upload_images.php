<?php
/**
 * Gestionar Imagenes - Mejorado
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Plant.php';
require_once __DIR__ . '/../../models/Database.php';

$uploadDir = __DIR__ . '/../../public/uploads/';
$message = '';
$messageType = 'success';

// Crear directorio si no existe
if (!is_dir($uploadDir)) {
    mkdir($uploadDir, 0755, true);
}

$plantModel = new Plant();
$db = Database::getInstance()->getConnection();

// Procesar SUBIDA de imagen
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['image']) && isset($_POST['action']) && $_POST['action'] === 'upload') {
    $file = $_FILES['image'];
    $allowed = ['jpg', 'jpeg', 'png'];
    $ext = strtolower(pathinfo($file['name'], PATHINFO_EXTENSION));

    if (in_array($ext, $allowed) && $file['size'] < 5*1024*1024) {
        $basename = preg_replace('/[^a-zA-Z0-9_-]/', '', $_POST['filename'] ?? uniqid('plant_'));
        $filename = $basename . '.' . $ext;

        if (move_uploaded_file($file['tmp_name'], $uploadDir . $filename)) {
            // Eliminar imagenes con mismo nombre pero diferente extension
            foreach (['jpg', 'jpeg', 'png'] as $oldExt) {
                $oldFile = $uploadDir . $basename . '.' . $oldExt;
                if (file_exists($oldFile) && $oldFile !== $uploadDir . $filename) {
                    unlink($oldFile);
                    // Actualizar BD si habia referencia
                    $stmt = $db->prepare("UPDATE plants SET image_path = ? WHERE image_path = ?");
                    $stmt->execute([$filename, $basename . '.' . $oldExt]);
                }
            }

            // Si se selecciono una planta, vincular
            if (!empty($_POST['plant_id'])) {
                $stmt = $db->prepare("UPDATE plants SET image_path = ? WHERE id = ?");
                $stmt->execute([$filename, $_POST['plant_id']]);
                $message = "Imagen '$filename' subida y vinculada a la planta";
            } else {
                $message = "Imagen '$filename' subida exitosamente";
            }
        } else {
            $message = "Error al subir la imagen";
            $messageType = 'error';
        }
    } else {
        $message = "Archivo no valido (solo JPG/PNG, max 5MB)";
        $messageType = 'error';
    }
}

// Procesar ELIMINAR imagen
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['action']) && $_POST['action'] === 'delete') {
    $imageToDelete = basename($_POST['image_name']);
    $filePath = $uploadDir . $imageToDelete;

    if (file_exists($filePath)) {
        // Verificar si esta vinculada a alguna planta
        $stmt = $db->prepare("SELECT id, common_name FROM plants WHERE image_path = ?");
        $stmt->execute([$imageToDelete]);
        $linkedPlant = $stmt->fetch();

        if ($linkedPlant && empty($_POST['force_delete'])) {
            $message = "La imagen esta vinculada a '{$linkedPlant['common_name']}'. Marca 'Forzar eliminacion' para eliminarla.";
            $messageType = 'error';
        } else {
            unlink($filePath);
            // Limpiar referencia en BD
            $stmt = $db->prepare("UPDATE plants SET image_path = NULL WHERE image_path = ?");
            $stmt->execute([$imageToDelete]);
            $message = "Imagen '$imageToDelete' eliminada";
        }
    } else {
        $message = "La imagen no existe";
        $messageType = 'error';
    }
}

// Procesar VINCULAR imagen a planta
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['action']) && $_POST['action'] === 'link') {
    $imageName = basename($_POST['image_name']);
    $plantId = (int)$_POST['plant_id'];

    if ($plantId > 0) {
        $stmt = $db->prepare("UPDATE plants SET image_path = ? WHERE id = ?");
        $stmt->execute([$imageName, $plantId]);
        $message = "Imagen vinculada exitosamente";
    }
}

// Obtener imagenes y plantas (solo archivos, no carpetas)
$allItems = is_dir($uploadDir) ? array_diff(scandir($uploadDir), ['.', '..']) : [];
$images = array_filter($allItems, function($item) use ($uploadDir) {
    return is_file($uploadDir . $item);
});
$plants = $plantModel->getAll();

// Crear mapa de imagenes vinculadas
$linkedImages = [];
$stmt = $db->query("SELECT id, common_name, image_path FROM plants WHERE image_path IS NOT NULL AND image_path != ''");
while ($row = $stmt->fetch()) {
    $linkedImages[$row['image_path']] = $row;
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Imagenes</title>
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
        .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-size: 0.95em; transition: all 0.3s; text-decoration: none; display: inline-flex; align-items: center; gap: 8px; }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }
        .btn-danger { background: #e74c3c; color: white; }
        .btn-success { background: #2ecc71; color: white; }
        .btn-warning { background: #f39c12; color: white; }
        .btn-sm { padding: 5px 10px; font-size: 0.85em; }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.2); }
        .alert { padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; font-weight: 600; color: #2c3e50; }
        .form-group input, .form-group select { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; font-size: 0.95em; }
        .form-row { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 20px; }
        .gallery { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 20px; margin-top: 20px; }
        .gallery-item { border: 2px solid #e9ecef; border-radius: 12px; padding: 15px; text-align: center; transition: all 0.3s; position: relative; }
        .gallery-item:hover { border-color: #667eea; box-shadow: 0 5px 20px rgba(0,0,0,0.1); }
        .gallery-item.linked { border-color: #2ecc71; background: #f8fff8; }
        .gallery-item img { width: 100%; height: 150px; object-fit: cover; border-radius: 8px; }
        .gallery-item .img-name { margin-top: 10px; font-size: 0.9em; font-weight: 600; word-break: break-all; }
        .gallery-item .img-size { font-size: 0.8em; color: #6c757d; }
        .gallery-item .img-actions { margin-top: 10px; display: flex; gap: 5px; justify-content: center; flex-wrap: wrap; }
        .badge { padding: 4px 10px; border-radius: 20px; font-size: 0.75em; font-weight: 600; }
        .badge-success { background: #d4edda; color: #155724; }
        .badge-secondary { background: #e9ecef; color: #6c757d; }
        .linked-info { font-size: 0.8em; color: #2ecc71; margin-top: 5px; }
        .stats-bar { display: flex; gap: 20px; margin-bottom: 20px; }
        .stat-item { background: #f8f9fa; padding: 15px 25px; border-radius: 10px; }
        .stat-item strong { font-size: 1.5em; color: #667eea; }
        .modal { display: none; position: fixed; z-index: 9999; left: 0; top: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); }
        .modal-content { background: white; margin: 10% auto; padding: 30px; border-radius: 15px; width: 90%; max-width: 400px; }
        .close { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        .preview-large { max-width: 100%; max-height: 300px; border-radius: 8px; }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <h2><i class="fas fa-leaf"></i> Plantas Admin</h2>
        <a href="index.php" class="nav-item"><i class="fas fa-home"></i><span>Dashboard</span></a>
        <a href="manage_plants.php" class="nav-item"><i class="fas fa-seedling"></i><span>Gestionar Plantas</span></a>
        <a href="manage_users.php" class="nav-item"><i class="fas fa-users"></i><span>Usuarios</span></a>
        <a href="upload_images.php" class="nav-item active"><i class="fas fa-images"></i><span>Imagenes</span></a>
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
            <h1><i class="fas fa-images"></i> Gestionar Imagenes</h1>
            <p style="color: #6c757d; margin-top: 5px;">Sube y administra las imagenes de las plantas</p>
        </div>

        <?php if ($message): ?>
            <div class="alert alert-<?php echo $messageType; ?>"><?php echo htmlspecialchars($message); ?></div>
        <?php endif; ?>

        <!-- Estadisticas -->
        <div class="stats-bar">
            <div class="stat-item">
                <strong><?php echo count($images); ?></strong>
                <span> imagenes totales</span>
            </div>
            <div class="stat-item">
                <strong><?php echo count($linkedImages); ?></strong>
                <span> vinculadas a plantas</span>
            </div>
            <div class="stat-item">
                <strong><?php echo count($images) - count($linkedImages); ?></strong>
                <span> sin vincular</span>
            </div>
        </div>

        <!-- Formulario de Subida -->
        <div class="content-section">
            <h2 style="margin-bottom: 20px;"><i class="fas fa-upload"></i> Subir Nueva Imagen</h2>
            <form method="POST" enctype="multipart/form-data">
                <input type="hidden" name="action" value="upload">
                <div class="form-row">
                    <div class="form-group">
                        <label><i class="fas fa-seedling"></i> Seleccionar Planta</label>
                        <select name="plant_id" id="plantSelect" required onchange="updateFilename()">
                            <option value="">-- Seleccionar planta --</option>
                            <?php
                            // Obtener todas las plantas (incluidas las sin imagen)
                            $stmtAll = $db->query("SELECT id, common_name, image_path FROM plants WHERE is_active = 1 ORDER BY common_name ASC");
                            $allPlants = $stmtAll->fetchAll();
                            foreach ($allPlants as $plant):
                                $hasImage = !empty($plant['image_path']) && file_exists($uploadDir . $plant['image_path']);
                                $icon = $hasImage ? '✓' : '○';
                            ?>
                            <option value="<?php echo $plant['id']; ?>"
                                    data-filename="<?php echo htmlspecialchars(strtolower(str_replace(' ', '_', $plant['common_name']))); ?>"
                                    <?php echo $hasImage ? 'style="color: #27ae60;"' : ''; ?>>
                                <?php echo $icon; ?> <?php echo htmlspecialchars($plant['common_name']); ?>
                                <?php echo $hasImage ? ' (tiene imagen)' : ' (sin imagen)'; ?>
                            </option>
                            <?php endforeach; ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label><i class="fas fa-file-image"></i> Seleccionar imagen</label>
                        <input type="file" name="image" accept=".jpg,.jpeg,.png" required id="imageInput">
                    </div>
                    <div class="form-group">
                        <label><i class="fas fa-tag"></i> Nombre de archivo (auto)</label>
                        <input type="text" name="filename" id="filenameInput" placeholder="Se genera automaticamente" readonly style="background: #f8f9fa;">
                    </div>
                </div>
                <div id="imagePreview" style="margin-bottom: 15px; display: none;">
                    <img id="previewImg" src="" alt="Preview" style="max-height: 150px; border-radius: 8px;">
                </div>
                <button type="submit" class="btn btn-primary"><i class="fas fa-cloud-upload-alt"></i> Subir Imagen</button>
                <small style="margin-left: 15px; color: #6c757d;">Formatos: JPG, PNG | Max: 5MB</small>
            </form>
        </div>

        <!-- Galeria de Imagenes -->
        <div class="content-section">
            <h2 style="margin-bottom: 10px;"><i class="fas fa-th"></i> Galeria de Imagenes (<?php echo count($images); ?>)</h2>

            <?php if (empty($images)): ?>
            <div style="text-align: center; padding: 50px; color: #6c757d;">
                <i class="fas fa-image" style="font-size: 4em; opacity: 0.3;"></i>
                <p style="margin-top: 15px;">No hay imagenes subidas</p>
            </div>
            <?php else: ?>
            <div class="gallery">
                <?php foreach ($images as $img):
                    $isLinked = isset($linkedImages[$img]);
                    $fileSize = filesize($uploadDir . $img);
                ?>
                <div class="gallery-item <?php echo $isLinked ? 'linked' : ''; ?>">
                    <?php if ($isLinked): ?>
                    <span class="badge badge-success" style="position: absolute; top: 10px; right: 10px;">
                        <i class="fas fa-link"></i> Vinculada
                    </span>
                    <?php endif; ?>

                    <img src="../../public/uploads/<?php echo htmlspecialchars($img); ?>"
                         alt="<?php echo htmlspecialchars($img); ?>"
                         onclick="showPreview('<?php echo htmlspecialchars($img); ?>')"
                         style="cursor: pointer;">

                    <p class="img-name"><?php echo htmlspecialchars($img); ?></p>
                    <p class="img-size"><?php echo number_format($fileSize / 1024, 2); ?> KB</p>

                    <?php if ($isLinked): ?>
                    <p class="linked-info">
                        <i class="fas fa-seedling"></i> <?php echo htmlspecialchars($linkedImages[$img]['common_name']); ?>
                    </p>
                    <?php endif; ?>

                    <div class="img-actions">
                        <?php if (!$isLinked): ?>
                        <button class="btn btn-success btn-sm" onclick="showLinkModal('<?php echo htmlspecialchars($img); ?>')">
                            <i class="fas fa-link"></i> Vincular
                        </button>
                        <?php endif; ?>
                        <button class="btn btn-danger btn-sm" onclick="confirmDelete('<?php echo htmlspecialchars($img); ?>', <?php echo $isLinked ? 'true' : 'false'; ?>)">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                </div>
                <?php endforeach; ?>
            </div>
            <?php endif; ?>
        </div>
    </div>

    <!-- Modal Vincular -->
    <div id="linkModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeLinkModal()">&times;</span>
            <h2 style="margin-bottom: 20px;"><i class="fas fa-link"></i> Vincular Imagen</h2>
            <form method="POST" id="linkForm">
                <input type="hidden" name="action" value="link">
                <input type="hidden" name="image_name" id="linkImageName">
                <p style="margin-bottom: 15px;">Imagen: <strong id="linkImageLabel"></strong></p>
                <div class="form-group">
                    <label>Seleccionar Planta</label>
                    <select name="plant_id" required>
                        <option value="">-- Seleccionar --</option>
                        <?php foreach ($plants as $plant): ?>
                        <option value="<?php echo $plant['id']; ?>"><?php echo htmlspecialchars($plant['common_name']); ?></option>
                        <?php endforeach; ?>
                    </select>
                </div>
                <button type="submit" class="btn btn-success"><i class="fas fa-check"></i> Vincular</button>
            </form>
        </div>
    </div>

    <!-- Modal Preview -->
    <div id="previewModal" class="modal" onclick="closePreviewModal()">
        <div class="modal-content" style="max-width: 600px; text-align: center;" onclick="event.stopPropagation()">
            <span class="close" onclick="closePreviewModal()">&times;</span>
            <img id="previewLargeImg" src="" class="preview-large">
            <p id="previewName" style="margin-top: 15px; font-weight: 600;"></p>
        </div>
    </div>

    <!-- Form oculto para eliminar -->
    <form id="deleteForm" method="POST" style="display: none;">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="image_name" id="deleteImageName">
        <input type="hidden" name="force_delete" id="forceDelete" value="">
    </form>

    <script>
        // Actualizar nombre de archivo al seleccionar planta
        function updateFilename() {
            const select = document.getElementById('plantSelect');
            const filenameInput = document.getElementById('filenameInput');
            const selectedOption = select.options[select.selectedIndex];

            if (selectedOption.value) {
                filenameInput.value = selectedOption.getAttribute('data-filename');
            } else {
                filenameInput.value = '';
            }
        }

        // Preview al seleccionar archivo
        document.getElementById('imageInput').addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('previewImg').src = e.target.result;
                    document.getElementById('imagePreview').style.display = 'block';
                }
                reader.readAsDataURL(file);
            }
        });

        // Modal vincular
        function showLinkModal(imageName) {
            document.getElementById('linkImageName').value = imageName;
            document.getElementById('linkImageLabel').textContent = imageName;
            document.getElementById('linkModal').style.display = 'block';
        }

        function closeLinkModal() {
            document.getElementById('linkModal').style.display = 'none';
        }

        // Modal preview
        function showPreview(imageName) {
            document.getElementById('previewLargeImg').src = '../../public/uploads/' + imageName;
            document.getElementById('previewName').textContent = imageName;
            document.getElementById('previewModal').style.display = 'block';
        }

        function closePreviewModal() {
            document.getElementById('previewModal').style.display = 'none';
        }

        // Eliminar
        function confirmDelete(imageName, isLinked) {
            let msg = '¿Eliminar la imagen "' + imageName + '"?';
            if (isLinked) {
                msg += '\n\nADVERTENCIA: Esta imagen esta vinculada a una planta.';
                if (confirm(msg)) {
                    document.getElementById('forceDelete').value = '1';
                    document.getElementById('deleteImageName').value = imageName;
                    document.getElementById('deleteForm').submit();
                }
            } else {
                if (confirm(msg)) {
                    document.getElementById('deleteImageName').value = imageName;
                    document.getElementById('deleteForm').submit();
                }
            }
        }

        // Cerrar modales con click fuera
        window.onclick = function(event) {
            if (event.target.classList.contains('modal')) {
                event.target.style.display = 'none';
            }
        }
    </script>
</body>
</html>

<?php
/**
 * Gestionar Usuarios - CRUD Completo
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/User.php';
$userModel = new User();

$message = '';
$messageType = '';

// Procesar acciones (Agregar, Editar, Eliminar)
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // AGREGAR USUARIO
    if (isset($_POST['action']) && $_POST['action'] === 'add') {
        try {
            if (empty($_POST['password'])) {
                throw new Exception("La contraseña es requerida");
            }
            if ($userModel->create($_POST)) {
                $message = "Usuario agregado exitosamente";
                $messageType = "success";
            }
        } catch (Exception $e) {
            $message = "Error: " . $e->getMessage();
            $messageType = "error";
        }
    }

    // EDITAR USUARIO
    if (isset($_POST['action']) && $_POST['action'] === 'edit') {
        try {
            if ($userModel->update($_POST['user_id'], $_POST)) {
                $message = "Usuario actualizado exitosamente";
                $messageType = "success";
            }
        } catch (Exception $e) {
            $message = "Error: " . $e->getMessage();
            $messageType = "error";
        }
    }

    // ELIMINAR USUARIO
    if (isset($_POST['action']) && $_POST['action'] === 'delete') {
        try {
            // No permitir eliminar el usuario actual
            if ($_POST['user_id'] == $_SESSION['user_id']) {
                throw new Exception("No puedes eliminar tu propio usuario");
            }
            if ($userModel->delete($_POST['user_id'])) {
                $message = "Usuario eliminado exitosamente";
                $messageType = "success";
            }
        } catch (Exception $e) {
            $message = "Error: " . $e->getMessage();
            $messageType = "error";
        }
    }
}

$users = $userModel->getAll();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Usuarios</title>
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
        .modal { display: none; position: fixed; z-index: 9999; left: 0; top: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); }
        .modal-content { background: white; margin: 5% auto; padding: 30px; border-radius: 15px; width: 90%; max-width: 500px; max-height: 85vh; overflow-y: auto; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; font-weight: 600; color: #2c3e50; }
        .form-group input { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; font-size: 0.95em; }
        .form-group input:focus { outline: none; border-color: #667eea; box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1); }
        .close { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        .close:hover { color: #000; }
        .alert { padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .badge { padding: 5px 12px; border-radius: 20px; font-size: 0.85em; font-weight: 600; }
        .badge-primary { background: #e3e8ff; color: #667eea; }
        .badge-success { background: #d4edda; color: #155724; }
        .password-hint { font-size: 0.85em; color: #6c757d; margin-top: 5px; }
        .user-avatar { width: 40px; height: 40px; border-radius: 50%; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <h2><i class="fas fa-leaf"></i> Plantas Admin</h2>
        <a href="index.php" class="nav-item"><i class="fas fa-home"></i><span>Dashboard</span></a>
        <a href="manage_plants.php" class="nav-item"><i class="fas fa-seedling"></i><span>Gestionar Plantas</span></a>
        <a href="manage_users.php" class="nav-item active"><i class="fas fa-users"></i><span>Usuarios</span></a>
        <a href="upload_images.php" class="nav-item"><i class="fas fa-images"></i><span>Imagenes</span></a>
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
            <h1><i class="fas fa-users"></i> Gestionar Usuarios</h1>
            <button class="btn btn-primary" onclick="openModal('add')"><i class="fas fa-plus"></i> Nuevo Usuario</button>
        </div>

        <?php if ($message): ?>
            <div class="alert alert-<?php echo $messageType; ?>"><?php echo htmlspecialchars($message); ?></div>
        <?php endif; ?>

        <div class="content-section">
            <h2 style="margin-bottom: 20px;">Usuarios Registrados (<?php echo count($users); ?>)</h2>
            <table>
                <thead>
                    <tr>
                        <th>Usuario</th>
                        <th>Email</th>
                        <th>Nombre Completo</th>
                        <th>Creado</th>
                        <th>Ultimo Login</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($users as $user): ?>
                    <tr>
                        <td>
                            <div style="display: flex; align-items: center; gap: 12px;">
                                <div class="user-avatar"><?php echo strtoupper(substr($user['username'], 0, 1)); ?></div>
                                <div>
                                    <strong><?php echo htmlspecialchars($user['username']); ?></strong>
                                    <?php if ($user['id'] == $_SESSION['user_id']): ?>
                                        <span class="badge badge-primary" style="margin-left: 5px;">Tu</span>
                                    <?php endif; ?>
                                </div>
                            </div>
                        </td>
                        <td><?php echo htmlspecialchars($user['email']); ?></td>
                        <td><?php echo htmlspecialchars($user['full_name']); ?></td>
                        <td><?php echo date('d/m/Y H:i', strtotime($user['created_at'])); ?></td>
                        <td>
                            <?php if ($user['last_login']): ?>
                                <span class="badge badge-success"><?php echo date('d/m/Y H:i', strtotime($user['last_login'])); ?></span>
                            <?php else: ?>
                                <span style="color: #6c757d;">Nunca</span>
                            <?php endif; ?>
                        </td>
                        <td>
                            <button class="btn btn-warning" style="padding: 5px 12px;" onclick='editUser(<?php echo json_encode($user); ?>)'><i class="fas fa-edit"></i></button>
                            <?php if ($user['id'] != $_SESSION['user_id']): ?>
                                <button class="btn btn-danger" style="padding: 5px 12px;" onclick="deleteUser(<?php echo $user['id']; ?>, '<?php echo htmlspecialchars($user['username']); ?>')"><i class="fas fa-trash"></i></button>
                            <?php endif; ?>
                        </td>
                    </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Modal Agregar/Editar -->
    <div id="userModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2 id="modalTitle" style="margin-bottom: 20px;"><i class="fas fa-user-plus"></i> Nuevo Usuario</h2>
            <form method="POST" id="userForm">
                <input type="hidden" name="action" id="formAction" value="add">
                <input type="hidden" name="user_id" id="user_id">

                <div class="form-group">
                    <label><i class="fas fa-user"></i> Nombre de Usuario *</label>
                    <input type="text" name="username" id="username" required minlength="3" maxlength="50" placeholder="Ej: juan_perez">
                </div>

                <div class="form-group">
                    <label><i class="fas fa-envelope"></i> Email *</label>
                    <input type="email" name="email" id="email" required placeholder="Ej: juan@email.com">
                </div>

                <div class="form-group">
                    <label><i class="fas fa-id-card"></i> Nombre Completo *</label>
                    <input type="text" name="full_name" id="full_name" required placeholder="Ej: Juan Perez">
                </div>

                <div class="form-group">
                    <label><i class="fas fa-lock"></i> Contrasena <span id="passwordRequired">*</span></label>
                    <input type="password" name="password" id="password" minlength="6" placeholder="Minimo 6 caracteres">
                    <p class="password-hint" id="passwordHint">La contrasena es requerida para nuevos usuarios</p>
                </div>

                <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 25px;">
                    <button type="button" class="btn btn-danger" onclick="closeModal()"><i class="fas fa-times"></i> Cancelar</button>
                    <button type="submit" class="btn btn-success"><i class="fas fa-save"></i> Guardar</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openModal(action) {
            document.getElementById('userModal').style.display = 'block';
            document.getElementById('formAction').value = action;
            if (action === 'add') {
                document.getElementById('modalTitle').innerHTML = '<i class="fas fa-user-plus"></i> Nuevo Usuario';
                document.getElementById('userForm').reset();
                document.getElementById('password').required = true;
                document.getElementById('passwordRequired').style.display = 'inline';
                document.getElementById('passwordHint').textContent = 'La contrasena es requerida para nuevos usuarios';
            }
        }

        function closeModal() {
            document.getElementById('userModal').style.display = 'none';
        }

        function editUser(user) {
            openModal('edit');
            document.getElementById('modalTitle').innerHTML = '<i class="fas fa-user-edit"></i> Editar Usuario';
            document.getElementById('user_id').value = user.id;
            document.getElementById('username').value = user.username;
            document.getElementById('email').value = user.email;
            document.getElementById('full_name').value = user.full_name;
            document.getElementById('password').value = '';
            document.getElementById('password').required = false;
            document.getElementById('passwordRequired').style.display = 'none';
            document.getElementById('passwordHint').textContent = 'Dejar vacio para mantener la contrasena actual';
        }

        function deleteUser(id, username) {
            if (confirm('¿Estas seguro de eliminar al usuario "' + username + '"?\n\nEsta accion no se puede deshacer.')) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.innerHTML = '<input type="hidden" name="action" value="delete"><input type="hidden" name="user_id" value="' + id + '">';
                document.body.appendChild(form);
                form.submit();
            }
        }

        window.onclick = function(event) {
            const modal = document.getElementById('userModal');
            if (event.target == modal) closeModal();
        }
    </script>
</body>
</html>

<?php
session_start();

// Si ya está autenticado, redirigir al dashboard
if (isset($_SESSION['admin_logged_in']) && $_SESSION['admin_logged_in'] === true) {
    header('Location: index.php');
    exit;
}

$error = '';

// Procesar login
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    require_once __DIR__ . '/../../models/User.php';

    $username = $_POST['username'] ?? '';
    $password = $_POST['password'] ?? '';

    if (!empty($username) && !empty($password)) {
        $userModel = new User();
        $user = $userModel->authenticate($username, $password);

        if ($user) {
            $_SESSION['admin_logged_in'] = true;
            $_SESSION['user_id'] = $user['id'];
            $_SESSION['username'] = $user['username'];
            $_SESSION['full_name'] = $user['full_name'];
            header('Location: index.php');
            exit;
        } else {
            $error = 'Usuario o contraseña incorrectos';
        }
    } else {
        $error = 'Por favor completa todos los campos';
    }
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Plantas Admin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            overflow: hidden;
            position: relative;
        }

        /* Video de fondo */
        .video-background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            z-index: -2;
        }

        /* Overlay oscuro sobre el video */
        .video-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.2);
            z-index: -1;
        }

        .login-container {
            background: linear-gradient(135deg, rgba(232, 228, 217, 0.95) 0%, rgba(255, 255, 255, 0.9) 100%);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            width: 100%;
            max-width: 400px;
            animation: slideIn 0.5s ease;
            border: 2px solid rgba(138, 154, 91, 0.3);
            backdrop-filter: blur(10px);
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .logo {
            text-align: center;
            margin-bottom: 30px;
        }

        .logo i {
            font-size: 4em;
            color: #8A9A5B;
            margin-bottom: 10px;
            filter: drop-shadow(0 2px 4px rgba(138, 154, 91, 0.3));
        }

        .logo h1 {
            color: #2D3436;
            font-size: 1.8em;
            margin-bottom: 5px;
            font-weight: 700;
            letter-spacing: -0.5px;
        }

        .logo p {
            color: #4A5D6E;
            font-size: 0.9em;
            font-weight: 500;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #2D3436;
            font-weight: 600;
            font-size: 0.95em;
        }

        .input-group {
            position: relative;
        }

        .input-group i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #5B7389;
        }

        .form-group input {
            width: 100%;
            padding: 12px 15px 12px 45px;
            border: 2px solid #A8B68C;
            border-radius: 10px;
            font-size: 1em;
            transition: all 0.3s;
            background: rgba(255, 255, 255, 0.8);
        }

        .form-group input:focus {
            outline: none;
            border-color: #8A9A5B;
            box-shadow: 0 0 0 3px rgba(138, 154, 91, 0.2);
            background: rgba(255, 255, 255, 1);
        }

        /* Deshabilitar el ícono de mostrar/ocultar del navegador */
        input[type="password"]::-ms-reveal,
        input[type="password"]::-ms-clear {
            display: none;
        }

        input[type="password"]::-webkit-credentials-auto-fill-button,
        input[type="password"]::-webkit-contacts-auto-fill-button {
            visibility: hidden;
            pointer-events: none;
        }

        .password-toggle {
            left: auto !important;
            right: 15px;
            color: #5B7389;
            transition: color 0.3s;
        }

        .password-toggle:hover {
            color: #8A9A5B;
        }

        #password {
            padding-right: 45px;
        }

        .btn-login {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #8A9A5B 0%, #A8B68C 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 1.1em;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            box-shadow: 0 4px 15px rgba(138, 154, 91, 0.3);
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(138, 154, 91, 0.5);
            background: linear-gradient(135deg, #9AAA6B 0%, #B8C69C 100%);
        }

        .btn-login:active {
            transform: translateY(0);
        }

        .error-message {
            background: #fee;
            color: #c33;
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #c33;
            display: flex;
            align-items: center;
            gap: 10px;
            animation: shake 0.5s;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-10px); }
            75% { transform: translateX(10px); }
        }

        .remember-forgot {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            font-size: 0.9em;
        }

        .remember-me {
            display: flex;
            align-items: center;
            gap: 8px;
            color: #555;
        }

        .remember-me input[type="checkbox"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
        }

        .footer {
            text-align: center;
            margin-top: 25px;
            padding-top: 20px;
            border-top: 1px solid rgba(168, 182, 140, 0.3);
            color: #4A5D6E;
            font-size: 0.85em;
        }

        .password-toggle {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #7f8c8d;
            transition: color 0.3s;
        }

        .password-toggle:hover {
            color: #667eea;
        }
    </style>
</head>
<body>
    <!-- Video de fondo -->
    <video class="video-background" autoplay muted loop playsinline>
        <source src="../../public/assets/videos/00cf74e3-6a5f-494c-816d-1363e56e4534.mp4" type="video/mp4">
        <!-- Fallback para navegadores que no soporten el video -->
    </video>

    <!-- Overlay oscuro semitransparente -->
    <div class="video-overlay"></div>

    <div class="login-container">
        <div class="logo">
            <i class="fas fa-leaf"></i>
            <h1>Plantas Admin</h1>
            <p>Sistema de Gestión de Plantas Medicinales</p>
        </div>

        <?php if ($error): ?>
            <div class="error-message">
                <i class="fas fa-exclamation-circle"></i>
                <span><?php echo htmlspecialchars($error); ?></span>
            </div>
        <?php endif; ?>

        <form method="POST" action="">
            <div class="form-group">
                <label for="username">Usuario</label>
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        placeholder="Ingresa tu usuario"
                        required
                        autofocus
                        value="<?php echo htmlspecialchars($_POST['username'] ?? ''); ?>"
                    >
                </div>
            </div>

            <div class="form-group">
                <label for="password">Contraseña</label>
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Ingresa tu contraseña"
                        required
                    >
                    <i class="fas fa-eye-slash password-toggle" id="togglePassword"></i>
                </div>
            </div>

            <div class="remember-forgot">
                <label class="remember-me">
                    <input type="checkbox" name="remember" id="remember">
                    <span>Recordarme</span>
                </label>
            </div>

            <button type="submit" class="btn-login">
                <i class="fas fa-sign-in-alt"></i>
                <span>Iniciar Sesión</span>
            </button>
        </form>

        <div class="footer">
            <p>&copy; 2025 Plantas Medicinales. Todos los derechos reservados.</p>
        </div>
    </div>

    <script>
        // Toggle password visibility
        const togglePassword = document.getElementById('togglePassword');
        const passwordInput = document.getElementById('password');

        // Mostrar contraseña mientras se mantiene presionado
        togglePassword.addEventListener('mousedown', function() {
            passwordInput.setAttribute('type', 'text');
        });

        togglePassword.addEventListener('mouseup', function() {
            passwordInput.setAttribute('type', 'password');
        });

        togglePassword.addEventListener('mouseleave', function() {
            passwordInput.setAttribute('type', 'password');
        });

        // Auto-focus on username field
        window.addEventListener('load', function() {
            document.getElementById('username').focus();
        });
    </script>
</body>
</html>

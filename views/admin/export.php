<?php
/**
 * Exportar datos a CSV/Excel
 */
require_once 'auth_check.php';
require_once __DIR__ . '/../../models/Plant.php';
require_once __DIR__ . '/../../models/User.php';
require_once __DIR__ . '/../../models/Prediction.php';

$type = $_GET['type'] ?? 'plants';
$format = $_GET['format'] ?? 'csv';

// Obtener datos segun el tipo
switch ($type) {
    case 'plants':
        $plantModel = new Plant();
        $data = $plantModel->getAll();
        $filename = 'plantas_' . date('Y-m-d');
        $headers = ['ID', 'Nombre Comun', 'Nombre Cientifico', 'Familia', 'Descripcion', 'Usos Medicinales', 'Preparacion', 'Precauciones', 'Estado', 'Fecha Creacion'];
        break;

    case 'users':
        $userModel = new User();
        $data = $userModel->getAll();
        $filename = 'usuarios_' . date('Y-m-d');
        $headers = ['ID', 'Username', 'Email', 'Nombre Completo', 'Fecha Creacion', 'Ultimo Login'];
        break;

    case 'predictions':
        $predictionModel = new Prediction();
        $data = $predictionModel->getAll(1000, 0);
        $filename = 'predicciones_' . date('Y-m-d');
        $headers = ['ID', 'Usuario', 'Planta Identificada', 'Nombre Cientifico', 'Confianza %', 'Fecha'];
        break;

    default:
        header('Location: index.php');
        exit;
}

// Exportar como CSV
if ($format === 'csv') {
    header('Content-Type: text/csv; charset=utf-8');
    header('Content-Disposition: attachment; filename="' . $filename . '.csv"');

    // BOM para Excel reconozca UTF-8
    echo "\xEF\xBB\xBF";

    $output = fopen('php://output', 'w');

    // Escribir encabezados
    fputcsv($output, $headers, ';');

    // Escribir datos
    foreach ($data as $row) {
        switch ($type) {
            case 'plants':
                fputcsv($output, [
                    $row['id'],
                    $row['common_name'],
                    $row['scientific_name'],
                    $row['family'],
                    $row['description'],
                    $row['medicinal_uses'],
                    $row['preparation'],
                    $row['precautions'],
                    $row['is_active'] ? 'Activa' : 'Inactiva',
                    $row['created_at']
                ], ';');
                break;

            case 'users':
                fputcsv($output, [
                    $row['id'],
                    $row['username'],
                    $row['email'],
                    $row['full_name'],
                    $row['created_at'],
                    $row['last_login'] ?? 'Nunca'
                ], ';');
                break;

            case 'predictions':
                fputcsv($output, [
                    $row['id'],
                    $row['username'] ?? 'Anonimo',
                    $row['plant_name'] ?? 'No identificada',
                    $row['scientific_name'] ?? '-',
                    $row['confidence'] ?? 0,
                    $row['created_at']
                ], ';');
                break;
        }
    }

    fclose($output);
    exit;
}

// Exportar como Excel (XLSX) usando HTML simple que Excel puede abrir
if ($format === 'excel') {
    header('Content-Type: application/vnd.ms-excel; charset=utf-8');
    header('Content-Disposition: attachment; filename="' . $filename . '.xls"');

    echo "\xEF\xBB\xBF"; // BOM UTF-8
    ?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        table { border-collapse: collapse; width: 100%; }
        th { background-color: #667eea; color: white; font-weight: bold; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        tr:nth-child(even) { background-color: #f2f2f2; }
    </style>
</head>
<body>
<table>
    <thead>
        <tr>
            <?php foreach ($headers as $header): ?>
            <th><?php echo htmlspecialchars($header); ?></th>
            <?php endforeach; ?>
        </tr>
    </thead>
    <tbody>
        <?php foreach ($data as $row): ?>
        <tr>
            <?php
            switch ($type) {
                case 'plants':
                    echo '<td>' . $row['id'] . '</td>';
                    echo '<td>' . htmlspecialchars($row['common_name']) . '</td>';
                    echo '<td>' . htmlspecialchars($row['scientific_name']) . '</td>';
                    echo '<td>' . htmlspecialchars($row['family']) . '</td>';
                    echo '<td>' . htmlspecialchars($row['description']) . '</td>';
                    echo '<td>' . htmlspecialchars($row['medicinal_uses']) . '</td>';
                    echo '<td>' . htmlspecialchars($row['preparation']) . '</td>';
                    echo '<td>' . htmlspecialchars($row['precautions']) . '</td>';
                    echo '<td>' . ($row['is_active'] ? 'Activa' : 'Inactiva') . '</td>';
                    echo '<td>' . $row['created_at'] . '</td>';
                    break;

                case 'users':
                    echo '<td>' . $row['id'] . '</td>';
                    echo '<td>' . htmlspecialchars($row['username']) . '</td>';
                    echo '<td>' . htmlspecialchars($row['email']) . '</td>';
                    echo '<td>' . htmlspecialchars($row['full_name']) . '</td>';
                    echo '<td>' . $row['created_at'] . '</td>';
                    echo '<td>' . ($row['last_login'] ?? 'Nunca') . '</td>';
                    break;

                case 'predictions':
                    echo '<td>' . $row['id'] . '</td>';
                    echo '<td>' . htmlspecialchars($row['username'] ?? 'Anonimo') . '</td>';
                    echo '<td>' . htmlspecialchars($row['plant_name'] ?? 'No identificada') . '</td>';
                    echo '<td>' . htmlspecialchars($row['scientific_name'] ?? '-') . '</td>';
                    echo '<td>' . ($row['confidence'] ?? 0) . '%</td>';
                    echo '<td>' . $row['created_at'] . '</td>';
                    break;
            }
            ?>
        </tr>
        <?php endforeach; ?>
    </tbody>
</table>
</body>
</html>
<?php
    exit;
}

<?php
/**
 * Search Plants API Endpoint
 */
require_once __DIR__ . '/../controllers/PlantController.php';

$controller = new PlantController();
$controller->search();

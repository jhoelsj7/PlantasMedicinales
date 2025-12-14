<?php
/**
 * Plants API Endpoint - Get All Plants
 */
require_once __DIR__ . '/../controllers/PlantController.php';

$controller = new PlantController();
$controller->getAll();

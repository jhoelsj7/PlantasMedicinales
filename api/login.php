<?php
/**
 * Login API Endpoint
 */
require_once __DIR__ . '/../controllers/AuthController.php';

$controller = new AuthController();
$controller->login();

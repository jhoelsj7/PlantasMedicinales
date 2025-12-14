<?php
/**
 * Authentication Check
 * Include this file at the top of every admin page
 */
session_start();

if (!isset($_SESSION['admin_logged_in']) || $_SESSION['admin_logged_in'] !== true) {
    header('Location: login.php');
    exit;
}

// Refresh session activity
$_SESSION['last_activity'] = time();

// Session timeout (30 minutes)
if (isset($_SESSION['last_activity']) && (time() - $_SESSION['last_activity'] > 1800)) {
    session_destroy();
    header('Location: login.php?timeout=1');
    exit;
}

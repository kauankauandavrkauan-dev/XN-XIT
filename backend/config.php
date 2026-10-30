<?php
// Configure antes de publicar. Use HTTPS e uma senha longa.
define('ADMIN_PASSWORD', getenv('XN_ADMIN_PASSWORD') ?: 'TROQUE-ESTA-SENHA-ANTES-DE-PUBLICAR');
define('DB_PATH', __DIR__ . '/keys.sqlite');
function db() {
  static $pdo = null;
  if (!$pdo) {
    $pdo = new PDO('sqlite:' . DB_PATH);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $pdo->exec('CREATE TABLE IF NOT EXISTS keys_store (id INTEGER PRIMARY KEY, key_value TEXT UNIQUE NOT NULL, created_at INTEGER NOT NULL, expires_at INTEGER NOT NULL, revoked INTEGER NOT NULL DEFAULT 0)');
  }
  return $pdo;
}
function json_out($data, $status=200) {
  http_response_code($status); header('Content-Type: application/json; charset=utf-8');
  echo json_encode($data); exit;
}

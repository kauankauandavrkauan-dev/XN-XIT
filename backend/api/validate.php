<?php
require_once __DIR__ . '/../config.php';
if ($_SERVER['REQUEST_METHOD'] !== 'POST') json_out(['valid'=>false,'message'=>'Método não permitido.'],405);
$body = json_decode(file_get_contents('php://input'), true);
$key = trim($body['key'] ?? '');
if ($key === '' || strlen($key) > 100) json_out(['valid'=>false,'message'=>'KEY inválida.'],400);
$q = db()->prepare('SELECT expires_at, revoked FROM keys_store WHERE key_value = ?');
$q->execute([$key]); $row = $q->fetch(PDO::FETCH_ASSOC);
if (!$row || (int)$row['revoked'] === 1) json_out(['valid'=>false,'message'=>'KEY inválida ou bloqueada.'],403);
if ((int)$row['expires_at'] <= time()) json_out(['valid'=>false,'message'=>'KEY expirada.'],403);
json_out(['valid'=>true,'expires_at'=>gmdate('d/m/Y H:i', (int)$row['expires_at']) . ' UTC']);

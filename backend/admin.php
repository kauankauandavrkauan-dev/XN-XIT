<?php
require_once __DIR__ . '/config.php';
session_start();
if (isset($_POST['logout'])) { session_destroy(); header('Location: admin.php'); exit; }
if (isset($_POST['password'])) {
  if (hash_equals(ADMIN_PASSWORD, $_POST['password'])) $_SESSION['admin'] = true;
  else $error = 'Senha incorreta.';
}
if (empty($_SESSION['admin'])): ?>
<!doctype html><meta charset="utf-8"><title>XN XIT Admin</title>
<style>body{background:#0a0a14;color:#fff;font:16px Arial;display:grid;place-items:center;height:90vh}form{background:#19192a;padding:28px;border-radius:16px}input,button{padding:12px;margin:6px;border-radius:8px}</style>
<form method="post"><h2>XN XIT • Admin</h2><input type="password" name="password" placeholder="Senha administrativa" required><button>Entrar</button><p><?=htmlspecialchars($error ?? '')?></p></form>
<?php exit; endif;
$msg = '';
if (isset($_POST['create'])) {
  $key = 'XNXIT-' . strtoupper(bin2hex(random_bytes(5)));
  $now = time(); $q = db()->prepare('INSERT INTO keys_store(key_value,created_at,expires_at) VALUES(?,?,?)');
  $q->execute([$key,$now,$now+86400]); $msg = $key;
}
if (isset($_POST['revoke'])) { $q=db()->prepare('UPDATE keys_store SET revoked=1 WHERE id=?'); $q->execute([(int)$_POST['revoke']]); }
$rows = db()->query('SELECT * FROM keys_store ORDER BY id DESC')->fetchAll(PDO::FETCH_ASSOC);
?>
<!doctype html><html><meta charset="utf-8"><title>XN XIT Admin</title>
<style>body{background:#0a0a14;color:#eee;font:15px Arial;margin:40px}h1{color:#b58aff}button{background:#8055d9;color:white;border:0;padding:10px 16px;border-radius:8px;cursor:pointer}table{border-collapse:collapse;width:100%;margin-top:25px}td,th{padding:12px;border-bottom:1px solid #333;text-align:left}.key{color:#8ee6ad;font-weight:bold}</style>
<h1>XN XIT • Administração</h1><form method="post"><button name="create" value="1">Gerar KEY de 24 horas</button> <button name="logout" value="1">Sair</button></form>
<?php if($msg): ?><p>KEY criada: <span class="key"><?=htmlspecialchars($msg)?></span> (copie agora)</p><?php endif; ?>
<table><tr><th>KEY</th><th>Expira</th><th>Status</th><th>Ação</th></tr>
<?php foreach($rows as $r): ?><tr><td><?=htmlspecialchars($r['key_value'])?></td><td><?=gmdate('d/m/Y H:i:s',$r['expires_at'])?> UTC</td><td><?=$r['revoked']?'Bloqueada':($r['expires_at']<=time()?'Expirada':'Ativa')?></td><td><?php if(!$r['revoked'] && $r['expires_at']>time()): ?><form method="post"><button name="revoke" value="<?=$r['id']?>">Bloquear</button></form><?php endif; ?></td></tr><?php endforeach; ?>
</table></html>

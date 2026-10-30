package com.xnxit.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {
    // Troque pela URL HTTPS onde você publicar a pasta backend/.
    private static final String API = "https://xnxit.42web.io/backend/api/validate.php";
    private final ExecutorService worker = Executors.newSingleThreadExecutor();
    private LinearLayout root;
    private EditText keyInput;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(10, 10, 20));
        getWindow().setNavigationBarColor(Color.rgb(10, 10, 20));
        showLogin();
    }

    private TextView label(String text, int size, int color) {
        TextView v = new TextView(this); v.setText(text); v.setTextSize(size);
        v.setTextColor(color); v.setGravity(Gravity.CENTER); return v;
    }
    private void base() {
        root = new LinearLayout(this); root.setOrientation(1);
        root.setGravity(Gravity.CENTER); root.setPadding(28, 28, 28, 28);
        root.setBackgroundColor(Color.rgb(10, 10, 20));
        setContentView(root);
    }
    private void showLogin() {
        base();
        TextView logo = label("XN XIT", 36, Color.rgb(165, 110, 255));
        logo.setTypeface(null, Typeface.BOLD); root.addView(logo);
        TextView sub = label("PAINEL DE ACESSO", 12, Color.LTGRAY);
        root.addView(sub);
        keyInput = new EditText(this); keyInput.setSingleLine(true);
        keyInput.setHint("Digite sua KEY"); keyInput.setHintTextColor(0xff888899);
        keyInput.setTextColor(Color.WHITE); keyInput.setPadding(18, 14, 18, 14);
        keyInput.setBackgroundColor(0xff202033);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.topMargin = 34; root.addView(keyInput, p);
        Button enter = new Button(this); enter.setText("VALIDAR KEY");
        root.addView(enter, p);
        TextView status = label("Acesso protegido • XN XIT", 12, 0xff888899);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2); sp.topMargin = 18;
        root.addView(status, sp);
        enter.setOnClickListener(v -> {
            String key = keyInput.getText().toString().trim();
            if (key.isEmpty()) { status.setText("Digite uma KEY para continuar."); return; }
            status.setText("Validando...");
            worker.execute(() -> validate(key, status));
        });
    }
    private void validate(String key, TextView status) {
        HttpURLConnection c = null;
        try {
            URL url = new URL(API); c = (HttpURLConnection)url.openConnection();
            c.setRequestMethod("POST"); c.setConnectTimeout(12000); c.setReadTimeout(12000);
            c.setDoOutput(true); c.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            JSONObject body = new JSONObject(); body.put("key", key);
            try (OutputStream os = c.getOutputStream()) { os.write(body.toString().getBytes(StandardCharsets.UTF_8)); }
            int code = c.getResponseCode();
            InputStream in = code < 400 ? c.getInputStream() : c.getErrorStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream(); byte[] b = new byte[1024]; int n;
            while ((n = in.read(b)) > 0) out.write(b, 0, n);
            JSONObject result = new JSONObject(out.toString("UTF-8"));
            boolean ok = result.optBoolean("valid", false);
            runOnUiThread(() -> {
                if (ok) showHome(result.optString("expires_at", ""));
                else status.setText(result.optString("message", "KEY inválida ou expirada."));
            });
        } catch (Exception e) {
            runOnUiThread(() -> status.setText("Não foi possível conectar ao servidor. Verifique a URL HTTPS."));
        } finally { if (c != null) c.disconnect(); }
    }
    private void showHome(String expiry) {
        base();
        TextView title = label("XN XIT", 34, 0xffa56eff); title.setTypeface(null, Typeface.BOLD);
        root.addView(title);
        root.addView(label("ACESSO LIBERADO", 18, 0xff75e0a3));
        root.addView(label("Sua KEY expira em: " + expiry, 13, Color.LTGRAY));
        TextView info = label("Painel principal\n\nÁrea de configurações\n\nEste aplicativo é uma base de acesso e configurações. Não altera partidas nem interfere em outros aplicativos.", 15, Color.WHITE);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2); p.topMargin = 30; root.addView(info, p);
        Button back = new Button(this); back.setText("SAIR"); root.addView(back);
        back.setOnClickListener(v -> showLogin());
    }
    @Override protected void onDestroy() { super.onDestroy(); worker.shutdownNow(); }
}

package com.xnxit.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private LinearLayout root;
    private final int background = Color.rgb(10, 10, 20);
    private final int purple = Color.rgb(165, 110, 255);

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(background);
        getWindow().setNavigationBarColor(background);
        showHome();
    }

    private TextView label(String value, int size, int color) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    private void showHome() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(28, 28, 28, 28);
        root.setBackgroundColor(background);
        setContentView(root);

        TextView logo = label("XN XIT", 36, purple);
        logo.setTypeface(null, Typeface.BOLD);
        root.addView(logo);
        root.addView(label("PAINEL PRINCIPAL", 12, Color.LTGRAY));

        TextView status = label("ACESSO LIVRE", 18, Color.rgb(117, 224, 163));
        LinearLayout.LayoutParams statusParams =
            new LinearLayout.LayoutParams(-1, -2);
        statusParams.topMargin = 28;
        root.addView(status, statusParams);

        TextView info = label(
            "Bem-vindo ao XN XIT!\n\n" +
            "Esta versão abre diretamente, sem solicitar KEY.\n\n" +
            "Use as opções abaixo para acessar as configurações visuais do app.",
            15, Color.WHITE);
        LinearLayout.LayoutParams infoParams =
            new LinearLayout.LayoutParams(-1, -2);
        infoParams.topMargin = 24;
        root.addView(info, infoParams);

        Button about = new Button(this);
        about.setText("SOBRE O APLICATIVO");
        LinearLayout.LayoutParams buttonParams =
            new LinearLayout.LayoutParams(-1, -2);
        buttonParams.topMargin = 28;
        root.addView(about, buttonParams);
        about.setOnClickListener(v -> showAbout());
    }

    private void showAbout() {
        root.removeAllViews();
        TextView title = label("SOBRE", 28, purple);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);
        root.addView(label(
            "XN XIT — versão sem validação de KEY.\n\n" +
            "Este projeto contém somente a interface do aplicativo. " +
            "Não inclui aimbot, ESP nem funções que interfiram em jogos ou em outros apps.",
            15, Color.WHITE));
        Button back = new Button(this);
        back.setText("VOLTAR");
        root.addView(back);
        back.setOnClickListener(v -> showHome());
    }
}

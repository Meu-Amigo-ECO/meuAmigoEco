package com.example.meuamigoeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PontuacaoActivity extends AppCompatActivity {
    private TextView txtPontuacao;
    private Button btnFeito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao);

        txtPontuacao = findViewById(R.id.txtPontuacao);
        btnFeito = findViewById(R.id.btnFeito);

        String pontuacaoString = getIntent().getStringExtra("PONTUAÇÃO");
        txtPontuacao.setText(pontuacaoString);

        btnFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(PontuacaoActivity.this, MainActivity.class);
                PontuacaoActivity.this.startActivity(mainActivity);
                PontuacaoActivity.this.finish();
            }
        });
    }
}
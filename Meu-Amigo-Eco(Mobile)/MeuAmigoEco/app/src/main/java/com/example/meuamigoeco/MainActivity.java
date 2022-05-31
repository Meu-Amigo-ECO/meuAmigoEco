package com.example.meuamigoeco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tituloDoApp, subtitulo;
    private Button btnComecar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tituloDoApp = findViewById(R.id.txtTitulo);
        subtitulo = findViewById(R.id.txtSubtitulo);
        btnComecar = findViewById(R.id.startButton);

        // Colocar a cor do botão de verde
        //btnComecar.setBackgroundColor(Color.GREEN);

        // Mudar font do titulo
        Typeface typeface = ResourcesCompat.getFont(this, R.font.grinchedregular);
        tituloDoApp.setTypeface(typeface);
        subtitulo.setTypeface(typeface);

        // Ao clickar no botão ser redirecionado para a Activity categoria
        btnComecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
                startActivity(intent);
            }
        });
    }
}
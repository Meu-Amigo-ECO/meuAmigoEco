package com.example.meuamigoeco;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    private TextView nomeApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        nomeApp = findViewById(R.id.nomeApp);

        // Pega a font importada para a pasta Font
        Typeface typeface = ResourcesCompat.getFont(this, R.font.grinchedregular);
        nomeApp.setTypeface(typeface);

        // Coloca animação no nome do App
        Animation animacao = AnimationUtils.loadAnimation(this, R.anim.animation);
        nomeApp.setAnimation(animacao);

        // Após 3 segundos, será redirecionado para a Intent Main
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }).start();
    }
}
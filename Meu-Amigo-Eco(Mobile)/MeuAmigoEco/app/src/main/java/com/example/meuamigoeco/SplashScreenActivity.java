package com.example.meuamigoeco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private TextView nomeApp;
    public static List<String> listaCategorias = new ArrayList<>();
    private FirebaseFirestore firestore;

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

        // Inicializando o Firebase
        firestore = FirebaseFirestore.getInstance();

        // Após 3 segundos, será redirecionado para a Intent Main
        new Thread() {
            @Override
            public void run() {
                loadData();
            }
        }.start();
    }

    private void loadData(){
        listaCategorias.clear();
        firestore.collection("MeuAmigoEco").document("Categorias")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()){
                        long count = (long)doc.get("count");
                        for (int i = 1; i <= count; i++){
                            String catNome = doc.getString("cat" + String.valueOf(i));
                            listaCategorias.add(catNome);
                        }

                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        SplashScreenActivity.this.finish();
                    }
                    else {
                        Toast.makeText(SplashScreenActivity.this, "Sem documento de Categoria!",
                                Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }
                else
                {
                    Toast.makeText(SplashScreenActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
package com.example.meuamigoeco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetsActivity extends AppCompatActivity {
    private GridView secao_gridView;
    private Toolbar toolbar;
    private FirebaseFirestore firestore;
    public static int idCategoria;
    private Dialog carregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        toolbar = findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CATEGORIA");
        idCategoria = getIntent().getIntExtra("CATEGORIA_ID", 1);

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        secao_gridView = findViewById(R.id.set_gridview);

        carregando = new Dialog(SetsActivity.this);
        carregando.setContentView(R.layout.barra_progresso_carregamento);
        carregando.setCancelable(false);
        carregando.getWindow().setBackgroundDrawableResource(R.drawable.background_progresso);
        carregando.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        carregando.show();

        firestore = FirebaseFirestore.getInstance();

        loadSets();
    }

    public void loadSets(){
        firestore.collection("MeuAmigoEco").document("Categoria" + String.valueOf(idCategoria))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()){
                        long sets = (long)doc.get("SETS");

                        SetsAdapter adapter = new SetsAdapter((int)sets);
                        secao_gridView.setAdapter(adapter);
                    }
                    else {
                        Toast.makeText(SetsActivity.this, "Não existe o documento!",
                                Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }
                else {
                    Toast.makeText(SetsActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                carregando.cancel();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            SetsActivity.this.finish();
        return super.onOptionsItemSelected(item);
    }
}
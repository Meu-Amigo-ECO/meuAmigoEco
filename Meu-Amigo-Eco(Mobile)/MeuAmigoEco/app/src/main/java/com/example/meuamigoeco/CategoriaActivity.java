package com.example.meuamigoeco;

import static com.example.meuamigoeco.SplashScreenActivity.listaCategorias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

public class CategoriaActivity extends AppCompatActivity {
    private GridView catGridView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Configurando o toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categorias do Quiz");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configurando a GridView
        catGridView = findViewById(R.id.categoryGridView);

        // Populando o adapter com a lista de categorias
        AdapterCaregoria adapter = new AdapterCaregoria(listaCategorias);
        catGridView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            CategoriaActivity.this.finish();

        return super.onOptionsItemSelected(item);
    }
}
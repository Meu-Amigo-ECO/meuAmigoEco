package com.example.meuamigoeco;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class AdapterCaregoria extends BaseAdapter {
    private List<String> catArrayList;

    public AdapterCaregoria(List<String> catArrayList) {
        this.catArrayList = catArrayList;
    }

    @Override
    public int getCount() {
        return catArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cat_item_layout, parent, false);
        }
        else {
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(),SetsActivity.class);
                intent.putExtra("CATEGORIA", catArrayList.get(position));
                intent.putExtra("CATEGORIA_ID", position + 1);
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.catNome)).setText(catArrayList.get(position));

        // Pegando uma cor randômica e setando na View Categoria
        Random random = new Random();
        int cor = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        view.setBackgroundColor(cor);

        return view;
    }
}

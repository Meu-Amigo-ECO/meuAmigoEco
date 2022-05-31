package com.example.meuamigoeco;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SetsAdapter extends BaseAdapter {
    private int numeroDasSecoes;

    public SetsAdapter(int numeroDasSecoes) {
        this.numeroDasSecoes = numeroDasSecoes;
    }

    @Override
    public int getCount() {
        return numeroDasSecoes;
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

        if (convertView == null)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout, parent, false);
        else
            view = convertView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), QuestaoActivity.class);
                parent.getContext().startActivity(intent);
            }
        });
        ((TextView) view.findViewById(R.id.setNo_tv)).setText(String.valueOf(position+1));
        return view;
    }
}

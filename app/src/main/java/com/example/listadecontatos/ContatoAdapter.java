package com.example.listadecontatos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class ContatoAdapter extends BaseAdapter {

    private Context context;
    private List<Contato> contatos;

    public ContatoAdapter(Context context, List<Contato> contatos) {
        this.context = context;
        this.contatos = contatos;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context)
                .inflate(R.layout.contato_item, parent, false);

        Contato contato = contatos.get(position);

        TextView tvNomeItem = convertView.findViewById(R.id.tvNomeItem);
        TextView tvTelefoneItem = convertView.findViewById(R.id.tvTelefoneItem);
        ImageButton ibtnFavoritar = convertView.findViewById(R.id.ibtnFavoritar);

        tvNomeItem.setText(contato.getNome());
        tvTelefoneItem.setText(contato.getTelefone());

        if (contato.isFavorito()) {
            ibtnFavoritar.setColorFilter(ContextCompat.getColor(context, R.color.black));
        } else {
            ibtnFavoritar.setColorFilter(ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_primary_variant));
        }

        return convertView;
    }
}

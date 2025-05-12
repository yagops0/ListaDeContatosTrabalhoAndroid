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
    private ContatoAdapterListener listener;

    // Interface para comunicação com a Activity
    public interface ContatoAdapterListener {
        void onDeleteClick(int contatoId);
    }

    public ContatoAdapter(Context context, List<Contato> contatos) {
        this.context = context;
        this.contatos = contatos;

        // Verifica se o contexto implementa nossa interface
        if (context instanceof ContatoAdapterListener) {
            this.listener = (ContatoAdapterListener) context;
        } else {
            throw new RuntimeException(context + " deve implementar ContatoAdapterListener");
        }
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
        ImageButton ibtnExcluir = convertView.findViewById(R.id.imageButton);  // Botão de excluir

        tvNomeItem.setText(contato.getNome());
        tvTelefoneItem.setText(contato.getTelefone());

        if (contato.isFavorito()) {
            ibtnFavoritar.setColorFilter(ContextCompat.getColor(context, R.color.black));
        } else {
            ibtnFavoritar.setColorFilter(ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_primary_variant));
        }

        // Adiciona listener de clique no botão de excluir
        ibtnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    // Aqui assumo que cada contato tenha um ID único
                    // Se você não tiver esse ID, você pode passar o objeto contato inteiro
                    listener.onDeleteClick(contato.getId());
                }
            }
        });

        return convertView;
    }
}
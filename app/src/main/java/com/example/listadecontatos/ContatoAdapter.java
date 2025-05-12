package com.example.listadecontatos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    public interface ContatoAdapterListener {
        void onDeleteClick(int contatoId);
    }

    public ContatoAdapter(Context context, List<Contato> contatos) {
        this.context = context;
        this.contatos = contatos;

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
        ImageButton ibtnLigar = convertView.findViewById(R.id.ibtnLigar);
        ImageButton ibtnExcluir = convertView.findViewById(R.id.imageButton);
        ImageButton ibtnEditar = convertView.findViewById(R.id.ibtnEditar);

        tvNomeItem.setText(contato.getNome());
        tvTelefoneItem.setText(contato.getTelefone());

        ibtnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {

                    listener.onDeleteClick(contato.getId());
                }
            }
        });

        ibtnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contato.getTelefone()));
                context.startActivity(intent);
            }
        });

        ibtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CadastroActivity.class);
                intent.putExtra("contato", contato);
                context.startActivity(intent);
            }
        });





        return convertView;
    }
}
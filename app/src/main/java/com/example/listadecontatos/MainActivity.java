package com.example.listadecontatos;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //TODO:Alguns itens são obrigatórios:
    //TODO: Usar a biblioteca Retrofit - CHECK
    //TODO: Criar um modelo para representar os dados
    //TODO: Montar a tela inicial do aplicativo onde deve ser exibido a lista de contatos.
    //TODO: Criar uma tela para o cadastro e edição do contato.
    //TODO: Permitir a exclusão de um contato.
    //TODO: Permitir telefonar para um contato.
    //TODO: Buscar somente os contatos favoritos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
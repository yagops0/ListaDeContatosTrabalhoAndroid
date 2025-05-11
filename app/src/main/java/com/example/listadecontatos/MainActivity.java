package com.example.listadecontatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.listadecontatos.api.Constantes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //TODO:Alguns itens são obrigatórios:
    //TODO: Usar a biblioteca Retrofit - CHECK
    //TODO: Criar um modelo para representar os dados - CHECK
    //TODO: Montar a tela inicial do aplicativo onde deve ser exibido a lista de contatos. - CHECK
    //TODO: Criar uma tela para o cadastro e edição do contato. - CHECK
    //TODO: Permitir a exclusão de um contato.
    //TODO: Permitir telefonar para um contato.
    //TODO: Buscar somente os contatos favoritos.

    private ListView lvContatos;

    private ContatoAdapter contatoAdapter;



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

        Toolbar tlbContatos = findViewById(R.id.tlbContatos);
        setSupportActionBar(tlbContatos);

        lvContatos =findViewById(R.id.lvContatos);
        buscarContatos();

        FloatingActionButton fabContatos = findViewById(R.id.fabAddContatos);
        fabContatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telaCadastro = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(telaCadastro);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contatos, menu);
        return true;
    }

    private void buscarContatos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContatosApi contatosApi = retrofit.create(ContatosApi.class);

        Call<List<Contato>> getContatosServer = contatosApi.getContacts();
        getContatosServer.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                if (response.isSuccessful()) {
                    List<Contato> contatos = response.body();
                    ContatoAdapter contatoAdapter = new ContatoAdapter(MainActivity.this, contatos);
                    lvContatos.setAdapter(contatoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Erro ao buscar tarefas", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void excluirContato(int contatoId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContatosApi contatosApi = retrofit.create(ContatosApi.class);

        Call<Void> deletarContato = contatosApi.deletarContato(contatoId);
        deletarContato.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Contato excluído com sucesso!", Toast.LENGTH_LONG).show();
                    buscarContatos();
                } else {
                    Toast.makeText(MainActivity.this, "Houve um erro ao excluir o contato", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Erro ao comunicar com a Api", Toast.LENGTH_LONG).show();
            }
        });
    }


}
package com.example.listadecontatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.listadecontatos.api.Constantes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etNome;

    private EditText etTelefone;

    private CheckBox cbFavorito;
    private int contatoId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnCadastrarEditar = findViewById(R.id.btnCadastrarEditar);
        btnCadastrarEditar.setOnClickListener(this);

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telaPrincipal = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(telaPrincipal);
            }
        });

        etNome = findViewById(R.id.etNome);
        etTelefone = findViewById(R.id.etTelefone);
        cbFavorito = findViewById(R.id.cbFavorito);


        if (getIntent().hasExtra("contato")) {
            Contato contato = (Contato) getIntent().getSerializableExtra("contato");

            etNome.setText(contato.getNome());
            etTelefone.setText(contato.getTelefone());
            cbFavorito.setChecked(contato.isFavorito());
            contatoId = contato.getId();

        }
    }

    public void onClick(View v){
        if (v.getId() == R.id.btnVoltar){
            finish();
        } else if (v.getId() == R.id.btnCadastrarEditar) {
            etNome = findViewById(R.id.etNome);
            etTelefone = findViewById(R.id.etTelefone);
            cbFavorito = findViewById(R.id.cbFavorito);

            Contato contato = new Contato(
                    etNome.getText().toString(),
                    etTelefone.getText().toString(),
                    cbFavorito.isChecked()
            );

            if (contatoId > 0) {
                editarContato(contato);
            } else if (contatoId == 0) {
                adicionarContato(contato);
            }
        }
    }

    private void adicionarContato(Contato contato){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContatosApi contatosApi = retrofit.create(ContatosApi.class);

        Call<Contato> criarContatoServer = contatosApi.criarContato(contato);
        criarContatoServer.enqueue(new Callback<Contato>() {
            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Contato adicionado!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(CadastroActivity.this, "Erro ao adicionar contato", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable throwable) {
                Toast.makeText(CadastroActivity.this, "Erro de comunicação API", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editarContato(Contato contato) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContatosApi contatosApi = retrofit.create(ContatosApi.class);

        Call<Void> editarContato = contatosApi.editarContato(contatoId, contato);
        editarContato.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Contato editado!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(CadastroActivity.this, "Erro ao editar tarefa!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(CadastroActivity.this, "Erro de comunicação com Api", Toast.LENGTH_LONG).show();
            }
        });

    }

}
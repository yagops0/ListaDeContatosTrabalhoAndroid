package com.example.listadecontatos;

import com.google.gson.annotations.SerializedName;

public class Contato {

    private int id;
    @SerializedName("name")
    private String nome;
    @SerializedName("phone")
    private String telefone;
    @SerializedName("favorite")
    private boolean favorito;
    public Contato() {
    }
    public Contato(int id, String nome, String telefone, boolean favorito) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.favorito = favorito;
    }
    public Contato(String nome, String telefone, boolean favorito) {
        this.nome = nome;
        this.telefone = telefone;
        this.favorito = favorito;
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}

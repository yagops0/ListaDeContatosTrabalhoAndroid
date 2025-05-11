package com.example.listadecontatos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ContatosApi {

    @GET("/contacts")
    Call<List<Contato>> getContacts();

    @POST("/contacts")
    Call<Contato> criarContato(@Body Contato contato);

    @PUT("/contacts/{id}")
    Call<Void> editarContato(@Path("id") int id, @Body Contato contato);

    @DELETE("/contacts/{id}")
    Call<Void> deletarContato(@Path("id") int id);


}

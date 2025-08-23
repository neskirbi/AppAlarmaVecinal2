package com.app.appalarmavecinal.Registro;

import com.app.appalarmavecinal.Models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("Registro")
    Call<Usuario> registerUser(@Body Usuario usuario);
}
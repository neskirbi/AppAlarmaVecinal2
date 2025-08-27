package com.app.appalarmavecinal.Services;

import com.app.appalarmavecinal.Models.LoginRequest;
import com.app.appalarmavecinal.Models.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // Opción 1: Usando @Body con LoginRequest
    @POST("login")
    Call<Usuario> loginUser(@Body LoginRequest loginRequest);

    // Opción 2: Usando @Query parameters
    @POST("login")
    Call<Usuario> loginUser(
            @Query("email") String email,
            @Query("password") String password
    );

    // ... otros métodos que tengas
}
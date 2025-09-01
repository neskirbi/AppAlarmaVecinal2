package com.app.appalarmavecinal.Login;

import com.app.appalarmavecinal.Models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("Login")
    Call<Usuario> loginUser(@Body LoginRequest loginRequest);

    // ... otros métodos que tengas
}

// Clase para el request del login

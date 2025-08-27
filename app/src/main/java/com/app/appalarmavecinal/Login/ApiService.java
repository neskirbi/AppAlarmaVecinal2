package com.app.appalarmavecinal.Login;

import com.app.appalarmavecinal.Models.Usuario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    // USAR ESTA OPCIÓN - Correcta para formularios
    @FormUrlEncoded
    @POST("Login")
    Call<Usuario> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    // ... otros métodos que tengas
}
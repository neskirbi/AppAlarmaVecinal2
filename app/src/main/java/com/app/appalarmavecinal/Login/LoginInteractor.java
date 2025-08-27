package com.app.appalarmavecinal.Login;

import android.util.Log;
import android.util.Patterns;
import android.os.Handler;
import android.os.Looper;

import com.app.appalarmavecinal.Models.Usuario;
import com.app.appalarmavecinal.Services.ApiService;
import com.app.appalarmavecinal.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor implements LoginContract.Interactor {

    private static final String TAG = "LoginInteractor";

    @Override
    public void performLogin(String email, String password, OnLoginFinishedListener listener) {
        // Validaciones
        if (email.isEmpty()) {
            listener.onEmailError("El email es requerido");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            listener.onEmailError("Email no válido");
            return;
        }

        if (password.isEmpty()) {
            listener.onPasswordError("La contraseña es requerida");
            return;
        }

        if (password.length() < 6) {
            listener.onPasswordError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        // Llamada real a la API
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Crear objeto para el login (puedes crear una clase LoginRequest si es necesario)
        // Por ahora asumamos que tu API espera email y password como parámetros
        Call<Usuario> call = apiService.loginUser(email, password);

        Log.i(TAG, "Intentando login con email: " + email);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    Log.i(TAG, "Login exitoso: " + usuario.toString());
                    listener.onSuccess();
                } else {
                    String errorMessage = "Error en el login";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing error response", e);
                        }
                    }
                    Log.e(TAG, "Login failed: " + errorMessage);
                    listener.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "Login failed", t);
                listener.onFailure("Error de conexión: " + t.getMessage());
            }
        });
    }
}
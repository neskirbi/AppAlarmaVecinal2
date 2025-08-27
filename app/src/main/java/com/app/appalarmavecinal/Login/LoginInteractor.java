package com.app.appalarmavecinal.Login;

import android.util.Log;
import android.util.Patterns;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.app.appalarmavecinal.Models.Usuario;
import com.app.appalarmavecinal.Login.ApiService;
import com.app.appalarmavecinal.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor implements LoginContract.Interactor {

    private static final String TAG = "LoginInteractor";

    @Override
    public void performLogin(String email, String password, OnLoginFinishedListener listener) {
        // Validaciones (igual que tienes)
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

        // Llamada a la API
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Usuario> call = apiService.loginUser(email, password);

        Log.i("Login", email + " " + password);

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
                            // Parsear el JSON de error
                            JsonObject errorJson = JsonParser.parseString(response.errorBody().string()).getAsJsonObject();
                            if (errorJson.has("error")) {
                                errorMessage = errorJson.get("error").getAsString();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing error response", e);
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception ex) {
                                Log.e(TAG, "Error reading error body", ex);
                            }
                        }
                    }

                    // Mostrar el error específico del servidor
                    Log.e(TAG, "Login failed: " + errorMessage);

                    // Diferenciar entre error de email y error de contraseña
                    if (errorMessage.contains("Correo")) {
                        listener.onEmailError(errorMessage);
                    } else if (errorMessage.contains("Contrasenia")) {
                        listener.onPasswordError(errorMessage);
                    } else {
                        listener.onFailure(errorMessage);
                    }
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
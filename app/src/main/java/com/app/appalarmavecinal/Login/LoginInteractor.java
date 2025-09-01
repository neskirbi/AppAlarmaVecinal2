package com.app.appalarmavecinal.Login;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.app.appalarmavecinal.Models.UsuarioDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.app.appalarmavecinal.Models.Usuario;
import com.app.appalarmavecinal.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor implements LoginContract.Interactor {

    private static final String TAG = "LoginInteractor";
    private Context context;
    private Gson gson = new Gson();

    public LoginInteractor(Context context) {
        this.context = context;
    }

    @Override
    public void performLogin(String email, String pass, OnLoginFinishedListener listener) {
        // Validaciones
        if (email.isEmpty()) {
            listener.onEmailError("El email es requerido");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            listener.onEmailError("Email no válido");
            return;
        }

        if (pass.isEmpty()) {
            listener.onPasswordError("La contraseña es requerida");
            return;
        }

        if (pass.length() < 6) {
            listener.onPasswordError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        // Crear objeto de request
        LoginRequest loginRequest = new LoginRequest(email, pass);

        // Llamada a la API
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Usuario> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    Log.i(TAG, "Login exitoso: " + usuario.toString());

                    // GUARDAR USUARIO EN SQLITE
                    UsuarioDAO usuarioDAO = new UsuarioDAO(context);
                    if (usuarioDAO.guardarUsuario(usuario)) {
                        listener.onSuccess();
                    } else {
                        Log.i(TAG, "Error al guardar datos locales " + usuario.toString());
                        listener.onFailure("Error al guardar datos locales");
                    }

                } else {
                    handleErrorResponse(response, listener);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "Login failed", t);
                listener.onFailure("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void handleErrorResponse(Response<Usuario> response, OnLoginFinishedListener listener) {
        String errorMessage = "Error en el login";

        try {
            if (response.errorBody() != null) {
                String errorBody = response.errorBody().string();
                JsonObject errorJson = JsonParser.parseString(errorBody).getAsJsonObject();

                // Verificar diferentes formatos de error comunes
                if (errorJson.has("message")) {
                    errorMessage = errorJson.get("message").getAsString();
                } else if (errorJson.has("error")) {
                    errorMessage = errorJson.get("error").getAsString();
                } else if (errorJson.has("detail")) {
                    errorMessage = errorJson.get("detail").getAsString();
                } else {
                    errorMessage = errorBody;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing error response", e);
            errorMessage = "Error del servidor";
        }

        Log.e(TAG, "Login failed: " + errorMessage);

        // Manejar errores específicos
        if (errorMessage.toLowerCase().contains("email") || errorMessage.toLowerCase().contains("correo")) {
            listener.onEmailError(errorMessage);
        } else if (errorMessage.toLowerCase().contains("password") ||
                errorMessage.toLowerCase().contains("contraseña") ||
                errorMessage.toLowerCase().contains("contrasenia")) {
            listener.onPasswordError(errorMessage);
        } else {
            listener.onFailure(errorMessage);
        }
    }


    public boolean isUserLoggedIn() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(context);
        return usuarioDAO.hayUsuarioGuardado();
    }
}
package com.app.appalarmavecinal.Registro;

import android.util.Log;
import com.app.appalarmavecinal.Models.Usuario;
import com.app.appalarmavecinal.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroInteractor implements RegistroContract.Interactor {

    private static final String TAG = "RegistroInteractor";

    @Override
    public void registerUser(String name, String apellido, String email, String password, RegistroContract.OnRegistrationFinishedListener listener) {
        // Aquí implementarías la lógica de registro
        // Por ahora simulo una llamada a API con Retrofit


        Log.i("Usuario",apellido);
        // Crear objeto usuario para enviar
        Usuario usuario = new Usuario();
        usuario.setNombres(name);
        usuario.setApellidos(apellido);
        usuario.setMail(email);
        usuario.setPass(password);

        // Llamada a la API (ejemplo con Retrofit)
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Usuario> call = apiService.registerUser(usuario);

        Log.i("Usuario",usuario.toString());
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess();
                } else {
                    String errorMessage = "Error en el registro";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing error response", e);
                        }
                    }
                    listener.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "Registration failed", t);
                listener.onFailure("Error de conexión: " + t.getMessage());
            }
        });

        // Para testing sin API, descomenta esto:
        /*
        new android.os.Handler().postDelayed(() -> {
            // Simular registro exitoso después de 2 segundos
            listener.onSuccess();
        }, 2000);
        */
    }
}
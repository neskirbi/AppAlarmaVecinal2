package com.app.appalarmavecinal;

import android.app.Application;
import com.app.appalarmavecinal.Retrofit.RetrofitClient;

public class AlarmaVecinal extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializar RetrofitClient
        RetrofitClient.initialize(this);

        // Opcional: Verificar la URL
        Metodos metodos = new Metodos(this);
        String url = metodos.GetUrl();
        android.util.Log.d("MyApplication", "URL base: " + url);
    }
}



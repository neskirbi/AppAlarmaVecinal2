package com.app.appalarmavecinal.Retrofit;

import android.content.Context;
import com.app.appalarmavecinal.Metodos;
import com.app.appalarmavecinal.Registro.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static Context appContext;

    // Método para inicializar con contexto
    public static void initialize(Context context) {
        appContext = context.getApplicationContext();
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            if (appContext == null) {
                throw new IllegalStateException("Debes llamar a RetrofitClient.initialize() primero");
            }

            // Obtener URL según el modo
            Metodos metodos = new Metodos(appContext);
            String BASE_URL = metodos.GetUrl();

            // Interceptor para logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Cliente HTTP
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            // Configurar GSON
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            // Crear instancia de Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getInstance().create(ApiService.class);
    }

    // Método sobrecargado para obtener el servicio con contexto
    public static ApiService getApiService(Context context) {
        initialize(context);
        return getInstance().create(ApiService.class);
    }
}
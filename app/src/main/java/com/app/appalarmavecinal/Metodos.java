package com.app.appalarmavecinal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class Metodos {
    private Context context;

    public Metodos(Context context) {
        this.context = context;
    }

    public String GetUrl() {
        if (isDebug()) {
            // URL para desarrollo/depuración
            return "http://192.168.100.6/AlarmaVecinal/public/api/"; // Cambia por tu IP local
        } else {
            // URL para producción
            return "https://tu-api-produccion.com/api/"; // Cambia por tu URL de producción
        }
    }

    private boolean isDebug() {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(),
                    PackageManager.GET_META_DATA
            );
            return (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método adicional para obtener la URL base sin el /api/ si es necesario
    public String GetBaseUrl() {
        if (isDebug()) {
            return "http://192.168.1.100:3000/"; // Sin /api/
        } else {
            return "https://tu-api-produccion.com/"; // Sin /api/
        }
    }

    // Método para verificar el modo (útil para logs)
    public String GetMode() {
        return isDebug() ? "DEBUG" : "PRODUCTION";
    }
}
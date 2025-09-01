package com.app.appalarmavecinal.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.appalarmavecinal.DB.DatabaseHelper;

public class GrupoDAO {
    private final DatabaseHelper dbHelper;

    public GrupoDAO(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    /**
     * Devuelve true si existe al menos 1 fila en la tabla grupo.
     */
    public boolean hayGrupoGuardado() {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = dbHelper.getReadableDatabase();
            String sql = "SELECT EXISTS(SELECT 1 FROM " + DatabaseHelper.TABLE_GRUPO + " LIMIT 1)";
            c = db.rawQuery(sql, null);
            boolean exists = false;
            if (c != null && c.moveToFirst()) {
                exists = (c.getInt(0) == 1);
            }
            return exists;
        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al verificar grupo: ", e);
            return false;
        } finally {
            if (c != null) c.close();
            if (db != null && db.isOpen()) db.close();
        }
    }

    /**
     * Obtiene el nombre del primer grupo guardado.
     */
    public String getNombreGrupo() {
        SQLiteDatabase db = null;
        Cursor c = null;
        String nombre = null;
        try {
            db = dbHelper.getReadableDatabase();
            c = db.query(
                    DatabaseHelper.TABLE_GRUPO,
                    new String[]{DatabaseHelper.COLUMN_GRUPO_NOMBRE},
                    null,
                    null,
                    null, null, null,
                    "1" // LIMIT 1
            );
            if (c != null && c.moveToFirst()) {
                nombre = c.getString(0);
            }
        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al obtener nombre del grupo: ", e);
        } finally {
            if (c != null) c.close();
            if (db != null && db.isOpen()) db.close();
        }
        return nombre;
    }
}

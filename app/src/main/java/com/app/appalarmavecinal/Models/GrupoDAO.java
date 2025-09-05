package com.app.appalarmavecinal.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.app.appalarmavecinal.DB.DatabaseHelper;
import java.util.UUID;

public class GrupoDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public GrupoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Crear nuevo grupo
    public boolean crearGrupo(String nombre, String descripcion, String idUsuario) {
        try {
            open();

            // Primero eliminar cualquier grupo existente
            database.delete(DatabaseHelper.TABLE_GRUPO, null, null);

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_GRUPO_ID, UUID.randomUUID().toString().replace("-",""));
            values.put(DatabaseHelper.COLUMN_GRUPO_USUARIO, idUsuario);
            values.put(DatabaseHelper.COLUMN_GRUPO_NOMBRE, nombre);
            values.put(DatabaseHelper.COLUMN_GRUPO_DESCRIPCION, descripcion);
            values.put(DatabaseHelper.COLUMN_GRUPO_CREATED_AT, String.valueOf(System.currentTimeMillis()));
            values.put(DatabaseHelper.COLUMN_GRUPO_UPDATED_AT, String.valueOf(System.currentTimeMillis()));

            long result = database.insert(DatabaseHelper.TABLE_GRUPO, null, values);
            close();

            return result != -1;

        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al crear grupo: " + e.getMessage());
            return false;
        }
    }

    // Obtener el grupo guardado
    public Grupo obtenerGrupo() {
        try {
            open();
            Cursor cursor = database.query(
                    DatabaseHelper.TABLE_GRUPO,
                    null,
                    null,
                    null,
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                Grupo grupo = cursorToGrupo(cursor);
                cursor.close();
                close();
                return grupo;
            }
            close();
            return null;
        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al obtener grupo: " + e.getMessage());
            return null;
        }
    }

    // Convertir Cursor a Grupo
    private Grupo cursorToGrupo(Cursor cursor) {
        Grupo grupo = new Grupo();
        grupo.setId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRUPO_ID)));
        grupo.setId_usuario(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRUPO_USUARIO)));
        grupo.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRUPO_NOMBRE)));
        grupo.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRUPO_DESCRIPCION)));
        grupo.setUpdated_at(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRUPO_UPDATED_AT)));
        grupo.setCreated_at(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRUPO_CREATED_AT)));
        return grupo;
    }

    // Verificar si hay grupo guardado
    public boolean hayGrupoGuardado() {
        try {
            open();
            Cursor cursor = database.query(
                    DatabaseHelper.TABLE_GRUPO,
                    new String[]{DatabaseHelper.COLUMN_GRUPO_ID},
                    null,
                    null,
                    null, null, null
            );
            boolean existe = cursor != null && cursor.getCount() > 0;
            if (cursor != null) cursor.close();
            close();
            return existe;
        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al verificar grupo: " + e.getMessage());
            return false;
        }
    }


    // Método para obtener el ID del grupo
    public String getIdGrupo() {
        try {
            open();
            Cursor cursor = database.query(
                    DatabaseHelper.TABLE_GRUPO,
                    new String[]{DatabaseHelper.COLUMN_GRUPO_ID},
                    null,
                    null,
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                String idGrupo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRUPO_ID));
                cursor.close();
                close();
                return idGrupo;
            }
            close();
            return null;
        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al obtener ID del grupo: " + e.getMessage());
            return null;
        }
    }


    // Obtener nombre del grupo
    public String getNombreGrupo() {
        try {
            open();
            Cursor cursor = database.query(
                    DatabaseHelper.TABLE_GRUPO,
                    new String[]{DatabaseHelper.COLUMN_GRUPO_NOMBRE},
                    null,
                    null,
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GRUPO_NOMBRE));
                cursor.close();
                close();
                return nombre;
            }
            close();
            return null;
        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al obtener nombre del grupo: " + e.getMessage());
            return null;
        }
    }

    // Eliminar todos los grupos
    public void eliminarGrupo() {
        try {
            open();
            database.delete(DatabaseHelper.TABLE_GRUPO, null, null);
            close();
        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al eliminar grupo: " + e.getMessage());
        }
    }


    // Método para obtener el grupo completo (incluyendo ID)
    public Grupo obtenerGrupoCompleto() {
        try {
            open();
            Cursor cursor = database.query(
                    DatabaseHelper.TABLE_GRUPO,
                    null, // Todas las columnas
                    null,
                    null,
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                Grupo grupo = cursorToGrupo(cursor);
                cursor.close();
                close();
                return grupo;
            }
            close();
            return null;
        } catch (Exception e) {
            Log.e("GrupoDAO", "Error al obtener grupo completo: " + e.getMessage());
            return null;
        }
    }
}
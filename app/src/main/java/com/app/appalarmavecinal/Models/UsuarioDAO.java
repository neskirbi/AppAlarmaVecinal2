package com.app.appalarmavecinal.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.appalarmavecinal.DB.DatabaseHelper;

public class UsuarioDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Guardar usuario (elimina primero cualquier usuario existente)
    public boolean guardarUsuario(Usuario usuario) {
        try {
            open();

            // Primero eliminar cualquier usuario existente
            database.delete(DatabaseHelper.TABLE_USUARIO, null, null);

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USUARIO_ID, usuario.getId_usuario());
            values.put(DatabaseHelper.COLUMN_USUARIO_GRUPO, usuario.getId_grupo());
            values.put(DatabaseHelper.COLUMN_USUARIO_MAIL, usuario.getMail());
            values.put(DatabaseHelper.COLUMN_USUARIO_PASS, usuario.getPass());
            values.put(DatabaseHelper.COLUMN_USUARIO_NOMBRES, usuario.getNombres());
            values.put(DatabaseHelper.COLUMN_USUARIO_APELLIDOS, usuario.getApellidos());
            values.put(DatabaseHelper.COLUMN_USUARIO_DIRECCION, usuario.getDireccion());

            // Ahora las fechas son Strings, así que las guardamos directamente
            values.put(DatabaseHelper.COLUMN_USUARIO_FECHA, usuario.getFecha());
            values.put(DatabaseHelper.COLUMN_USUARIO_ULT_LOGIN, usuario.getUlt_login());
            values.put(DatabaseHelper.COLUMN_USUARIO_UPDATED_AT, usuario.getUpdated_at());
            values.put(DatabaseHelper.COLUMN_USUARIO_CREATED_AT, usuario.getCreated_at());

            long result = database.insert(DatabaseHelper.TABLE_USUARIO, null, values);
            close();

            return result != -1;

        } catch (Exception e) {
            Log.e("UsuarioDAO", "Error al guardar usuario: " + e.getMessage());
            return false;
        }
    }

    // Obtener el usuario guardado
    public Usuario obtenerUsuario() {
        try {
            open();
            Cursor cursor = database.query(
                    DatabaseHelper.TABLE_USUARIO,
                    null,
                    null,
                    null,
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                Usuario usuario = cursorToUsuario(cursor);
                cursor.close();
                close();
                return usuario;
            }
            close();
            return null;
        } catch (Exception e) {
            Log.e("UsuarioDAO", "Error al obtener usuario: " + e.getMessage());
            return null;
        }
    }

    // Convertir Cursor a Usuario
    private Usuario cursorToUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();

        usuario.setId_usuario(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_ID)));
        usuario.setId_grupo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_GRUPO)));
        usuario.setMail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_MAIL)));
        usuario.setPass(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_PASS)));
        usuario.setNombres(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_NOMBRES)));
        usuario.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_APELLIDOS)));
        usuario.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_DIRECCION)));

        // Ahora las fechas son Strings, así que las leemos directamente
        usuario.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_FECHA)));
        usuario.setUlt_login(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_ULT_LOGIN)));
        usuario.setUpdated_at(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_UPDATED_AT)));
        usuario.setCreated_at(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_CREATED_AT)));

        return usuario;
    }

    // Verificar si hay usuario guardado
    public boolean hayUsuarioGuardado() {
        try {
            open();
            Cursor cursor = database.query(
                    DatabaseHelper.TABLE_USUARIO,
                    new String[]{DatabaseHelper.COLUMN_USUARIO_ID},
                    null,
                    null,
                    null, null, null
            );
            boolean existe = cursor != null && cursor.getCount() > 0;
            if (cursor != null) cursor.close();
            close();
            return existe;
        } catch (Exception e) {
            Log.e("UsuarioDAO", "Error al verificar usuario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar todos los usuarios
    public void eliminarUsuario() {
        try {
            open();
            database.delete(DatabaseHelper.TABLE_USUARIO, null, null);
            close();
        } catch (Exception e) {
            Log.e("UsuarioDAO", "Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Método para actualizar solo algunos campos
    public boolean actualizarUsuario(String idUsuario, ContentValues valores) {
        try {
            open();
            int result = database.update(
                    DatabaseHelper.TABLE_USUARIO,
                    valores,
                    DatabaseHelper.COLUMN_USUARIO_ID + " = ?",
                    new String[]{idUsuario}
            );
            close();
            return result > 0;
        } catch (Exception e) {
            Log.e("UsuarioDAO", "Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }
}
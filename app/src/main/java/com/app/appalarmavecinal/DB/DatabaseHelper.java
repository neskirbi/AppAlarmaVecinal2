package com.app.appalarmavecinal.DB.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alarma_vecinal.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla Usuario
    public static final String TABLE_USUARIO = "usuario";
    public static final String COLUMN_USUARIO_ID = "id_usuario";
    public static final String COLUMN_USUARIO_GRUPO = "id_grupo";
    public static final String COLUMN_USUARIO_NOMBRES = "nombres";
    public static final String COLUMN_USUARIO_APELLIDOS = "apellidos";
    public static final String COLUMN_USUARIO_MAIL = "mail";
    public static final String COLUMN_USUARIO_PASS = "pass";
    public static final String COLUMN_USUARIO_DIRECCION = "direccion";
    public static final String COLUMN_USUARIO_FECHA = "fecha";
    public static final String COLUMN_USUARIO_ULT_LOGIN = "ult_login";
    public static final String COLUMN_USUARIO_UPDATED_AT = "updated_at";
    public static final String COLUMN_USUARIO_CREATED_AT = "created_at";

    // Tabla Grupo
    public static final String TABLE_GRUPO = "grupo";
    public static final String COLUMN_GRUPO_ID = "id";
    public static final String COLUMN_GRUPO_USUARIO = "id_usuario";
    public static final String COLUMN_GRUPO_NOMBRE = "nombre";
    public static final String COLUMN_GRUPO_DESCRIPCION = "descripcion";
    public static final String COLUMN_GRUPO_UPDATED_AT = "updated_at";
    public static final String COLUMN_GRUPO_CREATED_AT = "created_at";

    // Sentencias SQL para crear tablas
    private static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE " + TABLE_USUARIO + " (" +
                    COLUMN_USUARIO_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_USUARIO_GRUPO + " TEXT, " +
                    COLUMN_USUARIO_NOMBRES + " TEXT, " +
                    COLUMN_USUARIO_APELLIDOS + " TEXT, " +
                    COLUMN_USUARIO_MAIL + " TEXT NOT NULL, " +
                    COLUMN_USUARIO_PASS + " TEXT NOT NULL, " +
                    COLUMN_USUARIO_DIRECCION + " TEXT, " +
                    COLUMN_USUARIO_FECHA + " DATETIME, " +
                    COLUMN_USUARIO_ULT_LOGIN + " DATETIME NOT NULL DEFAULT '2020-01-01 00:00:00', " +
                    COLUMN_USUARIO_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    COLUMN_USUARIO_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    private static final String CREATE_TABLE_GRUPO =
            "CREATE TABLE " + TABLE_GRUPO + " (" +
                    COLUMN_GRUPO_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_GRUPO_USUARIO + " TEXT, " +
                    COLUMN_GRUPO_NOMBRE + " TEXT, " +
                    COLUMN_GRUPO_DESCRIPCION + " TEXT, " +
                    COLUMN_GRUPO_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    COLUMN_GRUPO_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    // Crear índices
    private static final String CREATE_INDEX_MAIL =
            "CREATE INDEX idx_usuario_mail ON " + TABLE_USUARIO + "(" + COLUMN_USUARIO_MAIL + ");";

    private static final String CREATE_INDEX_GRUPO_USUARIO =
            "CREATE INDEX idx_grupo_usuario ON " + TABLE_GRUPO + "(" + COLUMN_GRUPO_USUARIO + ");";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_USUARIO);
            db.execSQL(CREATE_TABLE_GRUPO);
            db.execSQL(CREATE_INDEX_MAIL);
            db.execSQL(CREATE_INDEX_GRUPO_USUARIO);
            Log.d("DatabaseHelper", "Base de datos creada exitosamente");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error al crear la base de datos: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRUPO);
            db.execSQL("DROP INDEX IF EXISTS idx_usuario_mail");
            db.execSQL("DROP INDEX IF EXISTS idx_grupo_usuario");
            onCreate(db);
            Log.d("DatabaseHelper", "Base de datos actualizada a la versión: " + newVersion);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error al actualizar la base de datos: " + e.getMessage());
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Métodos utilitarios para obtener nombres de columnas
    public static String[] getUsuarioColumns() {
        return new String[]{
                COLUMN_USUARIO_ID,
                COLUMN_USUARIO_GRUPO,
                COLUMN_USUARIO_NOMBRES,
                COLUMN_USUARIO_APELLIDOS,
                COLUMN_USUARIO_MAIL,
                COLUMN_USUARIO_PASS,
                COLUMN_USUARIO_DIRECCION,
                COLUMN_USUARIO_FECHA,
                COLUMN_USUARIO_ULT_LOGIN,
                COLUMN_USUARIO_UPDATED_AT,
                COLUMN_USUARIO_CREATED_AT
        };
    }

    public static String[] getGrupoColumns() {
        return new String[]{
                COLUMN_GRUPO_ID,
                COLUMN_GRUPO_USUARIO,
                COLUMN_GRUPO_NOMBRE,
                COLUMN_GRUPO_DESCRIPCION,
                COLUMN_GRUPO_UPDATED_AT,
                COLUMN_GRUPO_CREATED_AT
        };
    }
}
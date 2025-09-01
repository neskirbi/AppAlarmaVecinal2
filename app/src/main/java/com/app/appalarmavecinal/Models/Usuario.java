package com.app.appalarmavecinal.Models;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    // Campos principales
    @SerializedName("id_usuario")
    private String id_usuario;

    @SerializedName("id_grupo")
    private String id_grupo;

    @SerializedName("nombres")
    private String nombres;

    @SerializedName("apellidos")
    private String apellidos;

    @SerializedName("mail")
    private String mail;

    @SerializedName("pass")
    private String pass;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("ult_login")
    private String ult_login;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("created_at")
    private String created_at;

    // Constructor vacío (necesario para Retrofit/GSON)
    public Usuario() {
    }

    // Constructor completo
    public Usuario(String id_usuario, String id_grupo, String nombres, String apellidos,
                   String mail, String pass, String direccion, String fecha,
                   String ult_login, String updated_at, String created_at) {
        this.id_usuario = id_usuario;
        this.id_grupo = id_grupo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.mail = mail;
        this.pass = pass;
        this.direccion = direccion;
        this.fecha = fecha;
        this.ult_login = ult_login;
        this.updated_at = updated_at;
        this.created_at = created_at;
    }

    // Getters y Setters
    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(String id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUlt_login() {
        return ult_login;
    }

    public void setUlt_login(String ult_login) {
        this.ult_login = ult_login;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    // Método toString para debugging
    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario='" + id_usuario + '\'' +
                ", id_grupo='" + id_grupo + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", mail='" + mail + '\'' +
                ", pass='" + pass + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", ult_login='" + ult_login + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
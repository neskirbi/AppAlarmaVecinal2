package com.app.appalarmavecinal.Models;

import java.util.Date;

public class Usuario {
    // Campos principales
    private String id_usuario;
    private String id_grupo;
    private String mail;
    private String pass;
    private Date ult_login;
    private Date updated_at;

    // Campos opcionales (pueden ser null)
    private String nombres;
    private String apellidos;
    private String direccion;
    private Date fecha;

    // Constructor vacío (necesario para Retrofit/GSON)
    public Usuario() {
    }

    // Constructor completo
    public Usuario(String id_usuario, String id_grupo, String mail, String pass,
                   Date ult_login, Date updated_at, String nombres, String apellidos,
                   String direccion, Date fecha) {
        this.id_usuario = id_usuario;
        this.id_grupo = id_grupo;
        this.mail = mail;
        this.pass = pass;
        this.ult_login = ult_login;
        this.updated_at = updated_at;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fecha = fecha;
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

    public Date getUlt_login() {
        return ult_login;
    }

    public void setUlt_login(Date ult_login) {
        this.ult_login = ult_login;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    // Método toString para debugging
    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario='" + id_usuario + '\'' +
                ", id_grupo='" + id_grupo + '\'' +
                ", mail='" + mail + '\'' +
                ", pass='" + pass + '\'' +
                ", ult_login=" + ult_login +
                ", updated_at=" + updated_at +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
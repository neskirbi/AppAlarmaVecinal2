package com.app.appalarmavecinal.Models;

public class Grupo {
    private String id;
    private String id_usuario;
    private String nombre;
    private String descripcion;
    private String updated_at;
    private String created_at;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getId_usuario() { return id_usuario; }
    public void setId_usuario(String id_usuario) { this.id_usuario = id_usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
}
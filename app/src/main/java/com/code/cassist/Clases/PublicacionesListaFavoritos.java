package com.code.cassist.Clases;

public class PublicacionesListaFavoritos {

    String id_pub;
    String titulo;
    String imagen;
    String descripcion;
    String telefono;
    String direccion;
    String email;
    String fecha_creacion;
    String id_usuario;

    public PublicacionesListaFavoritos(String id_pub, String titulo, String imagen, String descripcion, String telefono, String direccion, String email, String fecha_creacion, String id_usuario) {
        this.id_pub = id_pub;
        this.titulo = titulo;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.fecha_creacion = fecha_creacion;
        this.id_usuario = id_usuario;
    }

    public String getId_pub() {
        return id_pub;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public String getId_usuario() {
        return id_usuario;
    }
}

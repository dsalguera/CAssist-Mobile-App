package com.code.cassist.Clases;

public class Usuario {

    String id_usuario;
    String nombre_usuario;
    String contrasena;
    String tipoUsuario;
    String imagen;
    String primerNombre;
    String segundoNombre;
    String primerApellido;
    String segundoApellido;
    String email;
    String celular;
    String telefono;
    String fecha_nac;
    String fecha_creacion_cta;

    public Usuario(String id_usuario, String nombre_usuario, String contrasena, String tipoUsuario, String imagen, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String email, String celular, String telefono, String fecha_nac, String fecha_creacion_cta) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.imagen = imagen;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.email = email;
        this.celular = celular;
        this.telefono = telefono;
        this.fecha_nac = fecha_nac;
        this.fecha_creacion_cta = fecha_creacion_cta;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getImagen() {
        return imagen;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getEmail() {
        return email;
    }

    public String getCelular() {
        return celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public String getFecha_creacion_cta() {
        return fecha_creacion_cta;
    }
}

package com.universidad.mia_proyecto1.modelo;

public class Usuario {
    String tipo;
    String username;
    String sucursal;

    public Usuario(String tipo, String username, String sucursal) {
        this.tipo = tipo;
        this.username = username;
        this.sucursal = sucursal;
    }
    
    public String getTipo() {
        return tipo;
    }

    public String getUsername() {
        return username;
    }

    public String getSucursal() {
        return sucursal;
    }
}

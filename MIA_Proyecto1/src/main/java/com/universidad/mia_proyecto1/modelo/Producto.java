package com.universidad.mia_proyecto1.modelo;

public class Producto {
    String codigo;
    String nombre;
    Float precio;
    
    public Producto(String codigo, String nombre, Float precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Float getPrecio() {
        return precio;
    }
}

package com.universidad.mia_proyecto1.modelo;

public class ProductoIngresado {
    int id;
    String codigo;
    String nombre;
    
    public ProductoIngresado(int id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}

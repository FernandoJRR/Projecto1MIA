package com.universidad.mia_proyecto1.modelo;

public class ProductoSucursal {
    String id;
    String codigo;
    String nombre;
    
    public ProductoSucursal(String id, String codigo, String nombre){
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public String getId() {
        return id;
    }
    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
}

package com.universidad.mia_proyecto1.modelo;

public class ProductoVenta {
    Integer id;
    String codigo;
    String nombre;
    Float precio;
    
    public ProductoVenta(Integer id, String codigo, String nombre, Float precio){
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public Integer getId() {
        return id;
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

package com.universidad.mia_proyecto1.modelo;

public class ProductoUbicacion {
    String id;
    String codigo;
    String nombre;
    String ubicacion;
    
    public ProductoUbicacion(String id,String codigo,String nombre,String ubicacion){
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
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
    public String getUbicacion() {
        return ubicacion;
    }
}

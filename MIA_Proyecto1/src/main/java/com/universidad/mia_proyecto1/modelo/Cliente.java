package com.universidad.mia_proyecto1.modelo;

public class Cliente {
    String nit;
    String nombre;
    
    public Cliente(String nit, String nombre){
        this.nit = nit;
        this.nombre = nombre;
    }
    
    public String getNit() {
        return nit;
    }
    public String getNombre() {
        return nombre;
    }
}

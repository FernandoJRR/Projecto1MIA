package com.universidad.mia_proyecto1.modelo;

public class Trio {
    String primero;
    String segundo;
    String tercero;
    
    public Trio(String primero, String segundo, String tercero){
        this.primero = primero;
        this.segundo = segundo;
        this.tercero = tercero;
    }
    
    public String getPrimero() {
        return primero;
    }
    public String getSegundo() {
        return segundo;
    }
    public String getTercero() {
        return tercero;
    }
}

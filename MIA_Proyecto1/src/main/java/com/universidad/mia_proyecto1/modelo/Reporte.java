package com.universidad.mia_proyecto1.modelo;

import java.util.ArrayList;
import java.util.List;

public class Reporte {
    List<List<String>> campos;
    List<String> nombresCampos;
    
    public List<List<String>> getCampos() {
        return campos;
    }

    public List<String> getNombresCampos() {
        return nombresCampos;
    }
    
    public Reporte(List<String> campos) {
        nombresCampos = campos;
        this.campos = new ArrayList<List<String>>();
        for (int i = 0; i < campos.size(); i++) {
            this.campos.add(new ArrayList<String>());
        }
    }
    
    public List<String> getFila(int numeroFila){
        List<String> contenido = new ArrayList<String>();
        
        for (List<String> campo : campos) {
            contenido.add(campo.get(numeroFila));
        }
        
        return contenido;
    }
    
    public void addFila(List<String> contenido) {
        for (int i = 0; i < contenido.size(); i++) {
            campos.get(i).add(contenido.get(i));
        }
    }
}

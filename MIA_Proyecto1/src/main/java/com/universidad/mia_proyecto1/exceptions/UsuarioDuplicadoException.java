package com.universidad.mia_proyecto1.exceptions;

public class UsuarioDuplicadoException extends Exception{
    public UsuarioDuplicadoException(String username){
        super("El usuario: "+username+" ya existe");
    }
}

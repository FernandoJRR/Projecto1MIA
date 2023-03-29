package com.universidad.mia_proyecto1.exceptions;

public class ClienteDuplicadoException extends Exception{
    public ClienteDuplicadoException(String nit){
        super("Un cliente con el NIT "+nit+" ya existe");
    }   
}

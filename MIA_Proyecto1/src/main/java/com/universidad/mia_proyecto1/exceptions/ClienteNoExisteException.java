package com.universidad.mia_proyecto1.exceptions;

public class ClienteNoExisteException extends Exception{
    public ClienteNoExisteException(String nit){
        super("El cliente con el NIT "+nit+" no existe");
    }
}

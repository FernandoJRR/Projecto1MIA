package com.universidad.mia_proyecto1.exceptions;

public class UsuarioNoExisteException extends Exception {
    public UsuarioNoExisteException(String username) {
        super(username);
    }
}

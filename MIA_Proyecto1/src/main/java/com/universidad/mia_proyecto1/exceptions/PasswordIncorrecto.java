package com.universidad.mia_proyecto1.exceptions;

public class PasswordIncorrecto extends Exception {
    public PasswordIncorrecto(){
        super("El password ingresado es incorrecto");
    }
}

package com.universidad.mia_proyecto1.exceptions;

public class ProductoDentroException extends Exception{
    public ProductoDentroException(int id){
        super(String.valueOf(id));
    }
}

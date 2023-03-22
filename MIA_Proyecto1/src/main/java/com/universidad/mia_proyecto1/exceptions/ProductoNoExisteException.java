package com.universidad.mia_proyecto1.exceptions;

public class ProductoNoExisteException extends Exception{
    public ProductoNoExisteException(String codigoProducto){
        super("El producto con codigo: "+codigoProducto+" no existe");
    }
}

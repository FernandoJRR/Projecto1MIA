package com.universidad.mia_proyecto1.exceptions;

public class ProductoDuplicadoException extends Exception{
    public ProductoDuplicadoException(String codigoProducto){
        super("El producto con codigo: "+codigoProducto+" ya existe");
    }
}

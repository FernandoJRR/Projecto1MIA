package com.universidad.mia_proyecto1.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.universidad.mia_proyecto1.exceptions.ProductoDentroException;
import com.universidad.mia_proyecto1.exceptions.ProductoNoExisteException;
import com.universidad.mia_proyecto1.modelo.ProductoSucursal;
import com.universidad.mia_proyecto1.modelo.ProductoUbicacion;

public class ModeloInventario {
    public static List<ProductoUbicacion> getProductosInventario(String sucursalActual) throws SQLException, Exception {
        Conexion.crearConexion("gestor_inventario01", "inventario01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("""
        SELECT id, codigo_producto, nombre, sucursal, en_bodega 
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON codigo_producto = codigo
          WHERE codigo_venta IS NULL AND (sucursal != ? OR sucursal IS NULL)
        """);
        select.setString(1, sucursalActual);

        ResultSet resultado = select.executeQuery();
        
        List<ProductoUbicacion> productosList = new ArrayList<ProductoUbicacion>();
        while (resultado.next()) {
            if (resultado.getBoolean(5)) {
                productosList.add(new ProductoUbicacion(resultado.getString(1), resultado.getString(2), resultado.getString(3), "Bodega"));
            } else {
                productosList.add(new ProductoUbicacion(resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4)));
            }
        }
        
        return productosList;
    }
    
    public static List<ProductoSucursal> getProductosSucursal(String sucursalActual) throws SQLException, Exception {
        Conexion.crearConexion("gestor_inventario01", "inventario01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("""
        SELECT id, codigo_producto, nombre
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON codigo_producto = codigo
          WHERE codigo_venta IS NULL AND sucursal = ?
        """);
        select.setString(1, sucursalActual);

        ResultSet resultado = select.executeQuery();
        
        List<ProductoSucursal> productosList = new ArrayList<ProductoSucursal>();
        while (resultado.next()) {
            productosList.add(new ProductoSucursal(resultado.getString(1), resultado.getString(2), resultado.getString(3)));
        }
        
        return productosList;
    }
    
    public static void moverProducto(String sucursalActual, int idProducto) throws ProductoNoExisteException,ProductoDentroException,SQLException,Exception {
        //Se comprueba que el id del producto existe
        Conexion.crearConexion("gestor_inventario01", "inventario01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("""
        SELECT *
          FROM control_productos.producto_ingresado
          WHERE codigo_venta IS NULL AND id = ?
        """);
        select.setInt(1, idProducto);

        ResultSet producto = select.executeQuery();
        if (producto.next()) {
            String ubicacion = producto.getString(3);
            boolean enBodega = producto.getBoolean(4);
            //Si existe se comprueba si esta fuera de la sucursal
            if (!enBodega) {
                //Si no esta en bodega se comprueba la sucursal
                if (ubicacion.equals(sucursalActual)) {
                    throw new ProductoDentroException(idProducto);
                }
            } 
        } else {
            //Si no existe se envia un error
            throw new ProductoNoExisteException(String.valueOf(idProducto));
        }
        //Se mueve el producto
        Conexion.crearConexion("gestor_inventario01", "inventario01");
        java.sql.PreparedStatement update = Conexion.databaseConnection.prepareStatement("""
        UPDATE control_productos.producto_ingresado
          SET sucursal = ?,
          en_bodega = false
          WHERE id = ?
        """);
        update.setString(1, sucursalActual);
        update.setInt(2, idProducto);

        update.executeUpdate();
    }
}

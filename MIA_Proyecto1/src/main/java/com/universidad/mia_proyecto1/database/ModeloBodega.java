package com.universidad.mia_proyecto1.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.universidad.mia_proyecto1.exceptions.ProductoDuplicadoException;
import com.universidad.mia_proyecto1.exceptions.ProductoNoExisteException;
import com.universidad.mia_proyecto1.modelo.Producto;
import com.universidad.mia_proyecto1.modelo.ProductoIngresado;

public class ModeloBodega {
    public static void ingresarProductoExistente(String codigo, int cantidad) throws SQLException, ProductoNoExisteException, Exception{
        //Se comprueba si el codigo del producto ya existe
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_productos.producto WHERE codigo = ?");
        select.setString(1, codigo);

        ResultSet resultado = select.executeQuery();
        if (!resultado.next()) {
            throw new ProductoNoExisteException(codigo);
        } 
        for (int i = 0; i < cantidad; i++) {
            Conexion.crearConexion("gestor_bodega01", "bodega_01");
            java.sql.PreparedStatement insert = Conexion.databaseConnection.prepareStatement("""
            INSERT INTO control_productos.producto_ingresado ( 
              codigo_producto, 
              sucursal, 
              en_bodega, 
              codigo_venta 
            ) VALUES 
            ( ?, null, true, null );
            """);
            insert.setString(1, codigo);
            insert.executeUpdate();
        }
    }

    public static void ingresarProductoNuevo(String codigo, String nombre, float precio, int cantidad) throws SQLException, ProductoDuplicadoException, Exception{
        //Se comprueba si el codigo del producto ya existe
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_productos.producto WHERE codigo = ?");
        select.setString(1, codigo);

        ResultSet resultado = select.executeQuery();
        if (resultado.next()) {
            throw new ProductoDuplicadoException(codigo);
        } 
        //Se agrega el producto nuevo
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement insertProducto = Conexion.databaseConnection.prepareStatement("""
        INSERT INTO control_productos.producto ( codigo, nombre, precio ) VALUES 
        ( ?, ?, ? )
        """);
        insertProducto.setString(1, codigo);
        insertProducto.setString(2, nombre);
        insertProducto.setFloat(3, precio);
        insertProducto.executeUpdate();

        //Se agregan las unidades del producto
        for (int i = 0; i < cantidad; i++) {
            Conexion.crearConexion("gestor_bodega01", "bodega_01");
            java.sql.PreparedStatement insert = Conexion.databaseConnection.prepareStatement("""
            INSERT INTO control_productos.producto_ingresado ( 
              codigo_producto, 
              sucursal, 
              en_bodega, 
              codigo_venta 
            ) VALUES 
            ( ?, null, true, null )
            """);
            insert.setString(1, codigo);
            insert.executeUpdate();
        }
    }
    
    public static void modificarCodigoProducto(String antiguoCodigo, String nuevoCodigo) throws SQLException, ProductoNoExisteException, ProductoDuplicadoException, Exception{
        //Se comprueba si el codigo a cambiar del producto existe
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement selectViejo = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_productos.producto WHERE codigo = ?");
        selectViejo.setString(1, antiguoCodigo);

        ResultSet resultadoViejo = selectViejo.executeQuery();
        if (!resultadoViejo.next()) {
            throw new ProductoNoExisteException(antiguoCodigo);
        }

        //Se comprueba si el codigo nuevo del producto existe
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement selectNuevo = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_productos.producto WHERE codigo = ?");
        selectNuevo.setString(1, nuevoCodigo);

        ResultSet resultadoNuevo = selectNuevo.executeQuery();
        if (resultadoNuevo.next()) {
            throw new ProductoDuplicadoException(nuevoCodigo);
        }
        
        //Se modifica el codigo
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement insertProducto = Conexion.databaseConnection.prepareStatement("""
        UPDATE control_productos.producto
          SET codigo = ?
          WHERE codigo = ?
        """);
        insertProducto.setString(1, nuevoCodigo);
        insertProducto.setString(2, antiguoCodigo);
        insertProducto.executeUpdate();
    }

    public static void modificarNombreProducto(String codigo, String nuevoNombre) throws SQLException, ProductoNoExisteException, Exception{
        //Se comprueba si el codigo a cambiar del producto existe
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement selectViejo = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_productos.producto WHERE codigo = ?");
        selectViejo.setString(1, codigo);

        ResultSet resultadoViejo = selectViejo.executeQuery();
        if (!resultadoViejo.next()) {
            throw new ProductoNoExisteException(codigo);
        }

        //Se modifica el codigo
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement insertProducto = Conexion.databaseConnection.prepareStatement("""
        UPDATE control_productos.producto
          SET nombre = ?
          WHERE codigo = ?
        """);
        insertProducto.setString(1, nuevoNombre);
        insertProducto.setString(2, codigo);
        insertProducto.executeUpdate();
    }

    public static void modificarPrecioProducto(String codigo, float nuevoPrecio) throws SQLException, ProductoNoExisteException, Exception{
        //Se comprueba si el codigo a cambiar del producto existe
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement selectViejo = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_productos.producto WHERE codigo = ?");
        selectViejo.setString(1, codigo);

        ResultSet resultadoViejo = selectViejo.executeQuery();
        if (!resultadoViejo.next()) {
            throw new ProductoNoExisteException(codigo);
        }

        //Se modifica el codigo
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.PreparedStatement insertProducto = Conexion.databaseConnection.prepareStatement("""
        UPDATE control_productos.producto
          SET precio = ?
          WHERE codigo = ?
        """);
        insertProducto.setFloat(1, nuevoPrecio);
        insertProducto.setString(2, codigo);
        insertProducto.executeUpdate();
    }
    
    public static List<ProductoIngresado> getProductosIngresados() throws SQLException, Exception{
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT id, codigo, nombre 
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON codigo_producto = codigo
          WHERE en_bodega = true;
        """);
        
        List<ProductoIngresado> productosList = new ArrayList<ProductoIngresado>();
        while (resultado.next()) {
            productosList.add(new ProductoIngresado(resultado.getInt(1), resultado.getString(2), resultado.getString(3)));
        }
        
        return productosList;
    }
    
    public static List<Producto> getProductos() throws SQLException, Exception{
        Conexion.crearConexion("gestor_bodega01", "bodega_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT codigo,nombre,precio 
          FROM control_productos.producto
        """);
        
        List<Producto> productosList = new ArrayList<Producto>();
        while (resultado.next()) {
            productosList.add(new Producto(resultado.getString(1), resultado.getString(2), ((Float)resultado.getFloat(3))));
        }
        
        return productosList;
    }
}

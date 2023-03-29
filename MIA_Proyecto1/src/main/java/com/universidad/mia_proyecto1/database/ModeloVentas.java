package com.universidad.mia_proyecto1.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.universidad.mia_proyecto1.exceptions.ClienteDuplicadoException;
import com.universidad.mia_proyecto1.exceptions.ClienteNoExisteException;
import com.universidad.mia_proyecto1.modelo.Cliente;
import com.universidad.mia_proyecto1.modelo.ProductoVenta;

public class ModeloVentas {
    public static List<Cliente> getClientes() throws SQLException,Exception {
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT *
            FROM control_clientes.cliente
        """);
        
        List<Cliente> productosList = new ArrayList<Cliente>();
        while (resultado.next()) {
            productosList.add(new Cliente(resultado.getString(1), resultado.getString(2)));
        }
        
        return productosList;
    }

    public static Cliente getCliente(String nit) throws SQLException, Exception{
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_clientes.cliente WHERE nit = ?");
        select.setInt(1, Integer.valueOf(nit));

        ResultSet resultado = select.executeQuery();
        if (resultado.next()) {
            return new Cliente(resultado.getString(1), resultado.getString(2));
        } else {
            return null;
        }
    }
    
    public static void crearVenta(String nitCliente, String vendedor, List<Integer> idProductos) throws SQLException,Exception {
        int descuento;
        //Se calcula si existe un descuento
        //Se obtiene la ultima venta realizada por el cliente
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement ultimaVenta = Conexion.databaseConnection.prepareStatement("""
        SELECT *
          FROM control_ventas.venta
          WHERE cliente = ?
          ORDER BY codigo DESC
          LIMIT 1
        """);
        ultimaVenta.setInt(1, Integer.valueOf(nitCliente));
        ResultSet resultado = ultimaVenta.executeQuery();
        //Si existe una ultima venta se calcula el total
        if (resultado.next()) {
            Conexion.crearConexion("vendedor01", "vendedor01");
            java.sql.PreparedStatement total = Conexion.databaseConnection.prepareStatement("""
            SELECT SUM(precio) as total
              FROM control_productos.producto_ingresado
              JOIN control_productos.producto
              ON codigo_producto = codigo
              WHERE codigo_venta = ?
            """);
            total.setInt(1, Integer.valueOf(resultado.getInt(1)));
            ResultSet totalResultado = total.executeQuery();
            totalResultado.next();
            Float totalUltimaVenta = totalResultado.getFloat(1);
            //Se calcula si habra o no un descuento
            if (totalUltimaVenta >= 10000) {
                descuento = 10;
            } else if(totalUltimaVenta >= 5000){
                descuento = 5;
            } else if(totalUltimaVenta >= 1000){
                descuento = 2;
            } else {
                descuento = 0;
            }
        } else {
            //Si no hay ultima venta no se aplica descuento
            descuento = 0;
        }
        //Se inserta la venta
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement insert = Conexion.databaseConnection.prepareStatement("""
        INSERT INTO control_ventas.venta ( vendedor, cliente, descuento ) 
            VALUES ( ?, ?, ? ) 
        """);
        insert.setString(1, vendedor);
        insert.setInt(2, Integer.valueOf(nitCliente));
        insert.setFloat(3, descuento);
        
        insert.executeUpdate();
        
        //Se obtiene la ultima venta
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement ultimaVentaRealizada = Conexion.databaseConnection.prepareStatement("""
        SELECT *
          FROM control_ventas.venta
          ORDER BY codigo DESC
          LIMIT 1
        """);
        ResultSet resultadoVenta = ultimaVentaRealizada.executeQuery();
        resultadoVenta.next();
        Integer idVenta = resultadoVenta.getInt(1);
        
        //Se agregan los productos a la venta
        for (Integer id : idProductos) {
            Conexion.crearConexion("vendedor01", "vendedor01");
            java.sql.PreparedStatement updateProducto = Conexion.databaseConnection.prepareStatement("""
            UPDATE control_productos.producto_ingresado
              SET codigo_venta = ?,
              sucursal = null
              WHERE id = ?
            """);
            updateProducto.setInt(1, idVenta);
            updateProducto.setInt(2, id);

            updateProducto.executeUpdate();
        }
    }
    
    public static void crearVentaCF(String vendedor, List<Integer> idProductos) throws SQLException, Exception {
        //Se inserta la venta
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement insert = Conexion.databaseConnection.prepareStatement("""
        INSERT INTO control_ventas.venta ( vendedor, cliente, descuento ) 
            VALUES ( ?, null, 0 ) 
        """);
        insert.setString(1, vendedor);
        insert.executeUpdate();
        
        //Se obtiene la ultima venta para insertarla en los productos
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement ultimaVentaRealizada = Conexion.databaseConnection.prepareStatement("""
        SELECT *
          FROM control_ventas.venta
          ORDER BY codigo DESC
          LIMIT 1
        """);
        ResultSet resultadoVenta = ultimaVentaRealizada.executeQuery();
        resultadoVenta.next();
        Integer idVenta = resultadoVenta.getInt(1);
        
        //Se agregan los productos a la venta
        for (Integer id : idProductos) {
            Conexion.crearConexion("vendedor01", "vendedor01");
            java.sql.PreparedStatement updateProducto = Conexion.databaseConnection.prepareStatement("""
            UPDATE control_productos.producto_ingresado
              SET codigo_venta = ?,
              sucursal = null
              WHERE id = ?
            """);
            updateProducto.setInt(1, idVenta);
            updateProducto.setInt(2, id);

            updateProducto.executeUpdate();
        }
    }
    
    public static void crearCliente(String nit, String nombre) throws SQLException,Exception {
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement create = Conexion.databaseConnection.prepareStatement("""
        INSERT INTO control_clientes.cliente ( nit, nombre ) 
            VALUES ( ?, ? )
        """);
        
        create.setInt(1, Integer.valueOf(nit));
        create.setString(2, nombre);
        
        create.executeUpdate();
    }
    
    public static void crearVentaClienteNuevo(String nitCliente, String nombre, String vendedor, List<Integer> idProductos) throws SQLException, Exception {
        crearCliente(nitCliente, nombre);;
        crearVenta(nitCliente, nombre, idProductos);
    }
    
    public static List<ProductoVenta> getProductosSucursal(String sucursal) throws SQLException, Exception {
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("""
        SELECT id, codigo, nombre, precio
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON codigo_producto = codigo
          WHERE sucursal = ?
        """);
        select.setString(1, sucursal);

        ResultSet resultado = select.executeQuery();
        
        List<ProductoVenta> productosList = new ArrayList<ProductoVenta>();
        while (resultado.next()) {
            productosList.add(new ProductoVenta(resultado.getInt(1), resultado.getString(2), resultado.getString(3), resultado.getFloat(4)));
        }
        
        return productosList;
    }
    
    public static void modificarNIT(String nitAntiguo, String nuevoNit) throws SQLException, ClienteDuplicadoException, ClienteNoExisteException, Exception {
        //Se comprueba que el nit de origen existe
        Cliente clienteOrigen = getCliente(nitAntiguo);
        if (clienteOrigen == null) {
            //Si no existe se envia un error
            throw new ClienteNoExisteException(nitAntiguo);
        }

        //Se comprueba que el nit de destino NO existe
        Cliente clienteDestino = getCliente(nuevoNit);
        if (clienteDestino != null) {
            //Si existe se envia un error
            throw new ClienteDuplicadoException(nuevoNit);
        }
        
        //Si las comprobaciones se pasan se modifica el cliente
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement update = Conexion.databaseConnection.prepareStatement("""
        UPDATE control_clientes.cliente
          SET nit = ?
          WHERE nit = ?
        """);
        update.setInt(1, Integer.valueOf(nuevoNit));
        update.setInt(2, Integer.valueOf(nitAntiguo));

        update.executeUpdate();
    }
    
    public static void modificarNombre(String nit, String nuevoNombre) throws SQLException, ClienteNoExisteException, Exception {
        //Se comprueba que el nit existe
        Cliente clienteOrigen = getCliente(nit);
        if (clienteOrigen == null) {
            //Si no existe se envia un error
            throw new ClienteNoExisteException(nit);
        }

        //Si las comprobaciones se pasan se modifica el cliente
        Conexion.crearConexion("vendedor01", "vendedor01");
        java.sql.PreparedStatement update = Conexion.databaseConnection.prepareStatement("""
        UPDATE control_clientes.cliente
          SET nombre = ?
          WHERE nit = ?
        """);
        update.setString(1, nuevoNombre);
        update.setInt(2, Integer.valueOf(nit));

        update.executeUpdate();
    }
}

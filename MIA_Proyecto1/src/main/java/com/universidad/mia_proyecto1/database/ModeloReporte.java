package com.universidad.mia_proyecto1.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.universidad.mia_proyecto1.modelo.Reporte;

public class ModeloReporte {
    public static void printReporte(Reporte reporte){
        try {
            for (String nombre : reporte.getNombresCampos()) {
                System.out.print(nombre+" ");
            }
            System.out.println();
            
            for (int i = 0; i < reporte.getCampos().get(0).size(); i++) {
                List<String> fila = reporte.getFila(i);
                for (String dato : fila) {
                    System.out.print(dato+" ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Reporte getTop10ProductosVendidos() throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("Codigo del Producto", "Nombre", "Ventas")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT codigo_producto, nombre, COUNT(codigo_producto) as veces_vendido
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON control_productos.producto_ingresado.codigo_producto = control_productos.producto.codigo
          WHERE control_productos.producto_ingresado.codigo_venta IS NOT NULL
          GROUP BY control_productos.producto_ingresado.codigo_producto, nombre
          ORDER BY veces_vendido DESC
          LIMIT 10
        """);

        while (resultado.next()) {
            String codigo = resultado.getString(1);
            String nombre = resultado.getString(2);
            String unidades = resultado.getString(3);
            reporte.addFila(Arrays.asList(codigo,nombre,unidades));
        }

        return reporte;
    }

    public static Reporte getTop10ProductosGanancias() throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("Codigo del Producto", "Nombre", "Ganancias")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT codigo_producto, producto.nombre, SUM(precio) as total_producto
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON codigo_producto = codigo
          WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
          GROUP BY codigo_producto, producto.nombre
          ORDER BY total_producto DESC
          LIMIT 10
        """);

        while (resultado.next()) {
            String codigo = resultado.getString(1);
            String nombre = resultado.getString(2);
            String ganancias = resultado.getString(3);
            reporte.addFila(Arrays.asList(codigo,nombre,ganancias));
        }

        return reporte;
    }

    public static Reporte getTop10ClientesGanancias() throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("NIT del Cliente", "Nombre", "Total")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT cliente, cliente.nombre, SUM(precio) as total_gastado
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON control_productos.producto_ingresado.codigo_producto = control_productos.producto.codigo
          JOIN control_ventas.venta
          ON control_productos.producto_ingresado.codigo_venta = control_ventas.venta.codigo
          JOIN control_clientes.cliente
          ON control_ventas.venta.cliente = control_clientes.cliente.nit
          WHERE control_productos.producto_ingresado.codigo_venta IS NOT NULL
            AND control_ventas.venta.cliente IS NOT NULL
          GROUP BY cliente, cliente.nombre
          ORDER BY total_gastado DESC
          LIMIT 10
        """);

        while (resultado.next()) {
            String nit = resultado.getString(1);
            String nombre = resultado.getString(2);
            String total = resultado.getString(3);
            reporte.addFila(Arrays.asList(nit,nombre,total));
        }

        return reporte;
    }

    public static Reporte getTop3SucursalesVentas() throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("Sucursal", "Ventas")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT sucursal, COUNT(sucursal) as ventas_sucursal
          FROM control_ventas.venta
          JOIN control_usuarios.usuario
          ON venta.vendedor = usuario.username
          GROUP BY sucursal
          ORDER BY ventas_sucursal DESC
          LIMIT 3
        """);

        while (resultado.next()) {
            String sucursal = resultado.getString(1);
            String ventas = resultado.getString(2);
            reporte.addFila(Arrays.asList(sucursal,ventas));
        }

        return reporte;
    }

    public static Reporte getTop3SucursalesGanancias() throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("Sucursal", "Ganancias")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT  control_usuarios.usuario.sucursal, SUM(precio) as ventas_sucursal
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON control_productos.producto_ingresado.codigo_producto = control_productos.producto.codigo
          JOIN control_ventas.venta
          ON control_productos.producto_ingresado.codigo_venta = control_ventas.venta.codigo
          JOIN control_usuarios.usuario
          ON control_ventas.venta.vendedor = control_usuarios.usuario.username
          WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
          GROUP BY control_usuarios.usuario.sucursal
          ORDER BY ventas_sucursal DESC
          LIMIT 3
        """);

        while (resultado.next()) {
            String sucursal = resultado.getString(1);
            String ganancias = resultado.getString(2);
            reporte.addFila(Arrays.asList(sucursal,ganancias));
        }

        return reporte;
    }

    public static Reporte getTop3VendedoresVentas() throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("Vendedor", "Ventas")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT username, COUNT(username) as ventas_empleado
          FROM control_ventas.venta
          JOIN control_usuarios.usuario
          ON venta.vendedor = usuario.username
          GROUP BY username
          ORDER BY ventas_empleado DESC
          LIMIT 3
        """);

        while (resultado.next()) {
            String vendedor = resultado.getString(1);
            String ventas = resultado.getString(2);
            reporte.addFila(Arrays.asList(vendedor,ventas));
        }

        return reporte;
    }

    public static Reporte getTop3VendedoresGanancias() throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("Vendedor", "Ganancias")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("""
        SELECT  control_usuarios.usuario.username, SUM(precio) as ventas_sucursal
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON control_productos.producto_ingresado.codigo_producto = control_productos.producto.codigo
          JOIN control_ventas.venta
          ON control_productos.producto_ingresado.codigo_venta = control_ventas.venta.codigo
          JOIN control_usuarios.usuario
          ON control_ventas.venta.vendedor = control_usuarios.usuario.username
          WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
          GROUP BY control_usuarios.usuario.username
          ORDER BY ventas_sucursal DESC
          LIMIT 3
        """);

        while (resultado.next()) {
            String vendedor = resultado.getString(1);
            String ganancias = resultado.getString(2);
            reporte.addFila(Arrays.asList(vendedor,ganancias));
        }

        return reporte;
    }

    public static Reporte getTop5ProductosVentasSucursal(String sucursal) throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("Codigo del Producto", "Nombre", "Ventas")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("""
        SELECT codigo_producto, producto.nombre, COUNT(codigo_producto) as ventas_producto
          FROM control_productos.producto_ingresado
          JOIN control_ventas.venta
          ON codigo_venta = codigo
          JOIN control_usuarios.usuario
          ON vendedor = username
          JOIN control_productos.producto
          ON codigo_producto = producto.codigo
          WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
            AND usuario.sucursal = ?
          GROUP BY codigo_producto, producto.nombre
          ORDER BY ventas_producto DESC
          LIMIT 5
        """);
        select.setString(1, sucursal);

        ResultSet resultado = select.executeQuery();

        while (resultado.next()) {
            String codigo = resultado.getString(1);
            String nombre = resultado.getString(2);
            String ventas = resultado.getString(3);
            reporte.addFila(Arrays.asList(codigo,nombre,ventas));
        }

        return reporte;
    }

    public static Reporte getTop5ProductosGananciasSucursal(String sucursal) throws SQLException, Exception{
        Reporte reporte = new Reporte(new ArrayList<>(Arrays.asList("Codigo del Producto", "Nombre", "Ganancias")));
        
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("""
        SELECT codigo_producto, producto.nombre, SUM(precio) as total_producto
          FROM control_productos.producto_ingresado
          JOIN control_productos.producto
          ON codigo_producto = control_productos.producto.codigo
          JOIN control_ventas.venta
          ON codigo_venta = control_ventas.venta.codigo
          JOIN control_usuarios.usuario
          ON vendedor = username
          WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
            AND usuario.sucursal = ?
          GROUP BY codigo_producto, producto.nombre
          ORDER BY total_producto DESC
          LIMIT 5
        """);
        select.setString(1, sucursal);

        ResultSet resultado = select.executeQuery();

        while (resultado.next()) {
            String codigo = resultado.getString(1);
            String nombre = resultado.getString(2);
            String ganancias = resultado.getString(3);
            reporte.addFila(Arrays.asList(codigo,nombre,ganancias));
        }

        return reporte;
    }
}

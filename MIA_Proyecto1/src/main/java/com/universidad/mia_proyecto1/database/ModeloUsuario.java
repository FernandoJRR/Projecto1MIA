package com.universidad.mia_proyecto1.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.universidad.mia_proyecto1.exceptions.UsuarioDuplicadoException;
import com.universidad.mia_proyecto1.modelo.Usuario;
import com.universidad.mia_proyecto1.utilidades.ConvertidorHash;

public class ModeloUsuario {
    public static Usuario getUsuario(String username) throws SQLException, Exception{
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_usuarios.usuario WHERE username = ?");
        select.setString(1, username);

        ResultSet resultado = select.executeQuery();
        if (resultado.next()) {
            return new Usuario(resultado.getString(1), resultado.getString(2), resultado.getString(4));
        } else {
            return null;
        }

    }
    
    public static List<Usuario> getUsuarios() throws SQLException, Exception{
        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.Statement select = Conexion.databaseConnection.createStatement();
        ResultSet resultado = select.executeQuery("SELECT * FROM control_usuarios.usuario");
        
        List<Usuario> usuariosList = new LinkedList<Usuario>();
        while (resultado.next()) {
            usuariosList.add(new Usuario(resultado.getString(1), resultado.getString(2), resultado.getString(4)));
        }
        
        return usuariosList;
    }
    
    public static void insertarUsuario(String username, String password, String tipo, String sucursal) throws SQLException, UsuarioDuplicadoException, Exception{
        //Se comprueba si el usuario ingresado ya existe o no
        Usuario usuario = getUsuario(username);
        //Si ya existe se enviara un error
        if (usuario != null) {
            throw new UsuarioDuplicadoException(username);
        }

        Conexion.crearConexion("administrador01", "admin_01");
        java.sql.PreparedStatement insert = Conexion.databaseConnection.prepareStatement("INSERT INTO control_usuarios.usuario (tipo, username, password, sucursal) VALUES (?,?,?,?)");

        switch (tipo) {
            case "administrador":
            case "bodega":
                insert.setString(1, tipo);
                insert.setString(2, username);
                insert.setString(3, ConvertidorHash.stringSHA256(password));
                insert.setString(4, null);
                break;
            case "vendedor":
            case "inventario":
                insert.setString(1, tipo);
                insert.setString(2, username);
                insert.setString(3, ConvertidorHash.stringSHA256(password));
                insert.setString(4, sucursal);
                break;
        }

        insert.execute();
    }
}

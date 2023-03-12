package com.universidad.mia_proyecto1.database;

import java.sql.ResultSet;

import com.universidad.mia_proyecto1.exceptions.PasswordIncorrecto;
import com.universidad.mia_proyecto1.exceptions.UsuarioNoExisteException;
import com.universidad.mia_proyecto1.modelo.Usuario;

public class LoginUsuario {
    public static Usuario comprobarCredenciales(String username, String password) throws UsuarioNoExisteException,PasswordIncorrecto,Exception {
        Conexion.crearConexion("postgres", "");
        java.sql.PreparedStatement select = Conexion.databaseConnection.prepareStatement("SELECT * FROM control_usuarios.usuario WHERE username = ?");
        select.setString(1, username);

        ResultSet resultado = select.executeQuery();

        //Se comprueba si el usuario existe
        if (resultado.next()) {
            String tipo = resultado.getString(1);
            String user = resultado.getString(2);
            String pass = resultado.getString(3);
            
            //Se comprueba si el password coincide
            if (password.equals(pass)) {
                //Se obtiene el tipo de usuario que ingreso
                switch (tipo) {
                    case "administrador":
                    case "bodega":
                        return new Usuario(tipo, user, null);
                    case "vendedor":
                    case "inventario":
                        String sucursal = resultado.getString(4);
                        return new Usuario(tipo, user, sucursal);
                    default:
                        throw new Exception();
                }
            } else {
                //Si no coincide se lanza un error
                throw new PasswordIncorrecto();
            }
        } else {
            //Si el usuario no existe se enviara un error
            throw new UsuarioNoExisteException(username);
        }
    }
}

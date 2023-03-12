package com.universidad.mia_proyecto1.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection databaseConnection;

    static String connectionUrl = "jdbc:postgresql://localhost:5432/proyecto1_mia_db";

    public static void crearConexion(String user, String password) throws Exception{
        databaseConnection = DriverManager.getConnection(connectionUrl, user, password);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luis Sierra
 */
public class Conexion_DB {
    private static final String DB_NAME = "db_systemcity";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1234";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=America/Bogota";

    // Obtener una nueva conexión cada vez
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           System.out.println("Conectado a db_systemcity");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar a db_systemcity: " + e.getMessage());
            return null;
        }
    }
    
}

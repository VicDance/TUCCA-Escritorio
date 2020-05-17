package connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vicky
 */
public class Conector {
    Properties configuracion = new Properties();
    private Connection con;

    public Conector() {
    }

    public void connect() {
        con = null;
        final String server;
        final String user;
        final String password;
        try {
            configuracion.load(new FileInputStream("config.props"));
            server = configuracion.getProperty("server");
            user = configuracion.getProperty("user");
            password = configuracion.getProperty("password");
            con = DriverManager.getConnection(server, user, password);
            if (con != null) {
                System.out.println("Conexion exitosa");
            }
        } catch (SQLException ex) {
            //System.out.println(ex.getSQLState());
            System.out.println("No se pudo conectar con la base de datos" + ex);
            System.exit(-1);
        } catch (IOException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void disconect() {
        con = null;
        if (con != null) {
            System.out.println("Conexion terminada");
        }
    }
}

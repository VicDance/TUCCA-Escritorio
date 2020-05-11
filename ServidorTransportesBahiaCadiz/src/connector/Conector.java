package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    //?useServerPrepStmts=true
    private Connection con;
    private final String server = "jdbc:mysql://localhost:3306/transportes_cadiz?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "ItsMeVicky";

    public Conector() {
    }
    
    public void connect(){
        con = null;
        try {
            con = DriverManager.getConnection(server, user, password);
            if (con != null) {
                System.out.println("Conexion exitosa");
            }
        } catch (SQLException ex) {
            //System.out.println(ex.getSQLState());
            System.out.println("No se pudo conectar con la base de datos" + ex);
            System.exit(-1);
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

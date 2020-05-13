/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iClienteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Vicky
 */
public class ClienteDAOImp implements iClienteDAO{
    Conector con = new Conector();
    public boolean insertado;
    
    @Override
    public void insertar(int id) {
        try {
            con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            //ResultSet rs;
            String sqlNuevoUsuario = "INSERT INTO cliente (id_user) "
                    + "VALUES (?)";
            insertar = connection.prepareStatement(sqlNuevoUsuario);
            insertar.setInt(1, id);
            if(insertar.executeUpdate() != 0){
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            insertado = false;
            //Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.disconect();
        }
    }
    
    @Override
    public void insertarAdmin(int id) {
        try {
            con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            //ResultSet rs;
            String sqlNuevoUsuario = "INSERT INTO administrador (idadministrador) "
                    + "VALUES (?)";
            insertar = connection.prepareStatement(sqlNuevoUsuario);
            insertar.setInt(1, id);
            if(insertar.executeUpdate() != 0){
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            insertado = false;
            //Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.disconect();
        }
    }
}

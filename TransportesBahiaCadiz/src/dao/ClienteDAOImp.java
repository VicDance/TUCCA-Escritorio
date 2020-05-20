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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Usuario;

/**
 *
 * @author Vicky
 */
public class ClienteDAOImp implements iClienteDAO{
    Conector con/* = new Conector()*/;
    public boolean insertado;
    
    public ClienteDAOImp(){
        
    }

    public ClienteDAOImp(Conector con) {
        this.con = con;
    }
    @Override
    public void insertar(int id) {
        try {
            //con.connect();
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
            //con.disconect();
        }
    }
    
    @Override
    public void insertarAdmin(int id) {
        try {
            //con.connect();
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
            //con.disconect();
        }
    }

    @Override
    public List<Cliente> getAllClientes() {
        //con.connect();
        Connection connection = con.getConnection();
        List<Cliente> clientes = null;
        PreparedStatement buscar;
        try {
            String buscaUsuarios = "SELECT * FROM cliente";
            buscar = connection.prepareStatement(buscaUsuarios);
            ResultSet rs = buscar.executeQuery();
            clientes = new ArrayList<Cliente>();
            while (rs.next()) {
                Cliente cli = new Cliente();
                cli.setIdCliente(rs.getInt(1));
                clientes.add(cli);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return clientes;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iTarjetaBusDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.TarjetaBus;

/**
 *
 * @author Vicky
 */
public class TarjetaBusDAOImp implements iTarjetaBusDAO{
    Conector con = new Conector();
    public boolean insertado;
    
    @Override
    public void insertar(TarjetaBus tarjeta) {
        try {
            con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevaTarjeta = "INSERT INTO tarjeta (num_tarjeta, id_user, saldo, descuento) "
                    + "VALUES (?, ?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevaTarjeta);
            insertar.setLong(1, tarjeta.getNumTarjeta());
            insertar.setInt(2, tarjeta.getId());
            insertar.setInt(3, tarjeta.getSaldo());
            insertar.setDouble(4, tarjeta.getDescuento());
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

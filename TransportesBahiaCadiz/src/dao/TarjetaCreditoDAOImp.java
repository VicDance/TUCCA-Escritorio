/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iTarjetaCreditoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TarjetaCredito;

/**
 *
 * @author Vicky
 */
public class TarjetaCreditoDAOImp implements iTarjetaCreditoDAO {

    Conector con;
    public boolean insertado = false;

    public TarjetaCreditoDAOImp() {
    }

    public TarjetaCreditoDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void insertar(TarjetaCredito tarjeta) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevaTarjeta = "INSERT INTO tarjeta_banco (num_tarjeta_banco, id_user, caducidad, titular) "
                    + "VALUES (?, ?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevaTarjeta);
            insertar.setString(1, tarjeta.getNumTarjeta());
            insertar.setInt(2, tarjeta.getIdUser());
            insertar.setString(3, tarjeta.getCaducidad());
            insertar.setString(4, tarjeta.getTitular());
            if (insertar.executeUpdate() != 0) {
                insertado = true;
                System.out.println("Insercción exitosa");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            insertado = false;
        }
    }

    @Override
    public List<TarjetaCredito> getAllTarjetas() {
        //con.connect();
        Connection connection = con.getConnection();
        List<TarjetaCredito> tarjetas = null;
        PreparedStatement buscar;
        try {
            String buscaTarjeta = "SELECT * FROM tarjeta_banco";
            buscar = connection.prepareStatement(buscaTarjeta);
            ResultSet rs = buscar.executeQuery();
            tarjetas = new ArrayList<TarjetaCredito>();
            while (rs.next()) {
                TarjetaCredito tarjeta = new TarjetaCredito();
                tarjeta.setNumTarjeta(rs.getString(1));
                tarjeta.setIdUser(rs.getInt(2));
                tarjeta.setCaducidad(rs.getString(3));
                tarjeta.setTitular(rs.getString(4));
                tarjetas.add(tarjeta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TarjetaCreditoDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //con.disconect();
        }
        return tarjetas;
    }

    @Override
    public void borrar(int posicion) {
        Connection connection = con.getConnection();
        List<TarjetaCredito> tarjetas = getAllTarjetas();
        PreparedStatement borrar;
        String numTarjeta = null;
        try {
            String borraTarjeta = "DELETE FROM tarjeta_banco WHERE num_tarjeta_banco = ?";
            borrar = connection.prepareStatement(borraTarjeta);
            for (int i = 0; i < tarjetas.size(); i++) {
                if (i == posicion) {
                    numTarjeta = tarjetas.get(i).getNumTarjeta();
                    borrar.setString(1, numTarjeta);
                    borrar.executeUpdate();
                    break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

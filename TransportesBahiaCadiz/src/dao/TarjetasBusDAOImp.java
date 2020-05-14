/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iTarjetasBusDAO;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TarjetaEstandar;

/**
 *
 * @author Vicky
 */
public class TarjetasBusDAOImp implements iTarjetasBusDAO {
    Conector con = new Conector();
    public boolean insertado;

    @Override
    public void insertarEstandar(long numTarjeta) {
        try {
            con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevaTarjeta = "INSERT INTO tarjeta_estandar (num_tarjeta_estandar, fecha_expedicion) "
                    + "VALUES (?, ?)";
            Date d = new Date(); 
            java.sql.Date date2 = new java.sql.Date(d.getTime());
            insertar = connection.prepareStatement(sqlNuevaTarjeta);
            insertar.setLong(1, numTarjeta);
            insertar.setDate(2, date2);
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            insertado = false;
            //Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.disconect();
        }
    }

    @Override
    public void insertarEstudiante(long numTarjeta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertarJubilado(long numTarjeta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TarjetaEstandar> getAllTarjetasEstandar() {
        con.connect();
        Connection connection = con.getConnection();
        List<TarjetaEstandar> tarjetas = null;
        PreparedStatement buscar;
        try {
            String buscaMunicipios = "SELECT * FROM tarjeta_estandar";
            buscar = connection.prepareStatement(buscaMunicipios);
            ResultSet rs = buscar.executeQuery();
            tarjetas = new ArrayList<TarjetaEstandar>();
            while (rs.next()) {
                TarjetaEstandar tarjeta = new TarjetaEstandar();
                tarjeta.setNumTarjeta(rs.getLong(1));
                tarjeta.setFecha_expedicion(rs.getDate(2));
                tarjetas.add(tarjeta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.disconect();
        }
        return tarjetas;
    }
}

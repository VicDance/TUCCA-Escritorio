/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iPuntoVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PuntoVenta;

/**
 *
 * @author Vicky
 */
public class PuntoVentaDAOImp implements iPuntoVenta {

    private Conector con;

    public PuntoVentaDAOImp() {
    }

    public PuntoVentaDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public List<PuntoVenta> getAllPuntos() {
        Connection connection = con.getConnection();
        List<PuntoVenta> puntos = null;
        PreparedStatement buscar;
        try {
            String buscaPuntos = "SELECT * FROM puntos_venta";
            buscar = connection.prepareStatement(buscaPuntos);
            ResultSet rs = buscar.executeQuery();
            puntos = new ArrayList<PuntoVenta>();
            while (rs.next()) {
                PuntoVenta punto = new PuntoVenta();
                punto.setIdPunto(rs.getInt(1));
                punto.setIdNucleo(rs.getInt(2));
                punto.setTipo(rs.getString(3));
                punto.setLatitud(rs.getString(4));
                punto.setLongitud(rs.getString(5));
                puntos.add(punto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return puntos;
    }

    @Override
    public List<PuntoVenta> getPuntoVentaId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PuntoVenta> getPuntosVentaNucleo(int idNucleo) {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        PuntoVenta punto = null;
        List<PuntoVenta> puntos = null;
        try {
            String buscaPuntos = "SELECT * FROM puntos_venta WHERE id_nucleo = ?";
            buscar = connection.prepareStatement(buscaPuntos);
            buscar.setInt(1, idNucleo);
            ResultSet rs = buscar.executeQuery();
            puntos = new ArrayList<PuntoVenta>();
            while (rs.next()) {
                punto = new PuntoVenta();
                punto.setIdPunto(rs.getInt(1));
                punto.setIdNucleo(rs.getInt(2));
                punto.setTipo(rs.getString(3));
                punto.setDireccion(rs.getString(4));
                punto.setLatitud(rs.getString(5));
                punto.setLongitud(rs.getString(6));
                puntos.add(punto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return puntos;
    }

    @Override
    public PuntoVenta getPuntoVenta(int idNucleo) {
        PuntoVenta punto = null;
        /*Connection connection = con.getConnection();
        PreparedStatement buscar;
        PuntoVenta punto = null;
        try {
            String buscaPuntos = "SELECT * FROM puntos_venta WHERE idpuntos_venta = ?";
            buscar = connection.prepareStatement(buscaPuntos);
            buscar.setInt(1, idNucleo);
            ResultSet rs = buscar.executeQuery();
            while (rs.next()) {
                punto = new PuntoVenta();
                punto.setIdPunto(rs.getInt(1));
                punto.setIdNucleo(rs.getInt(2));
                punto.setTipo(rs.getString(3));
                punto.setLatitud(rs.getString(4));
                punto.setLongitud(rs.getString(5));
                //puntos.add(punto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return punto;*/
        return punto;
    }
}

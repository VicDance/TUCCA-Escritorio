/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iParadaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Parada;

/**
 *
 * @author Vicky
 */
public class ParadaDAOImp implements iParadaDAO {
    Conector con/* = new Conector()*/;

    public ParadaDAOImp() {
    }

    public ParadaDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void insertar(char idZona, String nombreParada, String latitud, String longitud) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(int idParada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Parada> getParada(String nombre) {
        //con.connect();
        Connection connection = con.getConnection();
        List<Parada> paradas = null;
        PreparedStatement buscar;
        try {
            String buscaParadas = "SELECT * FROM parada WHERE nombre_parada LIKE '%" + nombre + "%'";
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            paradas = new ArrayList<Parada>();
            while (rs.next()) {
                Parada parada = new Parada();
                parada.setIdParada(rs.getInt(1));
                parada.setIdZona(rs.getString(2).charAt(0));
                parada.setNombreParada(rs.getString(3));
                parada.setLatitud(rs.getString(4));
                parada.setLongitud(rs.getString(5));
                paradas.add(parada);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return paradas;
    }
    
    @Override
    public List<Parada> getAllParadas() {
        //con.connect();
        Connection connection = con.getConnection();
        List<Parada> paradas = null;
        PreparedStatement buscar;
        try {
            String buscaParadas = "SELECT * FROM parada";
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            paradas = new ArrayList<Parada>();
            while (rs.next()) {
                Parada parada = new Parada();
                parada.setIdParada(rs.getInt(1));
                parada.setIdZona(rs.getString(2).charAt(0));
                parada.setNombreParada(rs.getString(3));
                parada.setLatitud(rs.getString(4));
                parada.setLongitud(rs.getString(5));
                paradas.add(parada);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return paradas;
    }
}

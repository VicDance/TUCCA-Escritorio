/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iNucleoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Nucleo;

/**
 *
 * @author Vicky
 */
public class NucleoDAOImp implements iNucleoDAO{
    Conector con/* = new Conector()*/;

    public NucleoDAOImp() {
    }

    public NucleoDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void insertar(int idMunicipio, char idZona, String nombreNucleo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(int idNucleo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Nucleo> getNucleo(String nombre){
        //con.connect();
        Connection connection = con.getConnection();
        List<Nucleo> nucleos = null;
        PreparedStatement buscar;
        try {
            String buscaNucleos = "SELECT * FROM nucleo WHERE nombre_nucleo LIKE '%" + nombre + "%'";
            buscar = connection.prepareStatement(buscaNucleos);
            ResultSet rs = buscar.executeQuery();
            nucleos = new ArrayList<Nucleo>();
            while (rs.next()) {
                Nucleo nu = new Nucleo();
                nu.setIdNucleo(rs.getInt(1));
                nu.setIdMunicipio(rs.getInt(2));
                nu.setIdZona(rs.getString(3).charAt(0));
                nu.setNombreNucleo(rs.getString(4));
                nucleos.add(nu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return nucleos;
    }

    @Override
    public List<Nucleo> getAllNucleos() {
        //con.connect();
        Connection connection = con.getConnection();
        List<Nucleo> nucleos = null;
        PreparedStatement buscar;
        try {
            String buscaNucleos = "SELECT * FROM nucleo";
            buscar = connection.prepareStatement(buscaNucleos);
            ResultSet rs = buscar.executeQuery();
            nucleos = new ArrayList<Nucleo>();
            while (rs.next()) {
                Nucleo nu = new Nucleo();
                nu.setIdNucleo(rs.getInt(1));
                nu.setIdMunicipio(rs.getInt(2));
                nu.setIdZona(rs.getString(3).charAt(0));
                nu.setNombreNucleo(rs.getString(4));
                nucleos.add(nu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return nucleos;
    }
    
}

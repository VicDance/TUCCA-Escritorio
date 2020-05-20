/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iMunicipioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Municipio;

/**
 *
 * @author Vicky
 */
public class MunicipioDAOImp implements iMunicipioDAO{
    Conector con/* = new Conector()*/;
    public boolean insertado = false;

    public MunicipioDAOImp() {
    }

    public MunicipioDAOImp(Conector con) {
        this.con = con;
    }
    
    @Override
    public void insertar(String nombreMunicipio) {
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaMuni = "INSERT INTO municipio (nombre_municipio) VALUES (?)";
            insertar = connection.prepareStatement(insertaMuni);
            insertar.setString(1, nombreMunicipio);
            if(insertar.executeUpdate() != 0){
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            insertado = false;
        }finally{
            //con.disconect();
        }
    }

    @Override
    public void borrar(int idMunicipio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Municipio> getMunicipio(String nombre){
        //con.connect();
        Connection connection = con.getConnection();
        List<Municipio> municipios = null;
        PreparedStatement buscar;
        try {
            String buscaLineas = "SELECT * FROM municipio WHERE nombre_municipio LIKE '%" + nombre + "%'";
            buscar = connection.prepareStatement(buscaLineas);
            ResultSet rs = buscar.executeQuery();
            municipios = new ArrayList<Municipio>();
            while (rs.next()) {
                Municipio muni = new Municipio();
                muni.setIdMunicipio(rs.getInt(1));
                muni.setNombreMunicipio(rs.getString(2));
                municipios.add(muni);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return municipios;
    }
    
    @Override
    public List<Municipio> getAllMunicipios() {
        //con.connect();
        Connection connection = con.getConnection();
        List<Municipio> municipios = null;
        PreparedStatement buscar;
        try {
            String buscaMunicipios = "SELECT * FROM municipio";
            buscar = connection.prepareStatement(buscaMunicipios);
            ResultSet rs = buscar.executeQuery();
            municipios = new ArrayList<Municipio>();
            while (rs.next()) {
                Municipio muni = new Municipio();
                muni.setIdMunicipio(rs.getInt(1));
                muni.setNombreMunicipio(rs.getString(2));
                municipios.add(muni);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return municipios;
    }
    
}

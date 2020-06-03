/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iMunicipioDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
public class MunicipioDAOImp implements iMunicipioDAO {

    Conector con/* = new Conector()*/;
    public boolean insertado = false;

    public MunicipioDAOImp() {
    }

    public MunicipioDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void createTableAux() {
        Connection connection = con.getConnection();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "municipio", null);
            PreparedStatement insertar;
            if (rs.next()) {
                //Table Exist
                String insertaTabla = "CREATE TABLE IF NOT EXISTS`municipio_aux` (\n"
                        + "  `idmunicipio` int NOT NULL AUTO_INCREMENT,\n"
                        + "  `nombre_municipio` varchar(200) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL,\n"
                        + "  PRIMARY KEY (`idmunicipio`)\n"
                        + ")";
                insertar = connection.prepareStatement(insertaTabla);
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertar(Municipio muni) {
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaMuni = "INSERT INTO municipio (idmunicipio, nombre_municipio) VALUES (?, ?)";
            insertar = connection.prepareStatement(insertaMuni);
            insertar.setInt(1, muni.getIdMunicipio());
            insertar.setString(2, muni.getNombreMunicipio());
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            insertado = false;
        } finally {
            //con.disconect();
        }
    }
    
    @Override
    public void insertarAux(Municipio muni) {
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaMuni = "INSERT INTO municipio_aux (idmunicipio, nombre_municipio) VALUES (?, ?)";
            insertar = connection.prepareStatement(insertaMuni);
            insertar.setInt(1, muni.getIdMunicipio());
            insertar.setString(2, muni.getNombreMunicipio());
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            insertado = false;
        } finally {
            //con.disconect();
        }
    }

    @Override
    public void dropTableAux() {
        Connection connection = con.getConnection();
        try {
            PreparedStatement borrar;
            String borraTabla = "DROP TABLE IF EXISTS municipio_aux";
            borrar = connection.prepareStatement(borraTabla);
            borrar.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Municipio> getMunicipio(String nombre) {
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
    
    @Override
    public List<Municipio> getIntersection() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<Municipio> municipios = null;
        String buscaParadas = "SELECT * FROM municipio_aux aux WHERE aux.idmunicipio NOT IN (SELECT m.idmunicipio "
                + "FROM municipio m)";
        try {
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            municipios = new ArrayList<Municipio>();
            while (rs.next()) {
                Municipio muni = new Municipio();
                muni.setIdMunicipio(rs.getInt(1));
                muni.setNombreMunicipio(rs.getString(2));
                municipios.add(muni);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return municipios;
    }

    @Override
    public String getNombreMunicipio(int idMunicipio) {
        List<Municipio> municipios = getAllMunicipios();
        String nombre = "";
        for (int i = 0; i < municipios.size(); i++) {
            if (idMunicipio == municipios.get(i).getIdMunicipio()) {
                nombre = municipios.get(i).getNombreMunicipio();
            }
        }
        return nombre;
    }

}

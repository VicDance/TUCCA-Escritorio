/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iZonaDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Zona;

/**
 *
 * @author Vicky
 */
public class ZonaDAOImp implements iZonaDAO {

    private Conector con;

    public ZonaDAOImp() {

    }

    public ZonaDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void createTableAux() {
        Connection connection = con.getConnection();
        PreparedStatement insertar;
        String creaTabla = "CREATE TABLE IF NOT EXISTS `zona_aux` (\n"
                + "  `idzona` varchar(1) NOT NULL,\n"
                + "  `nombre_zona` varchar(45) NOT NULL,\n"
                + "  PRIMARY KEY (`idzona`),\n"
                + "  UNIQUE KEY `idzona_UNIQUE` (`idzona`)\n"
                + ")";
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "zona", null);
            if (rs.next()) {
                insertar = connection.prepareStatement(creaTabla);
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ZonaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertar(Zona zona) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevaZona = "INSERT INTO zona (idZona, nombre_zona) "
                    + "VALUES (?, ?)";
            insertar = connection.prepareStatement(sqlNuevaZona);
            insertar.setString(1, zona.getIdZona());
            insertar.setString(2, zona.getNombreZona());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertarAux(Zona zona) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevaZona = "INSERT INTO zona_aux (idZona, nombre_zona) "
                    + "VALUES (?, ?)";
            insertar = connection.prepareStatement(sqlNuevaZona);
            insertar.setString(1, zona.getIdZona());
            insertar.setString(2, zona.getNombreZona());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void dropTableAux(){
        Connection connection = con.getConnection();
        try {
            PreparedStatement borrar;
            String borraTabla = "DROP TABLE IF EXISTS zona_aux";
            borrar = connection.prepareStatement(borraTabla);
            borrar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Zona> getZona(String nombre) {
        //con.connect();
        Connection connection = con.getConnection();
        List<Zona> zonas = null;
        PreparedStatement buscar;
        try {
            String buscaZonas = "SELECT * FROM zona WHERE nombre_zona LIKE '%" + nombre + "%'";
            buscar = connection.prepareStatement(buscaZonas);
            ResultSet rs = buscar.executeQuery();
            zonas = new ArrayList<Zona>();
            while (rs.next()) {
                Zona zona = new Zona();
                zona.setIdZona(rs.getString(1));
                zona.setNombreZona(rs.getString(2));
                zonas.add(zona);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //con.disconect();
        }
        return zonas;
    }

    @Override
    public List<Zona> getAll() {
        //con.connect();
        Connection connection = con.getConnection();
        List<Zona> zonas = null;
        PreparedStatement buscar;
        try {
            String buscaZonas = "SELECT * FROM zona";
            buscar = connection.prepareStatement(buscaZonas);
            ResultSet rs = buscar.executeQuery();
            zonas = new ArrayList<Zona>();
            while (rs.next()) {
                Zona zona = new Zona();
                zona.setIdZona(rs.getString(1));
                zona.setNombreZona(rs.getString(2));
                zonas.add(zona);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zonas;
    }

    @Override
    public List<Zona> getIntersection() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<Zona> zonas = null;
        String buscaParadas = "SELECT * FROM zona_aux aux WHERE aux.idzona NOT IN (SELECT z.idzona "
                + "FROM zona z)";
        try {
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            zonas = new ArrayList<Zona>();
            while (rs.next()) {
                Zona zona = new Zona();
                zona.setIdZona(rs.getString(1));
                zona.setNombreZona(rs.getString(2));
                zonas.add(zona);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zonas;
    }
}

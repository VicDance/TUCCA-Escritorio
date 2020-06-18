/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iLugarInteresDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import serializable.LugarInteres;

/**
 *
 * @author Vicky
 */
public class LugarInteresDAOImp implements iLugarInteresDAO{

    private Conector con;

    public LugarInteresDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void createTableLugarInteresAux() {
        Connection connection = con.getConnection();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "lugar_interes", null);
            PreparedStatement insertar;
            if (rs.next()) {
                String insertaTabla = "CREATE TABLE `lugar_interes_aux` (\n"
                        + "  `idlugar_interes` int NOT NULL AUTO_INCREMENT,\n"
                        + "  `id_municipio` int NOT NULL,\n"
                        + "  `latitud` varchar(100) DEFAULT NULL,\n"
                        + "  `longitud` varchar(100) DEFAULT NULL,\n"
                        + "  `nombre_lugar` varchar(200) NOT NULL,\n"
                        + "  PRIMARY KEY (`idlugar_interes`,`id_municipio`)\n"
                        + ")";
                insertar = connection.prepareStatement(insertaTabla);
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LugarInteresDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void insertarLugarInteres(LugarInteres lugar) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaLugar = "INSERT INTO lugar_interes (idlugar_interes, id_municipio, latitud, longitud,"
                    + "nombre_lugar) VALUES (?, ?, ?, ?, ?)";
            insertar = connection.prepareStatement(insertaLugar);
            insertar.setInt(1, lugar.getLugarInteres());
            insertar.setInt(2, lugar.getIdMunicipio());
            insertar.setString(3, (String) lugar.getLatitud());
            insertar.setString(4, (String) lugar.getLongitud());
            insertar.setString(5, lugar.getNombreLugar());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getMessage();
            //insertado = false;
        }
    }
    
    @Override
    public void insertarLugarInteresAux(LugarInteres lugar) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaLugar = "INSERT INTO lugar_interes_aux (idlugar_interes, id_municipio, latitud, longitud,"
                    + "nombre_lugar) VALUES (?, ?, ?, ?, ?)";
            insertar = connection.prepareStatement(insertaLugar);
            insertar.setInt(1, lugar.getLugarInteres());
            insertar.setInt(2, lugar.getIdMunicipio());
            insertar.setObject(3, lugar.getLatitud());
            insertar.setObject(4, lugar.getLongitud());
            insertar.setString(5, lugar.getNombreLugar());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getMessage();
            //insertado = false;
        }
    }
    
    @Override
    public void dropTablaLugaresAux() {
        Connection connection = con.getConnection();
        try {
            PreparedStatement borrar;
            String borraTabla = "DROP TABLE IF EXISTS lugar_interes_aux";
            borrar = connection.prepareStatement(borraTabla);
            borrar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public List<LugarInteres> getIntersectCorresponde() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<LugarInteres> lugares = null;
        String buscaLugares = "SELECT * FROM lugar_interes_aux aux WHERE aux.idlugar_interes NOT IN (SELECT l.idlugar_interes "
                + "FROM lugar_interes l)";
        try {
            buscar = connection.prepareStatement(buscaLugares);
            ResultSet rs = buscar.executeQuery();
            lugares = new ArrayList<LugarInteres>();
            while (rs.next()) {
                LugarInteres lugar = new LugarInteres();
                lugar.setIdLugarInteres(rs.getInt(1));
                lugar.setIdMunicipio(rs.getInt(2));
                lugar.setLatitud(rs.getString(3));
                lugar.setLongitud(rs.getString(4));
                lugar.setNombreLugar(rs.getString(5));
                lugares.add(lugar);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lugares;
    }

    @Override
    public List<LugarInteres> getAllLugares() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<LugarInteres> lugares = null;
        String buscaLugares = "SELECT * FROM lugar_interes";
        try {
            buscar = connection.prepareStatement(buscaLugares);
            ResultSet rs = buscar.executeQuery();
            lugares = new ArrayList<LugarInteres>();
            while (rs.next()) {
                LugarInteres lugar = new LugarInteres();
                lugar.setIdLugarInteres(rs.getInt(1));
                lugar.setIdMunicipio(rs.getInt(2));
                lugar.setLatitud(rs.getString(3));
                lugar.setLongitud(rs.getString(4));
                lugar.setNombreLugar(rs.getString(5));
                lugares.add(lugar);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lugares;
    }
}

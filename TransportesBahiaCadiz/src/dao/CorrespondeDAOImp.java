/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iCorrespondeDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import serializable.Corresponde;

/**
 *
 * @author Vicky
 */
public class CorrespondeDAOImp implements iCorrespondeDAO{
    private Conector con;
    public boolean insertado = false;
    
    public CorrespondeDAOImp(Conector con){
        this.con = con;
    }
    
    @Override
    public void insertaTablaCorresponde() {
        Connection connection = con.getConnection();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "corresponde", null);
            PreparedStatement insertar;
            if (rs.next()) {
                //Table Exist
                String insertaTabla = "CREATE TABLE IF NOT EXISTS `corresponde_aux` (\n"
                        + "  `id_linea` int NOT NULL,\n"
                        + "  `id_parada` int NOT NULL,\n"
                        + "  PRIMARY KEY (`id_linea`,`id_parada`),"
                        + "CONSTRAINT `id_linea_corresponde_aux` FOREIGN KEY (`id_linea`) REFERENCES `linea` "
                        + "(`idlinea`) ON DELETE CASCADE ON UPDATE CASCADE, "
                        + "CONSTRAINT `id_parada_corresponde_aux` FOREIGN KEY (`id_parada`) REFERENCES `parada` "
                        + "(`idparada`) ON DELETE CASCADE ON UPDATE CASCADE)";
                insertar = connection.prepareStatement(insertaTabla);
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void insertarCorresponde(int idLinea, int idParada) {
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaCorresponde = "INSERT INTO corresponde (id_linea, id_parada) VALUES (?, ?) ON DUPLICATE KEY "
                    + "UPDATE id_linea = id_linea AND id_parada = id_parada";
            insertar = connection.prepareStatement(insertaCorresponde);
            insertar.setInt(1, idLinea);
            insertar.setInt(2, idParada);
            insertar.executeUpdate();
        } catch (SQLException ex) {
            //ex.printStackTrace();
            //ex.getMessage();
            //insertado = false;
        }
    }
    
    @Override
    public void insertarCorrespondeAux(int idLinea, int idParada) {
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaCorresponde = "INSERT INTO corresponde_aux (id_linea, id_parada) VALUES (?, ?) ON DUPLICATE KEY "
                    + "UPDATE id_linea = id_linea AND id_parada = id_parada";
            insertar = connection.prepareStatement(insertaCorresponde);
            insertar.setInt(1, idLinea);
            insertar.setInt(2, idParada);
            insertar.executeUpdate();
        } catch (SQLException ex) {
            //ex.printStackTrace();
            //ex.getMessage();
            //insertado = false;
        }
    }
    
    @Override
    public void dropTablaCorresponde() {
        Connection connection = con.getConnection();
        try {
            PreparedStatement borrar;
            String borraTabla = "DROP TABLE IF EXISTS corresponde_aux";
            borrar = connection.prepareStatement(borraTabla);
            borrar.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public List<Corresponde> getIntersectCorresponde() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<Corresponde> paradas = null;
        String buscaParadas = "SELECT * FROM corresponde_aux aux WHERE aux.id_linea NOT IN (SELECT c.id_linea "
                + "FROM corresponde c) AND aux.id_parada NOT IN (SELECT c.id_parada FROM corresponde c)";
        try {
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            paradas = new ArrayList<Corresponde>();
            while (rs.next()) {
                Corresponde c = new Corresponde();
                c.setIdLinea(rs.getInt(1));
                c.setIdParada(rs.getInt(2));
                paradas.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paradas;
    }

    @Override
    public List<Corresponde> getAllCorresponde() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<Corresponde> paradas = null;
        String buscaIds = "SELECT * FROM corresponde";
        try {
            buscar = connection.prepareStatement(buscaIds);
            ResultSet rs = buscar.executeQuery();
            paradas = new ArrayList<Corresponde>();
            while (rs.next()) {
                Corresponde c = new Corresponde();
                c.setIdLinea(rs.getInt(1));
                c.setIdParada(rs.getInt(2));
                paradas.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return paradas;
    }
}

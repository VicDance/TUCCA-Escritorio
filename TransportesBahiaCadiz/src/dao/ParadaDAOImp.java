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
    public boolean insertado = false;

    public ParadaDAOImp() {
    }

    public ParadaDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void insertarCorresponde(int idLinea, int idParada) {
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaCorresponde = "INSERT INTO corresponde (id_linea, id_parada) VALUES (?, ?)";
            insertar = connection.prepareStatement(insertaCorresponde);
            insertar.setInt(1, idLinea);
            insertar.setInt(2, idParada);
            if(insertar.executeUpdate() != 0){
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            insertado = false;
        }
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

    @Override
    public List<Parada> getParadasViaje(int idLinea, int idNucleoOrigen, int idNucleoDestino) {
        Connection connection = con.getConnection();
        List<Parada> paradas = null;
        List<Parada> paradasOrigen = null;
        List<Parada> paradasDestino = null;
        PreparedStatement buscar;
        //paradasDestino = new ArrayList<Parada>();
        try {
            String buscaParadasOrigen = "SELECT idparada, id_zona, nombre_parada, latitud, longitud "
                    + "FROM parada p JOIN corresponde c ON p.idparada = c.id_parada WHERE c.id_linea = ? AND p.id_zona IN "
                    + "(SELECT id_zona FROM nucleo WHERE nombre_nucleo IN (SELECT nombre_nucleo FROM nucleo WHERE idnucleo "
                    + "= ?))";
            buscar = connection.prepareStatement(buscaParadasOrigen);
            buscar.setInt(1, idLinea);
            buscar.setInt(2, idNucleoOrigen);
            ResultSet rs = buscar.executeQuery();
            paradas = new ArrayList<Parada>();
            paradasOrigen = new ArrayList<Parada>();
            paradasDestino = new ArrayList<Parada>();
            while (rs.next()) {
                Parada parada = new Parada();
                parada.setIdParada(rs.getInt(1));
                parada.setIdZona(rs.getString(2).charAt(0));
                parada.setNombreParada(rs.getString(3));
                parada.setLatitud(rs.getString(4));
                parada.setLongitud(rs.getString(5));
                paradasOrigen.add(parada);
            }
            String buscaParadasDestino = "SELECT idparada, id_zona, nombre_parada, latitud, longitud "
                    + "FROM parada p JOIN corresponde c ON p.idparada = c.id_parada WHERE c.id_linea = ? AND p.id_zona IN "
                    + "(SELECT id_zona FROM nucleo WHERE nombre_nucleo IN (SELECT nombre_nucleo FROM nucleo WHERE idnucleo "
                    + "= ?))";
            buscar = connection.prepareStatement(buscaParadasDestino);
            buscar.setInt(1, idLinea);
            buscar.setInt(2, idNucleoDestino);
            rs = buscar.executeQuery();
            paradas = new ArrayList<Parada>();
            while (rs.next()) {
                Parada parada = new Parada();
                parada.setIdParada(rs.getInt(1));
                parada.setIdZona(rs.getString(2).charAt(0));
                parada.setNombreParada(rs.getString(3));
                parada.setLatitud(rs.getString(4));
                parada.setLongitud(rs.getString(5));
                paradasDestino.add(parada);
            }
            //int size = paradasOrigen.size() + paradasDestino.size();
            for(int i = 0; i < paradasOrigen.size(); i++){
                paradas.add(paradasOrigen.get(i));
            }
            for(int i = 0; i < paradasDestino.size(); i++){
                paradas.add(paradasDestino.get(i));
            }
            /*System.out.println("IdLinea: " + idLinea);
            System.out.println("IdNucleoOrigen: " + idNucleoOrigen);
            System.out.println("IdNucleoDestino: " + idNucleoDestino);*/
            /*for(int i = 0; i < paradas.size(); i++){
                System.out.println("IdLinea: " + idLinea + " " + paradas.get(i)
                + " IdNucleo: " + idNucleo);
            }*/
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return paradas;
    } 
}

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
import model.TarjetaEstudiante;
import model.TarjetaJubilado;

/**
 *
 * @author Vicky
 */
public class TarjetasBusDAOImp implements iTarjetasBusDAO {
    Conector con = new Conector();
    public boolean insertado;

    @Override
    public void insertarEstandar(long numTarjeta) {
        insertado = false;
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
    public void insertarEstudiante(long numTarjeta, java.sql.Date inicio, java.sql.Date fin) {
        insertado = false;
        try {
            con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevaTarjeta = "INSERT INTO tarjeta_estudiante (num_tarjeta_estudiante, fecha_ini, fecha_fin) "
                    + "VALUES (?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevaTarjeta);
            insertar.setLong(1, numTarjeta);
            insertar.setDate(2, inicio);
            insertar.setDate(3, fin);
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
    public void insertarJubilado(long numTarjeta, int año) {
        insertado = false;
        try {
            con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevaTarjeta = "INSERT INTO tarjeta_jubilado (num_tarjeta_jubilado, año_validez) "
                    + "VALUES (?, ?)";
            insertar = connection.prepareStatement(sqlNuevaTarjeta);
            insertar.setLong(1, numTarjeta);
            insertar.setInt(2, año);
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
    public List<TarjetaEstandar> getAllTarjetasEstandar() {
        con.connect();
        Connection connection = con.getConnection();
        List<TarjetaEstandar> tarjetas = null;
        PreparedStatement buscar;
        try {
            String buscaTarjeta = "SELECT * FROM tarjeta_estandar";
            buscar = connection.prepareStatement(buscaTarjeta);
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

    @Override
    public List<TarjetaEstudiante> getAllTarjetasEstudiante() {
        con.connect();
        Connection connection = con.getConnection();
        List<TarjetaEstudiante> tarjetas = null;
        PreparedStatement buscar;
        try {
            String buscaTarjeta = "SELECT * FROM tarjeta_estudiante";
            buscar = connection.prepareStatement(buscaTarjeta);
            ResultSet rs = buscar.executeQuery();
            tarjetas = new ArrayList<TarjetaEstudiante>();
            while (rs.next()) {
                TarjetaEstudiante tarjeta = new TarjetaEstudiante();
                tarjeta.setNumTarjeta(rs.getLong(1));
                tarjeta.setFecha_ini(rs.getDate(2));
                tarjeta.setFecha_fin(rs.getDate(3));
                tarjetas.add(tarjeta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.disconect();
        }
        return tarjetas;
    }

    @Override
    public List<TarjetaJubilado> getAllTarjetasJubilado() {
        con.connect();
        Connection connection = con.getConnection();
        List<TarjetaJubilado> tarjetas = null;
        PreparedStatement buscar;
        try {
            String buscaTarjeta = "SELECT * FROM tarjeta_jubilado";
            buscar = connection.prepareStatement(buscaTarjeta);
            ResultSet rs = buscar.executeQuery();
            tarjetas = new ArrayList<TarjetaJubilado>();
            while (rs.next()) {
                TarjetaJubilado tarjeta = new TarjetaJubilado();
                tarjeta.setNumTarjeta(rs.getLong(1));
                tarjeta.setAñoValidez(rs.getDate(2));
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iZonaDAO;
import java.sql.Connection;
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

    Conector con/* = new Conector()*/;
    public boolean insertado = false;
    public boolean borrado = false;
    
    public ZonaDAOImp(){
        
    }
    
    public ZonaDAOImp(Conector con){
        this.con = con;
    }

    @Override
    public void insertar(String idZona, String nombre) {
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            //ResultSet rs;
            String sqlNuevaZona = "INSERT INTO zona (idZona, nombre_zona) "
                    + "VALUES (?, ?)";
            insertar = connection.prepareStatement(sqlNuevaZona);
            insertar.setString(1, idZona);
            insertar.setString(2, nombre);
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ClienteDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            insertado = false;
        } finally {
            //con.disconect();
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
            String buscaMunicipios = "SELECT * FROM zona";
            buscar = connection.prepareStatement(buscaMunicipios);
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
    public void deleteId(Zona zona) {
        //con.connect();
        Connection connection = con.getConnection();
        PreparedStatement borrar;
        try {
            String borraZona = "DELETE FROM zona WHERE idZona = ?";
            borrar = connection.prepareStatement(borraZona);
            borrar.setString(1, zona.getIdZona());
            //borrar.setString(2, zona.getNombreZona());
            if(borrar.executeUpdate() != 0){
                System.out.println("borrado con exito");
                borrado = true;
            }
            //borrar.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(ZonaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            borrado = false;
            ex.printStackTrace();
        }finally{
            //con.disconect();
        }
    }

    @Override
    public void deleteNombre(Zona zona) {
        //con.connect();
        Connection connection = con.getConnection();
        PreparedStatement borrar;
        try {
            String borraZona = "DELETE FROM zona WHERE nombre_zona LIKE '%" + zona.getNombreZona() + "%'";
            borrar = connection.prepareStatement(borraZona);
            borrar.setString(2, zona.getNombreZona());
            if(borrar.executeUpdate() != 0){
                System.out.println("borrado con exito");
                borrado = true;
            }
            //borrar.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(ZonaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            borrado = false;
            ex.printStackTrace();
        }finally{
            //con.disconect();
        }
    }

}

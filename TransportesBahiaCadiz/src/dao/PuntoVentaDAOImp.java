/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iPuntoVenta;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import serializable.PuntoVenta;

/**
 *
 * @author Vicky
 */
public class PuntoVentaDAOImp implements iPuntoVenta {

    private Conector con;

    public PuntoVentaDAOImp() {
    }

    public PuntoVentaDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void createTableAux() {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "puntos_venta", null);
            String creaTabla = "CREATE TABLE IF NOT EXISTS `puntos_venta_aux` (\n"
                    + "  `idpuntos_venta` int NOT NULL AUTO_INCREMENT,\n"
                    + "  `id_nucleo` int NOT NULL,\n"
                    + "  `tipo` varchar(45) NOT NULL,\n"
                    + "  `direccion` varchar(200) NOT NULL,\n"
                    + "  `latitud` varchar(45) NOT NULL,\n"
                    + "  `longitud` varchar(45) NOT NULL,\n"
                    + "  PRIMARY KEY (`idpuntos_venta`,`id_nucleo`),\n"
                    + "  KEY `id_nucleo_venta_idx` (`id_nucleo`),\n"
                    + "  CONSTRAINT `id_nucleo_venta_aux` FOREIGN KEY (`id_nucleo`) REFERENCES `nucleo` (`idnucleo`) ON DELETE CASCADE ON UPDATE CASCADE\n"
                    + ")";
            if (rs.next()) {
                insertar = connection.prepareStatement(creaTabla);
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PuntoVentaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertar(PuntoVenta punto) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevoPuntoVenta = "INSERT INTO puntos_venta (idpuntos_venta, id_nucleo, tipo, direccion, latitud, "
                    + "longitud) VALUES (?, ?, ?, ?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevoPuntoVenta);
            insertar.setInt(1, punto.getIdPunto());
            insertar.setInt(2, punto.getIdNucleo());
            insertar.setString(3, punto.getTipo());
            insertar.setString(4, punto.getDireccion());
            insertar.setString(5, punto.getLatitud());
            insertar.setString(6, punto.getLongitud());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertarAux(PuntoVenta punto) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevoPuntoVenta = "INSERT INTO puntos_venta_aux (idpuntos_venta, id_nucleo, tipo, direccion, latitud, "
                    + "longitud) VALUES (?, ?, ?, ?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevoPuntoVenta);
            insertar.setInt(1, punto.getIdPunto());
            insertar.setInt(2, punto.getIdNucleo());
            insertar.setString(3, punto.getTipo());
            insertar.setString(4, punto.getDireccion());
            insertar.setString(5, punto.getLatitud());
            insertar.setString(6, punto.getLongitud());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dropTableAux() {
        Connection connection = con.getConnection();
        try {
            PreparedStatement borrar;
            String borraTabla = "DROP TABLE IF EXISTS puntos_venta_aux";
            borrar = connection.prepareStatement(borraTabla);
            borrar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<PuntoVenta> getIntersection() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<PuntoVenta> puntos = null;
        String buscaParadas = "SELECT * FROM puntos_venta_aux aux WHERE aux.idpuntos_venta NOT IN (SELECT p.idpuntos_venta "
                + "FROM puntos_venta p)";
        try {
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            puntos = new ArrayList<PuntoVenta>();
            while (rs.next()) {
                PuntoVenta punto = new PuntoVenta();
                punto.setIdPunto(rs.getInt(1));
                punto.setIdNucleo(rs.getInt(2));
                punto.setTipo(rs.getString(3));
                punto.setDireccion(rs.getString(4));
                punto.setLatitud(rs.getString(5));
                punto.setLongitud(rs.getString(6));
                puntos.add(punto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return puntos;
    }

    @Override
    public List<PuntoVenta> getAllPuntos() {
        Connection connection = con.getConnection();
        List<PuntoVenta> puntos = null;
        PreparedStatement buscar;
        try {
            String buscaPuntos = "SELECT * FROM puntos_venta";
            buscar = connection.prepareStatement(buscaPuntos);
            ResultSet rs = buscar.executeQuery();
            puntos = new ArrayList<PuntoVenta>();
            while (rs.next()) {
                PuntoVenta punto = new PuntoVenta();
                punto.setIdPunto(rs.getInt(1));
                punto.setIdNucleo(rs.getInt(2));
                punto.setTipo(rs.getString(3));
                punto.setLatitud(rs.getString(4));
                punto.setLongitud(rs.getString(5));
                puntos.add(punto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return puntos;
    }

    @Override
    public List<PuntoVenta> getPuntoVentaId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PuntoVenta> getPuntosVentaNucleo(int idNucleo) {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        PuntoVenta punto = null;
        List<PuntoVenta> puntos = null;
        try {
            String buscaPuntos = "SELECT * FROM puntos_venta WHERE id_nucleo = ?";
            buscar = connection.prepareStatement(buscaPuntos);
            buscar.setInt(1, idNucleo);
            ResultSet rs = buscar.executeQuery();
            puntos = new ArrayList<PuntoVenta>();
            while (rs.next()) {
                punto = new PuntoVenta();
                punto.setIdPunto(rs.getInt(1));
                punto.setIdNucleo(rs.getInt(2));
                punto.setTipo(rs.getString(3));
                punto.setDireccion(rs.getString(4));
                punto.setLatitud(rs.getString(5));
                punto.setLongitud(rs.getString(6));
                puntos.add(punto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return puntos;
    }

    @Override
    public PuntoVenta getPuntoVenta(int idNucleo) {
        PuntoVenta punto = null;
        /*Connection connection = con.getConnection();
         PreparedStatement buscar;
         PuntoVenta punto = null;
         try {
         String buscaPuntos = "SELECT * FROM puntos_venta WHERE idpuntos_venta = ?";
         buscar = connection.prepareStatement(buscaPuntos);
         buscar.setInt(1, idNucleo);
         ResultSet rs = buscar.executeQuery();
         while (rs.next()) {
         punto = new PuntoVenta();
         punto.setIdPunto(rs.getInt(1));
         punto.setIdNucleo(rs.getInt(2));
         punto.setTipo(rs.getString(3));
         punto.setLatitud(rs.getString(4));
         punto.setLongitud(rs.getString(5));
         //puntos.add(punto);
         }
         } catch (SQLException ex) {
         Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
         }

         return punto;*/
        return punto;
    }
}

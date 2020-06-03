/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iNucleoDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
public class NucleoDAOImp implements iNucleoDAO {

    Conector con;

    public NucleoDAOImp() {
    }

    public NucleoDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void createTableAux() {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "nucleo", null);
            String creaTabla = "CREATE TABLE IF NOT EXISTS `nucleo_aux` (\n"
                    + "  `idnucleo` int NOT NULL AUTO_INCREMENT,\n"
                    + "  `id_municipio` int NOT NULL,\n"
                    + "  `id_zona` varchar(1) NOT NULL,\n"
                    + "  `nombre_nucleo` varchar(100) NOT NULL,\n"
                    + "  PRIMARY KEY (`idnucleo`,`id_municipio`,`id_zona`),\n"
                    + "  KEY `id_municipio_nucleo_idx` (`id_municipio`),\n"
                    + "  KEY `id_zona_nucleo_idx` (`id_zona`),\n"
                    + "  CONSTRAINT `id_municipio_nucleo_aux` FOREIGN KEY (`id_municipio`) REFERENCES `municipio` (`idmunicipio`) ON DELETE CASCADE ON UPDATE CASCADE,\n"
                    + "  CONSTRAINT `id_zona_nucleo_aux` FOREIGN KEY (`id_zona`) REFERENCES `zona` (`idzona`) ON DELETE CASCADE ON UPDATE CASCADE\n"
                    + ") ";
            if (rs.next()) {
                insertar = connection.prepareStatement(creaTabla);
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NucleoDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertar(Nucleo nucleo) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevoNucleo = "INSERT INTO nucleo (idnucleo, id_municipio, id_zona, nombre_nucleo) "
                    + "VALUES (?, ?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevoNucleo);
            insertar.setInt(1, nucleo.getIdNucleo());
            insertar.setInt(2, nucleo.getIdMunicipio());
            insertar.setString(3, nucleo.getIdZona());
            insertar.setString(4, nucleo.getNombreNucleo());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertarAux(Nucleo nucleo) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevoNucleo = "INSERT INTO nucleo_aux (idnucleo, id_municipio, id_zona, nombre_nucleo) "
                    + "VALUES (?, ?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevoNucleo);
            insertar.setInt(1, nucleo.getIdNucleo());
            insertar.setInt(2, nucleo.getIdMunicipio());
            insertar.setString(3, nucleo.getIdZona());
            insertar.setString(4, nucleo.getNombreNucleo());
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
            String borraTabla = "DROP TABLE IF EXISTS nucleo_aux";
            borrar = connection.prepareStatement(borraTabla);
            borrar.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Nucleo> getNucleo(String nombre) {
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
                nu.setIdZona(rs.getString(3));
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
                nu.setIdZona(rs.getString(3));
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
    public List<Nucleo> getIntersection() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<Nucleo> nucleos = null;
        String buscaParadas = "SELECT * FROM nucleo_aux aux WHERE aux.idnucleo NOT IN (SELECT n.idnucleo "
                + "FROM nucleo n)";
        try {
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            nucleos = new ArrayList<Nucleo>();
            while (rs.next()) {
                Nucleo nucleo = new Nucleo();
                nucleo.setIdNucleo(rs.getInt(1));
                nucleo.setIdMunicipio(rs.getInt(2));
                nucleo.setIdZona(rs.getString(3));
                nucleo.setNombreNucleo(rs.getString(4));
                nucleos.add(nucleo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nucleos;
    }
}

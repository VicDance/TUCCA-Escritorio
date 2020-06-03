/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iLineaDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.Linea;

/**
 *
 * @author Vicky
 */
public class LineaDAOImp implements iLineaDAO {

    Conector con/* = new Conector()*/;
    public boolean insertado = false;

    public LineaDAOImp() {
    }

    public LineaDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void insertarTablaAux() {
        Connection connection = con.getConnection();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "linea", null);
            PreparedStatement insertar;
            if (rs.next()) {
                //Table Exist
                String insertaTabla = "CREATE TABLE IF NOT EXISTS `linea_aux` (\n"
                        + "  `idlinea` int NOT NULL AUTO_INCREMENT,\n"
                        + "  `nombre_linea` varchar(100) NOT NULL,\n"
                        + "  PRIMARY KEY (`idlinea`),\n"
                        + "  UNIQUE KEY `nombre_linea_UNIQUE` (`nombre_linea`),\n"
                        + "  UNIQUE KEY `idlinea_UNIQUE` (`idlinea`)\n"
                        + ")";
                insertar = connection.prepareStatement(insertaTabla);
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertar(Linea linea) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaLinea = "INSERT INTO linea (idlinea, nombre_linea) VALUES (?, ?) ON DUPLICATE KEY UPDATE "
                    + "idlinea = idlinea";
            insertar = connection.prepareStatement(insertaLinea);
            insertar.setInt(1, linea.getIdLinea());
            insertar.setString(2, linea.getNombreLinea());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertarAux(Linea linea) {
        insertado = false;
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaLinea = "INSERT INTO linea_aux (idlinea, nombre_linea) VALUES (?, ?) ON DUPLICATE KEY UPDATE "
                    + "idlinea = idlinea";
            insertar = connection.prepareStatement(insertaLinea);
            insertar.setInt(1, linea.getIdLinea());
            insertar.setString(2, linea.getNombreLinea());
            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dropTablaAux() {
        Connection connection = con.getConnection();
        try {
            PreparedStatement borrar;
            String borraTabla = "DROP TABLE IF EXISTS linea_aux";
            borrar = connection.prepareStatement(borraTabla);
            borrar.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public List<Linea> getIntersectLinea() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<Linea> lineas = null;
        String buscaParadas = "SELECT * FROM linea_aux aux WHERE aux.idlinea NOT IN (SELECT l.idlinea "
                + "FROM linea l)";
        try {
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            lineas = new ArrayList<Linea>();
            while (rs.next()) {
                Linea l = new Linea();
                l.setIdLinea(rs.getInt(1));
                l.setNombreLinea(rs.getString(2));
                lineas.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lineas;
    }

    @Override
    public List<Linea> getLinea(String nombre) {
        //con.connect();
        Connection connection = con.getConnection();
        List<Linea> lineas = null;
        PreparedStatement buscar;
        try {
            String buscaLineas = "SELECT * FROM linea WHERE nombre_linea LIKE '%" + nombre + "%'";
            buscar = connection.prepareStatement(buscaLineas);
            ResultSet rs = buscar.executeQuery();
            lineas = new ArrayList<Linea>();
            while (rs.next()) {
                Linea linea = new Linea();
                linea.setIdLinea(rs.getInt(1));
                linea.setNombreLinea(rs.getString(2));
                lineas.add(linea);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return lineas;
    }

    @Override
    public List<Linea> getAllLineas() {
        //con.connect();
        Connection connection = con.getConnection();
        List<Linea> lineas = null;
        PreparedStatement buscar;
        try {
            String buscaLineas = "SELECT * FROM linea";
            buscar = connection.prepareStatement(buscaLineas);
            ResultSet rs = buscar.executeQuery();
            lineas = new ArrayList<Linea>();
            while (rs.next()) {
                Linea linea = new Linea();
                linea.setIdLinea(rs.getInt(1));
                linea.setNombreLinea(rs.getString(2));
                lineas.add(linea);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return lineas;
    }

    @Override
    public List<Linea> getLineasNucleo(int idNucleoOrigen, int idNucleoDestino) {
        Connection connection = con.getConnection();
        List<Linea> lineasOrigen = null;
        List<Linea> lineasDestino = null;
        List<Linea> lineas = null;
        PreparedStatement buscar;
        try {
            String buscaLineasOrigen = "SELECT DISTINCT idlinea, nombre_linea FROM linea l JOIN corresponde c ON l.idlinea = c.id_linea JOIN"
                    + " parada p ON c.id_parada = p.idparada JOIN zona z ON p.id_zona = z.idzona JOIN nucleo n ON"
                    + " z.idzona = n.id_zona WHERE n.idnucleo = ?";
            buscar = connection.prepareStatement(buscaLineasOrigen);
            buscar.setInt(1, idNucleoOrigen);
            ResultSet rs = buscar.executeQuery();
            lineas = new ArrayList<Linea>();
            lineasOrigen = new ArrayList<Linea>();
            lineasDestino = new ArrayList<Linea>();
            while (rs.next()) {
                Linea linea = new Linea();
                linea.setIdLinea(rs.getInt(1));
                linea.setNombreLinea(rs.getString(2));
                lineasOrigen.add(linea);
            }

            String buscaLineasDestino = "SELECT DISTINCT idlinea, nombre_linea FROM linea l JOIN corresponde c ON l.idlinea = c.id_linea JOIN"
                    + " parada p ON c.id_parada = p.idparada JOIN zona z ON p.id_zona = z.idzona JOIN nucleo n ON"
                    + " z.idzona = n.id_zona WHERE n.idnucleo = ?";
            buscar = connection.prepareStatement(buscaLineasDestino);
            buscar.setInt(1, idNucleoDestino);
            rs = buscar.executeQuery();
            lineas = new ArrayList<Linea>();
            while (rs.next()) {
                Linea linea = new Linea();
                linea.setIdLinea(rs.getInt(1));
                linea.setNombreLinea(rs.getString(2));
                lineasDestino.add(linea);
            }

            for (int i = 0; i < lineasOrigen.size(); i++) {
                //System.out.println("Origen: " + lineasOrigen.get(i));
                //lineas.add(lineasDestino.get(i));
                for (int j = 0; j < lineasDestino.size(); j++) {
                    //System.out.println("Destino: " + lineasDestino.get(i));
                    //lineas.add(lineasDestino.get(i));
                    if (lineasOrigen.get(i).getIdLinea() == lineasDestino.get(j).getIdLinea()) {
                        lineas.add(lineasOrigen.get(i));
                    }
                }
            }

            /*for (int i = 0; i < lineas.size(); i++) {
             System.out.println("Lineas: " + lineas.get(i));
             //lineas.add(lineasDestino.get(i));
             }*/
            //lineas = lineas.stream().distinct().collect(Collectors.toList());
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //con.disconect();
        return lineas;
    }

    @Override
    public String getNombreLinea(int idLinea) {
        List<Linea> lineas = getAllLineas();
        String linea = null;
        for (int i = 0; i < lineas.size(); i++) {
            if (idLinea == lineas.get(i).getIdLinea()) {
                linea = lineas.get(i).getNombreLinea();
            }
        }
        return linea;
    }

    @Override
    public int getIdLinea(String nombreLinea) {
        List<Linea> lineas = getAllLineas();
        int idLinea = 0;
        for (int i = 0; i < lineas.size(); i++) {
            if (nombreLinea.equalsIgnoreCase(lineas.get(i).getNombreLinea())) {
                idLinea = lineas.get(i).getIdLinea();
            }
        }
        return idLinea;
    }

}

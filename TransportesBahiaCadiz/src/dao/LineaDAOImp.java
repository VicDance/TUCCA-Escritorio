/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iLineaDAO;
import java.sql.Connection;
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
    public void insertar(String nombreLinea) {
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaLinea = "INSERT INTO linea (nombre_linea) VALUES (?)";
            insertar = connection.prepareStatement(insertaLinea);
            insertar.setString(1, nombreLinea);
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
    public void borrar(int idLinea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}

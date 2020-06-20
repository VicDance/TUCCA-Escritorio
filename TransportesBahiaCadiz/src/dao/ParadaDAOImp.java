/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iParadaDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import serializable.Cabecera;
import serializable.Corresponde;
import serializable.Parada;

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
    public void insertaTablaParadas() {
        Connection connection = con.getConnection();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "parada", null);
            PreparedStatement insertar;
            if (rs.next()) {
                //Table Exist
                String insertaTabla = "CREATE TABLE IF NOT EXISTS `parada_aux` (\n"
                        + "  `idparada` int NOT NULL AUTO_INCREMENT,\n"
                        + "  `id_zona` varchar(1) NOT NULL,\n"
                        + "  `nombre_parada` varchar(100) NOT NULL,\n"
                        + "  `latitud` varchar(100) NOT NULL,\n"
                        + "  `longitud` varchar(100) NOT NULL,\n"
                        + "  PRIMARY KEY (`idparada`,`id_zona`),\n"
                        + "  KEY `id_zona_parada_idx` (`id_zona`),\n"
                        + "  CONSTRAINT `id_zona_parada_aux` FOREIGN KEY (`id_zona`) REFERENCES `zona` (`idzona`) ON DELETE CASCADE ON UPDATE CASCADE\n"
                        + ")";
                insertar = connection.prepareStatement(insertaTabla);
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertaParadas(Parada parada) {
        Connection connection = con.getConnection();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "parada_aux", null);
            PreparedStatement insertar;
            String insertaParadas = "INSERT INTO parada (idparada, id_zona, nombre_parada, latitud, longitud) "
                    + "VALUES (?, ?, ?, ?, ?)";
            if (rs.next()) {
                //Table Exist
                insertar = connection.prepareStatement(insertaParadas);
                insertar.setInt(1, parada.getIdParada());
                insertar.setString(2, parada.getIdZona());
                insertar.setString(3, parada.getNombreParada());
                insertar.setString(4, parada.getLatitud());
                insertar.setString(5, parada.getLongitud());
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertaParadasAux(Parada parada) {
        Connection connection = con.getConnection();
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet rs = data.getTables(null, null, "parada_aux", null);
            PreparedStatement insertar;
            String insertaParadas = "INSERT INTO parada_aux (idparada, id_zona, nombre_parada, latitud, longitud) "
                    + "VALUES (?, ?, ?, ?, ?)";
            if (rs.next()) {
                //Table Exist
                insertar = connection.prepareStatement(insertaParadas);
                insertar.setInt(1, parada.getIdParada());
                insertar.setString(2, parada.getIdZona());
                insertar.setString(3, parada.getNombreParada());
                insertar.setString(4, parada.getLatitud());
                insertar.setString(5, parada.getLongitud());
                insertar.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertaCabecera(int id) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaParadas = "INSERT INTO cabecera (idcabecera) "
                    + "VALUES (?)";
            insertar = connection.prepareStatement(insertaParadas);
            insertar.setInt(1, id);
            insertar.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void insertaRegular(int id) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaParadas = "INSERT INTO regular (idregular) "
                    + "VALUES (?)";
            insertar = connection.prepareStatement(insertaParadas);
            insertar.setInt(1, id);
            insertar.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void dropTablaParadas() {
        Connection connection = con.getConnection();
        try {
            PreparedStatement borrar;
            String borraTabla = "DROP TABLE IF EXISTS parada_aux";
            borrar = connection.prepareStatement(borraTabla);
            borrar.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void borrar(int idParada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Parada> getIntersectParadas() {
        Connection connection = con.getConnection();
        PreparedStatement buscar;
        List<Parada> paradas = null;
        String buscaParadas = "SELECT * FROM parada_aux aux WHERE aux.idparada NOT IN (SELECT p.idparada "
                + "FROM parada p)";
        try {
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            paradas = new ArrayList<Parada>();
            while (rs.next()) {
                Parada parada = new Parada();
                parada.setIdParada(rs.getInt(1));
                parada.setIdZona(rs.getString(2));
                parada.setNombreParada(rs.getString(3));
                parada.setLatitud(rs.getString(4));
                parada.setLongitud(rs.getString(5));
                paradas.add(parada);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paradas;
    }

    @Override
    public List<Parada> getParada(String nombre) {
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
                parada.setIdZona(rs.getString(2));
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
                parada.setIdZona(rs.getString(2));
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
    public List<Cabecera> getAllCabecera() {
        Connection connection = con.getConnection();
        List<Cabecera> paradas = null;
        PreparedStatement buscar;
        try {
            String buscaParadas = "SELECT * FROM parada JOIN cabecera ON idparada = idcabecera";
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            paradas = new ArrayList<Cabecera>();
            while (rs.next()) {
                Cabecera parada = new Cabecera();
                parada.setIdParada(rs.getInt(1));
                parada.setIdZona(rs.getString(2));
                parada.setNombreParada(rs.getString(3));
                parada.setLatitud(rs.getString(4));
                parada.setLongitud(rs.getString(5));
                paradas.add(parada);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParadaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paradas;
    }
    
    @Override
    public List<Cabecera> getAllRegular() {
        Connection connection = con.getConnection();
        List<Cabecera> paradas = null;
        PreparedStatement buscar;
        try {
            String buscaParadas = "SELECT * FROM parada JOIN regular ON idparada = idregular";
            buscar = connection.prepareStatement(buscaParadas);
            ResultSet rs = buscar.executeQuery();
            paradas = new ArrayList<Cabecera>();
            while (rs.next()) {
                Cabecera parada = new Cabecera();
                parada.setIdParada(rs.getInt(1));
                parada.setIdZona(rs.getString(2));
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
                parada.setIdZona(rs.getString(2));
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
                parada.setIdZona(rs.getString(2));
                parada.setNombreParada(rs.getString(3));
                parada.setLatitud(rs.getString(4));
                parada.setLongitud(rs.getString(5));
                paradasDestino.add(parada);
            }
            //int size = paradasOrigen.size() + paradasDestino.size();
            for (int i = 0; i < paradasOrigen.size(); i++) {
                paradas.add(paradasOrigen.get(i));
            }
            for (int i = 0; i < paradasDestino.size(); i++) {
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

    @Override
    public String getDireccion(String nombre) {
        List<Parada> paradas = getAllParadas();
        String direccion = "";
        String[] nombre_split;
        //List<String> palabras = null;
        for (int i = 0; i < paradas.size(); i++) {
            if (nombre.startsWith("Pz") && !nombre.contains("-")) {
                nombre_split = nombre.split(" ");
                System.out.println(nombre_split[1]);
                direccion = comparaNombre(nombre_split[1]);
                break;
            } else if (nombre.startsWith("Pz") && nombre.contains("-")) {
                nombre_split = nombre.split("-");
                System.out.println(nombre_split[0]);
                if (nombre_split[0].contains(".")) {
                    System.out.println("entra punto 1");
                    String[] nombre_split2 = nombre.split(".");
                    if (nombre_split2 == null || nombre_split2.length == 0) {
                        System.out.println("entra nulo");
                        System.out.println(nombre_split[0].substring(3));
                        direccion = comparaNombre(nombre_split[0].substring(3));
                    } else {
                        System.out.println("entra no nulo");
                        System.out.println(nombre_split2.length);
                    }
                }
                break;
            } else if (!nombre.startsWith("Pz") && nombre.contains("-")) {
                nombre_split = nombre.split("-");
                System.out.println(nombre_split[0]);
                if (nombre_split[0].contains(".")) {
                    System.out.println("entra punto 2");
                    if (nombre_split[0].contains("SF") && nombre_split[0].contains("Est")) {
                        direccion = comparaNombre("Estación FC-Bahía Sur");
                    } else {
                        System.out.println(nombre_split[0].substring(2));
                        direccion = comparaNombre(nombre_split[0].substring(2));
                    }
                } else {
                    System.out.println(nombre_split[0]);
                    direccion = comparaNombre(nombre_split[0]);
                }
                break;
            } else if (!nombre.startsWith("Pz") && !nombre.contains("-") && !nombre.contains("/")) {
                nombre_split = nombre.split(" ");
                System.out.println(nombre_split[0]);
                if (nombre_split[0].length() <= 3) {
                    direccion = comparaNombre(nombre_split[1]);
                }
                direccion = comparaNombre(nombre_split[0]);
                break;
            } else if (nombre.contains("/")) {
                nombre_split = nombre.split("/");
                direccion = comparaNombre(nombre_split[1]);
                break;
            }
            //}
            //palabras = utils.allLongestCommonSubstring(paradas.get(i).getNombreParada(), nombre);
        }
        //System.out.println("Palabras: " + palabras);
        return direccion;
    }

    private String comparaNombre(String nombre) {
        String cadena = "";
        List<Parada> paradas = getAllParadas();
        for (int i = 0; i < paradas.size(); i++) {
            if (paradas.get(i).getNombreParada().contains(nombre)) {
                cadena = paradas.get(i).getLatitud() + "/" + paradas.get(i).getLongitud();
            }
        }
        return cadena;
    }
}

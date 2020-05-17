/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iTarjetaBusDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TarjetaBus;
import model.TarjetaEstandar;

/**
 *
 * @author Vicky
 */
public class TarjetaBusDAOImp implements iTarjetaBusDAO{
    Conector con = new Conector();
    public boolean insertado;
    public boolean borrado;
    
    @Override
    public void insertar(TarjetaBus tarjeta) {
        try {
            con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevaTarjeta = "INSERT INTO tarjeta (num_tarjeta, id_user, saldo, descuento) "
                    + "VALUES (?, ?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevaTarjeta);
            insertar.setLong(1, tarjeta.getNumTarjeta());
            insertar.setInt(2, tarjeta.getId());
            insertar.setInt(3, tarjeta.getSaldo());
            insertar.setDouble(4, tarjeta.getDescuento());
            if(insertar.executeUpdate() != 0){
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            insertado = false;
            //Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.disconect();
        }
    }

    @Override
    public void borrar(int posicion) {
        borrado = false;
        con.connect();
        Connection connection = con.getConnection();
        List<TarjetaBus> tarjetas = getAllTarjetas();
        PreparedStatement borrar;
        long numTarjeta = 0;
        try {
            String borraTarjeta = "DELETE FROM tarjeta WHERE num_tarjeta = ?";
            borrar = connection.prepareStatement(borraTarjeta);
            for(int i = 0; i < tarjetas.size(); i++){
                if(i == posicion){
                    numTarjeta = tarjetas.get(i).getNumTarjeta();
                    break;
                }
            }
            borrar.setLong(1, numTarjeta);
            if(borrar.executeUpdate() != 0){
                System.out.println("borrado con exito");
                borrado = true;
            }
        } catch (SQLException ex) {
            borrado = false;
            ex.printStackTrace();
        } finally {
            con.disconect();
        }
    }

    @Override
    public List<TarjetaBus> getAllTarjetas() {
        con.connect();
        Connection connection = con.getConnection();
        List<TarjetaBus> tarjetas = null;
        PreparedStatement buscar;
        try {
            String buscaMunicipios = "SELECT * FROM tarjeta";
            buscar = connection.prepareStatement(buscaMunicipios);
            ResultSet rs = buscar.executeQuery();
            tarjetas = new ArrayList<TarjetaBus>();
            while (rs.next()) {
                TarjetaBus tarjeta = new TarjetaBus();
                tarjeta.setNumTarjeta(rs.getLong(1));
                tarjeta.setId(rs.getInt(2));
                tarjeta.setSaldo(rs.getInt(3));
                tarjeta.setDescuento(rs.getDouble(4));
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

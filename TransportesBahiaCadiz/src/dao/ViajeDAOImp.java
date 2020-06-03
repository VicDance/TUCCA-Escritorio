/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iViajeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Viaje;

/**
 *
 * @author Vicky
 */
public class ViajeDAOImp implements iViajeDAO{
    private Conector con;
    public boolean insertado = false;
    
    public ViajeDAOImp(Conector con){
        this.con = con;
    }
    @Override
    public void insertarViaje(Viaje viaje) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String insertaViaje = "INSERT INTO viaje (id_usuario, id_linea, id_municipio, tarifa, hora_salida, hora_llegada"
                    + ", fecha_viaje) VALUES (?, ?, ?, ?, ?, ?, ?)";
            insertar = connection.prepareStatement(insertaViaje);
            insertar.setInt(1, viaje.getIdUsuario());
            insertar.setInt(2, viaje.getIdLinea());
            insertar.setInt(3, viaje.getIdMunicipio());
            insertar.setDouble(4, viaje.getTarifa());
            insertar.setString(5, viaje.getHoraSalida());
            insertar.setString(6, viaje.getHoraLlegada());
            insertar.setDate(7, viaje.getFechaViaje());
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Viaje> getAllViajes() {
        Connection connection = con.getConnection();
        List<Viaje> viajes = null;
        PreparedStatement buscar;
        try {
            String buscaViajes = "SELECT * FROM viaje";
            buscar = connection.prepareStatement(buscaViajes);
            ResultSet rs = buscar.executeQuery();
            viajes = new ArrayList<Viaje>();
            while (rs.next()) {
                Viaje viaje = new Viaje();
                viaje.setIdViaje(rs.getInt(1));
                viaje.setIdUsuario(rs.getInt(2));
                viaje.setIdLinea(rs.getInt(3));
                viaje.setIdMunicipio(rs.getInt(4));
                viaje.setTarifa(rs.getDouble(5));
                viaje.setHoraSalida(rs.getString(6));
                viaje.setHoraLlegada(rs.getString(7));
                viaje.setFechaViaje(rs.getDate(8));
                viajes.add(viaje);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viajes;
    }

    @Override
    public List<Viaje> getViajesId(int idUsuario) {
        List<Viaje> viajes = getAllViajes();
        List<Viaje> viajesId = new ArrayList<Viaje>();
        for(int i = 0; i < viajes.size(); i++){
            if(idUsuario == viajes.get(i).getIdUsuario()){
                viajesId.add(viajes.get(i));
            }
        }
        
        return viajesId;
    }
    
}

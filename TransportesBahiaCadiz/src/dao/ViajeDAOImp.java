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
import java.sql.SQLException;
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
    
}

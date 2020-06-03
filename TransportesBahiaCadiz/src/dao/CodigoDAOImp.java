/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iCodigoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CodigoQR;
/**
 *
 * @author Vicky
 */
public class CodigoDAOImp implements iCodigoDAO{
    private Conector con;
    public boolean insertado;
    
    public CodigoDAOImp(Conector con){
        this.con = con;
    }

    @Override
    public boolean insertar(CodigoQR codigo) {
        insertado = false;
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevoCodigo = "INSERT INTO codigoqr (idcodigoqr, hora_uso) "
                    + "VALUES (?, ?)";
            insertar = connection.prepareStatement(sqlNuevoCodigo);
            insertar.setInt(1, codigo.getIdCodigo());
            insertar.setString(2, codigo.getHoraUso());
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
    }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return insertado;
    }
    
    @Override
    public List<CodigoQR> getAllCodigos(){
        Connection connection = con.getConnection();
        List<CodigoQR> codigos = null;
        PreparedStatement buscar;
        try {
            String buscaCodigos = "SELECT * FROM codigoqr";
            buscar = connection.prepareStatement(buscaCodigos);
            ResultSet rs = buscar.executeQuery();
            codigos = new ArrayList<CodigoQR>();
            while (rs.next()) {
                CodigoQR codigo = new CodigoQR();
                codigo.setIdCodigo(rs.getInt(1));
                codigo.setHoraUso(rs.getString(2));
                codigos.add(codigo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigos;
    }
    
    @Override
    public void updateHora(CodigoQR codigo){
        Connection connection = con.getConnection();
        PreparedStatement actualiza;
        try {
            String actualizaHora = "UPDATE codigoqr SET hora_uso = ? WHERE idcodigoqr = ?";
            actualiza = connection.prepareStatement(actualizaHora);
            actualiza.setString(1, codigo.getHoraUso());
            actualiza.setInt(2, codigo.getIdCodigo());
            actualiza.executeUpdate(); 
        } catch (SQLException ex) {
            Logger.getLogger(LineaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public CodigoQR getCodigo(String hora) {
        List<CodigoQR> codigos = getAllCodigos();
        CodigoQR codigo = null;
        for(int i = 0; i < codigos.size(); i++){
            if(hora.equalsIgnoreCase(codigos.get(i).getHoraUso())){
                codigo = new CodigoQR(codigos.get(i).getIdCodigo(), hora);
            }
        }
        
        return codigo;
    }
}

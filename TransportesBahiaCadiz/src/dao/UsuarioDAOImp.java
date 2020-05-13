/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iUsuarioDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

/**
 *
 * @author Vicky
 */
public class UsuarioDAOImp implements iUsuarioDAO{
    Conector con = new Conector();
    public boolean insertado;
    
    public UsuarioDAOImp(){
        //con.connect();
    }

    @Override
    public void insertar(/*String nombre, String contraseña, String email, Date fecha_nac, int tfno*/Usuario usuario) {
        try {
            con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            //ResultSet rs;
            String sqlNuevoUsuario = "INSERT INTO usuario (username, password, email, fecha_nac, tfno) "
                    + "VALUES (?, ?, ?, ?, ?)";
            insertar = connection.prepareStatement(sqlNuevoUsuario);
            insertar.setString(1, usuario.getNombre());
            insertar.setString(2, usuario.getContraseña());
            insertar.setString(3, usuario.getCorreo());
            insertar.setDate(4, usuario.getFecha_nac());
            insertar.setInt(5, usuario.getTfno());
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
    public List<Usuario> getUsuario(String nombre) {
        con.connect();
        Connection connection = con.getConnection();
        List<Usuario> usuarios = null;
        PreparedStatement buscar;
        try {
            String buscaUsuario = "SELECT * FROM usuario WHERE username LIKE '%" + nombre + "%'";
            buscar = connection.prepareStatement(buscaUsuario);
            ResultSet rs = buscar.executeQuery();
            usuarios = new ArrayList<Usuario>();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setContraseña(rs.getString(3));
                usuario.setCorreo(rs.getString(4));
                usuario.setFecha_nac(rs.getDate(5));
                usuario.setTfno(rs.getInt(6));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.disconect();
        return usuarios;
    }

    @Override
    public List<Usuario> getAll() {
        con.connect();
        Connection connection = con.getConnection();
        List<Usuario> usuarios = null;
        PreparedStatement buscar;
        try {
            String buscaUsuarios = "SELECT * FROM usuario";
            buscar = connection.prepareStatement(buscaUsuarios);
            ResultSet rs = buscar.executeQuery();
            usuarios = new ArrayList<Usuario>();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setContraseña(rs.getString(3));
                usuario.setCorreo(rs.getString(4));
                usuario.setFecha_nac(rs.getDate(5));
                usuario.setTfno(rs.getInt(6));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.disconect();
        return usuarios;
    }

    @Override
    public Usuario getId(String nombre) {
        Usuario usuario = null;
        List<Usuario> usuarios = getAll();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getNombre().equals(nombre)) {
                usuario = new Usuario(usuario1.getId(), nombre, usuario1.getContraseña(), usuario1.getCorreo(), usuario1.getFecha_nac(), usuario1.getTfno());
            }
        }   
        return usuario;
    }
}

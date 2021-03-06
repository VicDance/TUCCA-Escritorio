/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connector.Conector;
import idao.iUsuarioDAO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import serializable.Cliente;
import serializable.Usuario;

/**
 *
 * @author Vicky
 */
public class UsuarioDAOImp implements iUsuarioDAO {

    Conector con;
    public boolean insertado;
    public boolean borrado;

    public UsuarioDAOImp() {
    }

    public UsuarioDAOImp(Conector con) {
        this.con = con;
    }

    @Override
    public void insertar(Usuario usuario) {
        insertado = false;
        try {
            //con.connect();
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
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            insertado = false;
            //Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //con.disconect();
        }
    }

    @Override
    public void insertarCliente(int id) {
        insertado = false;
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            //ResultSet rs;
            String sqlNuevoUsuario = "INSERT INTO cliente (id_user)"
                    + "VALUES (?)";
            insertar = connection.prepareStatement(sqlNuevoUsuario);
            insertar.setInt(1, id);
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            insertado = false;
            //Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //con.disconect();
        }
    }

    @Override
    public void insertarAdmin(int id) {
        insertado = false;
        try {
            //con.connect();
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            //ResultSet rs;
            String sqlNuevoUsuario = "INSERT INTO administrador (idadministrador) "
                    + "VALUES (?)";
            insertar = connection.prepareStatement(sqlNuevoUsuario);
            insertar.setInt(1, id);
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertarRevisor(int id) {
        insertado = false;
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevoUsuario = "INSERT INTO revisor (idrevisor) "
                    + "VALUES (?)";
            insertar = connection.prepareStatement(sqlNuevoUsuario);
            insertar.setInt(1, id);
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void borrar(int id) {
        borrado = false;
        //con.connect();
        Connection connection = con.getConnection();
        PreparedStatement borrar;
        try {
            String borraUsuario = "DELETE FROM usuario WHERE idusuario = ?";
            borrar = connection.prepareStatement(borraUsuario);
            borrar.setInt(1, id);
            if (borrar.executeUpdate() != 0) {
                System.out.println("borrado con exito");
                borrado = true;
            }
        } catch (SQLException ex) {
            borrado = false;
            ex.printStackTrace();
        } finally {
            //con.disconect();
        }
    }

    @Override
    public void updateUsuario(Usuario usuario) {
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevoUsuario = "UPDATE usuario SET username = ?, password = ?, email = ?, imagen_perfil = ? "
                    + "WHERE idusuario = ?";
            insertar = connection.prepareStatement(sqlNuevoUsuario);

            Blob blob = new SerialBlob(usuario.getImagen());
            insertar.setString(1, usuario.getNombre());
            insertar.setString(2, usuario.getContraseña());
            insertar.setString(3, usuario.getCorreo());
            insertar.setBlob(4, blob);
            insertar.setInt(5, usuario.getId());

            insertar.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Usuario> getUsuario(String nombre) {
        //con.connect();
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
        //con.disconect();
        return usuarios;
    }

    @Override
    public List<Usuario> getCliente(String nombre) {
        //con.connect();
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
        //con.disconect();
        return usuarios;
    }

    @Override
    public List<Usuario> getRevisor(String nombre) {
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
        //con.disconect();
        return usuarios;
    }

    @Override
    public List<Usuario> getAll() {
        //con.connect();
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
                Blob blob = rs.getBlob(7);
                if (blob != null) {
                    byte[] imagen = blob.getBytes(1l, (int) blob.length());
                    System.out.println("imagen no nula");
                    usuario.setImagen(imagen);
                }
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    @Override
    public Usuario getId(String nombre) {
        Usuario usuario = null;
        List<Usuario> usuarios = getAll();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getNombre().equals(nombre)) {
                usuario = new Usuario(usuario1.getId(), nombre, usuario1.getContraseña(), usuario1.getCorreo(),
                        usuario1.getFecha_nac(), usuario1.getTfno(), usuario1.getImagen());
            }
        }
        return usuario;
    }

    @Override
    public List<Cliente> getAllAdmins() {
        //con.connect();
        Connection connection = con.getConnection();
        List<Cliente> clientes = null;
        PreparedStatement buscar;
        try {
            //String buscaUsuarios = "SELECT * FROM administrador";
            String buscaUsuarios = "SELECT * FROM usuario JOIN administrador ON idusuario = idadministrador";
            buscar = connection.prepareStatement(buscaUsuarios);
            ResultSet rs = buscar.executeQuery();
            clientes = new ArrayList<Cliente>();
            while (rs.next()) {
                Cliente cli = new Cliente();
                cli.setId(rs.getInt(1));
                cli.setNombre(rs.getString(2));
                cli.setContraseña(rs.getString(3));
                cli.setCorreo(rs.getString(4));
                cli.setFecha_nac(rs.getDate(5));
                cli.setTfno(rs.getInt(6));
                Blob blob = rs.getBlob(7);
                if (blob != null) {
                    byte[] imagen = blob.getBytes(1l, (int) blob.length());
                    cli.setImagen(imagen);
                }
                cli.setIdCliente(rs.getInt(8));
                clientes.add(cli);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    @Override
    public List<Cliente> getAllClientes() {
        Connection connection = con.getConnection();
        List<Cliente> usuarios = null;
        PreparedStatement buscar;
        try {
            String buscaUsuarios = "SELECT * FROM usuario JOIN cliente ON idusuario = id_user";
            buscar = connection.prepareStatement(buscaUsuarios);
            ResultSet rs = buscar.executeQuery();
            usuarios = new ArrayList<Cliente>();
            while (rs.next()) {
                Cliente cli = new Cliente();
                cli.setId(rs.getInt(1));
                cli.setNombre(rs.getString(2));
                cli.setContraseña(rs.getString(3));
                cli.setCorreo(rs.getString(4));
                cli.setFecha_nac(rs.getDate(5));
                cli.setTfno(rs.getInt(6));
                Blob blob = rs.getBlob(7);
                if (blob != null) {
                    byte[] imagen = blob.getBytes(1l, (int) blob.length());
                    cli.setImagen(imagen);
                }
                cli.setIdCliente(rs.getInt(8));
                usuarios.add(cli);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    @Override
    public List<Cliente> getAllRevisores() {
        Connection connection = con.getConnection();
        List<Cliente> usuarios = null;
        PreparedStatement buscar;
        try {
            String buscaUsuarios = "SELECT * FROM usuario JOIN revisor ON idusuario = idrevisor";
            buscar = connection.prepareStatement(buscaUsuarios);
            ResultSet rs = buscar.executeQuery();
            usuarios = new ArrayList<Cliente>();
            while (rs.next()) {
                Cliente cli = new Cliente();
                cli.setId(rs.getInt(1));
                cli.setNombre(rs.getString(2));
                cli.setContraseña(rs.getString(3));
                cli.setCorreo(rs.getString(4));
                cli.setFecha_nac(rs.getDate(5));
                cli.setTfno(rs.getInt(6));
                Blob blob = rs.getBlob(7);
                if (blob != null) {
                    byte[] imagen = blob.getBytes(1l, (int) blob.length());
                    cli.setImagen(imagen);
                }
                cli.setIdCliente(rs.getInt(8));
                usuarios.add(cli);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }
}

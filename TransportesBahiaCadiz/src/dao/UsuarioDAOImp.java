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
import model.Cliente;
import model.Usuario;

/**
 *
 * @author Vicky
 */
public class UsuarioDAOImp implements iUsuarioDAO {

    Conector con/* = new Conector()*/;
    public boolean insertado;
    public boolean borrado;

    public UsuarioDAOImp() {
        //con.connect();
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
            insertado = false;
            //Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
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
            //borrar.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(ZonaDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            borrado = false;
            ex.printStackTrace();
        } finally {
            //con.disconect();
        }
    }

    @Override
    public void insertarImagen(String nombre, String ruta) {
        insertado = false;
        byte[] imageByte;
        BufferedImage image = null;
        try {
            Connection connection = con.getConnection();
            PreparedStatement insertar;
            String sqlNuevoUsuario = "UPDATE usuario SET imagen_perfil = ? WHERE username = ?";
            insertar = connection.prepareStatement(sqlNuevoUsuario);
            Usuario usuario = getId(nombre);
            usuario.setImagen(ruta);
            if (usuario.getImagen() == null || usuario.getImagen().equals("null")) {
                usuario.setImagen("");
            }
            /*imageByte = Base64.getDecoder().decode(usuario.getImagen());
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            ImageIO.write(image, "jpg", new File("media/" + usuario.getId() + "_profile.jpg"));
            bis.close();
            usuario.setImagen("media/" + usuario.getId() + "_profile.jpg");*/
            //String path = ruta + "/" + usuario.getNombre();

            insertar.setString(2, usuario.getNombre());
            insertar.setString(1, usuario.getImagen());
            /*usuario.setFoto(ruta.getBytes());
             //usuario.setImagen("media/" + usuario.getId() + "_profile.jpg");
             insertar.setBytes(1, usuario.getFoto());
             
             //insertar.setString(2, usuario.getNombre());*/
            if (insertar.executeUpdate() != 0) {
                System.out.println("Insercción exitosa");
                insertado = true;
            }
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
                String imagen = rs.getString(7);
                if (imagen != null) {
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
            String buscaUsuarios = "SELECT * FROM administrador";
            buscar = connection.prepareStatement(buscaUsuarios);
            ResultSet rs = buscar.executeQuery();
            clientes = new ArrayList<Cliente>();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt(1));
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //con.disconect();
        }
        return clientes;
    }

    @Override
    public List<Usuario> getAllClientes() {
        //con.connect();
        Connection connection = con.getConnection();
        List<Cliente> clientes = null;
        List<Usuario> usuarios = null;
        PreparedStatement buscar;
        try {
            String buscaUsuarios = "SELECT * FROM usuario JOIN cliente ON idusuario = id_user";
            buscar = connection.prepareStatement(buscaUsuarios);
            ResultSet rs = buscar.executeQuery();
            usuarios = new ArrayList<Usuario>();
            while (rs.next()) {
                Usuario cli = new Cliente();
                cli.setId(rs.getInt(1));
                cli.setNombre(rs.getString(2));
                cli.setContraseña(rs.getString(3));
                cli.setCorreo(rs.getString(4));
                cli.setFecha_nac(rs.getDate(5));
                cli.setTfno(rs.getInt(6));
                usuarios.add(cli);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //con.disconect();
        }
        return usuarios;
    }
}

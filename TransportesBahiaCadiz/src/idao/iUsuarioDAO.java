package idao;

import java.sql.Date;
import java.util.List;
import model.Cliente;
import model.Usuario;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vicky
 */
public interface iUsuarioDAO {

    public void insertar(Usuario usuario);
    public void insertarCliente(int id);
    public void insertarAdmin(int id);
    public void insertarRevisor(int id);
    public void borrar(int id);
    public void insertarImagen(String nombre, String ruta);
    public List<Usuario> getUsuario(String nombre);
    public List<Usuario> getAll();
    public List<Cliente> getAllAdmins();
    public List<Cliente> getAllClientes();
    public List<Cliente> getAllRevisores();
    public Usuario getId(String nombre);
}

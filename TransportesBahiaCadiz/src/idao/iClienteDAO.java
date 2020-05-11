package idao;

import java.sql.Date;
import java.util.List;
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
public interface iClienteDAO {
    public void insertar(String nombre, String contraseña, String email, Date fecha_nac, int tfno);
    
    public List<Usuario> getUsuario(String nombre);
    
    public List<Usuario> getAll();
}

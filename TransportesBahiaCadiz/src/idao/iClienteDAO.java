/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import serializable.Cliente;

/**
 *
 * @author Vicky
 */
public interface iClienteDAO {
    public void insertar(int id);
    public void insertarAdmin(int id);
    public List<Cliente> getAllClientes();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.Linea;

/**
 *
 * @author Vicky
 */
public interface iLineaDAO {
    public void insertar(String nombreLinea);
    public void borrar(int idLinea);
    public List<Linea> getLinea(String nombre);
    public List<Linea> getAllLineas();
}

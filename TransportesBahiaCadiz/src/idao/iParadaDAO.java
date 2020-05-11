/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.Parada;

/**
 *
 * @author Vicky
 */
public interface iParadaDAO {
    public void insertar(char idZona, String nombreParada, String latitud, String longitud);
    public void borrar(int idParada);
    public List<Parada> getParada(String nombre);
    public List<Parada> getAllParadas();
}

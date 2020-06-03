/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.Corresponde;
import model.Parada;

/**
 *
 * @author Vicky
 */
public interface iParadaDAO {
    public void insertaTablaParadas();
    public void insertaParadas(Parada parada);
    public void insertaParadasAux(Parada parada);
    public void dropTablaParadas();
    public void borrar(int idParada);
    public List<Parada> getIntersectParadas();
    public List<Parada> getParada(String nombre);
    public List<Parada> getAllParadas();
    public List<Parada> getParadasViaje(int idLinea, int idNucleoOrigen, int idNucleoDestino);
    public String getDireccion(String nombre);
}

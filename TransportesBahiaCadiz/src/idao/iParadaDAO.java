/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import serializable.Cabecera;
import serializable.Parada;

/**
 *
 * @author Vicky
 */
public interface iParadaDAO {
    public void insertaTablaParadas();
    public void insertaParadas(Parada parada);
    public void insertaParadasAux(Parada parada);
    public void insertaCabecera(int id);
    public void insertaRegular(int id);
    public void dropTablaParadas();
    public void borrar(int idParada);
    public List<Parada> getIntersectParadas();
    public List<Parada> getParada(String nombre);
    public List<Parada> getAllParadas();
    public List<Cabecera> getAllCabecera();
    public List<Cabecera> getAllRegular();
    public List<Parada> getParadasViaje(int idLinea, int idNucleoOrigen, int idNucleoDestino);
    public String getDireccion(String nombre);
}

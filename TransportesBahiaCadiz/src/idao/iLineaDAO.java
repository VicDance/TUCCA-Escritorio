/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import serializable.Linea;

/**
 *
 * @author Vicky
 */
public interface iLineaDAO {
    public void insertarTablaAux();
    public void insertar(Linea linea);
    public void insertarAux(Linea linea);
    public void dropTablaAux();
    public List<Linea> getIntersectLinea();
    public List<Linea> getLinea(String nombre);
    public List<Linea> getAllLineas();
    public List<Linea> getLineasNucleo(int idNucleoOrigen, int idNucleoDestino);
    public String getNombreLinea(int idLinea);
    public int getIdLinea(String nombreLinea);
}

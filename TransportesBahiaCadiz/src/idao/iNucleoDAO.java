/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.Nucleo;

/**
 *
 * @author Vicky
 */
public interface iNucleoDAO {
    public void insertar(int idMunicipio, char idZona, String nombreNucleo);
    public void borrar(int idNucleo);
    public List<Nucleo> getNucleo(String nombre);
    public List<Nucleo> getAllNucleos();
}

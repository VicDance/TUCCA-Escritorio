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
    public void createTableAux();
    public void insertar(Nucleo nucleo);
    public void insertarAux(Nucleo nucleo);
    public void dropTableAux();
    public List<Nucleo> getNucleo(String nombre);
    public List<Nucleo> getAllNucleos();
    public List<Nucleo> getIntersection();
}

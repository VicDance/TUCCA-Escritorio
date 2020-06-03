/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.Corresponde;

/**
 *
 * @author Vicky
 */
public interface iCorrespondeDAO {
    public void insertaTablaCorresponde();
    public void insertarCorresponde(int idLinea, int idParada);
    public void insertarCorrespondeAux(int idLinea, int idParada);
    public void dropTablaCorresponde();
    public List<Corresponde> getIntersectCorresponde();
    public List<Corresponde> getAllCorresponde();
}

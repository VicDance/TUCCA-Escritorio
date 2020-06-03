/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.LugarInteres;

/**
 *
 * @author Vicky
 */
public interface iLugarInteresDAO {
    public void createTableLugarInteresAux();
    public void insertarLugarInteres(LugarInteres lugar);
    public void insertarLugarInteresAux(LugarInteres lugar);
    public void dropTablaLugaresAux();
    public List<LugarInteres> getIntersectCorresponde();
    public List<LugarInteres> getAllLugares();
}

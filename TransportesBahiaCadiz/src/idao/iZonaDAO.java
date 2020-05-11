/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.sql.Date;
import java.util.List;
import model.Zona;

/**
 *
 * @author Vicky
 */
public interface iZonaDAO {
    public void insertar(String idZona, String nombre); 
    public void deleteId(Zona zona);
    public void deleteNombre(Zona zona);
    public List<Zona> getZona(String nombre);
    public List<Zona> getAll();
}

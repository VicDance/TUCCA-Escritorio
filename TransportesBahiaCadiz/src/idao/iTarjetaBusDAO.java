/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.TarjetaBus;

/**
 *
 * @author Vicky
 */
public interface iTarjetaBusDAO {
    public void insertar(TarjetaBus tarjeta);
    public void borrar(int posicion);
    public List<TarjetaBus> getAllTarjetas();
}

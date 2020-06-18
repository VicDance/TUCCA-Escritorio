/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import serializable.Viaje;

/**
 *
 * @author Vicky
 */
public interface iViajeDAO {
    public void insertarViaje(Viaje viaje);
    public List<Viaje> getAllViajes();
    public List<Viaje> getViajesId(int idUsuario);
}

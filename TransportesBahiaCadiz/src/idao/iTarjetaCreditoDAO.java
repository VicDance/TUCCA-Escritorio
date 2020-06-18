/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import serializable.TarjetaCredito;

/**
 *
 * @author Vicky
 */
public interface iTarjetaCreditoDAO {
    public List<TarjetaCredito> getAllTarjetas();
    public List<TarjetaCredito> getTarjetasUsuario(int id);
    public void insertar(TarjetaCredito tarjeta);
    public void borrar(int posicion);
}

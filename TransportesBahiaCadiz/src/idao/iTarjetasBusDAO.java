/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.TarjetaEstandar;

/**
 *
 * @author Vicky
 */
public interface iTarjetasBusDAO {
    public void insertarEstandar(long numTarjeta);
    public void insertarEstudiante(long numTarjeta);
    public void insertarJubilado(long numTarjeta);
    public List<TarjetaEstandar> getAllTarjetasEstandar();
}

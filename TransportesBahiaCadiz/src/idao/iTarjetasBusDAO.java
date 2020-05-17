/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.sql.Date;
import java.util.List;
import model.TarjetaEstandar;
import model.TarjetaEstudiante;
import model.TarjetaJubilado;

/**
 *
 * @author Vicky
 */
public interface iTarjetasBusDAO {
    public void insertarEstandar(long numTarjeta);
    public void insertarEstudiante(long numTarjeta, Date inicio, Date fin);
    public void insertarJubilado(long numTarjeta, int año);
    public List<TarjetaEstandar> getAllTarjetasEstandar();
    public List<TarjetaEstudiante> getAllTarjetasEstudiante();
    public List<TarjetaJubilado> getAllTarjetasJubilado();
}

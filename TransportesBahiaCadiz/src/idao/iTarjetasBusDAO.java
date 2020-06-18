/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.sql.Date;
import java.util.List;
import serializable.TarjetaBus;
import serializable.TarjetaEstandar;
import serializable.TarjetaEstudiante;
import serializable.TarjetaJubilado;

/**
 *
 * @author Vicky
 */
public interface iTarjetasBusDAO {
    public void insertarTarjeta(TarjetaBus tarjeta);
    public void insertarEstandar(long numTarjeta);
    public void insertarEstudiante(long numTarjeta, Date inicio, Date fin);
    public void insertarJubilado(long numTarjeta, int año);
    public List<TarjetaBus> getAllTarjetas();
    public List<TarjetaEstandar> getAllTarjetasEstandar();
    public List<TarjetaEstudiante> getAllTarjetasEstudiante();
    public List<TarjetaJubilado> getAllTarjetasJubilado();
    public List<TarjetaBus> getTarjetasUsuario(int id);
    public TarjetaBus getTarjeta(long numTarjeta);
    public void borrarTarjeta(int posicion);
    public boolean recargarTarjeta(long numTarjeta, double saldo);
    public boolean restaSaldo(long numTarjeta, double saldo);
    public int getIdCodigo(long numTarjeta);
}

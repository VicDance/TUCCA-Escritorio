/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Vicky
 */
public class TarjetaEstandar {
    private long numTarjeta;
    private Date fecha_expedicion;
    
    public TarjetaEstandar(){
        
    }
    
    public TarjetaEstandar(long numTarjeta){
        this.numTarjeta = numTarjeta;
    }
    
    public TarjetaEstandar(long numTarjeta, Date fecha_expedicion){
        this.numTarjeta = numTarjeta;
        this.fecha_expedicion = fecha_expedicion;
    }

    public long getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(long numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public Date getFecha_expedicion() {
        return fecha_expedicion;
    }

    public void setFecha_expedicion(Date fecha_expedicion) {
        this.fecha_expedicion = fecha_expedicion;
    }

    @Override
    public String toString() {
        return "TarjetaEstandar{" + "numTarjeta=" + numTarjeta + ", fecha_expedicion=" + fecha_expedicion + '}';
    }

}

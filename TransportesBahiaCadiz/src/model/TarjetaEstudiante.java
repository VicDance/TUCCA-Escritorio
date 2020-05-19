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
public class TarjetaEstudiante extends TarjetaBus{
    private long numTarjeta;
    private Date fecha_ini;
    private Date fecha_fin;
    
    public TarjetaEstudiante(){}
    
    public TarjetaEstudiante(long numTarjeta){
        this.numTarjeta = numTarjeta;
    }
    
    public TarjetaEstudiante(long numTarjeta, Date fecha_ini, Date fecha_fin){
        this.numTarjeta = numTarjeta;
        this.fecha_ini = fecha_ini;
        this.fecha_fin = fecha_fin;
    }

    public long getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(long numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public Date getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(Date fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    @Override
    public String toString() {
        return "TarjetaEstudiante{" + "numTarjeta=" + numTarjeta + ", fecha_ini=" + fecha_ini + ", fecha_fin=" + fecha_fin + '}';
    }
}

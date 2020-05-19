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
public class TarjetaJubilado extends TarjetaBus{
    private long numTarjeta;
    private Date añoValidez;
    
    public TarjetaJubilado(){}
    
    public TarjetaJubilado(long numTarjeta){
        this.numTarjeta = numTarjeta;
    }
    
    public TarjetaJubilado(long numTarjeta, Date añoValidez){
        this.numTarjeta = numTarjeta;
        this.añoValidez = añoValidez;
    }

    public long getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(long numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public Date getAñoValidez() {
        return añoValidez;
    }

    public void setAñoValidez(Date añoValidez) {
        this.añoValidez = añoValidez;
    }

    @Override
    public String toString() {
        return "TarjetaJubilado{" + "numTarjeta=" + numTarjeta + ", a\u00f1oValidez=" + añoValidez + '}';
    }
}

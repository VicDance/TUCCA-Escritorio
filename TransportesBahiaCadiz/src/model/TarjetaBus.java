/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Vicky
 */
public class TarjetaBus {
    private long numTarjeta;
    private int id;
    private int saldo;
    private double descuento;
    
    public TarjetaBus(){}
    
    public TarjetaBus(long numTarjeta, int id, int saldo, double descuento){
        this.numTarjeta = numTarjeta;
        this.id = id;
        this.saldo = saldo;
        this.descuento = descuento;
    }

    public long getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(long numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "TarjetaBus{" + "numTarjeta=" + numTarjeta + ", id=" + id + ", saldo=" + saldo + ", descuento=" + descuento + '}';
    }
}

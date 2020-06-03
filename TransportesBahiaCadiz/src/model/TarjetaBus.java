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
    private double saldo;
    private double descuento;
    private int idCodigo;
    
    public TarjetaBus(){}
    
    public TarjetaBus(long numTarjeta, int id, double saldo, double descuento){
        this.numTarjeta = numTarjeta;
        this.id = id;
        this.saldo = saldo;
        this.descuento = descuento;
    }
    
    public TarjetaBus(long numTarjeta, int id, double saldo, double descuento, int idCodigo){
        this.numTarjeta = numTarjeta;
        this.id = id;
        this.saldo = saldo;
        this.descuento = descuento;
        this.idCodigo = idCodigo;
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(int idCodigo) {
        this.idCodigo = idCodigo;
    }

    @Override
    public String toString() {
        return "TarjetaBus{" + "numTarjeta=" + numTarjeta + ", id=" + id + ", saldo=" + saldo + ", descuento=" + descuento + ", idCodigo=" + idCodigo + '}';
    }
}

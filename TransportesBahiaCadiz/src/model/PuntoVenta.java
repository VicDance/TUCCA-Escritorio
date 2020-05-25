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
public class PuntoVenta {
    private int idPunto;
    private int idNucleo;
    private String tipo;
    private String direccion;
    private String latitud;
    private String longitud;

    public PuntoVenta() {
    }

    public PuntoVenta(int idPunto) {
        this.idPunto = idPunto;
    }

    public PuntoVenta(int idPunto, int idNucleo, String tipo, String direccion, String latitud, String longitud) {
        this.idPunto = idPunto;
        this.idNucleo = idNucleo;
        this.tipo = tipo;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public int getIdNucleo() {
        return idNucleo;
    }

    public void setIdNucleo(int idNucleo) {
        this.idNucleo = idNucleo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "PuntoVenta{" + "idPunto=" + idPunto + ", idNucleo=" + idNucleo + ", tipo=" + tipo + ", direccion=" + direccion + ", latitud=" + latitud + ", longitud=" + longitud + '}';
    }
}

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
public class Parada {
    private int idParada;
    private char idZona;
    private String nombreParada;
    private String latitud;
    private String longitud;
    
    public Parada(){
        
    }

    public int getIdParada() {
        return idParada;
    }
    
    public void setIdParada(int idParada){
        this.idParada = idParada;
    }

    public char getIdZona() {
        return idZona;
    }

    public void setIdZona(char idZona) {
        this.idZona = idZona;
    }

    public String getNombreParada() {
        return nombreParada;
    }

    public void setNombreParada(String nombreParada) {
        this.nombreParada = nombreParada;
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
        return "Parada{" + "idParada=" + idParada + ", idZona=" + idZona + ", nombreParada=" + nombreParada + ", latitud=" + latitud + ", longitud=" + longitud + '}';
    }
}

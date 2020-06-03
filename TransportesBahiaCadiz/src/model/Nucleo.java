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
public class Nucleo {
    private int idNucleo;
    private int idMunicipio;
    private String idZona;
    private String nombreNucleo;
    
    public Nucleo(){
        
    }

    public Nucleo(int idNucleo, int idMunicipio, String idZona, String nombreNucleo) {
        this.idNucleo = idNucleo;
        this.idMunicipio = idMunicipio;
        this.idZona = idZona;
        this.nombreNucleo = nombreNucleo;
    }

    public int getIdNucleo() {
        return idNucleo;
    }

    public void setIdNucleo(int idNucleo) {
        this.idNucleo = idNucleo;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getNombreNucleo() {
        return nombreNucleo;
    }

    public void setNombreNucleo(String nombreNucleo) {
        this.nombreNucleo = nombreNucleo;
    }

    @Override
    public String toString() {
        return "Nucleo{" + "idNucleo=" + idNucleo + ", idMunicipio=" + idMunicipio + ", idZona=" + idZona + ", nombreNucleo=" + nombreNucleo + '}';
    }
}

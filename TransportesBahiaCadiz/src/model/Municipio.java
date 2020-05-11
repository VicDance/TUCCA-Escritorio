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
public class Municipio {
    private int idMunicipio;
    private String nombreMunicipio;
    
    public Municipio(){
        
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    @Override
    public String toString() {
        return "Municipio{" + "idMunicipio=" + idMunicipio + ", nombreMunicipio=" + nombreMunicipio + '}';
    }
}

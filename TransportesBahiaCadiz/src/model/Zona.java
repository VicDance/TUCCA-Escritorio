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
public class Zona {
    private String idZona;
    private String nombreZona;
    
    public Zona(){
        
    }
    
    public Zona(String idZona, String nombreZona){
        this.idZona = idZona;
        this.nombreZona = nombreZona;
    }

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    @Override
    public String toString() {
        return "Zona{" + "idZona=" + idZona + ", nombreZona=" + nombreZona + '}';
    }
}

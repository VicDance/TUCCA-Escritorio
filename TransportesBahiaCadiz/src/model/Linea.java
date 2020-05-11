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
public class Linea {
    private int idLinea;
    private String nombreLinea;
    
    public Linea(){
        
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public String getNombreLinea() {
        return nombreLinea;
    }

    public void setNombreLinea(String nombreLinea) {
        this.nombreLinea = nombreLinea;
    }

    @Override
    public String toString() {
        return "Linea{" + "idLinea=" + idLinea + ", nombreLinea=" + nombreLinea + '}';
    }
}

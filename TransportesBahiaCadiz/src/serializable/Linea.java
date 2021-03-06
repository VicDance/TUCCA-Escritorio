/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializable;

import java.io.Serializable;

/**
 *
 * @author Vicky
 */
public class Linea implements Serializable{
    private static final long serialVersionUID = 6529685098267757694L;
    
    private int idLinea;
    private String nombreLinea;
    
    public Linea(){
        
    }
    
    public Linea(int idLinea){
        this.idLinea = idLinea;
    }
    
    public Linea(String nombreLinea){
        this.nombreLinea = nombreLinea;
    }

    public Linea(int idLinea, String nombreLinea) {
        this.idLinea = idLinea;
        this.nombreLinea = nombreLinea;
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

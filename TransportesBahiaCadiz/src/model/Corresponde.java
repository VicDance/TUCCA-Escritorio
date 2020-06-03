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
public class Corresponde {
    private int idLinea;
    private int idParada;
    
    public Corresponde(){
        
    }
    
    public Corresponde(int idLinea, int idParada){
        this.idLinea = idLinea;
        this.idParada = idParada;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdParada() {
        return idParada;
    }

    public void setIdParada(int idParada) {
        this.idParada = idParada;
    }

    @Override
    public String toString() {
        return "Corresponde{" + "idLinea=" + idLinea + ", idParada=" + idParada + '}';
    }
}

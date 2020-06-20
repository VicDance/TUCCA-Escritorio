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
public class Cabecera extends Parada implements Serializable{
    private int idCabecera;
    
    public Cabecera() {
    }
    
    public Cabecera(int idCabecera){
        this.idCabecera = idCabecera;
    }

    public int getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(int idCabecera) {
        this.idCabecera = idCabecera;
    }
    
    
}

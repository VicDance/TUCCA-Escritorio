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
public class CodigoQR {
    private int idCodigo;
    private String horaUso;
    
    public CodigoQR(){
        
    }

    public CodigoQR(String horaUso) {
        this.horaUso = horaUso;
    }

    public CodigoQR(int idCodigo, String horaUso) {
        this.idCodigo = idCodigo;
        this.horaUso = horaUso;
    }

    public int getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(int idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getHoraUso() {
        return horaUso;
    }

    public void setHoraUso(String horaUso) {
        this.horaUso = horaUso;
    }

    @Override
    public String toString() {
        return "CodigoQR{" + "idCodigo=" + idCodigo + ", horaUso=" + horaUso + '}';
    }
}

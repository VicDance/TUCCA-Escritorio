/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializable;

import java.sql.Time;
import java.sql.Date;

/**
 *
 * @author Vicky
 */
public class Viaje {
    private int idViaje;
    private int idUsuario;
    private int idLinea;
    private int idMunicipio;
    private double tarifa;
    /*private Time horaLlegada;
    private Time horaSalida;*/
    String horaLlegada;
    String horaSalida;
    private Date fechaViaje;
    
    public Viaje(){}

    public Viaje(int idViaje, int idUsuario, int idLinea, int idMunicipio, double tarifa, String horaSalida, String horaLlegada, Date fechaViaje) {
        this.idViaje = idViaje;
        this.idUsuario = idUsuario;
        this.idLinea = idLinea;
        this.idMunicipio = idMunicipio;
        this.tarifa = tarifa;
        this.horaLlegada = horaLlegada;
        this.horaSalida = horaSalida;
        this.fechaViaje = fechaViaje;
    }
    
    public Viaje(int idUsuario, int idLinea, int idMunicipio, double tarifa, String horaSalida, String horaLlegada, Date fechaViaje) {
        this.idUsuario = idUsuario;
        this.idLinea = idLinea;
        this.idMunicipio = idMunicipio;
        this.tarifa = tarifa;
        this.horaLlegada = horaLlegada;
        this.horaSalida = horaSalida;
        this.fechaViaje = fechaViaje;
    }
    
    public Viaje(int idViaje){
        this.idViaje = idViaje;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Date getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(Date fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    @Override
    public String toString() {
        return "Viaje{" + "idViaje=" + idViaje + ", idUsuario=" + idUsuario + ", idLinea=" + idLinea + ", idMunicipio=" + idMunicipio + ", tarifa=" + tarifa + ", horaLlegada=" + horaLlegada + ", horaSalida=" + horaSalida + ", fechaViaje=" + fechaViaje + '}';
    }
}

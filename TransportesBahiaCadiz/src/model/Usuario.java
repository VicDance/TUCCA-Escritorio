/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Vicky
 */
public class Usuario implements Serializable{
    private int id;
    private String nombre;
    private String contraseña;
    private String correo;
    private Date fecha_nac;
    private int tfno;
    private String imagen;
    private byte[] foto;
    
    public Usuario(){        
    }
    
    public Usuario(String nombre, String contraseña, String correo, Date fecha_nac, int tfno){
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fecha_nac = fecha_nac;
        this.tfno = tfno;
    }
    
    public Usuario(String nombre, String contraseña, String correo, Date fecha_nac, int tfno, String imagen){
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fecha_nac = fecha_nac;
        this.tfno = tfno;
        this.imagen = imagen;
    }
    
    public Usuario(String nombre, String contraseña, String correo, Date fecha_nac, int tfno, byte[] foto){
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fecha_nac = fecha_nac;
        this.tfno = tfno;
        this.foto = foto;
    }
    
    public Usuario(int id, String nombre, String contraseña, String correo, Date fecha_nac, int tfno){
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fecha_nac = fecha_nac;
        this.tfno = tfno;
    }
    
    public Usuario(int id, String nombre, String contraseña, String correo, Date fecha_nac, int tfno, String imagen){
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fecha_nac = fecha_nac;
        this.tfno = tfno;
        this.imagen = imagen;
    }
    
    public Usuario(int id, String nombre, String contraseña, String correo, Date fecha_nac, int tfno, byte[] foto){
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fecha_nac = fecha_nac;
        this.tfno = tfno;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public int getTfno() {
        return tfno;
    }

    public void setTfno(int tfno) {
        this.tfno = tfno;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public byte[] getFoto() {
        return foto;
    }
    
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", contrase\u00f1a=" + contraseña + ", correo=" + correo + ", fecha_nac=" + fecha_nac + ", tfno=" + tfno + ", imagen=" + imagen + '}';
    }
}

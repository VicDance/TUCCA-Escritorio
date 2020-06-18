/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializable;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Vicky
 */
public class Cliente extends Usuario implements Serializable{
    private static final long serialVersionUID = 6529685098267757696L;
    
    private int idCliente;
    
    public Cliente(){
        super();
    }
    
    public Cliente(int idCliente){
        super();
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + '}';
    }
}

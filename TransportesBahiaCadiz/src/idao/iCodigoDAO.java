/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import serializable.CodigoQR;

/**
 *
 * @author Vicky
 */
public interface iCodigoDAO {
    public boolean insertar(CodigoQR codigo);
    public List<CodigoQR> getAllCodigos();
    public void updateHora(CodigoQR codigo);
    public CodigoQR getCodigo(String hora);
    public CodigoQR getCodigoById(int id);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.PuntoVenta;

/**
 *
 * @author Vicky
 */
public interface iPuntoVenta {
    public List<PuntoVenta> getAllPuntos();
    public List<PuntoVenta> getPuntoVentaId(int id);
    public List<PuntoVenta> getPuntosVentaNucleo(int idNucleo);
    public PuntoVenta getPuntoVenta(int idNucleo);
}

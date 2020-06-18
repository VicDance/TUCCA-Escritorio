/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import serializable.PuntoVenta;

/**
 *
 * @author Vicky
 */
public interface iPuntoVenta {
    public void createTableAux();
    public void insertar(PuntoVenta punto);
    public void insertarAux(PuntoVenta punto);
    public void dropTableAux();
    public List<PuntoVenta> getIntersection();
    public List<PuntoVenta> getAllPuntos();
    public List<PuntoVenta> getPuntoVentaId(int id);
    public List<PuntoVenta> getPuntosVentaNucleo(int idNucleo);
    public PuntoVenta getPuntoVenta(int idNucleo);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import serializable.Municipio;

/**
 *
 * @author Vicky
 */
public interface iMunicipioDAO {
    public void createTableAux();
    public void insertar(Municipio muni);
    public void insertarAux(Municipio muni);
    public void dropTableAux();
    public List<Municipio> getMunicipio(String nombre);
    public List<Municipio> getAllMunicipios();
    public List<Municipio> getIntersection();
    public String getNombreMunicipio(int idMunicipio);
}

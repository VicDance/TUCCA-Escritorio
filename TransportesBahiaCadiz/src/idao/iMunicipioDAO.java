/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import java.util.List;
import model.Municipio;

/**
 *
 * @author Vicky
 */
public interface iMunicipioDAO {
    public void insertar(String nombreMunicipio);
    public void borrar(int idMunicipio);
    public List<Municipio> getMunicipio(String nombre);
    public List<Municipio> getAllMunicipios();
}

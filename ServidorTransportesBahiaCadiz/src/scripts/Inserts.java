/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts;

import connector.Conector;
import dao.CorrespondeDAOImp;
import dao.LineaDAOImp;
import dao.LugarInteresDAOImp;
import dao.MunicipioDAOImp;
import dao.NucleoDAOImp;
import dao.ParadaDAOImp;
import dao.PuntoVentaDAOImp;
import dao.ZonaDAOImp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Linea;
import model.Corresponde;
import model.LugarInteres;
import model.Municipio;
import model.Nucleo;
import model.Parada;
import model.PuntoVenta;
import model.Zona;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Vicky
 */
public class Inserts {

    static ParadaDAOImp pdi;
    static LineaDAOImp ldi;
    static CorrespondeDAOImp cdi;
    static Conector con;
    static URL url;

    public static void main(String[] args) {

        con = new Conector();
        con.connect();
        //ingresaParadas();
        //ingresaLineas();
        //ingresaCorresponde();
        //ingresaMunicipios();
        //ingresaLugaresInteres();
        //ingresaZonas();
        //ingresaNucleos();
        ingresaPuntosVenta();
    }

    private static void ingresaCorresponde() {
        cdi = new CorrespondeDAOImp(con);
        //pdi = new ParadaDAOImp(con);
        ldi = new LineaDAOImp(con);
        int idLinea = 0;
        //List<Parada> paradas = pdi.getAllParadas();
        List<Linea> lineas = ldi.getAllLineas();
        JSONObject objetoJson = null;
        try {
            cdi.dropTablaCorresponde();
            cdi.insertaTablaCorresponde();
            //antes era paradas.size
            for (int i = 0; i < lineas.size(); i++) {
                idLinea = lineas.get(i).getIdLinea();
                //System.out.println("Linea " + idLinea);
                //url = new URL("http://api.ctan.es/v1/Consorcios/2/paradas/lineasPorParadas/" + id);
                url = new URL("http://api.ctan.es/v1/Consorcios/2/lineas/" + idLinea + "/paradas");
                //System.out.println(url);
                //InputStream is = url.openStream();
                HttpURLConnection hr = (HttpURLConnection) url.openConnection();
                hr.setRequestMethod("GET");
                if (hr.getResponseCode() == 200) {
                    InputStream is = hr.getInputStream();
                    //StringBuffer sb = new StringBuffer();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while (((line = bf.readLine()) != null)) {
                        objetoJson = new JSONObject(line);
                        //System.out.println(objetoJson);
                    }
                    JSONArray array = objetoJson.getJSONArray("paradas");
                    List<Integer> idsParadas = new ArrayList<Integer>();
                    for (int x = 0; x < array.length(); x++) {
                        int idParada = array.getJSONObject(x).getInt("idParada");
                        idsParadas.add(idParada);
                        //System.out.println(idParada);
                    }
                    if (cdi.getAllCorresponde().isEmpty() || cdi.getAllCorresponde().size() < ldi.getAllLineas().size()) {
                        for (int y = 0; y < idsParadas.size(); y++) {
                            cdi.insertarCorresponde(idLinea, idsParadas.get(y));
                        }
                    }
                    for (int y = 0; y < idsParadas.size(); y++) {
                        //cdi.insertarCorresponde(idLinea, idsParadas.get(y));
                        cdi.insertarCorrespondeAux(idLinea, idsParadas.get(y));
                    }
                    List<Corresponde> list = cdi.getIntersectCorresponde();
                    if (list.size() > 0) {
                        for (int y = 0; y < list.size(); y++) {
                            cdi.insertarCorresponde(list.get(y).getIdLinea(), list.get(y).getIdParada());
                        }
                    }
                }
            }
            cdi.dropTablaCorresponde();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void ingresaParadas() {
        pdi = new ParadaDAOImp(con);
        JSONObject objetoJson = null;
        try {
            pdi.dropTablaParadas();
            pdi.insertaTablaParadas();

            url = new URL("http://api.ctan.es/v1/Consorcios/2/paradas");
            HttpURLConnection hr = (HttpURLConnection) url.openConnection();
            hr.setRequestMethod("GET");
            if (hr.getResponseCode() == 200) {
                InputStream is = hr.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                String line;
                while (((line = bf.readLine()) != null)) {
                    //System.out.println(line);
                    objetoJson = new JSONObject(line);
                    //System.out.println("objeto " + objetoJson);
                }
                List<Parada> list = new ArrayList<Parada>();
                JSONArray array = objetoJson.getJSONArray("paradas");
                for (int i = 0; i < array.length(); i++) {
                    Parada parada = new Parada(array.getJSONObject(i).getInt("idParada"), array.getJSONObject(i).getString("idZona"),
                            array.getJSONObject(i).getString("nombre"), array.getJSONObject(i).getString("latitud"), array.getJSONObject(i).getString("longitud"));
                    list.add(parada);
                }
                if (pdi.getAllParadas().isEmpty()) {
                    //System.out.println("vacio");
                    for (int y = 0; y < list.size(); y++) {
                        pdi.insertaParadas(list.get(y));
                    }
                }
                for (int y = 0; y < list.size(); y++) {
                    pdi.insertaParadasAux(list.get(y));
                }
                List<Parada> paradas = pdi.getIntersectParadas();
                if (paradas.size() > 0) {
                    for (int y = 0; y < paradas.size(); y++) {
                        pdi.insertaParadas(list.get(y));
                    }
                }
                pdi.dropTablaParadas();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void ingresaLineas() {
        JSONObject objetoJson = null;
        /*LineaDAOImp */
        ldi = new LineaDAOImp(con);

        ldi.dropTablaAux();
        ldi.insertarTablaAux();
        try {
            url = new URL("http://api.ctan.es/v1/Consorcios/2/modostransporte/1/lineas");
            HttpURLConnection hr = (HttpURLConnection) url.openConnection();
            hr.setRequestMethod("GET");
            if (hr.getResponseCode() == 200) {
                InputStream is = hr.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                String line;
                while (((line = bf.readLine()) != null)) {
                    //System.out.println(line);
                    objetoJson = new JSONObject(line);
                    //System.out.println("objeto " + objetoJson);
                }
                List<Linea> list = new ArrayList<Linea>();
                JSONArray array = objetoJson.getJSONArray("lineas");
                for (int i = 0; i < array.length(); i++) {
                    Linea linea = new Linea(array.getJSONObject(i).getInt("idLinea"), array.getJSONObject(i).getString("codigo"));
                    list.add(linea);
                    //System.out.println(linea);
                }
                if (ldi.getAllLineas().isEmpty()) {
                    //System.out.println("vacio");
                    for (int y = 0; y < list.size(); y++) {
                        ldi.insertar(list.get(y));
                    }
                }
                for (int y = 0; y < list.size(); y++) {
                    ldi.insertarAux(list.get(y));
                }
                List<Linea> lineas = ldi.getIntersectLinea();
                if (lineas.size() > 0) {
                    for (int y = 0; y < lineas.size(); y++) {
                        ldi.insertar(list.get(y));
                    }
                }
                ldi.dropTablaAux();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void ingresaLugaresInteres() {
        LugarInteresDAOImp lidi = new LugarInteresDAOImp(con);
        JSONObject objetoJson = null;

        lidi.dropTablaLugaresAux();
        lidi.createTableLugarInteresAux();
        try {
            url = new URL("http://api.ctan.es/v1/Consorcios/2/lugares_interes");
            HttpURLConnection hr = (HttpURLConnection) url.openConnection();
            hr.setRequestMethod("GET");
            if (hr.getResponseCode() == 200) {
                InputStream is = hr.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                String line;
                while (((line = bf.readLine()) != null)) {
                    //System.out.println(line);
                    objetoJson = new JSONObject(line);
                    //System.out.println("objeto " + objetoJson);
                }
                List<LugarInteres> list = new ArrayList<LugarInteres>();
                JSONArray array = objetoJson.getJSONArray("lugaresInteres");
                //System.out.println(array);
                for (int i = 0; i < array.length(); i++) {
                    //System.out.println(array.get(i));
                    LugarInteres lugar;
                    if (array.getJSONObject(i).get("latitud").toString().equalsIgnoreCase("null") 
                            || array.getJSONObject(i).get("longitud").toString().equalsIgnoreCase("null")) {
                        //System.out.println("null");
                        lugar = new LugarInteres(array.getJSONObject(i).getInt("idLugar"),
                                array.getJSONObject(i).getInt("idMunicipio"), "",
                                "", array.getJSONObject(i).getString("nombre"));
                    } else {
                        //System.out.println(array.getJSONObject(i).getString("latitud") + " " + array.getJSONObject(i).getString("longitud"));
                        lugar = new LugarInteres(array.getJSONObject(i).getInt("idLugar"),
                                array.getJSONObject(i).getInt("idMunicipio"), array.getJSONObject(i).getString("latitud"),
                                array.getJSONObject(i).getString("longitud"), array.getJSONObject(i).getString("nombre"));
                    }
                    list.add(lugar);
                    //System.out.println(linea);
                }
                if (lidi.getAllLugares().isEmpty()) {
                    for (int y = 0; y < list.size(); y++) {
                        lidi.insertarLugarInteres(list.get(y));
                    }
                }
                for (int y = 0; y < list.size(); y++) {
                    lidi.insertarLugarInteresAux(list.get(y));
                }
                List<LugarInteres> lugares = lidi.getIntersectCorresponde();
                if (lugares.size() > 0) {
                    for (int y = 0; y < lugares.size(); y++) {
                        lidi.insertarLugarInteres(list.get(y));
                    }
                }
                lidi.dropTablaLugaresAux();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void ingresaMunicipios() {
        MunicipioDAOImp mdi = new MunicipioDAOImp(con);
        JSONObject objetoJson = null;

        mdi.dropTableAux();
        mdi.createTableAux();
        try {
            url = new URL("http://api.ctan.es/v1/Consorcios/2/municipios");
            HttpURLConnection hr = (HttpURLConnection) url.openConnection();
            hr.setRequestMethod("GET");
            if (hr.getResponseCode() == 200) {
                InputStream is = hr.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                String line;
                while (((line = bf.readLine()) != null)) {
                    objetoJson = new JSONObject(line);
                }
                List<Municipio> list = new ArrayList<Municipio>();
                JSONArray array = objetoJson.getJSONArray("municipios");
                for (int i = 0; i < array.length(); i++) {
                    Municipio muni = new Municipio(array.getJSONObject(i).getInt("idMunicipio"), 
                            array.getJSONObject(i).getString("datos"));
                    
                    list.add(muni);
                }
                if (mdi.getAllMunicipios().isEmpty()) {
                    for (int y = 0; y < list.size(); y++) {
                        mdi.insertar(list.get(y));
                    }
                }
                for (int y = 0; y < list.size(); y++) {
                    mdi.insertarAux(list.get(y));
                }
                List<Municipio> municipios = mdi.getIntersection();
                if (municipios.size() > 0) {
                    for (int y = 0; y < municipios.size(); y++) {
                        mdi.insertar(list.get(y));
                    }
                }
                mdi.dropTableAux();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void ingresaZonas(){
        ZonaDAOImp zdi = new ZonaDAOImp(con);
        JSONObject objetoJson = null;

        zdi.dropTableAux();
        zdi.createTableAux();
        try {
            url = new URL("http://api.ctan.es/v1/Consorcios/2/zonas");
            HttpURLConnection hr = (HttpURLConnection) url.openConnection();
            hr.setRequestMethod("GET");
            if (hr.getResponseCode() == 200) {
                InputStream is = hr.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                String line;
                while (((line = bf.readLine()) != null)) {
                    objetoJson = new JSONObject(line);
                }
                List<Zona> list = new ArrayList<Zona>();
                JSONArray array = objetoJson.getJSONArray("zonas");
                for (int i = 0; i < array.length(); i++) {
                    Zona zona = new Zona(array.getJSONObject(i).getString("idZona"), 
                            array.getJSONObject(i).getString("nombre"));
                    
                    list.add(zona);
                }
                if (zdi.getAll().isEmpty()) {
                    for (int y = 0; y < list.size(); y++) {
                        zdi.insertar(list.get(y));
                    }
                }
                for (int y = 0; y < list.size(); y++) {
                    zdi.insertarAux(list.get(y));
                }
                List<Zona> zonas = zdi.getIntersection();
                if (zonas.size() > 0) {
                    for (int y = 0; y < zonas.size(); y++) {
                        zdi.insertar(list.get(y));
                    }
                }
                zdi.dropTableAux();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void ingresaNucleos(){
        NucleoDAOImp ndi = new NucleoDAOImp(con);
        JSONObject objetoJson = null;

        ndi.dropTableAux();
        ndi.createTableAux();
        try {
            url = new URL("http://api.ctan.es/v1/Consorcios/2/nucleos");
            HttpURLConnection hr = (HttpURLConnection) url.openConnection();
            hr.setRequestMethod("GET");
            if (hr.getResponseCode() == 200) {
                InputStream is = hr.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                String line;
                while (((line = bf.readLine()) != null)) {
                    objetoJson = new JSONObject(line);
                }
                List<Nucleo> list = new ArrayList<Nucleo>();
                JSONArray array = objetoJson.getJSONArray("nucleos");
                for (int i = 0; i < array.length(); i++) {
                    Nucleo nucleo = new Nucleo(array.getJSONObject(i).getInt("idNucleo"), 
                            array.getJSONObject(i).getInt("idMunicipio"), array.getJSONObject(i).getString("idZona"),
                    array.getJSONObject(i).getString("nombre"));
                    
                    list.add(nucleo);
                }
                if (ndi.getAllNucleos().isEmpty()) {
                    for (int y = 0; y < list.size(); y++) {
                        ndi.insertar(list.get(y));
                    }
                }
                for (int y = 0; y < list.size(); y++) {
                    ndi.insertarAux(list.get(y));
                }
                List<Nucleo> nucleos = ndi.getIntersection();
                if (nucleos.size() > 0) {
                    for (int y = 0; y < nucleos.size(); y++) {
                        ndi.insertar(list.get(y));
                    }
                }
                ndi.dropTableAux();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void ingresaPuntosVenta(){
        PuntoVentaDAOImp pvdi = new PuntoVentaDAOImp(con);
        JSONObject objetoJson = null;

        pvdi.dropTableAux();
        pvdi.createTableAux();
        try {
            url = new URL("http://api.ctan.es/v1/Consorcios/2/puntos_venta");
            HttpURLConnection hr = (HttpURLConnection) url.openConnection();
            hr.setRequestMethod("GET");
            if (hr.getResponseCode() == 200) {
                InputStream is = hr.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                String line;
                while (((line = bf.readLine()) != null)) {
                    objetoJson = new JSONObject(line);
                }
                List<PuntoVenta> list = new ArrayList<PuntoVenta>();
                JSONArray array = objetoJson.getJSONArray("puntosVenta");
                for (int i = 0; i < array.length(); i++) {
                    PuntoVenta punto = new PuntoVenta(array.getJSONObject(i).getInt("idComercio"), 
                            array.getJSONObject(i).getInt("idNucleo"), array.getJSONObject(i).getString("tipo"),
                    array.getJSONObject(i).getString("direccion"), array.getJSONObject(i).getString("latitud"),
                    array.getJSONObject(i).getString("longitud"));
                    
                    list.add(punto);
                }
                if (pvdi.getAllPuntos().isEmpty()) {
                    for (int y = 0; y < list.size(); y++) {
                        pvdi.insertar(list.get(y));
                    }
                }
                for (int y = 0; y < list.size(); y++) {
                    pvdi.insertarAux(list.get(y));
                }
                List<PuntoVenta> puntos = pvdi.getIntersection();
                if (puntos.size() > 0) {
                    for (int y = 0; y < puntos.size(); y++) {
                        pvdi.insertar(list.get(y));
                    }
                }
                pvdi.dropTableAux();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

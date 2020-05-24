/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts;

import connector.Conector;
import dao.ParadaDAOImp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Linea;
import model.Parada;
import org.json.JSONArray;

import org.json.JSONObject;

/**
 *
 * @author Vicky
 */
public class Corresponde {

    public static void main(String[] args) {

        try {
            Conector con = new Conector();
            con.connect();
            ParadaDAOImp pdi = new ParadaDAOImp(con);
            int id = 0;
            List<Parada> paradas = pdi.getAllParadas();
            //List<Linea> lineas = new ArrayList<Linea>();
            URL url;
            JSONObject objetoJson;
            JSONArray jsonArray;
            Linea linea;
            for (int i = 0; i < paradas.size(); i++) {
                id = paradas.get(i).getIdParada();
                url = new URL("http://api.ctan.es/v1/Consorcios/2/paradas/lineasPorParadas/" + id);
                //InputStream is = url.openStream();
                HttpURLConnection hr = (HttpURLConnection) url.openConnection();
                hr.setRequestMethod("GET");
                if (hr.getResponseCode() == 200) {
                    InputStream is = hr.getInputStream();
                    StringBuffer sb = new StringBuffer();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while (((line = bf.readLine()) != null)) {
                        //System.out.println(line);
                        //objetoJson = new JSONObject(line);
                        jsonArray = new JSONArray(line);
                        for (int x = 0; x < jsonArray.length(); x++) {
                            objetoJson = jsonArray.getJSONObject(x);
                            int idLinea = objetoJson.getInt("idLinea");
                            String nombre = objetoJson.getString("codigo");
                            linea = new Linea(idLinea, nombre);
                            //lineas.add(linea);
                            //System.out.println(linea.getNombreLinea());
                            pdi.insertarCorresponde(idLinea, id);
                        }
                        /*int idLinea = jsonArray.getInt("idLinea");
                         String nombre = jsonArray.getString("codigo");
                         linea = new Linea(idLinea, nombre);
                         System.out.println(linea.getNombreLinea());*/
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Corresponde.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Corresponde.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

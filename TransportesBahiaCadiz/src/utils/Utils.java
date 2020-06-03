/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import static javax.management.Query.lt;

/**
 *
 * @author Vicky
 */
public class Utils {
    /*public void compruebaCoincidencia(String palabra){
        String coincidencia = "";
        String coincidenciaFinal = "";
        
        for (int i = 1; i &lt; palabra.length; i++) { // no es necesario comprobar la primera cadena

            if (!(coincidencia.length() > 0)) {
                final String cadena = palabra[i];
                final String cadenaAnterior = palabra[i - 1];

                for (int z = 0; z &lt; cadenaAnterior.length(); z++) { // se recorre la cadena anterior
                    final String caracterEvaluar = String.valueOf(cadenaAnterior.charAt(z));

                    // se hace la comprobacion de coincidencias
                    if (coincidencia.equals(cadena)) { 
                        if (cadena.contains(coincidencia)) {
                            coincidencia += caracterEvaluar;
                        }
                    } else {
                        if (cadena.contains(caracterEvaluar)) {
                            coincidenciaFinal += caracterEvaluar;
                            coincidencia = caracterEvaluar;
                        }
                    }
                }
            }
        }
    }*/
    
    /*private static */public ArrayList allLongestCommonSubstring(String s1, String s2) {
        ArrayList<String> palabras = new ArrayList<>();
        ciclo:
        // recorrer caracter por caracter el string "s1"
        for (int i = 0, ilargo = s1.length(); i < ilargo; i++) {
            // para cada caracter de "s1" recorrer todos los de "s2"
            for (int j = 0, jlargo = s2.length(); j < jlargo; j++) {

                // contador de maximo
                int max = 0;

                // comparar "s1" frente a "s2" para encontrar la palabra
                while (s1.charAt(i + max) == s2.charAt(j + max)) {

                    // por cada coincidencia aumentar el maximo
                    max++;

                    // pero si el maximo excede el largo de algunos de los strings, romper el ciclo
                    if ((i + max >= ilargo) || (j + max >= jlargo)) {
                        break;
                    }
                }

                // si la cantidad de coincidencias es mayor que 3
                if (max > 3) {
                    // obtener la cadenas desde i hasta donde terminan de coincidir (i+max)
                    palabras.add(s1.substring(i, (i + max)));

                    if (i + max >= ilargo) {
                        // si está al final, saltar los caracteres que quedan y romper el ciclo
                        break ciclo;
                    } else {
                        // si no, saltar la palabra encontrada
                        i += (i + max < ilargo) ? max : 0;
                    }
                }
            }
        }

        return palabras;
    }
}

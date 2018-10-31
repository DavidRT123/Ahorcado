/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Datos;
import vista.VentanaPrincipal;

/**
 *
 * @author mdfda
 */
public class Controlador {
    
    private static Datos d = new Datos();
    private static VentanaPrincipal ventana = new VentanaPrincipal(d.getImagenes());
    
    public static void iniciar(){
        ventana.setVisible(true);
    }
    
    /**
     * Método para devolver una palabra al azar
     * @return letras = Array de carácteres con la palabra al azar seleccionada 
     */
    public static char[] obtenerPalabra(){
        int longitudPalabras = d.getPalabras().length, i; 
        //Palabra obtenida al azar
        String palabra = d.getPalabra((int) (Math.random() * longitudPalabras));
        char[] letras = new char[palabra.length()];
        for(i = 0; i < palabra.length(); i++){
            letras[i] = palabra.charAt(i);
        }
        return letras;
    }
}

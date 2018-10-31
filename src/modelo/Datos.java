/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author mdfda
 */
public class Datos {
    private final static String[] imagenes = {"/modelo/imagenes/foto1.png", "/modelo/imagenes/foto2.png", "/modelo/imagenes/foto3.png", "/modelo/imagenes/foto4.png", "/modelo/imagenes/foto5.png", "/modelo/imagenes/foto6.png"};
    private String[] palabras = new String[6];

    public Datos(){
        palabras[0] = "Tigre";
        palabras[1] = "Lenteja";
        palabras[2] = "Banana";
        palabras[3] = "Queso";
        palabras[4] = "Zahorí";
        palabras[5] = "Bruja";
        //Añadir aquí para cargar desde archivo
        //...
    }

    public static String[] getImagenes() {
        return imagenes;
    }
    
    public String[] getPalabras() {
        return palabras;
    }

    public void setPalabras(String[] palabras) {
        this.palabras = palabras;
    }
    
    //Para que te devuelva una palabra al azar
    public String getPalabra(int i){
        return palabras[i];
    }
}

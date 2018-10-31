/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author mdfda
 */
public class VentanaPrincipal extends JFrame implements ActionListener {

    private final String[] rutas;
    private TitledBorder titulo;
    private final JLabel jug1, jug2, resuelto, imagen, letrasUsadas, cartelLetrasUsadas;
    private JTextField letra;
    private final JPasswordField palabra;
    private final JPanel panel1, panel2, jugador1, jugador2, botonesJug1, principal;
    private final JButton jug1Button, generarPalabra, jug2Button;
    private final ImageIcon[] ahorcado;
    private int intentos;
    private char[] adivinar, adivinado;

    public VentanaPrincipal(String[] rutas) {
        this.rutas = rutas;
        this.ahorcado = new ImageIcon[rutas.length];
        setTitle("Ahorcado");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        intentos = 0;
        resuelto = new JLabel();
        letrasUsadas = new JLabel("");
        cartelLetrasUsadas = new JLabel("LETRAS USADAS: ");

        //Inicializar paneles
        panel1 = new JPanel(new BorderLayout());
        panel2 = new JPanel(new GridLayout(2, 1)); //dos filas, una para cada jugador
        jugador1 = new JPanel(new GridLayout(3, 0, 10, 10));  //tres filas para los diferentes elementos
        jugador2 = new JPanel(new GridLayout(6, 0, 10, 10)); //tres filas para los diferentes elementos
        botonesJug1 = new JPanel(new GridLayout(0, 2));
        principal = new JPanel(new GridLayout(1, 2)); //dos columnas, una para cada panel

        //Añadir panel donde se añadirán el resto a la ventana
        add(principal);

        //Añadir paneles
        principal.add(panel1);
        principal.add(panel2);
        panel2.add(jugador1);
        panel2.add(jugador2);

        //Rellenar el array de ahorcado con las imagenes que se encuentran en las rutas
        for (int i = 0; i < rutas.length; i++) {
            ahorcado[i] = new ImageIcon(getClass().getResource(rutas[i]));
        }

        //Añadir la imagen inicial al JLabel y añadirla al panel 1
        imagen = new JLabel();
        imagen.setIcon(ahorcado[intentos]);
        panel1.add(imagen);

        //Iniciar y añadir elementos al panel jugador1
        jug1 = new JLabel("Introduce la palabra a adivinar: ");
        palabra = new JPasswordField();
        jug1Button = new JButton("Aceptar");
        generarPalabra = new JButton("Palabra al azar");
        titulo = BorderFactory.createTitledBorder("Jugador 1");
        jugador1.setBorder(titulo);
        jugador1.add(jug1);
        jugador1.add(palabra);
        botonesJug1.add(jug1Button);
        botonesJug1.add(generarPalabra);
        jugador1.add(botonesJug1);

        //Iniciar y añadir elementos al panel jugador2
        jug2 = new JLabel("Introduce una letra: ");
        letra = new JTextField();

        //Añadir a letra un KeyListener para evitar que se introduzcan más carácteres de los necesarios
        letra.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //El cero representa un único caracter (en concreto es el índice 0 en el String de letra)
                if (letra.getText().length() > 0) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });

        jug2Button = new JButton("Validar letra");
        titulo = BorderFactory.createTitledBorder("Jugador 2");
        jugador2.setBorder(titulo);
        jugador2.add(jug2);
        jugador2.add(letra);
        jugador2.add(jug2Button);
        jugador2.add(resuelto);
        jugador2.add(cartelLetrasUsadas);
        jugador2.add(letrasUsadas);

        //Deshabilitar elementos jugador2
        cambiar(2, false);

        //Añadir eventos a los botones
        jug1Button.addActionListener(this);
        generarPalabra.addActionListener(this);
        jug2Button.addActionListener(this);
    }

    /**
     * Método para activar y desactivar, en función de los parámetros, los
     * elementos de las paneles jugador 1 y 2
     *
     * @param jugador = para saber el panel sobre el que actuar
     * @param accion = la acción a llevar a cabo (activar/desativar)
     */
    private void cambiar(int jugador, Boolean accion) {
        switch (jugador) {
            case 1:
                palabra.setEditable(accion);
                jug1Button.setEnabled(accion);
                generarPalabra.setEnabled(accion);
                break;
            case 2:
                letra.setEditable(accion);
                jug2Button.setEnabled(accion);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i;
        if (e.getSource() == jug1Button || e.getSource() == generarPalabra) {
            if (e.getSource() == jug1Button) {
                adivinar = palabra.getPassword();
            } else {
                adivinar = Controlador.obtenerPalabra();
            }

            //Para evitar que con cadenas vacias haga algo (solo en el caso de introducirlas en el TextField)
            if (adivinar.length != 0) {
                //Variable donde se guardarán los carácteres resueltos
                adivinado = new char[adivinar.length];
                //Tranformar carácteres en minúsculas (primero el array se hace String y despúes al revés)
                adivinar = (String.valueOf(adivinar).toLowerCase()).toCharArray();
                //Setear JLabel resultado y adivinado con guiones bajos (con la medida del array de char adivinar)
                for (i = 0; i < adivinar.length; i++) {
                    adivinado[i] = '_';
                }
                //Se añade adivinado (que son todo guiones bajos) a resuelto. A parte, se separan los guiones bajos
                resuelto.setText(String.valueOf(adivinado).replaceAll("", " "));

                //Asignar fuente y color al JLabel resultado
                resuelto.setFont(new Font("Arial", Font.BOLD, 18));
                resuelto.setForeground(Color.red);

                //Deshabilitar jugador 1, habilitar jugador 2
                cambiar(1, false);
                cambiar(2, true);
            }
        }

        if (e.getSource() == jug2Button) {
            if(letra.getText().length() > 0){
                Boolean acierto = false;
                //Trasformar la letra introducida en un caracter en minúscula
                char letraEscrita = Character.toLowerCase(letra.getText().charAt(0));

                //Si la letra introducida esta en la palabra a adivinar guárdala en adivinado
                for (i = 0; i < adivinar.length; i++) {
                    if (letraEscrita == adivinar[i]) {
                        adivinado[i] = adivinar[i];
                        acierto = true;
                    }
                }

                //Setear el JLabel resultado introduciendo espacios en el array de carácteres para conservar el formato visual
                resuelto.setText(String.valueOf(adivinado).replaceAll("", " "));

                //si ha fallado se cuenta el intento y se cambia la imagen
                if (!acierto) {
                    intentos++;
                    if(!letrasUsadas.getText().contains(String.valueOf(letra.getText()).toUpperCase())){
                        letrasUsadas.setText(letrasUsadas.getText() + " " + letra.getText().toUpperCase() + " |");
                    }
                    imagen.setIcon(ahorcado[intentos]);
                    //Si ha agotado los intentos, termina
                    if (intentos == 5) {
                        terminar("HAS AGOTADO LOS 5 INTENTOS, HAS PERDIDO :(.\nLA PALABRA ERA: " + String.valueOf(adivinar).toUpperCase());
                    }
                } else {
                    //Si ha terminado de rellenar la palabra y coincide, ha ganado
                    if (String.valueOf(adivinado).equals(String.valueOf(adivinar))) {
                        terminar("¡ENHORABUENA, HAS ACERTADO!");
                    }
                }
                letra.setText("");
            }
        }
    }

    /**
     * Método para mostrar un mensaje cuando el jugador 2 gane o pierda y
     * terminar con su turno
     *
     * @param mensaje = mensaje que se va a mostrar en un diálogo
     */
    private void terminar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
        intentos = 0;
        imagen.setIcon(ahorcado[intentos]);
        resuelto.setText("");
        palabra.setText("");
        letrasUsadas.setText("");
        cambiar(2, false);
        cambiar(1, true);
    }
}

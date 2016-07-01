/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hundirlaflota;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class Tablero extends JPanel implements MouseMotionListener, MouseListener {

    /**
     * Atributos de la clase Tablero.
     */
    public static final int MAXIMO = 600;
    public static final int DIMENSION = 10;
    private final int LADO = MAXIMO / DIMENSION; //10 Casillas de tablero(medida lado de cada casilla)
    private Casilla t[][];
    public static int barcos[] = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};//Array longitud de los barcos.

    private static int indiceBarco = 0;//Esta variable se utiliza como indice al recorres la array de barcos del tablero jugador
    private static Boolean barcosJugadorColocados = false;
    private static Boolean desactivarTableroJugador = false;
    private static int contCPU = 0;
    private static int contJugador = 0;
    private static Boolean finDelJuego = false;

    private Color c;
/**
 * En el constructor de la clase tablero se pintan las casillas del tablero.
 */
    public Tablero() {
        t = new Casilla[DIMENSION][DIMENSION];

        int y = 0;
        for (int i = 0; i < DIMENSION; i++) {
            int x = 0;
            for (int j = 0; j < DIMENSION; j++) {

                Rectangle2D.Float r
                        = new Rectangle2D.Float(x, y, LADO, LADO);

                t[i][j] = new Casilla(r, false);

                x += LADO;

            }
            y += LADO;
        }

    }

    /**
     * Este método genera 'X','Y' y una posicion aleatoria. Seguidamente
     * comprueba si desde esa posición el barco cabe y no hay barcos cerca. Si
     * se cumplen las condiciones añade el barco .
     *
     * @param largariaBarco: Pasamos por parámetro la largaria del barco que
     * vamos a colocar del CPU.
     */
    public void colocarCPU(int largariaBarco) {
        Boolean colocado = false;
        while (!colocado) {

            Random rnd = new Random();
            int X = rnd.nextInt(10);
            int Y = rnd.nextInt(10);
            int posicion = rnd.nextInt(2);
            /**
             * Si la posicion = 1, sera vertical. Si la posicion = 0, sera
             * horizontal.
             */

            if (barcoCabe(X, Y, posicion, largariaBarco)) {

                if (!barcoCerca(Y, X, posicion, largariaBarco)) {
                    colocado = true;
                    if (posicion == 0) {

                        for (int i = 0; i < largariaBarco; i++) {

                            t[Y][X + i].ponerBarco();

                        }

                    }

                    if (posicion == 1) {

                        for (int i = 0; i < largariaBarco; i++) {

                            t[Y + i][X].ponerBarco();

                        }

                    }

                }
            }

        }
    }

    /**
     * Este método genera 'X','Y' aleatorias. Si las coordenadas generadas ya
     * han sido disparades, se vuelve a generar el random. Seguidamente se
     * comprueba si la casilla objetivo esta ocupada. Si esta ocupada se pinta
     * de color rojo y se aumenta el contador de puntos del CPU.
     */
    public void disparoCPU() {
        Boolean exito = false;

        while (!exito) {
            Random rnd = new Random();
            int X, Y;
            do {
                X = rnd.nextInt(10);
                Y = rnd.nextInt(10);
            } while (t[X][Y].getTiro());

            t[X][Y].setTiro();
            if (t[X][Y].getOcupada()) {
                t[X][Y].setColorRed();
                contCPU += 1;

            } else {
                t[X][Y].setColorBlue();
                exito = true;

            }
            repaint();

        }
    }

    /**
     *
     * @param X: Coordenada X.
     * @param Y: Coordenada Y.
     * @param posicion: Si la posicion = 1, sera vertical. Si la posicion = 0,
     * sera horizontal.
     * @param largariaBarco
     * @return: Devuelve true si cabe en el tablero.
     */
    private Boolean barcoCabe(int X, int Y, int posicion, int largariaBarco) {

        if (posicion == 0) {

            if (X + largariaBarco <= DIMENSION) {

                return true;

            }

        }
        if (posicion == 1) {
            if (Y + largariaBarco <= DIMENSION) {

                return true;

            }

        }
        return false;
    }

    /**
     *
     * @param Y: Coordenada Y.
     * @param X: Coordenada X.
     * @param posicion: Si la posicion = 1, sera vertical. Si la posicion = 0,
     * sera horizontal.
     * @param largariaBarco
     * @return: Si hay un barco adyacente devuelve true.
     */
    private Boolean barcoCerca(int Y, int X, int posicion, int largariaBarco) {
        Boolean auxiliar[][] = new Boolean[12][12];
        Boolean cerca = false;
        //copiamos el tablero en una array secundaria:

        for (int i = 0; i < auxiliar.length; i++) {

            for (int j = 0; j < auxiliar.length; j++) {

                auxiliar[i][j] = false;

            }

        }
        for (int i = 0; i < t.length; i++) {

            for (int j = 0; j < t.length; j++) {

                auxiliar[i + 1][j + 1] = t[i][j].getOcupada();

            }

        }

        //En este punto ya tenemos la array secundaria con la informacion de la primera.
        if (posicion == 0) {

            for (int i = 0; i < 3 && cerca == false; i++) {

                for (int j = 0; j < largariaBarco + 2 && cerca == false; j++) {
                    cerca = auxiliar[Y + i][X + j];

                }

            }

        }
        if (posicion == 1) {
            for (int i = 0; i < largariaBarco + 2 && cerca == false; i++) {

                for (int j = 0; j < 3 && cerca == false; j++) {

                    cerca = auxiliar[Y + i][X + j];


                }

            }

        }
        if (cerca == true) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Método que pinta los cuadrados del tablero.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                t[i][j].paintComponent(g);
            }
        }
    }

    /**
     *
     * @param X: Coordenada X.
     * @param Y: Coordenada Y.
     * @return: Si las coordenadas se encuentran en una casilla ya disparada
     * devuelve false o si hemos acertado el tiro. De esta forma volvemos a tener el turno.
     */
    public Boolean dispararACpu(int X, int Y) {

        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {

                if (t[i][j].getContiene(X, Y)) {

                    if (t[i][j].getTiro()) {

                        return false;
                    }

                    t[i][j].setTiro();

                    if (t[i][j].getOcupada()) {
                        HundirLaFlota.mensajeTexto = "TOCADO";

                        t[i][j].setColorRed();

                        contJugador += 1;
                        return false;
                    } else {
                        t[i][j].setColorBlue();
                        HundirLaFlota.mensajeTexto = "AGUA";
                    }

                }

            }

        }
        repaint();
        return true;

    }

    /**
     *
     * @param X: Coordenada X.
     * @param Y: Coordenada Y.
     * @param posicion: Si la posicion = 1, sera vertical. Si la posicion = 0,
     * sera horizontal.
     */
    public void colocarBarcoJugador(int X, int Y, int posicion) {

        Boolean haChocado = true;

        for (int i = 0; i < t.length && !barcosJugadorColocados; i++) {

            for (int j = 0; j < t.length; j++) {

                if (t[i][j].getContiene(X, Y)) {

                    Boolean cabe = this.barcoCabe(j, i, posicion, barcos[indiceBarco]);

                    if (cabe) {
                        haChocado = barcoCerca(i, j, posicion, barcos[indiceBarco]);
                    }
                    if ((posicion == 1) && (i + barcos[indiceBarco] <= 10) && (!haChocado) && (cabe)) {

                        for (int z = 0; z < barcos[indiceBarco]; z++) {

                            t[i + z][j].setOcupada();
                            t[i + z][j].setColorLightGray();

                        }

                    }

                    if ((posicion == 0) && (j + barcos[indiceBarco] <= 10) && (!haChocado) && (cabe)) {

                        for (int z = 0; z < barcos[indiceBarco]; z++) {

                            t[i][j + z].setOcupada();
                            t[i][j + z].setColorLightGray();

                        }

                    }

                    if (indiceBarco == barcos.length - 1) {//Si hemos colocado todos los barcos
                        indiceBarco = 0;//Reiniciar puntero de los barcos
                        this.barcosJugadorColocados = true;

                    } else {
                        if (!haChocado) {
                            indiceBarco += 1;
                        }
                    }

                    repaint();

                }

            }
        }

    }

    /**
     * Pinta de color gris la solución del tablero del CPU. Si una casilla ha
     * sido disparada la repinta de rojo.
     */
    public void respuestaCPU() {

        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {

                if (t[i][j].getOcupada()) {

                    if (t[i][j].getTiro()) {
                        t[i][j].setColorRed();

                    } else {
                        t[i][j].setColorDarkGray();

                    }

                }

            }
        }

        repaint();

    }

    /**
     * Este método reinicializa todas las casillas. Hacemos un recorrido de
     * todas las casillas del tablero y ejecutamos el metodo
     * 'reiniciarCasilla()' de la clase Casilla.
     */
    public void reiniciarJuego() {

        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                t[i][j].reiniciarCasilla();

            }
        }

        contJugador = 0;
        contCPU = 0;
        indiceBarco = 0;
        finDelJuego = false;
        barcosJugadorColocados = false;
        desactivarTableroJugador = false;
        repaint();
    }

   

    /**
     * Todos los Gets y los Sets.
     */
    public int getIndiceBarco() {
        return indiceBarco;
    }

    public Boolean getBarcosJugadorColocados() {
        return barcosJugadorColocados;
    }

    public Boolean getDesactivarTableroJugador() {
        return desactivarTableroJugador;
    }

    public void setDesactivarTableroJugador() {
        desactivarTableroJugador = true;
    }

    public int getContCPU() {
        return contCPU;
    }

    public int getContJugador() {
        return contJugador;
    }

    public Boolean getFinDelJuego() {
        return finDelJuego;
    }

    public void setFinDelJuego() {

        this.finDelJuego = true;
    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseMoved(MouseEvent me) {

    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hundirlaflota;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author GeeKTM
 */
public class Casilla {

    private Rectangle2D.Float rec;
    private Color col;
    private Boolean ocupada;
    private Boolean tiro;
    private Boolean comprobacionJugador;

    public Casilla(Rectangle2D.Float r, Boolean ocupada) {
        this.rec = r;
        this.ocupada = false;
        this.col = calcularAzul();
        this.tiro = false;

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.col);
        g2d.fill(this.rec);
        g2d.setColor(Color.BLACK);
        g2d.draw(this.rec);
        if (this.ocupada) {

        }
    }

    /**
     * Reinicia la casilla con un azul aleatorio, la desocupa y borra los tiros.
     */
    public void reiniciarCasilla() {
        this.col = this.calcularAzul();
        this.ocupada = false;
        this.tiro = false;

    }

    /**
     * Pone la casilla en ocupado.
     */
    public void ponerBarco() {
        ocupada = true;
    }

    /**
     * Pone la casilla en disparada.
     */
    public void setTiro() {
        tiro = true;

    }

    /**
     *
     * @return: Casilla ocupada.
     */
    public Boolean getOcupada() {

        return this.ocupada;
    }

    /**
     * Pone la casilla en ocupada.
     */
    public void setOcupada() {
        this.ocupada = true;
    }

    /**
     *
     * @return: Casilla disparada.
     */
    public Boolean getTiro() {
        return this.tiro;
    }

    

    /**
     * Pone la casilla de color Gris oscuro.
     */
    public void setColorDarkGray() {

        col = Color.DARK_GRAY;
    }

    public void setColorRed() {

        col = Color.RED;
    }

    /**
     * Calcula un azul aleatorio para pintar el fondo del tablero.
     *
     * @return
     */
    private Color calcularAzul() {
        Color c;
        float azul = (float) Math.random();

        while (azul < 0.4) {

            azul = (float) Math.random();

        }
        return c = new java.awt.Color(0, 0, azul);

    }

    /**
     * Pone la casilla de color Cyan.
     */
    public void setColorBlue() {
        col = Color.CYAN;
    }

    /**
     * Pone la casilla de color Gris.
     */
    public void setColorLightGray() {
        col = Color.LIGHT_GRAY;
    }

    /**
     *
     * @param X: Coordenada X(Pixeles).
     * @param Y: Coordenada Y(Pixeles).
     * @return
     */
    public Boolean getContiene(int X, int Y) {
        if (this.rec.contains(X, Y)) {
            return true;
        }
        return false;
    }

}

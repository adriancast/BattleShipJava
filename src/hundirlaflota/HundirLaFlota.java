
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hundirlaflota;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author ajf034
 */
public class HundirLaFlota extends JFrame implements MouseMotionListener, MouseListener {

    // Atributos
    private JMenuBar jmbarEditor;
    private JMenu jmnuOpciones;
    private JMenuItem jmItemSol;
    private JMenuItem jmItemRestart;
    private JMenuItem jmItemExit;
    private JButton jbJuega;
    private JTextField jtInformacion;

    public static String mensajeTexto = "Para jugar introduce:1 barco de 4 casillas - 2 barcos de 3 casillas - 3 barcos de 2 casillas - 4 barcos de 1 casillas. Seguidamente pulsa ''Juega!''"
            + "                             Con el click izquierdo se colocan en horizontal, con el derecho en vertical  ";

    private Tablero tab1;
    private Tablero tab2;

    public HundirLaFlota() {
        this.setSize(Tablero.MAXIMO * 2 + 88, Tablero.MAXIMO + 130);
        this.setTitle("Hundir la flota");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {

        // Creacion de las componentes
        this.jtInformacion = new JTextField(mensajeTexto);
        this.jbJuega = new JButton();
        this.jmbarEditor = new JMenuBar();
        this.jmnuOpciones = new JMenu();
        this.jmItemSol = new JMenuItem();
        this.jmItemRestart = new JMenuItem();
        this.jmItemExit = new JMenuItem();

        final Tablero tablero = new Tablero();//objeto para utilizar los Get y Set.
        this.tab1 = new Tablero();
        this.tab1.addMouseMotionListener(this);
        this.tab1.addMouseListener(this);
        this.tab2 = new Tablero();
        this.tab2.addMouseMotionListener(this);
        this.tab2.addMouseListener(this);

        //Ponemos Layout en null
        this.getContentPane().setLayout(null);

        // Añadimos al JFrame las componentes
        this.jmItemSol.setText("Solución     (ALT+S)");
        this.jmItemSol.setMnemonic('S');
        this.jmItemRestart.setText("Reiniciar      (ALT+R)");
        this.jmItemRestart.setMnemonic('R');
        this.jmItemExit.setText("Exit             (ALT+E)");
        this.jmItemExit.setMnemonic('E');

        this.jmnuOpciones.setText("Options");
        this.jmnuOpciones.add(jmItemSol);
        this.jmnuOpciones.add(jmItemRestart);
        this.jmnuOpciones.add(jmItemExit);

        this.jmbarEditor.add(jmnuOpciones);

        this.getContentPane().add(this.jbJuega);
        this.jbJuega.setBounds(595, 615, 92, 25);
        this.jbJuega.setLabel("Juega!");

        this.getContentPane().add(this.jtInformacion);
        this.jtInformacion.setBounds(0, 650, 1300, 35);
        this.jtInformacion.setEditable(false);

        this.setJMenuBar(this.jmbarEditor);
        //Añadimos los paneles
        this.tab1.setBounds(10, 10, Tablero.MAXIMO + 1, Tablero.MAXIMO + 1);
        this.getContentPane().add(this.tab1);
        for (int i = 0; i < Tablero.barcos.length; i++) {
            tab1.colocarCPU(Tablero.barcos[i]);
        }

        this.tab2.setBounds((Tablero.MAXIMO + 1) + 70, 10, Tablero.MAXIMO + 1, Tablero.MAXIMO + 1);
        this.getContentPane().add(this.tab2);

        //Listener del Button:
        this.jbJuega.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                if (tablero.getBarcosJugadorColocados()) {
                    tablero.setDesactivarTableroJugador();
                    jbJuega.setLabel("Jugando!");

                }

            }
        });

        // Listener a cada MenuItem
        this.jmItemSol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tab1.respuestaCPU();
            }
        });

        this.jmItemRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                tab1.reiniciarJuego();//pongo todas las casillas en el estado original
                tab2.reiniciarJuego();

                for (int i = 0; i < Tablero.barcos.length; i++) {
                    tab1.colocarCPU(Tablero.barcos[i]);
                }
                jbJuega.setLabel("Juega!");

                jtInformacion.setText("Para jugar introduce:1 barco de 4 casillas - 2 barcos de 3 casillas - 3 barcos de 2 casillas - 4 barcos de 1 casillas. Seguidamente pulsa ''Juega!''"
                        + "                             Con el click izquierdo se colocan en horizontal, con el derecho en vertical  ");

            }
        });

        this.jmItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmItemExitActionPerformed(evt);
            }
        });

    }

    private void jmItemExitActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    public static void main(String[] args) {
        HundirLaFlota p = new HundirLaFlota();
        p.setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    /**
     * Cuando soltemos el click, comprobaremos en que componente se ha
     * realizado. Seguidamente lo trataremos según el estado del juego.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        Tablero tablero = new Tablero();
        /**
         * Mientras no haya acabado el juego:
         */
        if (!tablero.getFinDelJuego()) {
            Component comp = e.getComponent();

            if (!tablero.getDesactivarTableroJugador()) {
                if (comp == tab2) {
                    int X;
                    int Y;
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        tab2.colocarBarcoJugador(X = e.getX(), Y = e.getY(), 0);
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) {

                        tab2.colocarBarcoJugador(X = e.getX(), Y = e.getY(), 1);

                    }

                }
            }
            if (tablero.getBarcosJugadorColocados() && tablero.getDesactivarTableroJugador()) {

                if (comp == tab1) {
                    int X = 0;
                    int Y = 0;
                    /**
                     * Hasta que no acabe nuestro ataque no dejamos disparar al
                     * CPU
                     */
                    if (tab1.dispararACpu(X = e.getX(), Y = e.getY())) {
                        /**
                         * Si la casilla que hemos clicado no tiene un tiro, el
                         * cpu dispara
                         */

                        tab2.disparoCPU();
                    }

                    this.jtInformacion.setText(this.mensajeTexto);
                    repaint();

                }

            }

            /**
             * En este momento se comprueba si ha acabado la partida. Si es el
             * caso se activa el fin del juego y se muestra por pantalla una
             * ventana que muestra el nombre del ganador
             */
            if (tablero.getContCPU() == 20) {
                tablero.setFinDelJuego();
                this.jtInformacion.setText("Fin del juego");
                JOptionPane.showOptionDialog(this, "Ha ganado el CPU", "Ganador de la partida", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "}, "OK");
            }

            if (tablero.getContJugador() == 20) {
                tablero.setFinDelJuego();
                this.jtInformacion.setText("Fin del juego");
                JOptionPane.showOptionDialog(this, "Ha ganado el jugador", "Ganador de la partida", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "}, "OK");
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }
}
